package mchhui.gunonhorse.item;

import javax.annotation.Nonnull;

import com.tacz.guns.api.item.IAmmo;
import com.tacz.guns.api.item.IAttachment;
import com.tacz.guns.api.item.IGun;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SaddleGunBagSlot extends SlotItemHandler {
    public boolean weapon = false;

    public SaddleGunBagSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
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
            }else {
                return false;
            }
        }
    }
}