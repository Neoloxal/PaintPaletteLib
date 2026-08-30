package com.neoloxal.paint_palette_lib.utils;

import net.minecraft.world.phys.Vec3;

public class Vec3Utils {
    public static Vec3 uniform(double length) {
        return new Vec3(length, length, length);
    }
}
