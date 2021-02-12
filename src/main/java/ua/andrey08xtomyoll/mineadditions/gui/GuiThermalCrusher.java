package ua.andrey08xtomyoll.mineadditions.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ua.andrey08xtomyoll.mineadditions.blocks.tiles.ThermalCrusher;
import ua.andrey08xtomyoll.mineadditions.gui.containers.ContainerThermalCrusher;
import ua.andrey08xtomyoll.mineadditions.util.Reference;

@SideOnly(Side.CLIENT)
public class GuiThermalCrusher extends GuiContainer
{
	// Текстура
	// Всё остальное со стандартного GuiFurnace
    private static final ResourceLocation FURNACE_GUI_TEXTURES = new ResourceLocation(Reference.MOD_ID + ":textures/gui/thermalcrusher.png");
    private final InventoryPlayer playerInventory;
    private final IInventory tileFurnace;

    public GuiThermalCrusher(InventoryPlayer playerInv, IInventory furnaceInv)
    {
        super(new ContainerThermalCrusher(playerInv, furnaceInv));
        this.playerInventory = playerInv;
        this.tileFurnace = furnaceInv;
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        String s = "Thermal Crusher";//this.tileFurnace.getDisplayName().getUnformattedText();
        this.fontRenderer.drawString(s, 125 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
        this.fontRenderer.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }

    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(FURNACE_GUI_TEXTURES);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

        if (ThermalCrusher.isBurning(this.tileFurnace))
        {
            int k = this.getBurnLeftScaled(13);
            this.drawTexturedModalRect(i + 58, j + 36 + 12 - k, 176, 12 - k, 14, k + 1);
        }

        int l = this.getCookProgressScaled(36);
        this.drawTexturedModalRect(i + 77, j + 39, 176, 14, l, 16);
    }

    private int getCookProgressScaled(int pixels)
    {
        int i = this.tileFurnace.getField(2);
        int j = this.tileFurnace.getField(3);
        return j != 0 && i != 0 ? i * pixels / j : 0;
    }

    private int getBurnLeftScaled(int pixels)
    {
        int i = this.tileFurnace.getField(1);

        if (i == 0)
        {
            i = 200;
        }

        return this.tileFurnace.getField(0) * pixels / i;
    }
}