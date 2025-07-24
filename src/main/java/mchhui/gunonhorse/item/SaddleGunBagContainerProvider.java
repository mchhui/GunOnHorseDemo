package mchhui.gunonhorse.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

public class SaddleGunBagContainerProvider implements MenuProvider {
    public final ItemStack bag;
    public final InteractionHand hand;
    public final LivingEntity horse;

    public SaddleGunBagContainerProvider(ItemStack bag, InteractionHand hand) {
        this.bag = bag;
        this.hand = hand;
        this.horse = null;
    }

    public SaddleGunBagContainerProvider(ItemStack bag, LivingEntity horse) {
        this.bag = bag;
        this.hand = null;
        this.horse = horse;
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
        if (horse != null) {
            return new SaddleGunBagContainer(id, this.bag, player, inv, horse);
        }
        return new SaddleGunBagContainer(id, this.bag, player, hand, inv);
    }

    @Override
    public Component getDisplayName() {
        // TODO Auto-generated method stub
        return Component.translatable("gui.saddlegunbag.tile");
    }

}
