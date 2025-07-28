package mchhui.gunonhorse.client.gui;

import mchhui.gunonhorse.ModGunOnHorse;
import mchhui.gunonhorse.item.SaddleGunBagContainer;
import mchhui.gunonhorse.network.NetworkHandler;
import mchhui.gunonhorse.network.PacketAskBack;
import mchhui.gunonhorse.network.PacketTakeOff;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SaddleGunBagContainerScreen extends AbstractContainerScreen<SaddleGunBagContainer> {
    private static final ResourceLocation CONTAINER_BACKGROUND = ResourceLocation.fromNamespaceAndPath(ModGunOnHorse.MODID, "textures/gui/saddle_gun_bag.png");
    private static final ResourceLocation UNEQUIP_ICON = ResourceLocation.fromNamespaceAndPath(ModGunOnHorse.MODID, "textures/gui/unequip.png");
    private static final ResourceLocation GOBACK_ICON = ResourceLocation.fromNamespaceAndPath(ModGunOnHorse.MODID, "textures/gui/goback.png");

    public SaddleGunBagContainerScreen(SaddleGunBagContainer container, Inventory inv, Component component) {
        super(container, inv, component);
    }

    public void render(GuiGraphics p_281745_, int p_282145_, int p_282358_, float p_283566_) {
        this.renderBackground(p_281745_);
        super.render(p_281745_, p_282145_, p_282358_, p_283566_);
        this.renderTooltip(p_281745_, p_282145_, p_282358_);
    }

    protected void renderBg(GuiGraphics p_281362_, float p_283080_, int p_281303_, int p_283275_) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        p_281362_.blit(CONTAINER_BACKGROUND, i, j, 0, 0, this.imageWidth, this.imageHeight);
    }

    private void sendAskTakeOffTheBag() {
        NetworkHandler.INSTANCE.sendToServer(new PacketTakeOff());
    }

    private void sendAskOpenTheHorseGui() {
        NetworkHandler.INSTANCE.sendToServer(new PacketAskBack());
    }

    @Override
    protected void init() {
        super.init();
        // 计算右上角按钮位置
        int btnWidth = 20;
        int btnHeight = 20;
        int spacing = 4;
        int x1 = this.leftPos + this.imageWidth - btnWidth * 2 - spacing * 2;
        int x2 = this.leftPos + this.imageWidth - btnWidth - spacing;
        int y = this.topPos + spacing;
        Minecraft mc = Minecraft.getInstance();
        if (mc.player.getVehicle() != null) {
            // 卸下包按钮
            this.addRenderableWidget(new IconButton(x1, y, btnWidth, btnHeight, UNEQUIP_ICON, 16, 16, () -> sendAskTakeOffTheBag()));
            // 打开马GUI按钮
            this.addRenderableWidget(new IconButton(x2, y, btnWidth, btnHeight, GOBACK_ICON, 16, 16, () -> sendAskOpenTheHorseGui()));
        }
    }
}
