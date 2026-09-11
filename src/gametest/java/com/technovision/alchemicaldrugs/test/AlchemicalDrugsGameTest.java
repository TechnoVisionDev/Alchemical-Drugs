package com.technovision.alchemicaldrugs.test;

import com.google.gson.*;
import com.technovision.alchemicaldrugs.registry.ItemRegistry;
import com.technovision.alchemicaldrugs.registry.VillagerRegistry;
import com.technovision.alchemicaldrugs.api.item.AbstractFoodItem;
import com.smashingmods.alchemistry.registry.BlockRegistry;
import com.smashingmods.alchemistry.common.block.combiner.CombinerBlockEntity;
import com.smashingmods.alchemistry.common.block.compactor.CompactorBlockEntity;
import com.smashingmods.alchemistry.common.block.dissolver.DissolverBlockEntity;
import com.smashingmods.alchemistry.common.recipe.combiner.CombinerRecipe;
import com.smashingmods.alchemistry.common.recipe.compactor.CompactorRecipe;
import com.smashingmods.alchemistry.common.recipe.dissolver.DissolverRecipe;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.*;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.*;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class AlchemicalDrugsGameTest {
    static JsonObject baseline() {
        return JsonParser.parseReader(new InputStreamReader(Objects.requireNonNull(AlchemicalDrugsGameTest.class.getResourceAsStream("/original-features.json")), StandardCharsets.UTF_8)).getAsJsonObject();
    }
    static Item item(String id) { return BuiltInRegistries.ITEM.getValue(Identifier.parse(id)); }
    static ItemStack stack(JsonObject json) { return new ItemStack(item(json.get("item").getAsString()), json.has("count") ? json.get("count").getAsInt() : 1); }

    @GameTest public void allConsumablesRetainEffectsAndConsumption(GameTestHelper test) {
        test.assertTrue(ItemRegistry.ITEMS.size() == 17, "All 17 original items must be registered");
        var expected = baseline().getAsJsonObject("items");
        for (Item item : ItemRegistry.ITEMS) {
            if (!(item instanceof AbstractFoodItem)) continue;
            for (GameType mode : new GameType[]{GameType.SURVIVAL, GameType.CREATIVE}) {
                var player = test.makeMockPlayer(mode);
                player.setHealth(10);
                var consumed = new ItemStack(item, 2);
                player.setItemInHand(InteractionHand.OFF_HAND, consumed);
                item.use(test.getLevel(), player, InteractionHand.OFF_HAND);
                test.assertTrue(player.getUsedItemHand() == InteractionHand.OFF_HAND && item.getUseDuration(consumed, player) == 32, "Offhand use and original duration: " + item);
                item.finishUsingItem(consumed, test.getLevel(), player);
                test.assertTrue(consumed.getCount() == (mode == GameType.CREATIVE ? 2 : 1), "Consumption: " + item + " " + mode);
                for (var entry : expected.getAsJsonObject(item.getClass().getSimpleName()).getAsJsonArray("effects")) {
                    var effect = entry.getAsJsonArray();
                    var holder = BuiltInRegistries.MOB_EFFECT.get(Identifier.withDefaultNamespace(effect.get(0).getAsString().toLowerCase(Locale.ROOT))).orElseThrow();
                    var actual = player.getEffect(holder);
                    test.assertTrue(actual != null && actual.getDuration() == effect.get(1).getAsInt() * 20 && actual.getAmplifier() == effect.get(2).getAsInt(), "Original effect retained: " + item + " / " + effect);
                }
                if (item == ItemRegistry.ASPIRIN) test.assertTrue(player.getHealth() == 15, "Aspirin heals 5");
                if (item == ItemRegistry.ANTIBIOTICS) test.assertTrue(player.getHealth() == 12, "Antibiotics heal 2");
            }
        }
        var player = test.makeMockPlayer(GameType.SURVIVAL);
        player.addEffect(new MobEffectInstance(MobEffects.POISON, 100));
        player.addEffect(new MobEffectInstance(MobEffects.SPEED, 100));
        ItemRegistry.ANTIBIOTICS.finishUsingItem(new ItemStack(ItemRegistry.ANTIBIOTICS), test.getLevel(), player);
        test.assertTrue(player.getActiveEffects().isEmpty(), "Antibiotics clear positive and negative effects");
        test.succeed();
    }

    @GameTest public void everyRecipeLoadsAndChemistryProcesses(GameTestHelper test) {
        var manager = test.getLevel().getServer().getRecipeManager();
        var pos = new BlockPos(1, 1, 1);
        int count = 0;
        for (var entry : baseline().getAsJsonObject("recipes").entrySet()) {
            String key = entry.getKey().replace("data/", "").replace("/recipes/", ":").replace(".json", "");
            var recipe = manager.byKey(ResourceKey.create(Registries.RECIPE, Identifier.parse(key))).orElseThrow(() -> new AssertionError("Missing recipe " + key)).value();
            var original = entry.getValue().getAsJsonObject();
            if (recipe instanceof CraftingRecipe crafting) {
                List<ItemStack> grid = new ArrayList<>(Collections.nCopies(9, ItemStack.EMPTY));
                if (original.has("pattern")) {
                    var pattern = original.getAsJsonArray("pattern");
                    for (int row = 0; row < pattern.size(); row++) {
                        String line = pattern.get(row).getAsString();
                        for (int col = 0; col < line.length(); col++) {
                            String symbol = line.substring(col, col + 1);
                            if (!symbol.equals(" ")) grid.set(row * 3 + col, stack(original.getAsJsonObject("key").getAsJsonObject(symbol)));
                        }
                    }
                } else {
                    var ingredients = original.getAsJsonArray("ingredients");
                    for (int i = 0; i < ingredients.size(); i++) {
                        var ingredient = ingredients.get(i).getAsJsonObject();
                        grid.set(i, ingredient.has("tag") ? new ItemStack(item("minecraft:red_dye")) : stack(ingredient));
                    }
                }
                var input = CraftingInput.of(3, 3, grid);
                test.assertTrue(crafting.matches(input, test.getLevel()), "Original crafting ingredients match: " + key);
                test.assertTrue(ItemStack.matches(crafting.assemble(input), stack(original.getAsJsonObject("result"))), "Original crafting output and count: " + key);
            } else if (recipe instanceof CombinerRecipe combiner) {
                test.setBlock(pos, BlockRegistry.COMBINER);
                var machine = test.getBlockEntity(pos, CombinerBlockEntity.class);
                machine.setRecipe(combiner); machine.setRecipeLocked(true); machine.insertEnergy(100000);
                var input = original.getAsJsonArray("input");
                for (int i = 0; i < input.size(); i++) machine.setStackInSlot(i, stack(input.get(i).getAsJsonObject()));
                for (int i = 0; i <= 1000; i++) machine.tick();
                test.assertTrue(ItemStack.matches(machine.getStackInSlot(4), stack(original.getAsJsonObject("result"))), "Original combiner output: " + key);
                for (int i = 0; i < 4; i++) test.assertTrue(machine.getStackInSlot(i).isEmpty(), "Exact ingredient consumption: " + key);
            } else if (recipe instanceof CompactorRecipe compactor) {
                test.setBlock(pos, BlockRegistry.COMPACTOR);
                var machine = test.getBlockEntity(pos, CompactorBlockEntity.class);
                machine.setRecipe(compactor); machine.setRecipeLocked(true); machine.insertEnergy(100000);
                machine.setStackInSlot(0, stack(original.getAsJsonObject("input")));
                for (int i = 0; i <= 1000; i++) machine.tick();
                test.assertTrue(ItemStack.matches(machine.getStackInSlot(2), stack(original.getAsJsonObject("result"))), "Original compactor output: " + key);
            } else if (recipe instanceof DissolverRecipe dissolver) {
                test.setBlock(pos, BlockRegistry.DISSOLVER);
                var machine = test.getBlockEntity(pos, DissolverBlockEntity.class);
                machine.setRecipe(dissolver); machine.setRecipeLocked(true); machine.insertEnergy(100000);
                machine.setStackInSlot(0, stack(original.getAsJsonObject("input")));
                for (int i = 0; i <= 1000 + 8; i++) machine.tick();
                Map<Item,Integer> actual = new HashMap<>();
                machine.getItems().forEach(s -> { if (!s.isEmpty()) actual.merge(s.getItem(), s.getCount(), Integer::sum); });
                Map<Item,Integer> expected = new HashMap<>();
                for (var output : original.getAsJsonObject("output").getAsJsonArray("groups").get(0).getAsJsonObject().getAsJsonArray("results")) {
                    var s = stack(output.getAsJsonObject()); expected.merge(s.getItem(), s.getCount(), Integer::sum);
                }
                test.assertTrue(actual.equals(expected), "Original dissolver elements and quantities: " + key + ": " + actual);
            }
            test.setBlock(pos, Blocks.AIR);
            count++;
        }
        test.assertTrue(count == 25, "All 25 original recipes checked");
        test.succeed();
    }

    @GameTest public void dealerRetainsWorkstationAndAllTradeLevels(GameTestHelper test) {
        test.assertTrue(PoiTypes.forState(Blocks.GOLD_BLOCK.defaultBlockState()).orElseThrow().is(VillagerRegistry.DEALER_POI_KEY), "Gold block remains dealer workstation");
        var registry = test.getLevel().registryAccess().lookupOrThrow(Registries.TRADE_SET);
        for (int level = 1; level <= 3; level++) {
            var set = registry.getValueOrThrow(VillagerRegistry.DEALER.getTrades(level));
            test.assertTrue(set.getTrades().size() == 11, "All 11 trades at level " + level);
            var dealer = test.spawn(EntityTypes.VILLAGER, new BlockPos(1, 2, 1));
            dealer.setVillagerData(dealer.getVillagerData().withProfession(test.getLevel().registryAccess(), VillagerRegistry.DEALER_KEY).withLevel(level));
            test.assertTrue(dealer.getOffers().size() == 2, "Dealer generates two offers at level " + level);
            for (var offer : dealer.getOffers()) {
                boolean matches = false;
                for (var value : baseline().getAsJsonArray("trades")) {
                    var e = value.getAsJsonObject();
                    if (e.get("level").getAsInt() != level) continue;
                    var a = e.getAsJsonObject("wants"); var b = e.getAsJsonObject("gives");
                    if (offer.getBaseCostA().is(item(a.get("id").getAsString())) && offer.getResult().is(item(b.get("id").getAsString()))
                        && offer.getBaseCostA().getCount() == a.get("count").getAsInt() && offer.getResult().getCount() == b.get("count").getAsInt()
                        && offer.getMaxUses() == e.get("max_uses").getAsInt() && offer.getXp() == e.get("xp").getAsInt()
                        && offer.getPriceMultiplier() == e.get("reputation_discount").getAsFloat()) matches = true;
                }
                test.assertTrue(matches, "Generated trade retains original price, stock, XP and multiplier");
            }
        }
        test.succeed();
    }
}
