package com.neoloxal.paint_palette_lib.datagen;

import com.neoloxal.paint_palette_lib.PaintPalette;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.apache.commons.lang3.text.WordUtils;

@EventBusSubscriber
public class LibDataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        for (String modid : PaintPalette.modIds) {
            generator.addProvider(event.includeClient(), new LibItemModelProvider(packOutput, modid, existingFileHelper));
            generator.addProvider(event.includeClient(), new LibLangProvider(packOutput, modid));
        }
    }

    private static class LibItemModelProvider extends ItemModelProvider {
        private final String modId;

        public LibItemModelProvider(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
            super(output, modid, existingFileHelper);
            this.modId = modid;
        }

        @Override
        protected void registerModels() {
            PaintPalette.itemRegisters.forEach(itemRegistrar -> {
                if (itemRegistrar.getModId().equals(this.modId)) {
                    itemRegistrar.getFunnySticks().forEach(item ->
                            withExistingParent(item.getId().toString(), mcLoc("item/stick"))
                    );
                }
            });
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
            PaintPalette.itemRegisters.forEach(itemRegistrar -> {
                if (itemRegistrar.getModId().equals(this.modId)) {
                    itemRegistrar.getFunnySticks().forEach(item ->
                            add(item.get(), WordUtils.capitalizeFully(item.getKey().location().getPath().replace('_', ' ')))
                    );
                }
            });
        }
    }
}
