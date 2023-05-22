package com.sonamorningstar.eternalartifacts.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.sonamorningstar.eternalartifacts.EternalArtifacts;
import com.sonamorningstar.eternalartifacts.client.models.DemonEyeModel;
import com.sonamorningstar.eternalartifacts.entity.DemonEyeEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class DemonEyeRenderer extends GeoEntityRenderer<DemonEyeEntity> {
    public DemonEyeRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new DemonEyeModel());
        this.shadowRadius = 0.3F;
    }

    @Override
    public ResourceLocation getTextureLocation(DemonEyeEntity animatable) {
        return new ResourceLocation(EternalArtifacts.MOD_ID, "textures/entity/demon_eye/demon_eye.png");
    }

    @Override
    public RenderType getRenderType(DemonEyeEntity animatable, float partialTick, PoseStack poseStack,
                                    MultiBufferSource bufferSource, VertexConsumer buffer,
                                    int packedLight, ResourceLocation texture) {
        poseStack.scale(1F, 1F, 1F);
        return super.getRenderType(animatable, partialTick, poseStack, bufferSource, buffer, packedLight, texture);
    }
}
