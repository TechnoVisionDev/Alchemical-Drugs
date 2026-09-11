package com.technovision.alchemicaldrugs.test;

import com.technovision.alchemicaldrugs.AlchemicalDrugs;
import com.technovision.alchemicaldrugs.client.DrugVisuals;
import com.technovision.alchemicaldrugs.effect.DrugEffect;
import com.technovision.alchemicaldrugs.registry.ItemRegistry;
import net.fabricmc.fabric.api.client.gametest.v1.FabricClientGameTest;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.item.MissingItemModel;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import java.util.Set;

public class AlchemicalDrugsClientTest implements FabricClientGameTest {
    @Override public void runTest(ClientGameTestContext context) {
        context.getInput().resizeWindow(1000, 700);
        try (var world = context.worldBuilder().create()) {
            world.getConnection().waitForChunksRender();
            context.runOnClient(client -> {
                for (var item : ItemRegistry.ITEMS) {
                    var id = BuiltInRegistries.ITEM.getKey(item);
                    if (client.getModelManager().getItemModel(id) instanceof MissingItemModel) throw new AssertionError("Missing item model " + id);
                    for (var mode : ItemDisplayContext.values()) {
                        var state = new ItemStackRenderState();
                        client.getItemModelResolver().updateForTopItem(state, new ItemStack(item), mode, null, null, 0);
                        if (state.isEmpty()) throw new AssertionError("Empty item model " + id + " / " + mode);
                    }
                }
                for (var effect : DrugEffect.values()) {
                    var chain = client.getShaderManager().getPostChain(AlchemicalDrugs.id(effect.path()), Set.of(PostChain.MAIN_TARGET_ID));
                    if (chain == null) throw new AssertionError("Shader failed to load: " + effect);
                }
            });
            var items = new net.minecraft.world.item.Item[]{ItemRegistry.LSD, ItemRegistry.COCAINE, ItemRegistry.METH, ItemRegistry.PSILOCYBIN, ItemRegistry.HEROIN_SHOT};
            for (int i = 0; i < items.length; i++) {
                var item = items[i]; var effect = DrugEffect.values()[i];
                context.runOnClient(client -> DrugVisuals.clear());
                world.getServer().runOnServer(server -> {
                    var player = world.getConnection().getServerPlayer();
                    player.removeAllEffects();
                    player.setHealth(20);
                    player.setItemInHand(net.minecraft.world.InteractionHand.OFF_HAND, new ItemStack(item, 2));
                    player.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND, ItemStack.EMPTY);
                });
                world.getConnection().waitForClientboundPackets();
                context.getInput().holdKey(options -> options.keyUse);
                context.waitFor(client -> DrugVisuals.isActive(effect));
                context.getInput().releaseKey(options -> options.keyUse);
                world.getServer().runOnServer(server -> {
                    var player = world.getConnection().getServerPlayer();
                    if (player.getOffhandItem().getCount() != 1) throw new AssertionError("Timed consumption failed: " + item);
                });
                context.waitTicks(5);
                context.takeScreenshot("effect-" + effect.path());
                context.runOnClient(client -> {
                    DrugVisuals.start(effect);
                    for (int tick = 0; tick < effect.durationTicks - 1; tick++) DrugVisuals.tick(client);
                    if (!DrugVisuals.isActive(effect)) throw new AssertionError("Effect expired early " + effect);
                    DrugVisuals.start(effect);
                    for (int tick = 0; tick < effect.durationTicks - 1; tick++) DrugVisuals.tick(client);
                    if (!DrugVisuals.isActive(effect)) throw new AssertionError("Redose did not refresh " + effect);
                    DrugVisuals.tick(client);
                    if (DrugVisuals.isActive(effect)) throw new AssertionError("Effect did not expire " + effect);
                });
            }
            context.runOnClient(client -> { for (var effect : DrugEffect.values()) DrugVisuals.start(effect); });
            context.waitTicks(5);
            context.getInput().resizeWindow(1100, 750);
            context.waitTicks(5);
            context.takeScreenshot("combined-effects-resized");
            var reload = context.computeOnClient(client -> client.reloadResourcePacks());
            context.waitFor(client -> reload.isDone());
            context.waitTicks(5);
            context.runOnClient(client -> {
                for (var effect : DrugEffect.values()) {
                    if (client.getShaderManager().getPostChain(AlchemicalDrugs.id(effect.path()), Set.of(PostChain.MAIN_TARGET_ID)) == null)
                        throw new AssertionError("Shader lost after resource reload: " + effect);
                }
            });
            context.runOnClient(client -> DrugVisuals.clear());
        }
        context.runOnClient(client -> {
            for (var effect : DrugEffect.values()) if (DrugVisuals.isActive(effect)) throw new AssertionError("Effect leaked after disconnect");
        });
    }
}
