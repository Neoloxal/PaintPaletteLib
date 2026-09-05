package com.neoloxal.paint_palette_lib;

import com.mojang.logging.LogUtils;
import com.neoloxal.paint_palette_lib.builtin.LibDataComponents;
import com.neoloxal.paint_palette_lib.builtin.item.FunnyStick;
import com.neoloxal.paint_palette_lib.datagen.LibDataGenerators;
import com.neoloxal.paint_palette_lib.registrar.BlockRegistrar;
import com.neoloxal.paint_palette_lib.registrar.ItemRegistrar;
import com.neoloxal.paint_palette_lib.builtin.demo.LibItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.model.generators.BlockModelProvider;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import oshi.util.tuples.Pair;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
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

            Canvas.Todo.generateName.put(modid, new CopyOnWriteArrayList<>());
            Canvas.Todo.generateItemModel.put(modid, new CopyOnWriteArrayList<>());
            Canvas.Todo.generateStickModel.put(modid, new CopyOnWriteArrayList<>());
            Canvas.Todo.generateBasicBlockDrop.put(modid, new CopyOnWriteArrayList<>());

            Canvas.Todo.createItemModelGenerator.put(modid, new CopyOnWriteArrayList<>());
            Canvas.Todo.createBlockModelGenerator.put(modid, new CopyOnWriteArrayList<>());
            Canvas.Todo.createRecipeGenerator.put(modid, new CopyOnWriteArrayList<>());
            Canvas.Todo.createTagsGenerator.put(modid, new CopyOnWriteArrayList<>());

            modEventBus.addListener(LibDataGenerators::gatherData);
            return;
        }
        LOGGER.warn("Mod {} already exists in mod palette!", modid);
    }

    public static class Canvas {
        private static final Logger LOGGER = LogUtils.getLogger();

        protected static class Todo {
            protected static Map<String, List<DeferredHolder<?, ?>>> generateName = new ConcurrentHashMap<>();

            protected static Map<String, List<DeferredItem<? extends Item>>> generateItemModel = new ConcurrentHashMap<>();
            protected static Map<String, List<DeferredItem<? extends FunnyStick>>> generateStickModel = new ConcurrentHashMap<>();

            protected static Map<String, List<DeferredBlock<? extends Block>>> generateBasicBlockDrop = new ConcurrentHashMap<>();

            protected static Map<String, List<ItemModelFactory>> createItemModelGenerator = new ConcurrentHashMap<>();
            protected static Map<String, List<BlockModelFactory>> createBlockModelGenerator = new ConcurrentHashMap<>();
            protected static Map<String, List<RecipeFactory>> createRecipeGenerator = new ConcurrentHashMap<>();

            protected static Map<String, List<Pair<BlockTagsFactory, ItemTagsFactory>>> createTagsGenerator = new ConcurrentHashMap<>();
        }

        public static <T extends DeferredHolder<?, ?>> void generateName(T deferredHolder) {
            addDeferredTask(
                    Todo.generateName,
                    deferredHolder.getId().getNamespace(),
                    deferredHolder,
                    "generate name for %s."
            );
        }

        public static <T extends DeferredItem<? extends Item>> void generateItemModel(T deferredItem) {
            addDeferredTask(
                    Todo.generateItemModel,
                    deferredItem.getId().getNamespace(),
                    deferredItem,
                    "generate item model for %s."
            );
        }

        public static <T extends DeferredItem<? extends FunnyStick>> void generateStickModel(T deferredItem) {
            addDeferredTask(
                    Todo.generateStickModel,
                    deferredItem.getId().getNamespace(),
                    deferredItem,
                    "generate stick model for %s."
            );
        }

        public static <T extends DeferredBlock<? extends Block>> void generateBasicBlockDrop(T deferredBlock) {
            addDeferredTask(
                    Todo.generateBasicBlockDrop,
                    deferredBlock.getId().getNamespace(),
                    deferredBlock,
                    "generate basic block drop for %s."
            );
        }

        public static void createTagsGenerator(String modid, BlockTagsFactory blockTagsFactory, @Nullable ItemTagsFactory itemTagsFactory) {
            addTask(
                    Todo.createTagsGenerator,
                    modid,
                    new Pair<>(blockTagsFactory, itemTagsFactory),
                    "create tags generators for mod %s".formatted(modid)
            );
        }

        public static void createItemModelGenerator(String modid, ItemModelFactory itemModelFactory) {
            addDataProviderTask(
                    Todo.createItemModelGenerator,
                    modid,
                    itemModelFactory,
                    "create item model generator for mod %s"
            );
        }

        public static void createBlockModelGenerator(String modid, BlockModelFactory blockModelFactory) {
            addDataProviderTask(
                    Todo.createBlockModelGenerator,
                    modid,
                    blockModelFactory,
                    "create block model generator for mod %s"
            );
        }

        public static void createRecipeGenerator(String modid, RecipeFactory recipeFactory) {
            addDataProviderTask(
                    Todo.createRecipeGenerator,
                    modid,
                    recipeFactory,
                    "create recipe generator for mod %s"
            );
        }


        private static <T extends DeferredHolder<?, ?>> void addDeferredTask(Map<String, List<T>> todoList, String modid, T task, String taskLabel) {
            taskLabel = taskLabel.formatted(task.getId());
            addTask(todoList, modid, task, taskLabel);
        }

        private static <T> void addDataProviderTask(Map<String, List<T>> todoList, String modid, T task, String taskLabel) {
            taskLabel = taskLabel.formatted(modid);
            addTask(todoList, modid, task, taskLabel);
        }

        private static <T> void addTask(Map<String, List<T>> todoList, String modid, T task, String taskLabel) {
            List<T> list = todoList.computeIfAbsent(modid, k -> new CopyOnWriteArrayList<>());
            if (!list.contains(task)) {
                list.add(task);
                LOGGER.info("Adding task: '{}' to canvas todo list.", taskLabel);
                return;
            }
            LOGGER.warn("Task: '{}' is already on canvas todo list!", taskLabel);
        }


        public static List<DeferredHolder<?, ?>> getGenerateName(String modid) {
            return Todo.generateName.get(modid);
        }

        public static List<DeferredItem<? extends Item>> getGenerateItemModel(String modid) {
            return Todo.generateItemModel.get(modid);
        }

        public static List<DeferredItem<? extends FunnyStick>> getGenerateStickModel(String modid) {
            return Todo.generateStickModel.get(modid);
        }

        public static List<DeferredBlock<? extends Block>> getGenerateBasicBlockDrop(String modid) {
            return Todo.generateBasicBlockDrop.get(modid);
        }

        public static List<Pair<BlockTagsFactory, ItemTagsFactory>> getCreateTagsGenerator(String modid) {
            return Todo.createTagsGenerator.get(modid);
        }

        public static List<ItemModelFactory> getCreateItemModelGenerator(String modid) {
            return Todo.createItemModelGenerator.get(modid);
        }

        public static List<BlockModelFactory> getCreateBlockModelGenerator(String modid) {
            return Todo.createBlockModelGenerator.get(modid);
        }

        public static List<RecipeFactory> getCreateRecipeGenerator(String modid) {
            return Todo.createRecipeGenerator.get(modid);
        }

        public interface BlockTagsFactory {
            BlockTagsProvider create(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId, ExistingFileHelper existingFileHelper);
        }

        public interface ItemTagsFactory {
            ItemTagsProvider create(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                                    CompletableFuture<TagsProvider.TagLookup<Block>> blockTags, String modId, @Nullable ExistingFileHelper existingFileHelper);
        }

        public interface ItemModelFactory {
            ItemModelProvider create(PackOutput output, String modid, ExistingFileHelper existingFileHelper);
        }

        public interface BlockModelFactory {
            BlockModelProvider create(PackOutput output, String modid, ExistingFileHelper existingFileHelper);
        }

        public interface RecipeFactory {
            RecipeProvider create(PackOutput output, CompletableFuture<HolderLookup.Provider> registries);
        }
    }
}
