/*
 * Decompiled with CFR 0.2.1 (FabricMC 53fa44c9).
 */
package rvl.stc_lib;

import net.minecraft.registry.tag.BlockTags;

public class SimplifyPickaxeItem
extends SimplifyMiningToolItem {
    public SimplifyPickaxeItem(SimplifyToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, BlockTags.PICKAXE_MINEABLE, settings);
    }
}

