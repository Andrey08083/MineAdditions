package ua.andrey08xtomyoll.mineadditions.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ua.andrey08xtomyoll.mineadditions.tiles.TileAlchemyExtractor;
import ua.andrey08xtomyoll.mineadditions.gui.containers.ContainerAlchemyExtractor;
import ua.andrey08xtomyoll.mineadditions.init.ModBlocks;
import ua.andrey08xtomyoll.mineadditions.util.Reference;

/**
 * В цьому класі малюється графічний інтерфейс тайла AlchemyExtractor
 */

@SideOnly(Side.CLIENT)
public class
GuiElchemyExtractor extends GuiContainer
{
    private static final ResourceLocation EXTRACTOR_GUI_TEXTURES = new ResourceLocation(Reference.MOD_ID + ":textures/gui/alchemy_extractor.png");
    private final InventoryPlayer playerInventory;
    private final IInventory tileinventory;

    /**
     * Конструктор класу
     * @param playerInv інвентар гравця
     * @param furnaceInv інвентар тайла
     */
    public GuiElchemyExtractor(InventoryPlayer playerInv, IInventory furnaceInv)
    {
        super(new ContainerAlchemyExtractor(playerInv, furnaceInv));
        this.playerInventory = playerInv;
        this.tileinventory = furnaceInv;
    }

    /**
     * Малює GUI
     * @param mouseX Х позиція миші
     * @param mouseY Y позиція миші
     * @param partialTicks грові тіки (необхідно для синхронізації)
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    /**
     * Малює передній план GUI
     * @param mouseX X позиція миші
     * @param mouseY Y позиція миші
     */
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        String name = I18n.format(ModBlocks.ALCHEMY_EXTRACTOR.getTranslationKey() + ".name");
        this.fontRenderer.drawString(name, 125 - this.fontRenderer.getStringWidth(name) / 2, 6, 4210752);
        this.fontRenderer.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }

    /**
     * Малює передній план GUI
     * @param partialTicks ігрові тіки (необхідно для синхронізації)
     * @param mouseX X позиція миші
     * @param mouseY Y позиція миші
     */
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(EXTRACTOR_GUI_TEXTURES);
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
        int gui_x;
        if (TileAlchemyExtractor.isBurning(this.tileinventory))
        {
         int k = this.getBurnLeftScaled(19); //Ширина від края GUI
        this.drawTexturedModalRect( x + 29,   y + 51 - k, 176, 19 - k,  7, k);
        }

        gui_x = this.getCookProgressScaled(64); //Ширина від края GUI
        this.drawTexturedModalRect(x + 59, y + 20, 176 + 8, 18, gui_x, 49);
    }

    /**
     * Розраховує, на скільки пікселів необхідно зсунути прогресбар
     * @param pixels ширина прогрессбара
     * @return на скільки пікселів необхідно зсунути
     */
    private int getCookProgressScaled(int pixels)
    {
        int i = this.tileinventory.getField(2);
        int j = this.tileinventory.getField(3);
        return j != 0 && i != 0 ? i * pixels / j : 0;
    }

    /**
     * Розраховує, на скільки пікселів необхідно зсунути прогресбар топлива
     * @param pixels ширина прогрессбара топлива
     * @return на скільки пікселів необхідно зсунути
     */
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
