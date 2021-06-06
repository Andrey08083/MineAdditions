package ua.andrey08xtomyoll.mineadditions.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ua.andrey08xtomyoll.mineadditions.tiles.TileThermalCrusher;
import ua.andrey08xtomyoll.mineadditions.gui.containers.ContainerThermalCrusher;
import ua.andrey08xtomyoll.mineadditions.init.ModBlocks;
import ua.andrey08xtomyoll.mineadditions.util.Reference;

@SideOnly(Side.CLIENT)
public class GuiThermalCrusher extends GuiContainer
{
    private static final ResourceLocation CRUSHER_GUI_TEXTURES = new ResourceLocation(Reference.MOD_ID + ":textures/gui/thermalcrusher.png");
    private final InventoryPlayer playerInventory;
    private final IInventory tileinventory;

    /**
     * Конструктор класу
     * @param playerInv інвентар гравця
     * @param furnaceInv інвентар тайла
     */
    public GuiThermalCrusher(InventoryPlayer playerInv, IInventory furnaceInv)
    {
        super(new ContainerThermalCrusher(playerInv, furnaceInv));
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
        String name = I18n.format(ModBlocks.THERMALCRUSHER.getTranslationKey() + ".name");
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
        this.mc.getTextureManager().bindTexture(CRUSHER_GUI_TEXTURES);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

        if (TileThermalCrusher.isBurning(this.tileinventory))
        {
            int k = this.getBurnLeftScaled(13);
            this.drawTexturedModalRect(i + 58, j + 36 + 12 - k, 176, 12 - k, 14, k + 1);
        }

        int l = this.getCookProgressScaled(36);
        this.drawTexturedModalRect(i + 77, j + 39, 176, 14, l, 16);
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
            i = 200;
        }

        return this.tileinventory.getField(0) * pixels / i;
    }
}