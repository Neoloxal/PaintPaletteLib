package com.neoloxal.paint_palette_lib.utils;

import com.neoloxal.paint_palette_lib.builtin.PaletteDataComponents;
import net.minecraft.world.item.ItemStack;

public class DataComponentUtils {
    /** Toggles the {@code TOGGLE} data component. */
    public static void toggleStack(ItemStack stack) {
        if (!stack.has(PaletteDataComponents.TOGGLE.get())) {
            stack.set(PaletteDataComponents.TOGGLE.get(), true);
            return;
        }
        stack.set(PaletteDataComponents.TOGGLE.get(), Boolean.FALSE.equals(stack.get(PaletteDataComponents.TOGGLE.get())));
    }
}
