package mchhui.gunonhorse.item;

import javax.annotation.Nonnull;

import com.mojang.datafixers.util.Pair;
import com.tacz.guns.api.item.IAmmo;
import com.tacz.guns.api.item.IAttachment;
import com.tacz.guns.api.item.IGun;

import mchhui.gunonhorse.ModGunOnHorse;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SaddleGunBagSlot extends SlotItemHandler {
    private Pair<ResourceLocation, ResourceLocation> icon = Pair.of(InventoryMenu.BLOCK_ATLAS, ResourceLocation.fromNamespaceAndPath(ModGunOnHorse.MODID, "item/empty_gun_slot"));
    private boolean weapon = false;
    private SaddleGunBagContainer con;

    public SaddleGunBagSlot(SaddleGunBagContainer con, IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
        this.con = con;
    }

    public SaddleGunBagSlot onlyWeapon() {
        this.weapon = true;
        return this;
    }

    @Override
    public boolean mayPlace(@Nonnull ItemStack stack) {
        if (!canHold(stack))
            return false;
        return super.mayPlace(stack);
    }

    public boolean canHold(@Nonnull ItemStack stack) {
        if (weapon) {
            if (stack.getItem() instanceof IGun) {
                return true;
            } else {
                return false;
            }
        } else {
            if (stack.getItem() instanceof IAttachment || stack.getItem() instanceof IAmmo) {
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
        if (!weapon) {
            return super.getNoItemIcon();
        }
        return icon;
    }
}