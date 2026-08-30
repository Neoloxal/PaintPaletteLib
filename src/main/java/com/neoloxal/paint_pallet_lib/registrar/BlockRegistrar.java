package com.neoloxal.paint_pallet_lib.registrar;

import com.neoloxal.paint_pallet_lib.PaintPallet;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Optional;
import java.util.function.Supplier;

public abstract class BlockRegistrar {
    protected final DeferredRegister.Blocks blocks;
    protected final ItemRegistrar itemRegistrar;
    protected final String modId;

    protected BlockRegistrar(String modid, ItemRegistrar itemRegistrar) {
        modId = modid;
        blocks = DeferredRegister.createBlocks(modid);
        this.itemRegistrar = itemRegistrar;
    }

    public void register(IEventBus eventBus) {
        registerBlocks();
        blocks.register(eventBus);
        PaintPallet.blockRegistrars.add(this);
    }

    protected abstract void registerBlocks();

    protected <B extends Block> DeferredBlock<B> basicBlock(String name, Supplier<? extends B> supplier, boolean registerItem) {
        DeferredBlock<B> block = blocks.register(name, supplier);
        if (registerItem) {
            itemRegistrar.blockItem(name, block);
        }
        return block;
    }

    protected <B extends  Block> DeferredBlock<B> basicBlock(String name, Supplier<? extends B> supplier) {
        return basicBlock(name, supplier, true);
    }

    protected <B extends Block> Optional<DeferredBlock<B>> conditionalBlock(boolean run, String name, Supplier<? extends  B> supplier, boolean registerItem) {
        if (run) {
            return Optional.of(basicBlock(name, supplier, registerItem));
        }
        return Optional.empty();
    }

    protected <B extends Block> Optional<DeferredBlock<B>> conditionalBlock(boolean run, String name, Supplier<? extends  B> supplier) {
        return conditionalBlock(run, name, supplier, true);
    }
}
