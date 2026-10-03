package com.neoloxal.paint_palette_lib;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;

public class PaletteMod {
    public final String modid;
    public final IEventBus mod_event_bus;
    public final boolean enable_canvas_language_provider;

    public PaletteMod(String modid, IEventBus modEventBus, boolean enableCanvasLanguageProvider) {
        this.modid = modid;
        this.mod_event_bus = modEventBus;
        this.enable_canvas_language_provider = enableCanvasLanguageProvider;
    }

    public PaletteMod(String modid, IEventBus modEventBus) {
        this(modid, modEventBus, true);
    }

    public PaletteMod(ModContainer modContainer, boolean enableCanvasLanguageProvider) {
        this(modContainer.getModId(), modContainer.getEventBus(), enableCanvasLanguageProvider);
    }

    public PaletteMod(ModContainer modContainer) {
        this(modContainer, true);
    }
}
