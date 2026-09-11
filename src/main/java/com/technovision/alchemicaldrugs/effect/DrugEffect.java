package com.technovision.alchemicaldrugs.effect;
public enum DrugEffect {
    LSD(1200, true), COCAINE(600, false), METH(600, false), SHROOMS(1200, true), HEROIN(1200, false);
    public final int durationTicks;
    public final boolean hallucinationAudio;
    DrugEffect(int durationTicks, boolean hallucinationAudio) {
        this.durationTicks = durationTicks;
        this.hallucinationAudio = hallucinationAudio;
    }
    public String path() { return name().toLowerCase(java.util.Locale.ROOT); }
}
