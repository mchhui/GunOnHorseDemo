package mchhui.gunonhorse.item;

import mchhui.gunonhorse.capability.ModCapabilities;
import mchhui.gunonhorse.util.HorseSaddleGunBagHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;

public class SaddleGunBagContainer extends AbstractContainerMenu {
    public static final int WEAPON_SLOT_COUNT = 3;
    public static final int COMMON_SLOT_COUNT = 18;

    private Inventory playerInventory;
    private ItemStack bag;
    private int bagSlotCount;

    private LivingEntity horse;
    private InteractionHand hand;
    private OpenFrom from;

    public static enum OpenFrom {
        HAND, HORSE
    }

    protected SaddleGunBagContainer(int id, ItemStack bag, Player player, InteractionHand hand, Inventory inv) {
        super(ModItems.SADDLE_GUN_BAG_CONTAINER.get(), id);
        this.playerInventory = inv;
        this.bag = bag;
        this.from = OpenFrom.HAND;
        this.hand = hand;
        addBagSlot(8, 18);
        addPlayerInvSlot(8, 84);
    }
    
    protected SaddleGunBagContainer(int id, ItemStack bag, Player player, Inventory inv,LivingEntity horse) {
        super(ModItems.SADDLE_GUN_BAG_CONTAINER.get(), id);
        this.playerInventory = inv;
        this.bag = bag;
        this.from = OpenFrom.HORSE;
        this.horse=horse;
        addBagSlot(8, 18);
        addPlayerInvSlot(8, 84);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slotId) {
        Slot clickedSlot = slots.get(slotId);
        if (clickedSlot == null || slotId < 0 || !clickedSlot.hasItem())
            return ItemStack.EMPTY;

        ItemStack clickedStack = clickedSlot.getItem();
        final ItemStack unchangedCopy = clickedStack.copy();

        if (slotId < bagSlotCount) {
            // 物品在包中，移动到玩家物品栏
            if (!moveItemStackTo(clickedStack, bagSlotCount, bagSlotCount + 36, false))
                return ItemStack.EMPTY;
        } else {
            // 物品在玩家物品栏中，移动到包中
            if (!moveItemStackTo(clickedStack, 0, bagSlotCount, false))
                return ItemStack.EMPTY;
        }

        if (clickedStack.isEmpty())
            clickedSlot.set(ItemStack.EMPTY);
        else
            clickedSlot.setChanged();

        if (clickedStack.getCount() == unchangedCopy.getCount())
            return ItemStack.EMPTY;

        clickedSlot.onTake(player, clickedStack);
        return clickedStack;
    }

    @Override
    public boolean stillValid(Player player) {
        switch (from) {
            case HAND:
                if (player.getItemInHand(hand).equals(bag)) {
                    return true;
                }
                return false;
            case HORSE:
                if (player.getVehicle().equals(horse)) {
                    if (player.getVehicle().getCapability(ModCapabilities.HORSE_SADDLE_GUN_BAG).isPresent()) {
                        if (bag.equals(player.getVehicle().getCapability(ModCapabilities.HORSE_SADDLE_GUN_BAG).resolve().get().getSaddleGunBag())) {
                            return true;
                        }
                    }
                }
                return false;
            default:
                return false;
        }
    }
    
    @Override
    public void broadcastChanges() {
        // TODO Auto-generated method stub
         super.broadcastChanges();
         if(from==OpenFrom.HORSE) {
             HorseSaddleGunBagHelper.broadcastHorseSaddleGunBag(horse);
         }
    }

    /**
     * 第一行三格（居中）
     * 第二行九格
     * 第三九格
     * 共21格
     * */
    private void addBagSlot(int beginX, int beginY) {
        // 获取包的物品处理器
        bag.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(h -> {
            bagSlotCount = h.getSlots();
            int slotIndex = 0;

            // 第一行：3格（居中）
            int firstRowX = beginX + (9 * 18 - 3 * 18) / 2; // 居中计算
            for (int col = 0; col < 3; col++) {
                int x = firstRowX + col * 18;
                int y = beginY;
                this.addSlot(new SaddleGunBagSlot(h, slotIndex++, x, y - 2).onlyWeapon());
            }

            // 第二行：9格
            for (int col = 0; col < 9; col++) {
                int x = beginX + col * 18;
                int y = beginY + 18;
                this.addSlot(new SaddleGunBagSlot(h, slotIndex++, x, y));
            }

            // 第三行：9格
            for (int col = 0; col < 9; col++) {
                int x = beginX + col * 18;
                int y = beginY + 36;
                this.addSlot(new SaddleGunBagSlot(h, slotIndex++, x, y));
            }
        });
    }

    private void addPlayerInvSlot(int beginX, int beginY) {
        // 添加玩家物品栏槽位（3行9列，索引从9开始）
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                int x = beginX + col * 18;
                int y = beginY + row * 18;
                int slotIndex = 9 + row * 9 + col; // 玩家物品栏索引是9-35
                this.addSlot(new Slot(playerInventory, slotIndex, x, y));
            }
        }

        // 添加快捷栏槽位（1行9列，索引从0开始）
        int hotbarY = beginY + 58;
        for (int col = 0; col < 9; col++) {
            int x = beginX + col * 18;
            int slotIndex = col; // 快捷栏槽位索引是0-8
            this.addSlot(new Slot(playerInventory, slotIndex, x, hotbarY));
        }
    }
}
