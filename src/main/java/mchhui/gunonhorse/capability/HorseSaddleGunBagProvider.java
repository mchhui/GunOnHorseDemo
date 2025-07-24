package mchhui.gunonhorse.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * 马鞍包Capability提供者
 */
public class HorseSaddleGunBagProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    
    private final HorseSaddleGunBag instance = new HorseSaddleGunBag();
    private final LazyOptional<IHorseSaddleGunBag> lazyOptional = LazyOptional.of(() -> instance);
    
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return ModCapabilities.HORSE_SADDLE_GUN_BAG.orEmpty(cap, lazyOptional);
    }
    
    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.put("SaddleGunBag", instance.getSaddleGunBag().save(new CompoundTag()));
        return tag;
    }
    
    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if (nbt.contains("SaddleGunBag")) {
            instance.setSaddleGunBag(ItemStack.of(nbt.getCompound("SaddleGunBag")));
        }
    }
} 