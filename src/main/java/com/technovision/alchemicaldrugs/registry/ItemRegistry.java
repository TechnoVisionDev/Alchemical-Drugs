package com.technovision.alchemicaldrugs.registry;

import com.technovision.alchemicaldrugs.AlchemicalDrugs;
import com.technovision.alchemicaldrugs.item.*;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ItemRegistry {

    public static final FabricItemSettings ITEM_SETTINGS = new FabricItemSettings().group(AlchemicalDrugs.TAB);
    public static CocaineItem COCAINE = new CocaineItem();
    public static MethItem METH = new MethItem();
    public static AcidItem LSD = new AcidItem();
    public static AcidTabItem ACID_TAB = new AcidTabItem();
    public static PsilocybinItem PSILOCYBIN = new PsilocybinItem();

    public static void registerItems() {
        Registry.register(Registry.ITEM, new Identifier(AlchemicalDrugs.MOD_ID, "cocaine"), COCAINE);
        Registry.register(Registry.ITEM, new Identifier(AlchemicalDrugs.MOD_ID, "methamphetamine"), METH);
        Registry.register(Registry.ITEM, new Identifier(AlchemicalDrugs.MOD_ID, "lysergic_acid_diethylamide"), LSD);
        Registry.register(Registry.ITEM, new Identifier(AlchemicalDrugs.MOD_ID, "psilocybin"), PSILOCYBIN);
        Registry.register(Registry.ITEM, new Identifier(AlchemicalDrugs.MOD_ID, "acid_tab"), ACID_TAB);
    }
}
