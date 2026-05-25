package rvl.stc_lib.item;


import net.minecraft.world.item.Item;
import rvl.stc_lib.FixerSettings;

public class SimplifyPickaxeItem extends SimplifyItem {
    public SimplifyPickaxeItem(FixerSettings fix, float attackDamage, float attackSpeed, Item.Properties settings) {
        super(fix.pickaxe(attackDamage, attackSpeed, settings));
    }
}
