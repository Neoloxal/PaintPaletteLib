package com.neoloxal.paint_palette_lib.builtin.registrar;

import com.mojang.serialization.Codec;
import com.neoloxal.paint_palette_lib.Palette;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class LibDataComponents {
    public static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, Palette.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> TOGGLE = DATA_COMPONENTS.registerComponentType(
            "toggle",
            builder -> builder.persistent(Codec.BOOL)
    );

    public static void register(IEventBus eventBus) {
        DATA_COMPONENTS.register(eventBus);
    }
}
