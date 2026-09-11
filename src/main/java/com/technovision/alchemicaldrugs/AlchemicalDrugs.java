package com.technovision.alchemicaldrugs;
import com.technovision.alchemicaldrugs.effect.DrugEffects;
import com.technovision.alchemicaldrugs.registry.ItemRegistry;
import com.technovision.alchemicaldrugs.registry.VillagerRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
public class AlchemicalDrugs implements ModInitializer {
    public static final String MOD_ID = "alchemicaldrugs";
    public static Identifier id(String path) { return Identifier.fromNamespaceAndPath(MOD_ID, path); }
    @Override public void onInitialize() {
        ItemRegistry.registerItems();
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, id("tab"), FabricCreativeModeTab.builder()
            .title(Component.translatable("itemGroup.alchemicaldrugs.tab"))
            .icon(() -> new ItemStack(ItemRegistry.ANTIBIOTICS))
            .displayItems((parameters, output) -> ItemRegistry.ITEMS.forEach(output::accept)).build());
        VillagerRegistry.registerTrades();
        DrugEffects.initialize();
    }
}
