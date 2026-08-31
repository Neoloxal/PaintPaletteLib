package com.neoloxal.paint_palette_lib.test;

import com.neoloxal.paint_palette_lib.Palette;
import com.neoloxal.paint_palette_lib.builtin.item.FunnyStick;
import com.neoloxal.paint_palette_lib.builtin.item.ToggleStick;
import com.neoloxal.paint_palette_lib.registrar.ItemRegistrar;
import net.minecraft.world.item.Item;

public class LibItems extends ItemRegistrar {
    public LibItems() {
        super(Palette.MODID);
    }

    @Override
    protected void registerItems() {
        conditionalItem("funny_stick", () -> new FunnyStick(new Item.Properties()), Palette.enableTestItems);
        conditionalItem("toggle_stick", () -> new ToggleStick(new Item.Properties()), Palette.enableTestItems);
    }
}
