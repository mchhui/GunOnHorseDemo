package mchhui.gunonhorse.network;

import java.util.function.Supplier;

import mchhui.gunonhorse.capability.ModCapabilities;
import mchhui.gunonhorse.util.HorseSaddleGunBagHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

public class PacketTakeOff {
    
    public PacketTakeOff() {
        // 空构造函数，用于创建数据包
    }
    
    public PacketTakeOff(FriendlyByteBuf buf) {
        // 从网络缓冲区读取数据（这个包不需要传输数据）
    }

    public void encode(FriendlyByteBuf buf) {
        // 编码数据到网络缓冲区（这个包不需要传输数据）
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            // 确保在服务端处理
            if (ctx.get().getSender() != null) {
                ServerPlayer player = ctx.get().getSender();
                
                // 检查玩家是否正在乘骑马
                if (player.getVehicle() instanceof AbstractHorse horse) {
                    // 检查马是否被驯服
                    if (horse.isTamed()) {
                        // 检查马是否有马鞍包
                        if (HorseSaddleGunBagHelper.hasHorseSaddleGunBag(horse)) {
                            // 获取马鞍包
                            ItemStack saddleGunBag = HorseSaddleGunBagHelper.getHorseSaddleGunBag(horse);
                            
                            // 尝试将马鞍包添加到玩家物品栏
                            if (player.getInventory().add(saddleGunBag)) {
                                // 成功添加到物品栏，移除马的马鞍包
                                HorseSaddleGunBagHelper.setHorseSaddleGunBag(horse, ItemStack.EMPTY);
                            } else {
                                // 物品栏已满，掉落马鞍包到地面
                                HorseSaddleGunBagHelper.setHorseSaddleGunBag(horse, ItemStack.EMPTY);
                                horse.spawnAtLocation(saddleGunBag);
                            }
                        }
                    }
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
