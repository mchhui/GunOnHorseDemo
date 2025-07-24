package mchhui.gunonhorse.capability;

import net.minecraft.world.item.ItemStack;

/**
 * 马实体马鞍包存储能力接口
 */
public interface IHorseSaddleGunBag {
    
    /**
     * 获取马鞍包物品
     * @return 马鞍包物品堆叠
     */
    ItemStack getSaddleGunBag();
    
    /**
     * 设置马鞍包物品
     * @param stack 马鞍包物品堆叠
     */
    void setSaddleGunBag(ItemStack stack);
    
    /**
     * 检查是否有马鞍包
     * @return 是否有马鞍包
     */
    default boolean hasSaddleGunBag() {
        return !getSaddleGunBag().isEmpty();
    }
} 