package mchhui.gunonhorse.item;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;

public class SaddleGunBagCapabilityProvider implements ICapabilitySerializable<CompoundTag> {
    private int slots;
    private final LazyOptional<ItemStackHandler> inventory = LazyOptional.of(() -> new ItemStackHandler(slots) {
        
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            return !(stack.getItem() instanceof ItemSaddleGunBag) && super.isItemValid(slot, stack);
        }
        
    });
    
    public SaddleGunBagCapabilityProvider(ItemStack stack, int slots) {
        this.slots = slots;
    }
    
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER)
            return inventory.cast();
        return LazyOptional.empty();
    }
    
    @Override
    public CompoundTag serializeNBT() {
        if (inventory.isPresent())
            return inventory.resolve().get().serializeNBT();
        return new CompoundTag();
    }
    
    @Override
    public void deserializeNBT(CompoundTag nbt) {
        inventory.ifPresent(h -> h.deserializeNBT(nbt));
    }
} 