package rvl.stc_lib;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.TagKey;

import java.util.List;

public interface SimplifyToolMaterial {
    public int getDurability();

    public float getMiningSpeedMultiplier();

    public TagKey<Block> getInverseTag();

    public int getEnchantability();

    public TagKey<Item> getRepairIngredient();


}
