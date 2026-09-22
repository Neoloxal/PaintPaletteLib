package com.neoloxal.paint_palette_lib.builtin.demo;

import com.neoloxal.paint_palette_lib.Palette;
import com.neoloxal.paint_palette_lib.builtin.item.OperatorBrush;
import com.neoloxal.paint_palette_lib.builtin.item.ToggleOperatorBrush;
import com.neoloxal.paint_palette_lib.registrar.ItemRegistrar;
import net.minecraft.world.item.Item;

public class LibItems extends ItemRegistrar {
    public LibItems() {
        super(Palette.MODID);
    }

    @Override
    protected void registerItems() {
        conditionalItem(Palette.enableDemoContent, () -> operatorBrush("operator_brush", () -> new OperatorBrush(new Item.Properties())));
        conditionalItem(Palette.enableDemoContent, () -> operatorBrush("toggle_operator_brush", () -> new ToggleOperatorBrush(new Item.Properties(), false)));
    }
}
