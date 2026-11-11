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

/** Draws the 3D alien body, copying every pose/animation from the (hidden) vanilla player model. */
public class AlienBodyFeatureRenderer extends FeatureRenderer<PlayerEntityRenderState, PlayerEntityModel> {
    private static Identifier tex(String n) { return Identifier.of(AlienClient.MOD_ID, "textures/entity/" + n + ".png"); }
    private static final Identifier SKIN = tex("alien_skin"), SUIT = tex("alien_suit"),
                                    DARK = tex("alien_dark"), GLOW = tex("alien_glow");
    private static final float MAX_JAW = 0.6f; // radians

    private final ModelPart skin, suit, dark, glow;

    public AlienBodyFeatureRenderer(PlayerEntityRenderer parent, LoadedEntityModels models) {
        super(parent);
        ModelPart root = models.getModelPart(AlienClient.ALIEN_LAYER);
        skin = root.getChild("skin");
        suit = root.getChild("suit");
        dark = root.getChild("dark");
        glow = root.getChild("glow");
    }

    private static void copy(ModelPart group, ModelPart vanillaRoot, String... names) {
        for (String n : names) {
            ModelPart dst = group.getChild(n);
            ModelPart src = vanillaRoot.getChild(n);
            dst.pivotX = src.pivotX; dst.pivotY = src.pivotY; dst.pivotZ = src.pivotZ;
            dst.pitch = src.pitch;   dst.yaw = src.yaw;       dst.roll = src.roll;
        }
    }

    @Override
    public void render(MatrixStack matrices, OrderedRenderCommandQueue queue, int light,
                       PlayerEntityRenderState state, float limbAngle, float limbDistance) {
        AlienRenderState a = (AlienRenderState) state;
        if (a.alien$isHidden()) return;

        ModelPart v = getContextModel().getRootPart();
        copy(skin, v, "head", "left_arm", "right_arm", "left_leg", "right_leg");
        copy(suit, v, "body", "left_leg", "right_leg");
        copy(dark, v, "head");
        copy(glow, v, "head");

        // Open the jaw based on voice volume
        skin.getChild("head").getChild("jaw").pitch = a.alien$getMouth() * 0.5f * MAX_JAW;

        int overlay = 655360; // = OverlayTexture.DEFAULT_UV (no hurt tint)
        queue.submitModelPart(skin, matrices, getContextModel().getLayer(SKIN), light, overlay, null);
        queue.submitModelPart(suit, matrices, getContextModel().getLayer(SUIT), light, overlay, null);
        queue.submitModelPart(dark, matrices, getContextModel().getLayer(DARK), light, overlay, null);
        queue.submitModelPart(glow, matrices, getContextModel().getLayer(GLOW),
            LightmapTextureManager.MAX_LIGHT_COORDINATE, overlay, null);
    }
}
