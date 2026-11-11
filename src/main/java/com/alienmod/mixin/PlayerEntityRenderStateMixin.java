package com.alienmod.mixin;

import com.alienmod.AlienRenderState;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(PlayerEntityRenderState.class)
public class PlayerEntityRenderStateMixin implements AlienRenderState {
    @Unique private int alien$mouth;
    @Unique private boolean alien$ufo;
    @Unique private float alien$pitch;
    @Unique private boolean alien$hidden;

    @Override public int alien$getMouth() { return alien$mouth; }
    @Override public void alien$setMouth(int m) { alien$mouth = m; }
    @Override public boolean alien$isUfo() { return alien$ufo; }
    @Override public void alien$setUfo(boolean b) { alien$ufo = b; }
    @Override public float alien$getPitch() { return alien$pitch; }
    @Override public void alien$setPitch(float p) { alien$pitch = p; }
    @Override public boolean alien$isHidden() { return alien$hidden; }
    @Override public void alien$setHidden(boolean b) { alien$hidden = b; }
}
