package mchhui.gunonhorse;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import mchhui.gunonhorse.block.ModBlocks;
import mchhui.gunonhorse.client.ClientGunOnHorse;
import mchhui.gunonhorse.handler.CapabilityEventHandler;
import mchhui.gunonhorse.handler.HorseSaddleGunBagEventHandler;
import mchhui.gunonhorse.item.ModItems;
import mchhui.gunonhorse.network.NetworkHandler;

@Mod(ModGunOnHorse.MODID)
public class ModGunOnHorse {
    public static final String MODID = "gunonhorse";
    public static final String MODNAME = "Gun on Horse";
    public static final String VERSION = "1.0.0";
    public static final String DESCRIPTION ="该mod为马添加了一个马鞍枪包，用于存在tacz的枪械。并计划支持SWEM星虫马术。";
    public static final String[] AUTHOURS=new String[] {"Hueihuea"};
    public static final String[] CREDITS=new String[] {"FML guys","TACZ Dev Team"};
    public static final String[] DEPENDENCIES=new String[] {"tacz"};
    
    public ModGunOnHorse(FMLJavaModLoadingContext context) {
        ModItems.ITEMS.register(context.getModEventBus());
        ModItems.MENU_TYPES.register(context.getModEventBus());
        ModBlocks.BLOCKS.register(context.getModEventBus());
        
        MinecraftForge.EVENT_BUS.register(CapabilityEventHandler.class);
        MinecraftForge.EVENT_BUS.register(HorseSaddleGunBagEventHandler.class);
        
        // 注册网络处理器
        context.getModEventBus().addListener(this::onCommonSetup);
        if(FMLEnvironment.dist==Dist.CLIENT) {
            context.getModEventBus().addListener(ClientGunOnHorse::onAddLayers);
            context.getModEventBus().addListener(ClientGunOnHorse::onSetupClient);  
        }
    }
    
    private void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(NetworkHandler::register);
    }
}
