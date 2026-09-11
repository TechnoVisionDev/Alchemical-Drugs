package com.technovision.alchemicaldrugs;
import com.technovision.alchemicaldrugs.client.DrugVisuals;
import com.technovision.alchemicaldrugs.effect.DrugEffect;
import com.technovision.alchemicaldrugs.effect.DrugEffectPayload;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
public class AlchemicalDrugsClient implements ClientModInitializer {
    @Override public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(DrugEffectPayload.TYPE, (payload, context) -> {
            if (payload.effect() >= 0 && payload.effect() < DrugEffect.values().length)
                DrugVisuals.start(DrugEffect.values()[payload.effect()]);
        });
        ClientTickEvents.END_CLIENT_TICK.register(DrugVisuals::tick);
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> DrugVisuals.clear());
    }
}
