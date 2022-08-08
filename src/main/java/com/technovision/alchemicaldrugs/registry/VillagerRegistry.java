package com.technovision.alchemicaldrugs.registry;

import com.google.common.collect.ImmutableSet;
import com.technovision.alchemicaldrugs.AlchemicalDrugs;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.object.builder.v1.villager.VillagerProfessionBuilder;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestType;

public class VillagerRegistry {

    public static final PointOfInterestType DEALER_POI = registerPOI("dealer_poi", Blocks.CARVED_PUMPKIN);
    public static final VillagerProfession DEALER = registerProfession("dealer", RegistryKey.of(Registry.POINT_OF_INTEREST_TYPE_KEY, new Identifier(AlchemicalDrugs.MOD_ID, "dealer_poi")));

    public static VillagerProfession registerProfession(String name, RegistryKey<PointOfInterestType> type) {
        Identifier id = new Identifier(AlchemicalDrugs.MOD_ID, name);
        return Registry.register(Registry.VILLAGER_PROFESSION, id,
                VillagerProfessionBuilder.create().id(id)
                        .workstation(type)
                        .workSound(SoundEvents.ENTITY_VILLAGER_WORK_WEAPONSMITH)
                        .build()
        );
    }

    public static PointOfInterestType registerPOI(String name, Block block) {
        return PointOfInterestHelper.register(new Identifier(AlchemicalDrugs.MOD_ID, name), 1, 1, ImmutableSet.copyOf(block.getStateManager().getStates()));
    }

    public static void registerTrades() {
        // Level 1 trades
        TradeOfferHelper.registerVillagerOffers(DEALER, 1, factories -> {
            // Sell
            factories.add(((entity, random) -> new TradeOffer(
                    new ItemStack(ItemRegistry.COCAINE),
                    new ItemStack(Items.EMERALD, 2),
                    6, 2, 0.02f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                    new ItemStack(ItemRegistry.METH),
                    new ItemStack(Items.EMERALD, 2),
                    6, 2, 0.02f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                    new ItemStack(ItemRegistry.HEROIN),
                    new ItemStack(Items.EMERALD, 2),
                    6, 2, 0.02f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                    new ItemStack(ItemRegistry.PAINKILLERS),
                    new ItemStack(Items.EMERALD, 2),
                    6, 2, 0.02f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                    new ItemStack(ItemRegistry.PSILOCYBIN),
                    new ItemStack(Items.EMERALD, 2),
                    6, 2, 0.02f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                    new ItemStack(ItemRegistry.LSD),
                    new ItemStack(Items.EMERALD, 2),
                    6, 2, 0.02f
            )));
            // Buy
            factories.add(((entity, random) -> new TradeOffer(
                    new ItemStack(Items.EMERALD, 5),
                    new ItemStack(ItemRegistry.COCAINE),
                    6, 2, 0.02f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                    new ItemStack(Items.EMERALD, 5),
                    new ItemStack(ItemRegistry.METH),
                    6, 2, 0.02f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                    new ItemStack(Items.EMERALD, 5),
                    new ItemStack(ItemRegistry.HEROIN),
                    6, 2, 0.02f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                    new ItemStack(Items.EMERALD, 5),
                    new ItemStack(ItemRegistry.PSILOCYBIN),
                    6, 2, 0.02f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                    new ItemStack(Items.EMERALD, 5),
                    new ItemStack(ItemRegistry.LSD),
                    6, 2, 0.02f
            )));
        });

        // Level 2 trades
        TradeOfferHelper.registerVillagerOffers(DEALER, 2, factories -> {
            // Sell
            factories.add(((entity, random) -> new TradeOffer(
                    new ItemStack(ItemRegistry.COCAINE),
                    new ItemStack(Items.EMERALD, 2),
                    8, 10, 0.02f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                    new ItemStack(ItemRegistry.METH),
                    new ItemStack(Items.EMERALD, 3),
                    8, 10, 0.02f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                    new ItemStack(ItemRegistry.HEROIN),
                    new ItemStack(Items.EMERALD, 2),
                    8, 10, 0.02f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                    new ItemStack(ItemRegistry.PAINKILLERS),
                    new ItemStack(Items.EMERALD, 3),
                    8, 10, 0.02f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                    new ItemStack(ItemRegistry.PSILOCYBIN),
                    new ItemStack(Items.EMERALD, 3),
                    8, 10, 0.02f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                    new ItemStack(ItemRegistry.LSD),
                    new ItemStack(Items.EMERALD, 2),
                    8, 10, 0.02f
            )));
            // Buy
            factories.add(((entity, random) -> new TradeOffer(
                    new ItemStack(Items.EMERALD, 4),
                    new ItemStack(ItemRegistry.COCAINE),
                    8, 10, 0.02f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                    new ItemStack(Items.EMERALD, 5),
                    new ItemStack(ItemRegistry.METH),
                    8, 10, 0.02f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                    new ItemStack(Items.EMERALD, 4),
                    new ItemStack(ItemRegistry.HEROIN),
                    8, 10, 0.02f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                    new ItemStack(Items.EMERALD, 5),
                    new ItemStack(ItemRegistry.PSILOCYBIN),
                    8, 10, 0.02f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                    new ItemStack(Items.EMERALD, 4),
                    new ItemStack(ItemRegistry.LSD),
                    8, 10, 0.02f
            )));
        });

        // Level 3 trades
        TradeOfferHelper.registerVillagerOffers(DEALER, 3, factories -> {
            // Sell
            factories.add(((entity, random) -> new TradeOffer(
                    new ItemStack(ItemRegistry.COCAINE),
                    new ItemStack(Items.EMERALD, 3),
                    10, 18, 0.02f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                    new ItemStack(ItemRegistry.METH),
                    new ItemStack(Items.EMERALD, 4),
                    10, 18, 0.02f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                    new ItemStack(ItemRegistry.HEROIN),
                    new ItemStack(Items.EMERALD, 3),
                    10, 18, 0.02f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                    new ItemStack(ItemRegistry.PAINKILLERS),
                    new ItemStack(Items.EMERALD, 4),
                    10, 18, 0.02f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                    new ItemStack(ItemRegistry.PSILOCYBIN),
                    new ItemStack(Items.EMERALD, 5),
                    10, 18, 0.02f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                    new ItemStack(ItemRegistry.LSD),
                    new ItemStack(Items.EMERALD, 4),
                    10, 18, 0.02f
            )));
            // Buy
            factories.add(((entity, random) -> new TradeOffer(
                    new ItemStack(Items.EMERALD, 4),
                    new ItemStack(ItemRegistry.COCAINE),
                    10, 18, 0.02f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                    new ItemStack(Items.EMERALD, 5),
                    new ItemStack(ItemRegistry.METH),
                    10, 18, 0.02f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                    new ItemStack(Items.EMERALD, 4),
                    new ItemStack(ItemRegistry.HEROIN),
                    10, 18, 0.02f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                    new ItemStack(Items.EMERALD, 5),
                    new ItemStack(ItemRegistry.PSILOCYBIN),
                    10, 18, 0.02f
            )));
            factories.add(((entity, random) -> new TradeOffer(
                    new ItemStack(Items.EMERALD, 4),
                    new ItemStack(ItemRegistry.LSD),
                    10, 18, 0.02f
            )));
        });
    }
}
