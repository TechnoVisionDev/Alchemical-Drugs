# Alchemical Drugs — Fabric 26.2

Alchemistry addon by TechnoVision, updated from Minecraft 1.19.2 to **Minecraft Java 26.2**.

## Install

Use **Java 25**, **Fabric Loader 0.19.5 or newer**, and these Minecraft 26.2 builds in your instance's `mods` folder:

- `AlchemicalDrugs-1.1.0+mc26.2.jar` from `build/libs/`
- Fabric API `0.160.0+26.2` or a compatible newer 26.2 release
- ChemLib `1.0.2+mc26.2`
- Alchemistry `1.0.3` for Minecraft 26.2

The matching chemistry ports are in the [ChemLib 26.2 branch](https://github.com/SmashingMods/ChemLib-Fabric/tree/26.2) and [Alchemistry 26.2 branch](https://github.com/SmashingMods/Alchemistry-Fabric/tree/26.2). Their older 1.19.2 builds cannot be used. Alchemistry bundles its energy and configuration libraries. This addon no longer needs Satin; its effects use Minecraft's rendering API. REI remains optional through Alchemistry.

## Preserved features

- All 17 items, original registry IDs, textures, models, names, chemical formulas, and creative tab.
- All 12 consumables, original use animations, 32-tick use time, status effects, strengths, durations, healing, injection damage, and creative-mode consumption behavior.
- All 25 recipes: six crafting recipes, eight combiner recipes, eight dissolver recipes, and three compactor recipes, with their original inputs and output quantities.
- Gold-block dealer workstation, original profession texture, and all 33 trade choices across levels 1–3, including prices, stock, XP, and price multipliers.
- LSD hue cycling and distortion, mushroom saturation and distortion, cocaine afterimages, meth embossing, heroin desaturation, and recurring hallucination audio. Effects can overlap and repeat doses refresh the corresponding timer.
- Original withdrawal behavior: cocaine, meth, and heroin have an **85%** chance of one random 30-second withdrawal effect after 30 seconds. This is the actual probability in the original code (`random >= 0.15`).

Delayed effects now run on game ticks and are cleaned up on death, disconnect, and server shutdown. Visual effects are sent from the server to the consuming player, so shared item code is safe on dedicated servers. Paused single-player games pause effect timers.

Registry IDs are preserved; conversion of existing 1.19.2 worlds has not been tested.

## Build

Install JDK 25. The wrapper uses Gradle 9.5.1 and Fabric Loom 1.17.20, following the [Fabric 26.2 migration guidance](https://fabricmc.net/2026/06/15/262.html).

Build the matching ChemLib and Alchemistry ports first. By default this project uses the existing sibling files:

```text
../ChemLib-Fabric/build/libs/ChemLib-1.0.2+mc26.2.jar
../Alchemistry-Fabric/build/libs/Alchemistry-1.0.3+fabric-26.2.jar
```

Or supply the actual JAR paths explicitly (their filenames may differ):

```sh
./gradlew -Pchemlib_jar=/absolute/path/to/ChemLib.jar \
  -Palchemistry_jar=/absolute/path/to/Alchemistry.jar build
```

The normal JAR in `build/libs/` is the installable mod. The `-sources.jar` is for development; chemistry dependencies are not bundled into the addon.

## Verification

```sh
./gradlew build                 # Compile, package, and run server game tests
./gradlew runClientGameTest     # Requires a graphical session; launches a temporary test client
```

Server tests compare against the original feature inventory in `src/gametest/resources/original-features.json`. They exercise survival/creative consumption, offhand use, status strengths and durations, healing and effect clearing, crafting matches/results, every chemistry recipe in its machine, and dealer trade generation.

Client tests verify all item models and display contexts, actual timed offhand consumption, server-to-client effect delivery, all five shaders, expiration and redosing, overlapping effects, window resizing, resource reload, and disconnect cleanup. Screenshots are saved under `build/run/clientGameTest/screenshots/`. Rendering has been checked with OpenGL on macOS; Vulkan has not been tested.

CI builds the pinned companion ports from source before building this addon and running its server tests.

## License

MIT; see [LICENSE](LICENSE).
