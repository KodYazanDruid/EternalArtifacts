package com.sonamorningstar.eternalartifacts.client.models;

import com.sonamorningstar.eternalartifacts.EternalArtifacts;
import com.sonamorningstar.eternalartifacts.entity.DemonEyeEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class DemonEyeModel extends AnimatedGeoModel<DemonEyeEntity> {
    @Override
    public ResourceLocation getModelLocation(DemonEyeEntity demonEyeEntity) {
        return new ResourceLocation(EternalArtifacts.MOD_ID, "geo/demon_eye.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(DemonEyeEntity demonEyeEntity) {
        return new ResourceLocation(EternalArtifacts.MOD_ID, "textures/entity/demon_eye/demon_eye.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(DemonEyeEntity demonEyeEntity) {
        return new ResourceLocation(EternalArtifacts.MOD_ID, "animations/demon_eye.animation.json");
    }
}
