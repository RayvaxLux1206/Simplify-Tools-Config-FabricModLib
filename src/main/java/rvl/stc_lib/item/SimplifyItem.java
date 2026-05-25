package rvl.stc_lib.item;


import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class SimplifyItem extends Item {

    public boolean alcaline = false;

    private boolean hasEffectOnTarget = false;
    private boolean HasEffectOnAttacker = false ;

    private Holder<MobEffect> effectOnTarget;
    private Holder<MobEffect> effectOnAttacker;

    private int effectTimeOnTarget;
    private int effectTimeOnAttacker;

    private int targetEffectAmp;
    private int attackerEffeectAmp;

    public SimplifyItem(Item.Properties settings) {
        super(settings);
    }

    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        super.postHurtEnemy(stack, target, attacker);
        if (!target.isClientAuthoritative()) {
            target.addEffect(new MobEffectInstance(this.effectOnTarget, 100, 0));

        }

    }











    //effect methods

    public Item targetEffect(boolean bl, @Nullable Holder<MobEffect> effect, int d, int a) {
        this.hasEffectOnTarget = bl;
        this.effectOnTarget = effect;
        this.effectTimeOnTarget = d;
        this.targetEffectAmp = a;

        return this;
    }

    public Item attackerEffect(boolean bl, @Nullable Holder<MobEffect> effect, int d, int a) {
        this.HasEffectOnAttacker = bl;
        this.effectOnAttacker = effect;
        this.effectTimeOnAttacker = d;
        this.attackerEffeectAmp = a;

        return this;
    }

    public Item bothEffect(boolean bl, @Nullable Holder<MobEffect> effect, int d, int a, boolean bl2, @Nullable Holder<MobEffect> effect2, int d2, int a2) {
        targetEffect(bl, effect, d, a);
        attackerEffect(bl2, effect2, d2, a2);
        return this;
    }
}
