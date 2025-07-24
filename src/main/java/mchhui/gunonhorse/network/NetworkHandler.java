package mchhui.gunonhorse.network;

import mchhui.gunonhorse.ModGunOnHorse;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

/**
 * 网络处理器
 */
public class NetworkHandler {
    
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(ResourceLocation.fromNamespaceAndPath(ModGunOnHorse.MODID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );
    
    private static int packetId = 0;
    
    public static void register() {
        // 注册同步马鞍包数据包
        INSTANCE.messageBuilder(PacketSyncHorseSaddleGunBag.class, packetId++, NetworkDirection.PLAY_TO_CLIENT)
                .encoder(PacketSyncHorseSaddleGunBag::encode)
                .decoder(PacketSyncHorseSaddleGunBag::new)
                .consumerMainThread(PacketSyncHorseSaddleGunBag::handle)
                .add();
        INSTANCE.messageBuilder(PacketTakeOff.class, packetId++, NetworkDirection.PLAY_TO_SERVER)
        .encoder(PacketTakeOff::encode)
        .decoder(PacketTakeOff::new)
        .consumerMainThread(PacketTakeOff::handle)
        .add();
        INSTANCE.messageBuilder(PacketAskBack.class, packetId++, NetworkDirection.PLAY_TO_SERVER)
        .encoder(PacketAskBack::encode)
        .decoder(PacketAskBack::new)
        .consumerMainThread(PacketAskBack::handle)
        .add();
    }
} 