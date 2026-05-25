package rvl.stc_lib.item;


import rvl.stc_lib.FixerSettings;

public class SimplifySpearItem extends SimplifyItem {

    public SimplifySpearItem(FixerSettings fix, float attackDamage, float swingAnimationSeconds, float chargeDamageMultiplier, float chargeDelaySeconds, float maxDurationForDismountSeconds, float minSpeedForDismount, float maxDurationForChargeKnockbackInSeconds, float maxDurationForChargeDamageInSeconds, net.minecraft.world.item.Item.Properties settings) {
        super(fix.spear(attackDamage, swingAnimationSeconds, chargeDamageMultiplier, chargeDelaySeconds, maxDurationForDismountSeconds, minSpeedForDismount, maxDurationForChargeKnockbackInSeconds, 5.1f, maxDurationForChargeDamageInSeconds, 4.6f, settings));
    }
}
