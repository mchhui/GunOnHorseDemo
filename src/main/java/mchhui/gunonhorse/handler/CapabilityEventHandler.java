package mchhui.gunonhorse.handler;

import mchhui.gunonhorse.capability.HorseSaddleGunBagProvider;
import mchhui.gunonhorse.capability.ModCapabilities;
import mchhui.gunonhorse.network.NetworkHandler;
import mchhui.gunonhorse.network.PacketSyncHorseSaddleGunBag;
import mchhui.gunonhorse.util.HorseSaddleGunBagHelper;
import mchhui.gunonhorse.capability.IHorseSaddleGunBag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.StartTracking;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import mchhui.gunonhorse.ModGunOnHorse;

/**
 * Capability事件处理器
 */
public class CapabilityEventHandler {

    /**
     * 注册Capability
     */
    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(IHorseSaddleGunBag.class);
    }

    /**
     * 为实体附加Capability
     */
    @SubscribeEvent
    public static void onAttachCapabilitiesEntity(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof AbstractHorse) {
            event.addCapability(ModCapabilities.HORSE_SADDLE_GUN_BAG_ID, new HorseSaddleGunBagProvider());
        }
    }
}