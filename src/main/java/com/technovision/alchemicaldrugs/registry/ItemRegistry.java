package com.technovision.alchemicaldrugs.registry;

import com.technovision.alchemicaldrugs.AlchemicalDrugs;
import com.technovision.alchemicaldrugs.item.CocaineItem;
import com.technovision.alchemicaldrugs.api.item.ItemWithTooltip;
import com.technovision.alchemicaldrugs.item.AcidTabItem;
import com.technovision.alchemicaldrugs.item.MethItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ItemRegistry {

    public static final FabricItemSettings ITEM_SETTINGS = new FabricItemSettings().group(AlchemicalDrugs.TAB);

    // Chemical compounds
    public static CocaineItem COCAINE = new CocaineItem();
    public static MethItem METH = new MethItem();
    public static Item LSD = new ItemWithTooltip("C₂₀H₂₅N₃O");

    // Crafting materials & Consumables
    public static AcidTabItem ACID_TAB = new AcidTabItem();

    public static void registerItems() {
        // Chemical compounds
        Registry.register(Registry.ITEM, new Identifier(AlchemicalDrugs.MOD_ID, "cocaine"), COCAINE);
        Registry.register(Registry.ITEM, new Identifier(AlchemicalDrugs.MOD_ID, "methamphetamine"), METH);
        Registry.register(Registry.ITEM, new Identifier(AlchemicalDrugs.MOD_ID, "lysergic_acid_diethylamide"), LSD);

        // Crafting materials & Consumables
        Registry.register(Registry.ITEM, new Identifier(AlchemicalDrugs.MOD_ID, "acid_tab"), ACID_TAB);
    }
}
