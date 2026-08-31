package com.neoloxal.paint_palette_lib.builtin.item;

import com.neoloxal.paint_palette_lib.builtin.registrar.LibDataComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ToggleStick extends FunnyStick {
    public ToggleStick(Properties properties) {
        super(properties.component(LibDataComponents.TOGGLE.get(), false));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        stack.set(LibDataComponents.TOGGLE.get(), Boolean.FALSE.equals(stack.get(LibDataComponents.TOGGLE.get())));
        return InteractionResultHolder.success(stack);
    }
}
