package com.neoloxal.paint_palette_lib.builtin.item;

import com.neoloxal.paint_palette_lib.builtin.PaletteDataComponents;
import com.neoloxal.paint_palette_lib.utils.DataComponentUtils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ToggleOperatorBrush extends OperatorBrush {
    private final boolean disableOnUnselected; // Recommended to set in the class.

    public ToggleOperatorBrush(Properties properties, boolean disableOnUnselected) {
        super(properties.component(PaletteDataComponents.TOGGLE.get(), false));
        this.disableOnUnselected = disableOnUnselected;
    }

    /** Override the {@link #inventoryTick(ItemStack, Level, Entity, int, boolean)} method instead of {@link #use(Level, Player, InteractionHand)}. */
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        DataComponentUtils.toggleStack(stack);

        if (Boolean.TRUE.equals(stack.get(PaletteDataComponents.TOGGLE.get()))) {
            toggle(level, player, usedHand, stack, true);
        } else {
            toggle(level, player, usedHand, stack, false);
        }

        return InteractionResultHolder.success(stack);
    }

    public void toggle(Level level, Player player, InteractionHand usedHand, ItemStack stack, boolean newState) {

    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);
        if (!isSelected && disableOnUnselected) {
            if (entity instanceof Player player) {
                stack.set(PaletteDataComponents.TOGGLE.get(), false);
                InteractionHand hand = player.getMainHandItem() == stack
                        ? InteractionHand.MAIN_HAND
                        : InteractionHand.OFF_HAND;
                toggle(level, player, hand, stack, false);
            }
        }
    }
}
