package mchhui.gunonhorse.capability;

import net.minecraft.world.item.ItemStack;

/**
 * 马实体马鞍包存储能力实现
 */
public class HorseSaddleGunBag implements IHorseSaddleGunBag {
    
    private ItemStack saddleGunBag = ItemStack.EMPTY;
    
    @Override
    public ItemStack getSaddleGunBag() {
        return saddleGunBag;
    }
    
    @Override
    public void setSaddleGunBag(ItemStack stack) {
        this.saddleGunBag = stack;
    }
} 