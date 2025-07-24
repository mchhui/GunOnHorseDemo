 package mchhui.gunonhorse.block;

import mchhui.gunonhorse.ModGunOnHorse;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
     public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ModGunOnHorse.MODID);

     public static final RegistryObject<Block> SADDLE_GUN_BAG = BLOCKS.register("saddle_gun_bag", () -> new BlockSaddleGunBag(BlockBehaviour.Properties.of()));
}
