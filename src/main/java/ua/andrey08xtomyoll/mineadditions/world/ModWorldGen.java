package ua.andrey08xtomyoll.mineadditions.world;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;
import ua.andrey08xtomyoll.mineadditions.handlers.ConfigHandler;
import ua.andrey08xtomyoll.mineadditions.init.ModBlocks;

import java.util.Random;

/**
 * Клас генерації руди в світі
 */
public class ModWorldGen implements IWorldGenerator {

    /**
     * Метод генерації для кожного чанку
     * @param random рандом, різний для кожного чанку
     * @param ChunkX координати чанку по осі Х
     * @param ChunkZ координати чанку по осі Z
     * @param world світ, в якому відбувається генерація
     * @param chunkGenerator об'єкт, який наслідує інтерфейс генератору чанків
     * @param chunkProvider об'єкт, який наслідує інтерфейс постачальнику чанків
     */
    @Override
    public void generate(Random random, int ChunkX, int ChunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        if (world.provider.getDimension() == 0) {
            generateOverworld(random, ChunkX, ChunkZ, world, chunkGenerator, chunkProvider);
        }
    }

    /**
     * Метод генерації руди для верхнього світу
     * @param random рандом, різний для кожного чанку
     * @param ChunkX координати чанку по осі Х
     * @param ChunkZ координати чанку по осі Z
     * @param world світ, в якому відбувається генерація
     * @param chunkGenerator об'єкт, який наслідує інтерфейс генератору чанків
     * @param chunkProvider об'єкт, який наслідує інтерфейс постачальнику чанків
     */
    private void generateOverworld(Random random, int ChunkX, int ChunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        generateOre(ModBlocks.LABATIUM_ORE.getDefaultState(), world, random, ChunkX * 16, ChunkZ * 16, ConfigHandler.oreGenSettings.labatiumMinHeight, ConfigHandler.oreGenSettings.labatiumMaxHeight, random.nextInt(7) + 4, ConfigHandler.oreGenSettings.labatiumPerChunkSpawn);
        generateOre(ModBlocks.TOMIUM_ORE.getDefaultState(), world, random, ChunkX * 16, ChunkZ * 16, ConfigHandler.oreGenSettings.tomiumMinHeight, ConfigHandler.oreGenSettings.tomiumMaxHeight, random.nextInt(7) + 4, ConfigHandler.oreGenSettings.tomiumPerChunkSpawn);
    }

    /**
     * Метож генерації руди в світі
     * @param ore блок руди
     * @param world світ
     * @param random рандом
     * @param x координата X чанку
     * @param z координата Z чанку
     * @param minY мінімальна координата спавну руди
     * @param maxY максимально координата спавну руди
     * @param size розмір кластеру руди
     * @param chances кількість спроб заспавнити руду в одному чанку
     */
    private void generateOre(IBlockState ore, World world, Random random, int x, int z, int minY, int maxY, int size, int chances) {
        int deltaY = Math.abs(maxY - minY);
        for (int i = 0; i < chances; i++) {
            BlockPos pos = new BlockPos(x + random.nextInt(16), minY + random.nextInt(deltaY), z + random.nextInt(16));

            WorldGenMinable generator = new WorldGenMinable(ore, size);
            generator.generate(world, random, pos);
        }
    }
}
