package rvl.stc_lib;


import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public interface SimplifyToolMaterial {
    public int getDurability();

    public float getMiningSpeedMultiplier();

    public TagKey<Block> getInverseTag();

    public int getEnchantability();

    public TagKey<Item> getRepairIngredient();


}
