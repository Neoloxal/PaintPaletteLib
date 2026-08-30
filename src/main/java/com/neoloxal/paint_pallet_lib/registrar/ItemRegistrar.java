package com.neoloxal.paint_pallet_lib.registrar;

import com.neoloxal.paint_pallet_lib.PaintPallet;
import com.neoloxal.paint_pallet_lib.item.FunnyStick;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;
import java.util.stream.Stream;

public abstract class ItemRegistrar {
    protected DeferredRegister.Items ITEMS;
    protected final String modId;

    protected ItemRegistrar(String modid) {
        modId = modid;
    }

    public void register(IEventBus eventBus) {
        ITEMS = DeferredRegister.createItems(modId);
        registerItems();
        ITEMS.register(eventBus);
        PaintPallet.itemRegisters.add(this);
    }

    protected abstract void registerItems();

    protected <I extends Item> DeferredItem<I> basicItem(String name, Supplier<? extends I> supplier) {
        return ITEMS.register(name, supplier);
    }

    protected <I extends FunnyStick> DeferredItem<I> funnyStick(String name, Supplier<? extends I> supplier) {
        return basicItem(name, supplier);
    }

    @SuppressWarnings("unchecked")
    public Stream<DeferredItem<? extends FunnyStick>> getFunnySticks() {
        return ITEMS.getEntries().stream()
                .filter(entry -> entry.get() instanceof FunnyStick)
                .map(entry -> (DeferredItem<? extends FunnyStick>) entry);
    }

    public String getModId() {
        return modId;
    }
}
