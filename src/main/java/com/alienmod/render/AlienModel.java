package com.alienmod.render;

import net.minecraft.client.model.*;

/**
 * Full 3D alien body: huge cranium, slanted black eyes, hinged jaw, antennae,
 * thin long arms, big hands, silver suit. Units are pixels, model space is y-down.
 * Split into groups so each group can use its own solid texture:
 *   skin (green), suit (grey), dark (eyes + mouth interior), glow (antenna tips, full-bright).
 */
public final class AlienModel {
    private AlienModel() {}

    public static TexturedModelData getTexturedModelData() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();
        float slant = (float) Math.toRadians(22);

        // ---------- SKIN ----------
        ModelPartData skin = root.addChild("skin", ModelPartBuilder.create(), ModelTransform.NONE);
        ModelPartData head = skin.addChild("head", ModelPartBuilder.create()
            .cuboid(-6, -14, -5, 12, 9, 10)         // big cranium
            .cuboid(-4, -5, -4, 8, 2.5f, 8)         // tapered lower face
            .cuboid(-2, -2.5f, -2, 4, 2.5f, 4)      // thin neck
            .cuboid(-3.5f, -19, -0.5f, 1, 5, 1)     // antennae
            .cuboid(2.5f, -19, -0.5f, 1, 5, 1),
            ModelTransform.NONE);
        head.addChild("jaw", ModelPartBuilder.create().cuboid(-3, 0, -1, 6, 2.5f, 5),
            ModelTransform.of(0, -2.5f, -3, 0, 0, 0));
        skin.addChild("right_arm", ModelPartBuilder.create()
            .cuboid(-1, -2, -1.5f, 3, 12, 3).cuboid(-1.5f, 10, -2, 4, 3, 4), ModelTransform.of(-5, 2, 0, 0, 0, 0));
        skin.addChild("left_arm", ModelPartBuilder.create()
            .cuboid(-2, -2, -1.5f, 3, 12, 3).cuboid(-2.5f, 10, -2, 4, 3, 4), ModelTransform.of(5, 2, 0, 0, 0, 0));
        skin.addChild("right_leg", ModelPartBuilder.create().cuboid(-2, 10, -3.5f, 4, 2, 6), ModelTransform.of(-1.9f, 12, 0, 0, 0, 0));
        skin.addChild("left_leg",  ModelPartBuilder.create().cuboid(-2, 10, -3.5f, 4, 2, 6), ModelTransform.of(1.9f, 12, 0, 0, 0, 0));

        // ---------- SUIT ----------
        ModelPartData suit = root.addChild("suit", ModelPartBuilder.create(), ModelTransform.NONE);
        suit.addChild("body", ModelPartBuilder.create().cuboid(-3, 0, -2, 6, 12, 4), ModelTransform.NONE);
        suit.addChild("right_leg", ModelPartBuilder.create().cuboid(-1.5f, 0, -1.5f, 3, 10, 3), ModelTransform.of(-1.9f, 12, 0, 0, 0, 0));
        suit.addChild("left_leg",  ModelPartBuilder.create().cuboid(-1.5f, 0, -1.5f, 3, 10, 3), ModelTransform.of(1.9f, 12, 0, 0, 0, 0));

        // ---------- DARK ----------
        ModelPartData dark = root.addChild("dark", ModelPartBuilder.create(), ModelTransform.NONE);
        ModelPartData dHead = dark.addChild("head", ModelPartBuilder.create()
            .cuboid(-3, -3, -3.5f, 6, 3, 1),        // mouth interior (shows when the jaw drops)
            ModelTransform.NONE);
        dHead.addChild("eye_r", ModelPartBuilder.create().cuboid(-2, -1.5f, -0.6f, 4, 3, 1.2f),
            ModelTransform.of(-3, -8, -5, 0, 0, slant));
        dHead.addChild("eye_l", ModelPartBuilder.create().cuboid(-2, -1.5f, -0.6f, 4, 3, 1.2f),
            ModelTransform.of(3, -8, -5, 0, 0, -slant));

        // ---------- GLOW ----------
        ModelPartData glow = root.addChild("glow", ModelPartBuilder.create(), ModelTransform.NONE);
        glow.addChild("head", ModelPartBuilder.create()
            .cuboid(-4, -21, -1, 2, 2, 2).cuboid(2, -21, -1, 2, 2, 2), ModelTransform.NONE);

        return TexturedModelData.of(data, 16, 16);
    }
}
