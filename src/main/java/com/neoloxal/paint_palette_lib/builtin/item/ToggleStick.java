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
    public ToggleStick(Properties properties) {
        super(properties.component(LibDataComponents.TOGGLE.get(), false));
    }

    /** Override the {@link #inventoryTick(ItemStack, Level, Entity, int, boolean)} method instead of {@link #use(Level, Player, InteractionHand)}. */
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        DataComponentUtils.toggleStack(stack);

        if (Boolean.TRUE.equals(stack.get(LibDataComponents.TOGGLE.get()))) {
            toggleOn(level, player, usedHand, stack);
        } else {
            toggleOff(level, player, usedHand, stack);
        }

        return InteractionResultHolder.success(stack);
    }

    public void toggleOn(Level level, Player player, InteractionHand usedHand, ItemStack stack) {

    }

    public void toggleOff(Level level, Player player, InteractionHand usedHand, ItemStack stack) {

    }
}
