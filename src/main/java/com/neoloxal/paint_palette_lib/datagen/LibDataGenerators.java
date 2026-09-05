package com.neoloxal.paint_palette_lib.datagen;

import com.mojang.logging.LogUtils;
import com.neoloxal.paint_palette_lib.Palette;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.apache.commons.lang3.text.WordUtils;
import org.slf4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class LibDataGenerators {
    public static final Logger LOGGER = LogUtils.getLogger();

    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        String modid = event.getModContainer().getModId();

        generator.addProvider(event.includeClient(), new LibItemModelProvider(packOutput, modid, existingFileHelper));
        generator.addProvider(event.includeClient(), new LibLangProvider(packOutput, modid));

        if (Palette.blockRegistrars.containsKey(modid)) {
            generator.addProvider(event.includeServer(), new LootTableProvider(packOutput, Collections.emptySet(),
                    List.of(new LootTableProvider.SubProviderEntry(registries -> new LibBlockLootTableProvider(registries, modid), LootContextParamSets.BLOCK)), lookupProvider));
        } else {
            LOGGER.info("No block registrar found for mod: {}, skipping.", modid);
        }

        Palette.Canvas.getCreateTagsGenerator(modid).forEach(tagGeneratorPair -> {
            Palette.Canvas.BlockTagsFactory blockTagsFactory = tagGeneratorPair.getA();
            Palette.Canvas.ItemTagsFactory itemTagsFactory = tagGeneratorPair.getB();

            BlockTagsProvider blockTagsProvider = blockTagsFactory.create(packOutput, lookupProvider, modid, existingFileHelper);
            generator.addProvider(event.includeServer(), blockTagsProvider);
            if (itemTagsFactory != null) {
                generator.addProvider(event.includeServer(), itemTagsFactory.create(packOutput, lookupProvider, blockTagsProvider.contentsGetter(), modid, existingFileHelper));
            }
        });

        Palette.Canvas.getCreateRecipeGenerator(modid).forEach(recipeFactory -> generator.addProvider(event.includeServer(), recipeFactory.create(packOutput, lookupProvider)));

        Palette.Canvas.getCreateItemModelGenerator(modid).forEach(itemModelFactory -> generator.addProvider(event.includeClient(), itemModelFactory.create(packOutput, modid, existingFileHelper)));

        Palette.Canvas.getCreateBlockModelGenerator(modid).forEach(blockModelFactory -> generator.addProvider(event.includeClient(), blockModelFactory.create(packOutput, modid, existingFileHelper)));
    }

    private static class LibItemModelProvider extends ItemModelProvider {
        private final String modId;

        public LibItemModelProvider(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
            super(output, modid, existingFileHelper);
            this.modId = modid;
        }

        @Override
        protected void registerModels() {
            Palette.Canvas.getGenerateItemModel(modId).forEach(item ->
                    basicItem(item.get())
            );

            Palette.Canvas.getGenerateStickModel(modId).forEach(item ->
                withExistingParent(item.getId().toString(), mcLoc("item/stick"))
            );
        }
    }

    private static class LibLangProvider extends LanguageProvider {
        private final String modId;

        public LibLangProvider(PackOutput output, String modid) {
            super(output, modid, "en_us");
            modId = modid;
        }

        @Override
        protected void addTranslations() {
            Palette.Canvas.getGenerateName(modId).forEach(deferredHolder ->
                add(deferredHolder.getId().toLanguageKey(deferredHolder.getKey().registryKey().location().getPath()), WordUtils.capitalizeFully(deferredHolder.getKey().location().getPath().replace('_', ' ')))
            );
        }
    }

    private static class LibBlockLootTableProvider extends BlockLootSubProvider {
        private final String modId;

        protected LibBlockLootTableProvider(HolderLookup.Provider registries, String modId) {
            super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
            this.modId = modId;
        }

        @Override
        protected void generate() {
            Palette.Canvas.getGenerateBasicBlockDrop(modId).forEach(block ->
                dropSelf(block.get())
            );
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return Palette.blockRegistrars.get(modId).getEntries().stream().map(Holder::value)::iterator;
        }
    }
}
