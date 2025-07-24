package mchhui.gunonhorse.item;

import javax.annotation.Nullable;

import mchhui.gunonhorse.item.SaddleGunBagContainer.OpenFrom;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.network.NetworkHooks;

/**
 * 最大堆叠1个
 * 有序合成配方:
 * 铁锭金锭铁锭
 * 皮革箱子皮革
 * 铁粒皮革铁粒
 * 产生一个马鞍包
 * */
public class ItemSaddleGunBag extends Item {
    public static final int WEAPON_SLOT_COUNT = 3;
    public static final int COMMON_SLOT_COUNT = 18;

    public ItemSaddleGunBag(Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (player instanceof ServerPlayer) {
            ItemStack stack = player.getItemInHand(hand);
            NetworkHooks.openScreen((ServerPlayer)player, new SaddleGunBagContainerProvider(stack, hand), (buf) -> {
                buf.writeEnum(OpenFrom.HAND);
                buf.writeItem(stack);
                buf.writeEnum(hand);
            });
        }
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }

    @Override
    public boolean useOnRelease(ItemStack stack) {
        // TODO Auto-generated method stub
        return true;
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new SaddleGunBagCapabilityProvider(stack, WEAPON_SLOT_COUNT + COMMON_SLOT_COUNT);
    }
}
