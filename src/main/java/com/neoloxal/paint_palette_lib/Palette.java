package com.neoloxal.paint_palette_lib;

import com.mojang.logging.LogUtils;
import com.neoloxal.paint_palette_lib.builtin.LibDataComponents;
import com.neoloxal.paint_palette_lib.builtin.item.FunnyStick;
import com.neoloxal.paint_palette_lib.datagen.LibDataGenerators;
import com.neoloxal.paint_palette_lib.registrar.BlockRegistrar;
import com.neoloxal.paint_palette_lib.registrar.ItemRegistrar;
import com.neoloxal.paint_palette_lib.builtin.demo.LibItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
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

        LibDataComponents.register(modEventBus);
        LIB_ITEMS.register(modEventBus);
    }

    public static void registerMod(String modid, IEventBus modEventBus) {
        if (modPalette.add(modid)) {
            LOGGER.info("Adding {} to mod palette.", modid);

            Canvas.generateName.put(modid, new CopyOnWriteArrayList<>());
            Canvas.generateItemModel.put(modid, new CopyOnWriteArrayList<>());
            Canvas.generateStickModel.put(modid, new CopyOnWriteArrayList<>());
            Canvas.generateBasicBlockDrop.put(modid, new CopyOnWriteArrayList<>());

            modEventBus.addListener(LibDataGenerators::gatherData);
            return;
        }
        LOGGER.warn("Mod {} already exists in mod palette!", modid);
    }

    public static class Canvas {
        private static final Logger LOGGER = LogUtils.getLogger();

        protected static Map<String, List<DeferredHolder<?, ?>>> generateName = new ConcurrentHashMap<>();

        protected static Map<String, List<DeferredItem<? extends Item>>> generateItemModel = new ConcurrentHashMap<>();
        protected static Map<String, List<DeferredItem<? extends FunnyStick>>> generateStickModel = new ConcurrentHashMap<>();

        protected static Map<String, List<DeferredBlock<? extends Block>>> generateBasicBlockDrop = new ConcurrentHashMap<>();

        public static <T extends DeferredHolder<?, ?>> void generateName(T deferredHolder) {
            addTask(
                    generateName,
                    deferredHolder.getId().getNamespace(),
                    deferredHolder,
                    "generate name for %s."
            );
        }

        public static <T extends DeferredItem<? extends Item>> void generateItemModel(T deferredItem) {
            addTask(
                    generateItemModel,
                    deferredItem.getId().getNamespace(),
                    deferredItem,
                    "generate item model for %s."
            );
        }

        public static <T extends DeferredItem<? extends FunnyStick>> void generateStickModel(T deferredItem) {
            addTask(
                    generateStickModel,
                    deferredItem.getId().getNamespace(),
                    deferredItem,
                    "generate stick model for %s."
            );
        }

        public static <T extends DeferredBlock<? extends Block>> void generateBasicBlockDrop(T deferredBlock) {
            addTask(
                    generateBasicBlockDrop,
                    deferredBlock.getId().getNamespace(),
                    deferredBlock,
                    "generate basic block drop for %s."
            );
        }

        private static <T extends DeferredHolder<?, ?>> void addTask(Map<String, List<T>> todoList, String modid, T task, String taskLabel) {
            List<T> list = todoList.computeIfAbsent(modid, k -> new CopyOnWriteArrayList<>());
            taskLabel = taskLabel.formatted(task.getId());
            if (!list.contains(task)) {
                list.add(task);
                LOGGER.info("Adding task: '{}' to canvas todo list.", taskLabel);
                return;
            }
            LOGGER.warn("Task: '{}' is already on canvas todo list!", taskLabel);
        }

        public static List<DeferredHolder<?, ?>> getGenerateName(String modid) {
            return generateName.get(modid);
        }

        public static List<DeferredItem<? extends Item>> getGenerateItemModel(String modid) {
            return generateItemModel.get(modid);
        }

        public static List<DeferredItem<? extends FunnyStick>> getGenerateStickModel(String modid) {
            return generateStickModel.get(modid);
        }

        public static List<DeferredBlock<? extends Block>> getGenerateBasicBlockDrop(String modid) {
            return generateBasicBlockDrop.get(modid);
        }
    }
}
