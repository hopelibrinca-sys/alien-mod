package com.alienmod.render;

import net.minecraft.client.model.*;

/** 3D flying saucer: octagonal hull, glass dome, ring of lights. */
public final class UfoModel {
    private UfoModel() {}

    public static TexturedModelData getTexturedModelData() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();
        float rot45 = (float) (Math.PI / 4);

        // Hull = two squares, one rotated 45 degrees => octagon. (units are pixels, y-down)
        ModelPartData hull = root.addChild("hull", ModelPartBuilder.create(), ModelTransform.NONE);
        for (int i = 0; i < 2; i++) {
            ModelTransform t = i == 0 ? ModelTransform.NONE : ModelTransform.rotation(0, rot45, 0);
            hull.addChild("plate" + i,   ModelPartBuilder.create().cuboid(-20, -2, -20, 40, 4, 40), t);
            hull.addChild("belly" + i,   ModelPartBuilder.create().cuboid(-13, 2, -13, 26, 3, 26), t);
            hull.addChild("belly2_" + i, ModelPartBuilder.create().cuboid(-7, 5, -7, 14, 2, 14), t);
            hull.addChild("top" + i,     ModelPartBuilder.create().cuboid(-15, -4, -15, 30, 2, 30), t);
        }

        // Glass dome (tall enough to enclose the gliding player)
        root.addChild("glass", ModelPartBuilder.create()
            .cuboid(-13, -24, -13, 26, 20, 26)
            .cuboid(-7, -30, -7, 14, 6, 14), ModelTransform.NONE);

        // Rim lights (spin)
        ModelPartBuilder lights = ModelPartBuilder.create();
        for (int k = 0; k < 8; k++) {
            double ang = k * Math.PI / 4;
            lights.cuboid((float) (Math.cos(ang) * 25) - 2, -1, (float) (Math.sin(ang) * 25) - 2, 4, 3, 4);
        }
        root.addChild("lights", lights, ModelTransform.NONE);

        return TexturedModelData.of(data, 16, 16);
    }
}
