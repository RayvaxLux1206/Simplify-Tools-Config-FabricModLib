package rvl.stc_lib.item;

import net.minecraft.item.Item;
import rvl.stc_lib.FixerSettings;

public class SimplifyPickaxeItem extends Item {
    public SimplifyPickaxeItem(FixerSettings fix, float attackDamage, float attackSpeed, Settings settings) {
        super(fix.pickaxe(attackDamage, attackSpeed, settings));
    }
}
