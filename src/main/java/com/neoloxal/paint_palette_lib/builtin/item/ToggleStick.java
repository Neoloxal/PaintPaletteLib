package com.neoloxal.paint_palette_lib.builtin.item;

import com.neoloxal.paint_palette_lib.builtin.LibDataComponents;
import com.neoloxal.paint_palette_lib.utils.DataComponentUtils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ToggleStick extends FunnyStick {
    private final boolean disableOnUnselected; // Recommended to set in the class.

    public ToggleStick(Properties properties, boolean disableOnUnselected) {
        super(properties.component(LibDataComponents.TOGGLE.get(), false));
        this.disableOnUnselected = disableOnUnselected;
    }

    /** Override the {@link #inventoryTick(ItemStack, Level, Entity, int, boolean)} method instead of {@link #use(Level, Player, InteractionHand)}. */
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        DataComponentUtils.toggleStack(stack);

        if (Boolean.TRUE.equals(stack.get(LibDataComponents.TOGGLE.get()))) {
            toggle(level, player, usedHand, stack, true);
            toggleOn(level, player, usedHand, stack);
        } else {
            toggle(level, player, usedHand, stack, false);
            toggleOff(level, player, usedHand, stack);
        }

        return InteractionResultHolder.success(stack);
    }

    public void toggle(Level level, Player player, InteractionHand usedHand, ItemStack stack, boolean newState) {

    }

    @Deprecated(since = "v0.2.2", forRemoval = true)
    public void toggleOn(Level level, Player player, InteractionHand usedHand, ItemStack stack) {

    }

    @Deprecated(since = "v0.2.2", forRemoval = true)
    public void toggleOff(Level level, Player player, InteractionHand usedHand, ItemStack stack) {

    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);
        if (!isSelected && disableOnUnselected) {
            if (entity instanceof Player player) {
                stack.set(LibDataComponents.TOGGLE.get(), false);
                toggle(level, player, player.getUsedItemHand(), stack, false);
            }
        }
    }
}
