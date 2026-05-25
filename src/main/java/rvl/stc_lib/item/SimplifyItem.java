package rvl.stc_lib.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import org.jetbrains.annotations.Nullable;

public class SimplifyItem extends Item {

    private boolean hasEffectOnTarget = false;
    private boolean hasEffectOnAttacker = false ;

    private RegistryEntry<StatusEffect> effectOnTarget;
    private RegistryEntry<StatusEffect> effectOnAttacker;

    private int effectTimeOnTarget;
    private int effectTimeOnAttacker;

    private int targetEffectAmp;
    private int attackerEffectAmp;

    public SimplifyItem(Settings settings) {
        super(settings);
    }


    public void postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        super.postHit(stack, target, attacker);
        if (!target.getEntityWorld().isClient()) {
            if (hasEffectOnTarget) {
                target.addStatusEffect(new StatusEffectInstance(effectOnTarget, effectTimeOnTarget, targetEffectAmp));
            }
            if (hasEffectOnAttacker) {
                attacker.addStatusEffect(new StatusEffectInstance(effectOnAttacker, effectTimeOnAttacker, attackerEffectAmp));
            }

        }

    }


    public Item addTargetEffect(boolean bl, @Nullable RegistryEntry<StatusEffect> effect, int d, int a) {
        this.hasEffectOnTarget = bl;
        this.effectOnTarget = effect;
        this.effectTimeOnTarget = d;
        this.targetEffectAmp = a;

        return this;
    }

    public Item addAttackerEffect(boolean bl, @Nullable RegistryEntry<StatusEffect> effect, int d, int a) {
        this.hasEffectOnAttacker = bl;
        this.effectOnAttacker = effect;
        this.effectTimeOnAttacker = d;
        this.attackerEffectAmp = a;

        return this;
    }

    public Item addBothEffect(boolean bl, @Nullable RegistryEntry<StatusEffect> effect, int d, int a, boolean bl2, @Nullable RegistryEntry<StatusEffect> effect2, int d2, int a2) {
        addTargetEffect(bl, effect, d, a);
        addAttackerEffect(bl2, effect2, d2, a2);
        return this;
    }
}
