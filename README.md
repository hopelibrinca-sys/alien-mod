# Alien UFO Mod (Fabric 1.21.11, client-side)

1. Every player's body is replaced by a full 3D alien: big cranium, slanted eyes, antennae with glowing tips,
   thin arms, silver suit. It copies all vanilla animations (walking, sneaking, swimming, swinging...).
2. Simple Voice Chat: the alien's JAW physically drops with voice volume (your mic + players you hear).
3. Elytra gliding draws a 3D flying saucer with a glass dome around you.

## Getting the .jar (no installs needed)
1. Create a free GitHub repo and upload all these files (keep the .github folder).
2. Open the Actions tab -> "Build mod jar" -> run it (or it runs on upload).
3. When finished, download the artifact "alien-ufo-mod-jar" -> unzip -> put the .jar in your mods folder.
Or locally with JDK 21: `gradle build` (or ./gradlew build if you add the Gradle wrapper) -> build/libs/.
Check gradle.properties versions against https://fabricmc.net/develop for 1.21.11.

## Install
Fabric Loader + Fabric API + optional Simple Voice Chat. Client-only mod.
