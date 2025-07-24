package mchhui.gunonhorse.item;

import org.openjdk.nashorn.api.tree.InstanceOfTree;

import mchhui.gunonhorse.ModGunOnHorse;
import mchhui.gunonhorse.item.SaddleGunBagContainer.OpenFrom;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ModGunOnHorse.MODID);

    public static final RegistryObject<Item> SADDLE_GUN_BAG = ITEMS.register("saddle_gun_bag", () -> new ItemSaddleGunBag(new Item.Properties()));

    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, ModGunOnHorse.MODID);
    public static final RegistryObject<MenuType<SaddleGunBagContainer>> SADDLE_GUN_BAG_CONTAINER = MENU_TYPES.register("saddle_gun_bag_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
        switch (data.readEnum(OpenFrom.class)) {
            case HAND:
                return new SaddleGunBagContainer(windowId, data.readItem(), inv.player, data.readEnum(InteractionHand.class), inv);
            case HORSE:
                ItemStack stack=data.readItem();
                int id=data.readVarInt();
                if ((!(inv.player.getVehicle() instanceof LivingEntity)) || inv.player.getVehicle().getId() != id) {
                    return null;
                }
                return new SaddleGunBagContainer(windowId,stack, inv.player, inv, (LivingEntity)inv.player.getVehicle());
            default:
                return null;
        }
    }));
}