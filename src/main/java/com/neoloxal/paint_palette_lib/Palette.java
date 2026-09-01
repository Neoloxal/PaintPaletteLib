package com.neoloxal.paint_palette_lib;

import com.mojang.logging.LogUtils;
import com.neoloxal.paint_palette_lib.builtin.LibDataComponents;
import com.neoloxal.paint_palette_lib.datagen.LibDataGenerators;
import com.neoloxal.paint_palette_lib.registrar.BlockRegistrar;
import com.neoloxal.paint_palette_lib.registrar.ItemRegistrar;
import com.neoloxal.paint_palette_lib.builtin.demo.LibItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Mod(Palette.MODID)
public class Palette {
    public static final String MODID = "paint_palette";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static Set<String> modPalette = ConcurrentHashMap.newKeySet(); // The registered registered mods.
    public static List<ItemRegistrar> itemRegisters = new ArrayList<>();
    public static List<BlockRegistrar> blockRegistrars = new ArrayList<>();

    public static boolean enableDemoContent = true;
    private static final ItemRegistrar LIB_ITEMS = new LibItems();

    public Palette(IEventBus modEventBus, ModContainer modContainer) {
        registerMod(MODID, modEventBus);

        LibDataComponents.register(modEventBus);
        LIB_ITEMS.register(modEventBus);
    }

    public static void registerMod(String modid, IEventBus modEventBus) {
        if (modPalette.add(modid)) {
            LOGGER.info("Adding {} to mod palette.", modid);
            modEventBus.addListener(LibDataGenerators::gatherData);
            return;
        }
        LOGGER.warn("Mod {} already exists in mod palette!", modid);
    }
}
