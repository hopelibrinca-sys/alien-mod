package com.alienmod.mixin;

import com.alienmod.AlienRenderState;
import com.alienmod.MouthTracker;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public class PlayerEntityRendererMixin {
    @Inject(
        method = "updateRenderState(Lnet/minecraft/client/network/AbstractClientPlayerEntity;Lnet/minecraft/client/render/entity/state/PlayerEntityRenderState;F)V",
        at = @At("TAIL"))
    private void alien$update(AbstractClientPlayerEntity player, PlayerEntityRenderState state, float tickDelta, CallbackInfo ci) {
        AlienRenderState a = (AlienRenderState) state;
        a.alien$setMouth(MouthTracker.mouthState(player.getUuid()));
        a.alien$setUfo(player.isGliding());
        a.alien$setPitch(player.getPitch(tickDelta));
        a.alien$setHidden(player.isInvisible());
        // Hide the vanilla Steve/Alex body; our feature renderer draws the alien instead.
        // (Invisible entities skip their body model but still run feature renderers.)
        state.invisible = true;
    }
}
