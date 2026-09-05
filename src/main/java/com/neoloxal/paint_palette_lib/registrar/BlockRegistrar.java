package com.neoloxal.paint_palette_lib.registrar;

import com.neoloxal.paint_palette_lib.Palette;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public abstract class BlockRegistrar {
    protected final DeferredRegister.Blocks blocks;
    protected final ItemRegistrar itemRegistrar;
    protected final String modId;

    protected Map<String, DeferredBlock<? extends Block>> registeredBlocks = new HashMap<>();

    public BlockRegistrar(String modid, ItemRegistrar itemRegistrar) {
        modId = modid;
        blocks = DeferredRegister.createBlocks(modid);
        this.itemRegistrar = itemRegistrar;
    }

    public void register(IEventBus eventBus) {
        registerBlocks();
        blocks.register(eventBus);
        if (!Palette.blockRegistrars.containsKey(modId)) {
            Palette.blockRegistrars.put(modId, this);
        } else {
            throw new IllegalStateException("A block registrar is already assinged to mod: %s!".formatted(modId));
        }
    }

    protected abstract void registerBlocks();

    protected <B extends Block> DeferredBlock<B> basicBlock(String name, Supplier<? extends B> supplier, boolean registerItem) {
        if (registeredBlocks.containsKey(name)) {
            throw new IllegalStateException("Duplicate block name: " + name);
        }
        DeferredBlock<B> block = blocks.register(name, supplier);
        registeredBlocks.put(name, block);
        if (registerItem) {
            itemRegistrar.blockItem(name, block);
        }
        return block;
    }

    protected <B extends  Block> DeferredBlock<B> basicBlock(String name, Supplier<? extends B> supplier) {
        return basicBlock(name, supplier, true);
    }

    protected <B extends Block> Optional<DeferredBlock<B>> conditionalBlock(boolean run, Supplier<DeferredBlock<B>> supplier) {
        if (run) {
            return Optional.of(supplier.get());
        }
        return Optional.empty();
    }

    public Collection<DeferredHolder<Block, ? extends Block>> getEntries() {
        return blocks.getEntries();
    }

    public DeferredBlock<? extends Block> getBlock(String name) {
        return registeredBlocks.get(name);
    }
}
