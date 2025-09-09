package rvl.stc_lib;

import net.minecraft.block.Block;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.TagKey;

import java.util.List;

public interface SimplifyToolMaterial {
    public int getDurability();

    public float getMiningSpeedMultiplier();

    public TagKey<Block> getInverseTag();

    public int getEnchantability();

    public Ingredient getRepairIngredient();

    default ToolComponent createComponent(TagKey<Block> tag) {
        return new ToolComponent(List.of(ToolComponent.Rule.ofNeverDropping(this.getInverseTag()), ToolComponent.Rule.ofAlwaysDropping(tag, this.getMiningSpeedMultiplier())), 1.0F, 1);
    }
}
