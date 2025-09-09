package rvl.stc_lib;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SimplifyToolItem extends Item {
    private final SimplifyToolMaterial material;

    public SimplifyToolItem(SimplifyToolMaterial material, Settings settings) {
        super(settings.maxDamageIfAbsent(material.getDurability()));
        this.material = material;
    }

    public SimplifyToolMaterial getMaterial() {
        return this.material;
    }

    public int getEnchantability() {
        return this.material.getEnchantability();
    }

    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return this.material.getRepairIngredient().test(ingredient) || super.canRepair(stack, ingredient);
    }
}
