package com.technovision.alchemicaldrugs.registry;
import com.google.common.collect.ImmutableSet;
import com.technovision.alchemicaldrugs.AlchemicalDrugs;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PoiHelper;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.item.trading.TradeSet;
import net.minecraft.world.level.block.Blocks;
public final class VillagerRegistry {
    public static final ResourceKey<PoiType> DEALER_POI_KEY = ResourceKey.create(Registries.POINT_OF_INTEREST_TYPE, AlchemicalDrugs.id("dealer_poi"));
    public static final ResourceKey<VillagerProfession> DEALER_KEY = ResourceKey.create(Registries.VILLAGER_PROFESSION, AlchemicalDrugs.id("dealer"));
    public static final PoiType DEALER_POI = PoiHelper.register(DEALER_POI_KEY.identifier(), 1, 1, Blocks.GOLD_BLOCK);
    public static final VillagerProfession DEALER = registerProfession();
    private static VillagerProfession registerProfession() {
        var trades = new Int2ObjectOpenHashMap<ResourceKey<TradeSet>>();
        for (int level = 1; level <= 3; level++) trades.put(level, ResourceKey.create(Registries.TRADE_SET, AlchemicalDrugs.id("dealer/level_" + level)));
        return Registry.register(BuiltInRegistries.VILLAGER_PROFESSION, DEALER_KEY,
            new VillagerProfession(Component.translatable("entity.minecraft.villager.alchemicaldrugs.dealer"),
                poi -> poi.is(DEALER_POI_KEY), poi -> poi.is(DEALER_POI_KEY), ImmutableSet.of(), ImmutableSet.of(),
                SoundEvents.VILLAGER_WORK_WEAPONSMITH, trades));
    }
    public static void registerTrades() { }
}
