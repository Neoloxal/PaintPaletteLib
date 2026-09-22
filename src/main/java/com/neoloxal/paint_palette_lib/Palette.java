package com.neoloxal.paint_palette_lib;

import com.mojang.logging.LogUtils;
import com.neoloxal.paint_palette_lib.builtin.PaletteDataComponents;
import com.neoloxal.paint_palette_lib.datagen.CanvasDatagen;
import com.neoloxal.paint_palette_lib.registrar.BlockRegistrar;
import com.neoloxal.paint_palette_lib.registrar.ItemRegistrar;
import com.neoloxal.paint_palette_lib.builtin.demo.LibItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Mod(Palette.MODID)
public class Palette {
    public static final String MODID = "paint_palette";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static Set<String> modPalette = ConcurrentHashMap.newKeySet(); // The registered registered mods.
    public static Map<String, BlockRegistrar> blockRegistrars = new ConcurrentHashMap<>();

    public static boolean enableDemoContent = true;
    private static final ItemRegistrar LIB_ITEMS = new LibItems();

    public Palette(IEventBus modEventBus, ModContainer modContainer) {
        registerMod(MODID, modEventBus);

        PaletteDataComponents.register(modEventBus);
        LIB_ITEMS.register(modEventBus);
    }

    public static void registerMod(String modid, IEventBus modEventBus) {
        if (!modPalette.add(modid)) {
            LOGGER.warn("Mod {} already exists in mod palette!", modid);
            return;
        }
        LOGGER.info("Adding {} to mod palette.", modid);

        // Data Gen
        PaletteUtils.Canvas.Todo.generateName.put(modid, new CopyOnWriteArrayList<>());
        PaletteUtils.Canvas.Todo.addTranslation.put(modid, new CopyOnWriteArrayList<>());
        PaletteUtils.Canvas.Todo.generateItemModel.put(modid, new CopyOnWriteArrayList<>());
        PaletteUtils.Canvas.Todo.generateOperatorBrushModel.put(modid, new CopyOnWriteArrayList<>());
        PaletteUtils.Canvas.Todo.generateBasicBlockDrop.put(modid, new CopyOnWriteArrayList<>());

        PaletteUtils.Canvas.Todo.createItemModelGenerator.put(modid, new CopyOnWriteArrayList<>());
        PaletteUtils.Canvas.Todo.createBlockModelGenerator.put(modid, new CopyOnWriteArrayList<>());
        PaletteUtils.Canvas.Todo.createRecipeGenerator.put(modid, new CopyOnWriteArrayList<>());
        PaletteUtils.Canvas.Todo.createTagsGenerator.put(modid, new CopyOnWriteArrayList<>());

        modEventBus.addListener(CanvasDatagen::gatherData);
    }
}
