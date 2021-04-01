package ua.andrey08xtomyoll.mineadditions.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ua.andrey08xtomyoll.mineadditions.blocks.tiles.TileAlchemyExtractor;
import ua.andrey08xtomyoll.mineadditions.gui.containers.ContainerAlchemyExtractor;
import ua.andrey08xtomyoll.mineadditions.init.ModBlocks;
import ua.andrey08xtomyoll.mineadditions.util.Reference;

@SideOnly(Side.CLIENT)
public class GuiElchemyExtractor extends GuiContainer
{
    // Текстура
    // Всё остальное со стандартного GuiFurnace
    private static final ResourceLocation EXTRACTOR_GUI_TEXTURES = new ResourceLocation(Reference.MOD_ID + ":textures/gui/alchemy_extractor.png");
    private final InventoryPlayer playerInventory;
    private final IInventory tileinventory;

    public GuiElchemyExtractor(InventoryPlayer playerInv, IInventory furnaceInv)
    {
        super(new ContainerAlchemyExtractor(playerInv, furnaceInv));
        this.playerInventory = playerInv;
        this.tileinventory = furnaceInv;
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        String name = I18n.format(ModBlocks.ALCHEMY_EXTRACTOR.getUnlocalizedName() + ".name");
        this.fontRenderer.drawString(name, 125 - this.fontRenderer.getStringWidth(name) / 2, 6, 4210752);
        this.fontRenderer.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }

    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(EXTRACTOR_GUI_TEXTURES);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
        int l;
        if (TileAlchemyExtractor.isBurning(this.tileinventory))
        {
         int k = this.getBurnLeftScaled(19); //Ширина от края gui
        this.drawTexturedModalRect( i + 29,   j + 51 - k, 176, 19 - k,  7, k);
        }

        l = this.getCookProgressScaled(64);//Ширина от края gui
        this.drawTexturedModalRect(i + 59, j + 20, 176 + 8, 18, l, 49);
    }

    private int getCookProgressScaled(int pixels)
    {
        int i = this.tileinventory.getField(2);
        int j = this.tileinventory.getField(3);
        return j != 0 && i != 0 ? i * pixels / j : 0;
    }

    private int getBurnLeftScaled(int pixels)
    {
        int i = this.tileinventory.getField(1);

        if (i == 0)
        {
            i = 500;
        }

        return this.tileinventory.getField(0) * pixels / i;
    }
}
