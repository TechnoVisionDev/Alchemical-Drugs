package com.technovision.alchemicaldrugs.registry;

import com.technovision.alchemicaldrugs.AlchemicalDrugs;
import com.technovision.alchemicaldrugs.api.item.ItemWithTooltip;
import com.technovision.alchemicaldrugs.item.*;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ItemRegistry {

    public static final FabricItemSettings ITEM_SETTINGS = new FabricItemSettings().group(AlchemicalDrugs.TAB);
    public static CocaineItem COCAINE = new CocaineItem();
    public static MethItem METH = new MethItem();
    public static ItemWithTooltip HEROIN = new ItemWithTooltip("C₂₁H₂₃NO₅");
    public static AcidItem LSD = new AcidItem();
    public static PsilocybinItem PSILOCYBIN = new PsilocybinItem();
    public static AcidTabItem ACID_TAB = new AcidTabItem();
    public static Item SYRINGE = new Item(ITEM_SETTINGS);
    public static AdrenalineItem ADRENALINE_SHOT = new AdrenalineItem();
    public static CaffeineItem CAFFEINE_SHOT = new CaffeineItem();
    public static HeroinItem HEROIN_SHOT = new HeroinItem();
    public static AspirinItem ASPIRIN = new AspirinItem();
    public static AntibioticsItem ANTIBIOTICS = new AntibioticsItem();

    public static void registerItems() {
        Registry.register(Registry.ITEM, new Identifier(AlchemicalDrugs.MOD_ID, "cocaine"), COCAINE);
        Registry.register(Registry.ITEM, new Identifier(AlchemicalDrugs.MOD_ID, "methamphetamine"), METH);
        Registry.register(Registry.ITEM, new Identifier(AlchemicalDrugs.MOD_ID, "heroin"), HEROIN);
        Registry.register(Registry.ITEM, new Identifier(AlchemicalDrugs.MOD_ID, "lysergic_acid_diethylamide"), LSD);
        Registry.register(Registry.ITEM, new Identifier(AlchemicalDrugs.MOD_ID, "psilocybin"), PSILOCYBIN);
        Registry.register(Registry.ITEM, new Identifier(AlchemicalDrugs.MOD_ID, "acid_tab"), ACID_TAB);
        Registry.register(Registry.ITEM, new Identifier(AlchemicalDrugs.MOD_ID, "syringe"), SYRINGE);
        Registry.register(Registry.ITEM, new Identifier(AlchemicalDrugs.MOD_ID, "adrenaline_shot"), ADRENALINE_SHOT);
        Registry.register(Registry.ITEM, new Identifier(AlchemicalDrugs.MOD_ID, "caffeine_shot"), CAFFEINE_SHOT);
        Registry.register(Registry.ITEM, new Identifier(AlchemicalDrugs.MOD_ID, "heroin_shot"), HEROIN_SHOT);
        Registry.register(Registry.ITEM, new Identifier(AlchemicalDrugs.MOD_ID, "aspirin"), ASPIRIN);
        Registry.register(Registry.ITEM, new Identifier(AlchemicalDrugs.MOD_ID, "antibiotics"), ANTIBIOTICS);
    }
}
