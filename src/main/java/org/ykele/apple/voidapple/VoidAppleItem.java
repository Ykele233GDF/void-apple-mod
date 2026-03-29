package org.ykele.apple.voidapple;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class VoidAppleItem extends Item {
    public VoidAppleItem() {
        super(new Properties()
                .food(new FoodProperties.Builder()
                        .nutrition(0)
                        .saturationMod(0)
                        .alwaysEat()
                        .build())
        );
    }
    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        ItemStack result = super.finishUsingItem(stack, level, entity);
        if (!level.isClientSide && entity instanceof Player player) {
            ServerLevel endDimension = player.getServer().getLevel(Level.END);

            if (endDimension != null) {
                player.changeDimension(endDimension);
                player.teleportTo(100, 50, 0);
                player.playSound(net.minecraft.sounds.SoundEvents.PORTAL_TRAVEL, 1.0F, 1.0F);
            }
        }
        return result;
    }
}