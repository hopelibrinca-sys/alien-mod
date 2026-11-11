package com.alienmod;

import com.alienmod.render.AlienBodyFeatureRenderer;
import com.alienmod.render.AlienModel;
import com.alienmod.render.UfoFeatureRenderer;
import com.alienmod.render.UfoModel;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class AlienClient implements ClientModInitializer {
    public static final String MOD_ID = "alienmod";
    public static final EntityModelLayer UFO_LAYER = new EntityModelLayer(Identifier.of(MOD_ID, "ufo"), "main");
    public static final EntityModelLayer ALIEN_LAYER = new EntityModelLayer(Identifier.of(MOD_ID, "alien"), "main");

    @Override
    public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(UFO_LAYER, UfoModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ALIEN_LAYER, AlienModel::getTexturedModelData);

        LivingEntityFeatureRendererRegistrationCallback.EVENT.register(
            (entityType, entityRenderer, registrationHelper, context) -> {
                if (entityRenderer instanceof PlayerEntityRenderer playerRenderer) {
                    registrationHelper.register(new AlienBodyFeatureRenderer(playerRenderer, context.getEntityModels()));
                    registrationHelper.register(new UfoFeatureRenderer(playerRenderer, context.getEntityModels()));
                }
            });
    }
}
