/*
 * Decompiled with CFR 0.2.1 (FabricMC 53fa44c9).
 */
package simplify.toolconfig;

import net.fabricmc.yarn.constants.MiningLevels;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;

public class SimplifyPickaxeItem
extends SimplifyToolItem {
    private final TagKey<Block> effectiveBlocks;
    public SimplifyPickaxeItem(SimplifyToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, BlockTags.PICKAXE_MINEABLE, settings);
        this.effectiveBlocks = BlockTags.PICKAXE_MINEABLE;
    }
    @Override
    public boolean isSuitableFor(BlockState state) {
        int i = this.getMaterial().getMiningLevel();
        if (i < MiningLevels.DIAMOND && state.isIn(BlockTags.NEEDS_DIAMOND_TOOL)) {
            return false;
        }
        if (i < MiningLevels.IRON && state.isIn(BlockTags.NEEDS_IRON_TOOL)) {
            return false;
        }
        if (i < MiningLevels.STONE && state.isIn(BlockTags.NEEDS_STONE_TOOL)) {
            return false;
        }
        return state.isIn(this.effectiveBlocks);
    }
}

