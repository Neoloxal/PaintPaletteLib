package com.neoloxal.paint_palette_lib.test;

import com.neoloxal.paint_palette_lib.PaintPalette;
import com.neoloxal.paint_palette_lib.item.FunnyStick;
import com.neoloxal.paint_palette_lib.registrar.ItemRegistrar;
import net.minecraft.world.item.Item;

public class LibItems extends ItemRegistrar {
    public LibItems() {
        super(PaintPalette.MODID);
    }

    @Override
    protected void registerItems() {
        conditionalItem("funny_stick", () -> new FunnyStick(new Item.Properties()), PaintPalette.enableTestItems);
    }
}
