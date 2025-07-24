package mchhui.gunonhorse.network;

import mchhui.gunonhorse.capability.ModCapabilities;
import mchhui.gunonhorse.capability.IHorseSaddleGunBag;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * 同步马鞍包数据包
 */
public class PacketSyncHorseSaddleGunBag {
    
    private final int entityId;
    private final ItemStack saddleGunBag;
    
    public PacketSyncHorseSaddleGunBag(int entityId, ItemStack saddleGunBag) {
        this.entityId = entityId;
        this.saddleGunBag = saddleGunBag;
    }
    
    public PacketSyncHorseSaddleGunBag(FriendlyByteBuf buf) {
        this.entityId = buf.readInt();
        this.saddleGunBag = ItemStack.of(buf.readNbt());
    }
    
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeNbt(saddleGunBag.serializeNBT());
    }
    
    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            // 客户端处理
            Minecraft mc=Minecraft.getInstance();
            if (mc.level != null) {
                Entity entity = mc.level.getEntity(entityId);
                if (entity instanceof AbstractHorse horse) {
                    horse.getCapability(ModCapabilities.HORSE_SADDLE_GUN_BAG).ifPresent(cap -> {
                        cap.setSaddleGunBag(saddleGunBag);
                    });
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
} 