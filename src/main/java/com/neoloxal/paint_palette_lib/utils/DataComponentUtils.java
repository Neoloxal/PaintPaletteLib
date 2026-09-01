package com.neoloxal.paint_palette_lib.utils;

import com.neoloxal.paint_palette_lib.builtin.LibDataComponents;
import net.minecraft.world.item.ItemStack;

public class DataComponentUtils {
    /** Toggles the {@code TOGGLE} data component. */
    public static void toggleStack(ItemStack stack) {
        if (!stack.has(LibDataComponents.TOGGLE.get())) {
            stack.set(LibDataComponents.TOGGLE.get(), true);
            return;
        }
        stack.set(LibDataComponents.TOGGLE.get(), Boolean.FALSE.equals(stack.get(LibDataComponents.TOGGLE.get())));
    }
}
