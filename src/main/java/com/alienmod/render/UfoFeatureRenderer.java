package com.alienmod.render;

import com.alienmod.AlienClient;
import com.alienmod.AlienRenderState;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

/** Draws the UFO around the player while gliding with an elytra. */
public class UfoFeatureRenderer extends FeatureRenderer<PlayerEntityRenderState, PlayerEntityModel> {
    private static final Identifier HULL   = Identifier.of(AlienClient.MOD_ID, "textures/entity/ufo_hull.png");
    private static final Identifier GLASS  = Identifier.of(AlienClient.MOD_ID, "textures/entity/ufo_glass.png");
    private static final Identifier LIGHTS = Identifier.of(AlienClient.MOD_ID, "textures/entity/ufo_lights.png");

    // ---- tuning knobs if the saucer isn't level / centred in-game ----
    private static final float CENTER_Y_BLOCKS = 0.75f;   // torso centre in model space
    private static final float UFO_DROP_BLOCKS = 0.75f;   // how far below the player the plate sits
    private static final float SCALE = 1.0f;

    private static final int OVERLAY = 655360; // = OverlayTexture.DEFAULT_UV

    private final ModelPart hull, glass, lights;

    public UfoFeatureRenderer(PlayerEntityRenderer parent, LoadedEntityModels models) {
        super(parent);
        ModelPart root = models.getModelPart(AlienClient.UFO_LAYER);
        this.hull = root.getChild("hull");
        this.glass = root.getChild("glass");
        this.lights = root.getChild("lights");
    }

    @Override
    public void render(MatrixStack matrices, OrderedRenderCommandQueue queue, int light,
                       PlayerEntityRenderState state, float limbAngle, float limbDistance) {
        AlienRenderState a = (AlienRenderState) state;
        if (!a.alien$isUfo()) return;

        matrices.push();
        // Vanilla tilts the body to horizontal when gliding; undo that so the saucer stays level.
        matrices.translate(0, CENTER_Y_BLOCKS, 0);
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90.0f + a.alien$getPitch()));
        matrices.scale(SCALE, SCALE, SCALE);
        matrices.translate(0, UFO_DROP_BLOCKS, 0); // model y is flipped, +y = down

        lights.yaw = state.age * 0.15f;

        queue.submitModelPart(hull, matrices, getContextModel().getLayer(HULL), light, OVERLAY, null);
        queue.submitModelPart(lights, matrices, getContextModel().getLayer(LIGHTS),
            LightmapTextureManager.MAX_LIGHT_COORDINATE, OVERLAY, null);
        queue.submitModelPart(glass, matrices, getContextModel().getLayer(GLASS), light, OVERLAY, null);

        matrices.pop();
    }
}
