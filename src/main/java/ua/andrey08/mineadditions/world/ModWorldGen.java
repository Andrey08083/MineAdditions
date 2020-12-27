package ua.andrey08.mineadditions.world;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;
import ua.andrey08.mineadditions.init.ModBlocks;

import java.util.Random;

public class ModWorldGen implements IWorldGenerator
{
    @Override
    public void generate(Random random, int ChunkX, int ChunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
        if(world.provider.getDimension() == 0)
        {
            generateOverworld(random, ChunkX, ChunkZ, world, chunkGenerator, chunkProvider);
        }
    }

    private void generateOverworld(Random random, int ChunkX, int ChunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
        generateOre(ModBlocks.LABATIUM_ORE.getDefaultState(), world, random, ChunkX * 16, ChunkZ * 16, 8, 35, random.nextInt(7) + 4, 4);
    }
    private void generateOre(IBlockState ore, World world, Random random, int x, int z, int minY, int maxY, int size, int chances)
    {
        int deltaY = maxY - minY;
        for (int i = 0; i < chances; i++)
        {
            BlockPos pos = new BlockPos(x + random.nextInt(16), minY + random.nextInt(deltaY), z + random.nextInt(16));

            WorldGenMinable generator = new WorldGenMinable(ore, size);
            generator.generate(world, random, pos);
        }
    }
}
