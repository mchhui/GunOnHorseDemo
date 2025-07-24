package mchhui.gunonhorse.util;

import mchhui.gunonhorse.capability.ModCapabilities;
import mchhui.gunonhorse.capability.IHorseSaddleGunBag;
import mchhui.gunonhorse.network.NetworkHandler;
import mchhui.gunonhorse.network.PacketSyncHorseSaddleGunBag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.PacketDistributor;

/**
 * 马鞍包辅助工具类
 */
public class HorseSaddleGunBagHelper {

    /**
     * 设置马的马鞍包并同步到客户端
     * @param horse 马实体
     * @param saddleGunBag 马鞍包物品
     */
    public static void setHorseSaddleGunBag(LivingEntity horse, ItemStack saddleGunBag) {
        horse.getCapability(ModCapabilities.HORSE_SADDLE_GUN_BAG).ifPresent(cap -> {
            cap.setSaddleGunBag(saddleGunBag);
            // 如果是服务端，同步到客户端
            if (!horse.level().isClientSide()) {
                NetworkHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> horse), new PacketSyncHorseSaddleGunBag(horse.getId(), saddleGunBag));
            }
        });
    }

    public static void syncHorseSaddleGunBag(LivingEntity horse, ServerPlayer player) {
        horse.getCapability(ModCapabilities.HORSE_SADDLE_GUN_BAG).ifPresent(cap -> {
            NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new PacketSyncHorseSaddleGunBag(horse.getId(), cap.getSaddleGunBag()));
        });
    }
    public static void broadcastHorseSaddleGunBag(LivingEntity horse) {
        horse.getCapability(ModCapabilities.HORSE_SADDLE_GUN_BAG).ifPresent(cap -> {
            NetworkHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> horse), new PacketSyncHorseSaddleGunBag(horse.getId(), cap.getSaddleGunBag()));
        });
    }

    /**
     * 获取马的马鞍包
     * @param horse 马实体
     * @return 马鞍包物品
     */
    public static ItemStack getHorseSaddleGunBag(LivingEntity horse) {
        return horse.getCapability(ModCapabilities.HORSE_SADDLE_GUN_BAG).map(IHorseSaddleGunBag::getSaddleGunBag).orElse(ItemStack.EMPTY);
    }

    /**
     * 检查马是否有马鞍包
     * @param horse 马实体
     * @return 是否有马鞍包
     */
    public static boolean hasHorseSaddleGunBag(LivingEntity horse) {
        return horse.getCapability(ModCapabilities.HORSE_SADDLE_GUN_BAG).map(IHorseSaddleGunBag::hasSaddleGunBag).orElse(false);
    }
}