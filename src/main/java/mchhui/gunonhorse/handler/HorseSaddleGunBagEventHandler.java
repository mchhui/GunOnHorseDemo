package mchhui.gunonhorse.handler;

import mchhui.gunonhorse.capability.ModCapabilities;
import mchhui.gunonhorse.item.ItemSaddleGunBag;
import mchhui.gunonhorse.item.SaddleGunBagContainerProvider;
import mchhui.gunonhorse.item.SaddleGunBagContainer.OpenFrom;
import mchhui.gunonhorse.util.HorseSaddleGunBagHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.HorseInventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.StartTracking;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkHooks;

import java.util.HashSet;
import java.util.UUID;

import mchhui.gunonhorse.ModGunOnHorse;

/**
 * 马鞍包事件处理器
 */
public class HorseSaddleGunBagEventHandler {
    public static HashSet<UUID> openHorseGuiPlayers=new HashSet<>();
    
    /**
     * 处理玩家与马的交互，装备马鞍包
     */
    @SubscribeEvent
    public static void onPlayerInteractEntity(PlayerInteractEvent.EntityInteract event) {
        if(event.getEntity().level().isClientSide()) {
            return;
        }
        if (event.getTarget() instanceof AbstractHorse horse) {
            if(!horse.isTamed()) {
                return;
            }
            ItemStack heldItem = event.getEntity().getItemInHand(event.getHand());
            
            // 如果玩家手持马鞍包
            if (heldItem.getItem() instanceof ItemSaddleGunBag) {
                // 检查马是否已经有马鞍包
                if (!HorseSaddleGunBagHelper.hasHorseSaddleGunBag(horse)) {
                    // 装备马鞍包
                    HorseSaddleGunBagHelper.setHorseSaddleGunBag(horse, heldItem.copy());
                    
                    // 消耗玩家手中的马鞍包
                    heldItem.shrink(1);
                    horse.playSound(SoundEvents.ARMOR_EQUIP_LEATHER);
                    event.setCanceled(true);
                }
            }
        }
    }
    
    @SubscribeEvent
    public static void onPlayerOpenHorseGui(PlayerContainerEvent.Open event) {
        if(event.getEntity().level().isClientSide()) {
            return;
        }
//        System.out.println(event.getContainer());
        if(event.getContainer() instanceof HorseInventoryMenu) {
            if(openHorseGuiPlayers.contains(event.getEntity().getUUID())) {
                openHorseGuiPlayers.remove(event.getEntity().getUUID());
                return;
            }
            if(event.getEntity().getVehicle() instanceof AbstractHorse) {
                event.getEntity().getVehicle().getCapability(ModCapabilities.HORSE_SADDLE_GUN_BAG).ifPresent((cap)->{
                    if(cap.hasSaddleGunBag()) {
                        NetworkHooks.openScreen((ServerPlayer)event.getEntity(), new SaddleGunBagContainerProvider(cap.getSaddleGunBag(),(AbstractHorse)event.getEntity().getVehicle()), (buf)->{
                            buf.writeEnum(OpenFrom.HORSE);
                            buf.writeItem(cap.getSaddleGunBag());
                            buf.writeVarInt(event.getEntity().getVehicle().getId());
                        });    
                    }
                });
            }
        }
    }
    
    @SubscribeEvent
    public static void onTrackStart(StartTracking event) {
        if(event.getEntity().level().isClientSide()) {
            return;
        }
        if (event.getTarget() instanceof AbstractHorse horse) {
            HorseSaddleGunBagHelper.syncHorseSaddleGunBag(horse, (ServerPlayer)event.getEntity());
        }
    }
} 