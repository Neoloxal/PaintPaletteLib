package com.neoloxal.paint_palette_lib;

import com.mojang.logging.LogUtils;
import com.neoloxal.paint_palette_lib.registrar.BlockRegistrar;
import com.neoloxal.paint_palette_lib.registrar.ItemRegistrar;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

@Mod(PaintPalette.MODID)
public class PaintPalette {
    public static final String MODID = "paint_palette";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static List<String> modIds = new ArrayList<>();
    public static List<ItemRegistrar> itemRegisters = new ArrayList<>();
    public static List<BlockRegistrar> blockRegistrars = new ArrayList<>();

    public PaintPalette(IEventBus modEventBus, ModContainer modContainer) {
    }

    public static void registerModID(String modid) {
        if (!modIds.contains(modid)) {
            modIds.add(modid);
        }
    }
}
