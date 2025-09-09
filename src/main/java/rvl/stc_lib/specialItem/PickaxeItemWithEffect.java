package rvl.stc_lib.specialItem;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import org.jetbrains.annotations.Nullable;
import rvl.stc_lib.SimplifyPickaxeItem;
import rvl.stc_lib.SimplifyToolMaterial;

public class PickaxeItemWithEffect extends SimplifyPickaxeItem {
    private final RegistryEntry<StatusEffect> effectOnTarget;
    private final RegistryEntry<StatusEffect> effectOnAttacker;
    private final boolean hasHitEffect;
    private final boolean HasAttackerEffect;
    private final int effectTime;
    private final int amplifier;
    public PickaxeItemWithEffect(SimplifyToolMaterial material, float attackDamage, float attackSpeed, @Nullable RegistryEntry<StatusEffect> effectOnTarget, @Nullable RegistryEntry<StatusEffect> effectOnAttacker, boolean hasHitEffect, boolean hasAttackerEffect, int effectTime, int amplifier, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
        this.effectOnTarget = effectOnTarget;
        this.effectOnAttacker = effectOnAttacker;
        this.hasHitEffect = hasHitEffect;
        this.HasAttackerEffect = hasAttackerEffect;
        this.effectTime = effectTime;
        this.amplifier = amplifier;
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        super.postHit(stack, target, attacker);
        if (!target.getWorld().isClient()) {
            if (this.hasHitEffect) {
                target.addStatusEffect(new StatusEffectInstance(this.effectOnTarget, this.effectTime, this.amplifier));
            } else if (this.HasAttackerEffect) {
                target.addStatusEffect(new StatusEffectInstance(this.effectOnAttacker, this.effectTime, this.amplifier));
            }


        }

        return true;
    }
}
