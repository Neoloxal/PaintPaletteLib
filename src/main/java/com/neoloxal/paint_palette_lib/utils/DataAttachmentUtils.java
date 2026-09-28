package com.neoloxal.paint_palette_lib.utils;

import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.attachment.AttachmentType;

public class DataAttachmentUtils {
    public static void changeData(Entity entity, AttachmentType<Integer> type, Integer increment) {
        entity.setData(type, entity.getData(type) + increment);
    }

    public static void changeData(Entity entity, AttachmentType<Long> type, Long increment) {
        entity.setData(type, entity.getData(type) + increment);
    }

    public static void changeData(Entity entity, AttachmentType<Double> type, Double increment) {
        entity.setData(type, entity.getData(type) + increment);
    }

    public static void changeData(Entity entity, AttachmentType<Float> type, Float increment) {
        entity.setData(type, entity.getData(type) + increment);
    }
}
