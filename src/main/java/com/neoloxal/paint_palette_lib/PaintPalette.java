package com.neoloxal.paint_palette_lib;

import com.mojang.logging.LogUtils;
import com.neoloxal.paint_palette_lib.datagen.LibDataGenerators;
import com.neoloxal.paint_palette_lib.registrar.BlockRegistrar;
import com.neoloxal.paint_palette_lib.registrar.ItemRegistrar;
import com.neoloxal.paint_palette_lib.test.LibItems;
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

    public static boolean enableTestItems = true;
    private static final ItemRegistrar LIB_ITEMS = new LibItems();

    public PaintPalette(IEventBus modEventBus, ModContainer modContainer) {
        registerMod(MODID, modEventBus);
        LIB_ITEMS.register(modEventBus);
    }

    public static void registerMod(String modid, IEventBus modEventBus) {
        if (!modIds.contains(modid)) {
            modIds.add(modid);
            modEventBus.addListener(LibDataGenerators::gatherData);
        }
    }
}
