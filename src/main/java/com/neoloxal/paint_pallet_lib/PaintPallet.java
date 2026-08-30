package com.neoloxal.paint_pallet_lib;

import com.mojang.logging.LogUtils;
import com.neoloxal.paint_pallet_lib.registrar.BlockRegistrar;
import com.neoloxal.paint_pallet_lib.registrar.ItemRegistrar;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

@Mod(PaintPallet.MODID)
public class PaintPallet {
    public static final String MODID = "paint_pallet";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static List<String> modIds = new ArrayList<>();
    public static List<ItemRegistrar> itemRegisters = new ArrayList<>();
    public static List<BlockRegistrar> blockRegistrars = new ArrayList<>();

    public PaintPallet(IEventBus modEventBus, ModContainer modContainer) {
    }

    public static void registerModID(String modid) {
        if (!modIds.contains(modid)) {
            modIds.add(modid);
        }
    }
}
