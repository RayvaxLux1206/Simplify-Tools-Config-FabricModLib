package rvl.stc_lib;

import net.minecraft.block.Block;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.TagKey;

public class SimplifyMiningToolItem extends SimplifyToolItem {
    public SimplifyMiningToolItem(SimplifyToolMaterial material, float attackDamage, float attackSpeed, TagKey<Block> effectiveBlocks, Item.Settings settings) {
        super(material, attackDamage, attackSpeed, settings.component(DataComponentTypes.TOOL, material.createComponent(effectiveBlocks)));
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        super.postHit(stack, target, attacker);
        stack.damage(2, attacker, EquipmentSlot.MAINHAND);
        return true;
    }
}
