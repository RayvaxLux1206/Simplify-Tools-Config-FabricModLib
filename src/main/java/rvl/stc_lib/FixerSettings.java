package rvl.stc_lib;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.*;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.LazyRegistryEntryReference;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.SwingAnimationType;

import java.util.List;
import java.util.Optional;

public class FixerSettings {
    private final SimplifyToolMaterial material;

    public FixerSettings(SimplifyToolMaterial material) {
        this.material = material;
    }

    public Item.Settings applyBaseSettings(Item.Settings settings) {
        return settings.maxDamage(this.material.getDurability()).repairable(this.material.getRepairIngredient()).enchantable(this.material.getEnchantability());
    }

    public Item.Settings applyToolSettings(Item.Settings settings, TagKey<Block> effectiveBlocks, float attackDamage, float attackSpeed, float disableBlockingForSeconds) {
        RegistryEntryLookup<Block> registryEntryLookup = Registries.createEntryLookup(Registries.BLOCK);
        return this.applyBaseSettings(settings).component(DataComponentTypes.TOOL, new ToolComponent(List.of(ToolComponent.Rule.ofNeverDropping(registryEntryLookup.getOrThrow(this.material.getInverseTag())), ToolComponent.Rule.ofAlwaysDropping(registryEntryLookup.getOrThrow(effectiveBlocks), this.material.getMiningSpeedMultiplier())), 1.0F, 1, true)).attributeModifiers(this.createToolAttributeModifiers(attackDamage, attackSpeed)).component(DataComponentTypes.WEAPON, new WeaponComponent(2, disableBlockingForSeconds));
    }

    public Item.Settings axe(float attackDamage, float attackSpeed, Item.Settings settings) {
        return this.applyToolSettings(settings, BlockTags.AXE_MINEABLE, attackDamage, attackSpeed, 5f);
    }

    public Item.Settings shovel(float attackDamage, float attackSpeed, Item.Settings settings) {
        return this.applyToolSettings(settings, BlockTags.SHOVEL_MINEABLE, attackDamage, attackSpeed, 0f);
    }

    public Item.Settings hoe(float attackDamage, float attackSpeed, Item.Settings settings) {
        return this.applyToolSettings(settings, BlockTags.HOE_MINEABLE, attackDamage, attackSpeed, 0f);
    }

    public Item.Settings pickaxe(float attackDamage, float attackSpeed, Item.Settings settings) {
        return this.applyToolSettings(settings, BlockTags.PICKAXE_MINEABLE, attackDamage, attackSpeed, 0f);
    }

    private AttributeModifiersComponent createToolAttributeModifiers(float attackDamage, float attackSpeed) {
        return AttributeModifiersComponent.builder().add(EntityAttributes.ATTACK_DAMAGE, new EntityAttributeModifier(Item.BASE_ATTACK_DAMAGE_MODIFIER_ID, (double)(attackDamage - 1), EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND).add(EntityAttributes.ATTACK_SPEED, new EntityAttributeModifier(Item.BASE_ATTACK_SPEED_MODIFIER_ID, (double)attackSpeed -4, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND).build();
    }

    public Item.Settings sword(float attackDamage, float attackSpeed, Item.Settings settings) {
        RegistryEntryLookup<Block> registryEntryLookup = Registries.createEntryLookup(Registries.BLOCK);
        return this.applyBaseSettings(settings).component(DataComponentTypes.TOOL, new ToolComponent(List.of(setComponent(Blocks.COBWEB, 15f), ToolComponent.Rule.of(registryEntryLookup.getOrThrow(BlockTags.SWORD_EFFICIENT), 1.5F), setComponent(Blocks.BAMBOO, 100f), ToolComponent.Rule.of(registryEntryLookup.getOrThrow(BlockTags.SWORD_EFFICIENT), 1.5F)), 1.0F, 2, false)).attributeModifiers(this.createSwordAttributeModifiers(attackDamage, attackSpeed));
    }

    private AttributeModifiersComponent createSwordAttributeModifiers(float attackDamage, float attackSpeed) {
        return AttributeModifiersComponent.builder().add(EntityAttributes.ATTACK_DAMAGE, new EntityAttributeModifier(Item.BASE_ATTACK_DAMAGE_MODIFIER_ID, (double)(attackDamage -1), EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND).add(EntityAttributes.ATTACK_SPEED, new EntityAttributeModifier(Item.BASE_ATTACK_SPEED_MODIFIER_ID, (double)attackSpeed -4, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND).build();
    }

    public Item.Settings spear(float swingAnimationSeconds, float chargeDamageMultiplier, float chargeDelaySeconds, float maxDurationForDismountSeconds, float minSpeedForDismount, float maxDurationForChargeKnockbackInSeconds, float minSpeedForChargeKnockback, float maxDurationForChargeDamageInSeconds, float minRelativeSpeedForChargeDamage, Item.Settings settings) {
        return applyBaseSettings(settings)
                .component(DataComponentTypes.DAMAGE_TYPE, new LazyRegistryEntryReference(DamageTypes.SPEAR))
                .component(DataComponentTypes.KINETIC_WEAPON, new KineticWeaponComponent(10, (int)(chargeDelaySeconds * 20.0F), KineticWeaponComponent.Condition.ofMinSpeed((int)(maxDurationForDismountSeconds * 20.0F), minSpeedForDismount), KineticWeaponComponent.Condition.ofMinSpeed((int)(maxDurationForChargeKnockbackInSeconds * 20.0F), minSpeedForChargeKnockback), KineticWeaponComponent.Condition.ofMinRelativeSpeed((int)(maxDurationForChargeDamageInSeconds * 20.0F), minRelativeSpeedForChargeDamage), 0.38F, chargeDamageMultiplier, Optional.of(SoundEvents.ITEM_SPEAR_USE), Optional.of(SoundEvents.ITEM_SPEAR_HIT)))
                .component(DataComponentTypes.PIERCING_WEAPON, new PiercingWeaponComponent(true, false, Optional.of(SoundEvents.ITEM_SPEAR_ATTACK), Optional.of(SoundEvents.ITEM_SPEAR_HIT)))
                .component(DataComponentTypes.ATTACK_RANGE, new AttackRangeComponent(2.0F, 4.5F, 2.0F, 6.5F, 0.125F, 0.5F))
                .component(DataComponentTypes.MINIMUM_ATTACK_CHARGE, 1.0F).component(DataComponentTypes.SWING_ANIMATION, new SwingAnimationComponent(SwingAnimationType.STAB, (int)(swingAnimationSeconds * 20.0F)))
                .attributeModifiers(AttributeModifiersComponent.builder().add(EntityAttributes.ATTACK_DAMAGE, new EntityAttributeModifier(Item.BASE_ATTACK_DAMAGE_MODIFIER_ID, (double)(0.0F), EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND).add(EntityAttributes.ATTACK_SPEED, new EntityAttributeModifier(Item.BASE_ATTACK_SPEED_MODIFIER_ID, (double)(1.0F / swingAnimationSeconds) - (double)4.0F, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND).build())
                .component(DataComponentTypes.USE_EFFECTS, new UseEffectsComponent(true, false, 1.0F))
                .component(DataComponentTypes.WEAPON, new WeaponComponent(1));
    }


    private static ToolComponent.Rule setComponent(Block block, float speed) {
        return ToolComponent.Rule.ofAlwaysDropping(RegistryEntryList.of(block.getRegistryEntry()), speed);
    }


}
