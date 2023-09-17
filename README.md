# Simplify-Tools-Config-FabricModLib
This Minecraft Fabric mod reconfig tool items properties to a simplify base to enter only one value on each tool properties for Minecraft Fabric Mod 1.20.1.

-I add file to reconfig tools properties values 
to simplify Java code and simplify implementation for
your modded tools configuration.

-The base of tool Items is implement, but you must code
the "ToolMaterials" Java file. TridentItem and others are not be 
implement on this mod now, maybe later.

-For attackDamage and attackSpeed are one simple value to add and an example: float attackSpeed = 1.6f ( With Default confuguration: 5.6 Attack Speed) and (With mod configuration: 1.6 attack Speed) 

Example:

ToolMaterials properties value:

public enum ToolMaterials implement SimplyToolMaterial {
     MODDEDMATERIAL(miningLevel, ItemDurability, miningSpeed,  enchantability, repair Ingrediant) 
}


Add Tool Items

public static final Item MODDEDTOOL = register( "name tool", 
new SimplySwordItem( material, attackDamage, attackSpeed, new FabricItemSettings()));
