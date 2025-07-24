package mchhui.gunonhorse.capability;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import mchhui.gunonhorse.ModGunOnHorse;

/**
 * Mod Capability 注册类
 */
public class ModCapabilities {
    
    public static final Capability<IHorseSaddleGunBag> HORSE_SADDLE_GUN_BAG = CapabilityManager.get(new CapabilityToken<>(){});
    
    public static final ResourceLocation HORSE_SADDLE_GUN_BAG_ID = ResourceLocation.fromNamespaceAndPath(ModGunOnHorse.MODID, "horse_saddle_gun_bag");
} 