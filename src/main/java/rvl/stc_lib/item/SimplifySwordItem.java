package rvl.stc_lib.item;

import net.minecraft.item.Item;
import rvl.stc_lib.FixerSettings;

public class SimplifySwordItem extends Item {
    public SimplifySwordItem(FixerSettings fix, float damage, float speed, Settings settings) {
        super(fix.sword(damage, speed, settings));
    }

}
