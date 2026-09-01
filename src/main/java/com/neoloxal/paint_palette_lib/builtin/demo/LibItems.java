package com.neoloxal.paint_palette_lib.builtin.demo;

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
        conditionalItem(Palette.enableDemoContent, "funny_stick", () -> new FunnyStick(new Item.Properties()));
        conditionalItem(Palette.enableDemoContent, "toggle_stick", () -> new ToggleStick(new Item.Properties()));
    }
}
