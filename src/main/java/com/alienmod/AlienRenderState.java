package com.alienmod;

/** Extra data we attach to PlayerEntityRenderState (via mixin). */
public interface AlienRenderState {
    int alien$getMouth();            // 0 closed, 1 half, 2 wide open
    void alien$setMouth(int m);
    boolean alien$isUfo();           // gliding with an elytra?
    void alien$setUfo(boolean b);
    float alien$getPitch();
    void alien$setPitch(float p);
    boolean alien$isHidden();        // player is really invisible (potion) -> draw no alien
    void alien$setHidden(boolean b);
}
