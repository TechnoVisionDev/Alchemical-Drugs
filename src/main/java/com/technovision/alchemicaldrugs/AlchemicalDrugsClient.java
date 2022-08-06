package com.technovision.alchemicaldrugs;

import ladysnake.satin.api.event.ShaderEffectRenderCallback;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

public class AlchemicalDrugsClient implements ClientModInitializer {

    public static final Map<String, ScheduledFuture<?>> AUDIO_THREADS = new HashMap<>();
    public static final Map<String, ScheduledFuture<?>> CANCEL_THREADS = new HashMap<>();

    private static final ManagedShaderEffect LSD_SHADER = ShaderEffectManager.getInstance().manage(new Identifier(AlchemicalDrugs.MOD_ID, "shaders/post/lsd.json"));
    private static final ManagedShaderEffect COCAINE_SHADER = ShaderEffectManager.getInstance().manage(new Identifier("shaders/post/phosphor.json"));
    private static final ManagedShaderEffect METH_SHADER = ShaderEffectManager.getInstance().manage(new Identifier("shaders/post/bumpy.json"));
    private static final ManagedShaderEffect SHROOMS_SHADER = ShaderEffectManager.getInstance().manage(new Identifier(AlchemicalDrugs.MOD_ID, "shaders/post/shrooms.json"));

    public static boolean isLSDEffectEnabled = false;
    public static boolean isCocaineEffectEnabled = false;
    public static boolean isMethEffectEnabled = false;
    public static boolean isShroomsEffectEnabled = false;

    @Override
    public void onInitializeClient() {
        ShaderEffectRenderCallback.EVENT.register(tickDelta -> {
            if (isLSDEffectEnabled) {
                LSD_SHADER.render(tickDelta);
            }
            if (isCocaineEffectEnabled) {
                COCAINE_SHADER.render(tickDelta);
            }
            if (isMethEffectEnabled) {
                METH_SHADER.render(tickDelta);
            }
            if (isShroomsEffectEnabled) {
                SHROOMS_SHADER.render(tickDelta);
            }
        });
    }

    public static void cancelThreads(String key) {
        CANCEL_THREADS.get(key).cancel(false);
        ScheduledFuture<?> audioThread = AUDIO_THREADS.get(key);
        if (audioThread != null) audioThread.cancel(false);
    }

    public static void setThreads(String key, ScheduledFuture<?> audioThread, ScheduledFuture<?> cancelThread) {
        AUDIO_THREADS.put(key, audioThread);
        CANCEL_THREADS.put(key, cancelThread);
    }

    public static void setThread(String key, ScheduledFuture<?> cancelThread) {
        CANCEL_THREADS.put(key, cancelThread);
    }
}
