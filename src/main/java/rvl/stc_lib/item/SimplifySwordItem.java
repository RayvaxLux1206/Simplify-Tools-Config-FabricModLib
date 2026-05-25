package rvl.stc_lib.item;


import net.minecraft.world.item.Item;
import rvl.stc_lib.FixerSettings;

public class SimplifySwordItem extends SimplifyItem {
    public SimplifySwordItem(FixerSettings fix, float damage, float speed, Item.Properties settings) {
        super(fix.sword(damage, speed, settings));
    }

}
