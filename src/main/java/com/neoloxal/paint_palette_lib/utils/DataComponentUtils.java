package com.neoloxal.paint_palette_lib.utils;

import com.neoloxal.paint_palette_lib.builtin.PaletteDataComponents;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class DataComponentUtils {
    /** Toggles the {@code TOGGLE} data component. */
    public static void toggleStack(ItemStack stack) {
        toggleComponent(stack, PaletteDataComponents.TOGGLE.get());
    }

    /** Toggles the given component. */
    public static void toggleComponent(ItemStack stack, DataComponentType<? super Boolean> type) {
        stack.set(type, stack.getOrDefault(type, false).equals(Boolean.FALSE));
    }

    /** Sets the {@code HOLDER} data component to holder. */
    public static void updateHolder(ItemStack stack, Player player) {
        stack.set(PaletteDataComponents.HOLDER.get(), player.getUUID());
    }
}
