package simplify.toolconfig;

import net.minecraft.recipe.Ingredient;

public interface SimplifyToolMaterial {
    public int getDurability();

    public float getMiningSpeedMultiplier();

    public int getMiningLevel();

    public int getEnchantability();

    public Ingredient getRepairIngredient();
}
