package com.neoloxal.paint_palette_lib.datagen;

import com.neoloxal.paint_palette_lib.PaletteUtils;
import org.apache.commons.lang3.text.WordUtils;

public class DatagenUtils {
    public interface CanvasLanguageProvider {
        default void generateLanguage(String modid, TranslationAdder addTranslation) {
            PaletteUtils.Canvas.getGenerateName(modid).forEach(deferredHolder -> {
                if (deferredHolder != null) {
                    addTranslation.add(
                            deferredHolder.getId().toLanguageKey(deferredHolder.getKey().registryKey().location().getPath()),
                            WordUtils.capitalizeFully(deferredHolder.getKey().location().getPath().replace('_', ' '))
                    );
                }
            });
        }

        interface TranslationAdder {
            void add(String key, String value);
        }
    }
}
