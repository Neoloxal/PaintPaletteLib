package com.neoloxal.paint_palette_lib.utils;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class TargetUtils {
    public static Vec3 getClickLocation(Player player, int distance) {
        return player.getEyePosition(1).add(player.getLookAngle().scale(distance));
    }

    public static BlockHitResult getClickedBlock(Player player, Level level, int distance) {
        BlockHitResult serverHit = level.clip(new ClipContext(
                player.getEyePosition(),
                player.getEyePosition().add(player.getViewVector(1).scale(distance)),
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                player
        ));
        return serverHit;
    }

    public static BlockHitResult getClickedBlock(Player player, Level level) {
        return getClickedBlock(player, level, 999999);
    }
}
