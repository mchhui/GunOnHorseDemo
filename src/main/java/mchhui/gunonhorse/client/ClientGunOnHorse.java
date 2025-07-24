 package mchhui.gunonhorse.client;

import java.util.function.Consumer;

import mchhui.gunonhorse.client.gui.SaddleGunBagContainerScreen;
import mchhui.gunonhorse.client.render.LayerHorseGunBag;
import mchhui.gunonhorse.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.HorseRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class ClientGunOnHorse {
    @SubscribeEvent()
    public static void onSetupClient(FMLClientSetupEvent event) {
        event.enqueueWork(() -> MenuScreens.register(ModItems.SADDLE_GUN_BAG_CONTAINER.get(), SaddleGunBagContainerScreen::new));
    }
    
    @SubscribeEvent()
    public static void onAddLayers(EntityRenderersEvent.AddLayers event) {
        Consumer<LivingEntityRenderer> c=(tar)->{
            tar.addLayer(new LayerHorseGunBag(tar));
        };
        c.accept(event.getRenderer(EntityType.HORSE));
        c.accept(event.getRenderer(EntityType.ZOMBIE_HORSE));
        c.accept(event.getRenderer(EntityType.SKELETON_HORSE));
    }
}
