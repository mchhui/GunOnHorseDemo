 package mchhui.gunonhorse.client.render;

import com.alaharranhonor.swem.forge.entities.horse.SWEMHorseEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class GeoLayerGunOnHorse extends GeoRenderLayer<SWEMHorseEntity>{

    public GeoLayerGunOnHorse(GeoRenderer<SWEMHorseEntity> entityRendererIn) {
        super(entityRendererIn);
         // TODO Auto-generated constructor stub
    }
    
    @Override
    public void renderForBone(PoseStack poseStack, SWEMHorseEntity animatable, GeoBone bone, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        
    }
}
