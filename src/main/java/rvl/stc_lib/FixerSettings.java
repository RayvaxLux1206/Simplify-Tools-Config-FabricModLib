package rvl.stc_lib;


import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwingAnimationType;
import net.minecraft.world.item.component.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.List;
import java.util.Optional;

public class FixerSettings {
    private final SimplifyToolMaterial material;

    public FixerSettings(SimplifyToolMaterial material) {
        this.material = material;
    }

    public Item.Properties applyBaseSettings(Item.Properties settings) {
        return settings.durability(this.material.getDurability()).repairable(this.material.getRepairIngredient()).enchantable(this.material.getEnchantability());
    }

    public Item.Properties applyToolSettings(Item.Properties settings, TagKey<Block> effectiveBlocks, float attackDamage, float attackSpeed, float disableBlockingForSeconds) {
        HolderGetter<Block> registrationLookup = BuiltInRegistries.acquireBootstrapRegistrationLookup(BuiltInRegistries.BLOCK);
        return this.applyBaseSettings(settings).component(DataComponents.TOOL, new Tool(List.of(Tool.Rule.deniesDrops(registrationLookup.getOrThrow(this.material.getInverseTag())), Tool.Rule.minesAndDrops(registrationLookup.getOrThrow(effectiveBlocks), this.material.getMiningSpeedMultiplier())), 1.0F, 1, true)).attributes(this.createToolAttributeModifiers(attackDamage, attackSpeed)).component(DataComponents.WEAPON, new Weapon(2, disableBlockingForSeconds));
    }

    public Item.Properties tool(Item.Properties settings, TagKey<Block> effectiveBlocks, float attackDamage, float attackSpeed, float disableBlockingForSeconds) {
        return applyToolSettings(settings, effectiveBlocks, attackDamage, attackSpeed, disableBlockingForSeconds);
    }

    public Item.Properties axe(float attackDamage, float attackSpeed, Item.Properties settings) {
        return this.tool(settings, BlockTags.MINEABLE_WITH_AXE, attackDamage, attackSpeed, 5f);
    }

    public Item.Properties shovel(float attackDamage, float attackSpeed, Item.Properties settings) {
        return this.tool(settings, BlockTags.MINEABLE_WITH_SHOVEL, attackDamage, attackSpeed, 0f);
    }

    public Item.Properties hoe(float attackDamage, float attackSpeed, Item.Properties settings) {
        return this.tool(settings, BlockTags.MINEABLE_WITH_HOE, attackDamage, attackSpeed, 0f);
    }

    public Item.Properties pickaxe(float attackDamage, float attackSpeed, Item.Properties settings) {
        return this.tool(settings, BlockTags.MINEABLE_WITH_PICKAXE, attackDamage, attackSpeed, 0f);
    }

    private ItemAttributeModifiers createToolAttributeModifiers(float attackDamage, float attackSpeed) {
        return ItemAttributeModifiers.builder().add(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID, (double)(attackDamage - 1), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).add(Attributes.ATTACK_SPEED, new AttributeModifier(Item.BASE_ATTACK_SPEED_ID, (double)attackSpeed -4, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build();
    }

    public Item.Properties sword(float attackDamage, float attackSpeed, Item.Properties settings) {
        HolderGetter<Block> registrationLookup = BuiltInRegistries.acquireBootstrapRegistrationLookup(BuiltInRegistries.BLOCK);
        return this.applyBaseSettings(settings).component(DataComponents.TOOL, new Tool(List.of(Tool.Rule.minesAndDrops(HolderSet.direct(new Holder[]{Blocks.COBWEB.builtInRegistryHolder()}), 15.0F), Tool.Rule.overrideSpeed(registrationLookup.getOrThrow(BlockTags.SWORD_INSTANTLY_MINES), Float.MAX_VALUE), Tool.Rule.overrideSpeed(registrationLookup.getOrThrow(BlockTags.SWORD_EFFICIENT), 1.5F)), 1.0F, 2, false)).attributes(this.createSwordAttributeModifiers(attackDamage, attackSpeed)).component(DataComponents.WEAPON, new Weapon(1));
    }

    private ItemAttributeModifiers createSwordAttributeModifiers(float attackDamage, float attackSpeed) {
        return ItemAttributeModifiers.builder().add(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID, (double)(attackDamage -1), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).add(Attributes.ATTACK_SPEED, new AttributeModifier(Item.BASE_ATTACK_SPEED_ID, (double)attackSpeed -4, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build();
    }

    public Item.Properties spear(float attackDamage, final float attackDuration, final float damageMultiplier, final float delay, final float dismountTime, final float dismountThreshold, final float knockbackTime, final float knockbackThreshold, final float damageTime, final float damageThreshold, Item.Properties settings) {
        return applyBaseSettings(settings)
                .delayedHolderComponent(DataComponents.DAMAGE_TYPE, DamageTypes.SPEAR)
                .component(DataComponents.KINETIC_WEAPON, new KineticWeapon(10, (int)(delay * 20.0F), KineticWeapon.Condition.ofAttackerSpeed((int)(dismountTime * 20.0F), dismountThreshold), KineticWeapon.Condition.ofAttackerSpeed((int)(knockbackTime * 20.0F), knockbackThreshold), KineticWeapon.Condition.ofRelativeSpeed((int)(damageTime * 20.0F), damageThreshold), 0.38F, damageMultiplier, Optional.of(SoundEvents.SPEAR_ATTACK), Optional.of(SoundEvents.SPEAR_HIT)))
                .component(DataComponents.PIERCING_WEAPON, new PiercingWeapon(true, false, Optional.of(SoundEvents.SPEAR_ATTACK), Optional.of(SoundEvents.SPEAR_HIT)))
                .component(DataComponents.ATTACK_RANGE, new AttackRange(2.0F, 4.5F, 2.0F, 6.5F, 0.125F, 0.5F))
                .component(DataComponents.MINIMUM_ATTACK_CHARGE, 1.0F).component(DataComponents.SWING_ANIMATION, new SwingAnimation(SwingAnimationType.STAB, (int)(attackDuration * 20.0F))).attributes(ItemAttributeModifiers.builder().add(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID, (double)(attackDamage), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).add(Attributes.ATTACK_SPEED, new AttributeModifier(Item.BASE_ATTACK_SPEED_ID, (double)(1.0F / attackDuration) - (double)4.0F, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build())
                .component(DataComponents.USE_EFFECTS, new UseEffects(true, false, 1.0F))
                .component(DataComponents.WEAPON, new Weapon(1));
    }













}
