package com.technovision.alchemicaldrugs.effect;
import com.technovision.alchemicaldrugs.AlchemicalDrugs;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
public record DrugEffectPayload(int effect) implements CustomPacketPayload {
    public static final Type<DrugEffectPayload> TYPE = new Type<>(AlchemicalDrugs.id("drug_effect"));
    public static final StreamCodec<RegistryFriendlyByteBuf, DrugEffectPayload> CODEC = StreamCodec.composite(ByteBufCodecs.VAR_INT, DrugEffectPayload::effect, DrugEffectPayload::new);
    @Override public Type<DrugEffectPayload> type() { return TYPE; }
}
