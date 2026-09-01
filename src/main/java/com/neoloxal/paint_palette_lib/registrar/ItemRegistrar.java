package com.neoloxal.paint_palette_lib.registrar;

import com.neoloxal.paint_palette_lib.Palette;
import com.neoloxal.paint_palette_lib.builtin.item.FunnyStick;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

public abstract class ItemRegistrar {
    protected final DeferredRegister.Items items;
    protected final String modId;

    protected Map<String, DeferredItem<? extends Item>> registeredItems = new HashMap<>();

    public ItemRegistrar(String modid) {
        modId = modid;
        items = DeferredRegister.createItems(modId);
    }

    public void register(IEventBus eventBus) {
        registerItems();
        items.register(eventBus);
        Palette.itemRegisters.add(this);
    }

    protected abstract void registerItems();


    protected <I extends Item> DeferredItem<I> basicItem(String name, Supplier<? extends I> supplier) {
        if (registeredItems.containsKey(name)) {
            throw new IllegalStateException("Duplicate item name: " + name);
        }
        DeferredItem<I> item = items.register(name, supplier);
        registeredItems.put(name, item);
        return item;
    }

    protected <I extends FunnyStick> DeferredItem<I> funnyStick(String name, Supplier<? extends I> supplier) {
        return basicItem(name, supplier);
    }

    protected <I extends Item> Optional<DeferredItem<I>> conditionalItem(boolean run, String name, Supplier<? extends I> supplier) {
        if (run) {
            return Optional.of(basicItem(name, supplier));
        }
        return Optional.empty();
    }

    protected  <I extends Block> void blockItem(String name, DeferredBlock<I> block) {
        basicItem(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public Map<String, DeferredItem<? extends Item>> getRegisteredItems() {
        return registeredItems;
    }


    @SuppressWarnings("unchecked")
    public Stream<DeferredItem<? extends FunnyStick>> getFunnySticks() {
        return items.getEntries().stream()
                .filter(entry -> entry.get() instanceof FunnyStick)
                .map(entry -> (DeferredItem<? extends FunnyStick>) entry);
    }

    public String getModId() {
        return modId;
    }
}
