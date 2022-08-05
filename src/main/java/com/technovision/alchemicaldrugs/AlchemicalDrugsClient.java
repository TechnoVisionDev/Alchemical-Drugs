package com.technovision.alchemicaldrugs;

import ladysnake.satin.api.event.ShaderEffectRenderCallback;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.util.Identifier;

public class AlchemicalDrugsClient implements ClientModInitializer {

    private static final ManagedShaderEffect LSD_SHADER = ShaderEffectManager.getInstance().manage(new Identifier(AlchemicalDrugs.MOD_ID, "shaders/post/trip.json"));
    private static final ManagedShaderEffect COCAINE_SHADER = ShaderEffectManager.getInstance().manage(new Identifier("shaders/post/phosphor.json"));
    private static final ManagedShaderEffect METH_SHADER = ShaderEffectManager.getInstance().manage(new Identifier("shaders/post/bumpy.json"));

    public static boolean isLSDEffectEnabled = false;
    public static boolean isCocaineEffectEnabled = false;
    public static boolean isMethEffectEnabled = false;

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
        });
    }
}
