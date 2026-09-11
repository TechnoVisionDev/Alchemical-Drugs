package com.technovision.alchemicaldrugs.registry;
import com.technovision.alchemicaldrugs.AlchemicalDrugs;
import com.technovision.alchemicaldrugs.api.item.ItemWithTooltip;
import com.technovision.alchemicaldrugs.item.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import java.util.ArrayList;
import java.util.List;
public final class ItemRegistry {
    public static final List<Item> ITEMS = new ArrayList<>();
    public static final CocaineItem COCAINE = register("cocaine", new CocaineItem(properties("cocaine")));
    public static final MethItem METH = register("methamphetamine", new MethItem(properties("methamphetamine")));
    public static final ItemWithTooltip HEROIN = register("heroin", new ItemWithTooltip(properties("heroin"), "C₂₁H₂₃NO₅"));
    public static final AcidItem LSD = register("lysergic_acid_diethylamide", new AcidItem(properties("lysergic_acid_diethylamide")));
    public static final PsilocybinItem PSILOCYBIN = register("psilocybin", new PsilocybinItem(properties("psilocybin")));
    public static final ItemWithTooltip HYDROCODONE = register("hydrocodone", new ItemWithTooltip(properties("hydrocodone"), "C₁₈H₂₁NO₃"));
    public static final ItemWithTooltip DINITROGEN_TETROXIDE = register("dinitrogen_tetroxide", new ItemWithTooltip(properties("dinitrogen_tetroxide"), "N₂O₄"));
    public static final ItemWithTooltip PENICILLIN = register("penicillin", new ItemWithTooltip(properties("penicillin"), "C₁₆H₁₈(N₂O₄)S"));
    public static final AcidTabItem ACID_TAB = register("acid_tab", new AcidTabItem(properties("acid_tab")));
    public static final Item SYRINGE = register("syringe", new Item(properties("syringe")));
    public static final AdrenalineItem ADRENALINE_SHOT = register("adrenaline_shot", new AdrenalineItem(properties("adrenaline_shot")));
    public static final CaffeineItem CAFFEINE_SHOT = register("caffeine_shot", new CaffeineItem(properties("caffeine_shot")));
    public static final HeroinItem HEROIN_SHOT = register("heroin_shot", new HeroinItem(properties("heroin_shot")));
    public static final AspirinItem ASPIRIN = register("aspirin", new AspirinItem(properties("aspirin")));
    public static final AntibioticsItem ANTIBIOTICS = register("antibiotics", new AntibioticsItem(properties("antibiotics")));
    public static final PainkillerItem PAINKILLERS = register("painkillers", new PainkillerItem(properties("painkillers")));
    public static final LeanItem LEAN = register("lean", new LeanItem(properties("lean")));
    private static Item.Properties properties(String id) {
        return new Item.Properties().setId(ResourceKey.create(Registries.ITEM, AlchemicalDrugs.id(id)));
    }
    private static <T extends Item> T register(String id, T item) {
        Registry.register(BuiltInRegistries.ITEM, AlchemicalDrugs.id(id), item);
        ITEMS.add(item);
        return item;
    }
    public static void registerItems() { }
}
