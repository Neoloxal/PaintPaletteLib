package com.neoloxal.paint_palette_lib.utils;

import net.minecraft.core.Vec3i;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3d;
import org.joml.Vector3f;
import org.joml.Vector3i;

public class Vec3Utils {
    public static Vec3 uniform(double length) {
        return new Vec3(length, length, length);
    }

    public static Vector3i toVector3i(Vec3i vec3) {
        return new Vector3i(vec3.getX(), vec3.getY(), vec3.getZ());
    }

    public static Vector3d toVector3d(Vec3 vec3) {
        return new Vector3d(vec3.x, vec3.y, vec3.z);
    }

    public static Vector3f toVector3f(Vec3 vec3) {
        return new Vector3f((float) vec3.x, (float) vec3.y, (float) vec3.z);
    }
}
