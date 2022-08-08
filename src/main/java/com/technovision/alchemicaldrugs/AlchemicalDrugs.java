package com.technovision.alchemicaldrugs;

import com.technovision.alchemicaldrugs.registry.ItemRegistry;
import com.technovision.alchemicaldrugs.registry.VillagerRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class AlchemicalDrugs implements ModInitializer {

    public static String MOD_ID = "alchemicaldrugs";

    public static final ItemGroup TAB = FabricItemGroupBuilder.build(
            new Identifier(MOD_ID, "tab"),
            () -> new ItemStack(ItemRegistry.ANTIBIOTICS)
    );

    @Override
    public void onInitialize() {
        ItemRegistry.registerItems();
        VillagerRegistry.registerTrades();
    }
}
