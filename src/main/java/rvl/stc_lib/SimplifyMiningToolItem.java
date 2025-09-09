package rvl.stc_lib;

import net.minecraft.block.Block;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.TagKey;

public class SimplifyMiningToolItem extends Item {
    public SimplifyMiningToolItem(FixerSettings fix, float attackDamage, float attackSpeed, TagKey<Block> effectiveBlocks, Item.Settings settings) {
        super(fix.applyToolSettings(effectiveBlocks, attackDamage, attackSpeed, settings));
    }

    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return true;
    }

    public void postDamageEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(2, attacker, EquipmentSlot.MAINHAND);
    }
}
