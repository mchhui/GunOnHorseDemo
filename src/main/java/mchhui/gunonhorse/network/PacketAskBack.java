package mchhui.gunonhorse.network;

import java.util.UUID;
import java.util.function.Supplier;

import mchhui.gunonhorse.handler.HorseSaddleGunBagEventHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraftforge.network.NetworkEvent;

public class PacketAskBack {
    
    public PacketAskBack() {
        // TODO Auto-generated constructor stub
    }
    
    public PacketAskBack(FriendlyByteBuf buf) {

    }

    public void encode(FriendlyByteBuf buf) {

    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            if (context.getSender() != null) {
                ServerPlayer player = context.getSender();
                if(player.getVehicle() instanceof AbstractHorse horse) {
                    if(horse.isTamed()) {
                        UUID playerUUID = player.getUUID();
                        HorseSaddleGunBagEventHandler.openHorseGuiPlayers.add(playerUUID);
                        horse.openCustomInventoryScreen(player);
                    }
                }
            }
        });
        context.setPacketHandled(true);
    }
}
