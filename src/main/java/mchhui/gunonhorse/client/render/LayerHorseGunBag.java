package mchhui.gunonhorse.client.render;

import java.lang.reflect.Field;

import org.joml.Math;
import org.joml.Matrix4f;

import com.mojang.blaze3d.vertex.PoseStack;
import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.item.IGun;

import mchhui.gunonhorse.block.ModBlocks;
import mchhui.gunonhorse.capability.ModCapabilities;
import mchhui.gunonhorse.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HorseModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Saddleable;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

public class LayerHorseGunBag<T extends AbstractHorse, M extends EntityModel<T>> extends RenderLayer<T, M> {
    private static Field bodyField = ObfuscationReflectionHelper.findField(HorseModel.class, "f_102751_");

    public LayerHorseGunBag(RenderLayerParent<T, M> p_117346_) {
        super(p_117346_);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        entity.getCapability(ModCapabilities.HORSE_SADDLE_GUN_BAG).ifPresent((cap -> {
            if (!cap.hasSaddleGunBag()) {
                return;
            }
            poseStack.pushPose();
            //cast no check XP npe no check IM JUST NOOB DEV hahaha
            getBodyPart((HorseModel)this.getParentModel()).translateAndRotate(poseStack);
            final Matrix4f mat = new Matrix4f();
            mat.translate(0.5f, 0.1f, -1.2f);
            mat.rotate(Math.toRadians(180), 0, 0, 1);
            poseStack.mulPoseMatrix(mat);
            Minecraft mc = Minecraft.getInstance();
            ModelResourceLocation modelRes = new ModelResourceLocation("gunonhorse", "saddle_gun_bag", "");
            BakedModel model = mc.getModelManager().getModel(modelRes);
            BlockRenderDispatcher blockRenderer = mc.getBlockRenderer();
            BlockState state = ModBlocks.SADDLE_GUN_BAG.get().defaultBlockState();
            blockRenderer.getModelRenderer().renderModel(poseStack.last(), bufferSource.getBuffer(RenderType.solid()), state, model, 1.0F, 1.0F, 1.0F, packedLight, OverlayTexture.NO_OVERLAY);
            cap.getSaddleGunBag().getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent((bagcap) -> {
                if(entity.getPassengers().size()>0) {
                    ItemStack displayGun = null;
                if (bagcap.getStackInSlot(0).getItem() instanceof IGun gun && !TimelessAPI.getClientGunIndex(gun.getGunId(bagcap.getStackInSlot(0))).get().getType().equals("pistol")) {
                    displayGun = bagcap.getStackInSlot(0);
                } else if (bagcap.getStackInSlot(1).getItem() instanceof IGun gun && !TimelessAPI.getClientGunIndex(gun.getGunId(bagcap.getStackInSlot(1))).get().getType().equals("pistol")) {
                    displayGun = bagcap.getStackInSlot(1);
                } else if (bagcap.getStackInSlot(2).getItem() instanceof IGun gun && !TimelessAPI.getClientGunIndex(gun.getGunId(bagcap.getStackInSlot(2))).get().getType().equals("pistol")) {
                    displayGun = bagcap.getStackInSlot(2);
                }
                    if (displayGun!=null) {
                        poseStack.pushPose();
                        mat.identity();
                        mat.translate(0.15f, 0.45f, 0.55f);
                        mat.rotate(Math.toRadians(90), 0, 1, 0);
                        mat.rotate(Math.toRadians(85), 0, 0, 1);
                        mat.scale(0.4f);
                        poseStack.mulPoseMatrix(mat);
                        mc.getItemRenderer().renderStatic(entity, displayGun, ItemDisplayContext.FIXED, false, poseStack, bufferSource, entity.level(), packedLight, OverlayTexture.NO_OVERLAY, entity.getId());
                        poseStack.popPose();
                    }
                }else {
                    first:
                    {
                        ItemStack displayGun = bagcap.getStackInSlot(0);
                        if (displayGun.getItem() instanceof IGun gun && !TimelessAPI.getClientGunIndex(gun.getGunId(displayGun)).get().getType().equals("pistol")) {
                            poseStack.pushPose();
                            mat.identity();
                            mat.translate(0.15f, 0.45f, 0.55f);
                            mat.rotate(Math.toRadians(90), 0, 1, 0);
                            mat.rotate(Math.toRadians(70), 0, 0, 1);
                            mat.scale(0.4f);
                            poseStack.mulPoseMatrix(mat);
                            mc.getItemRenderer().renderStatic(entity, displayGun, ItemDisplayContext.FIXED, false, poseStack, bufferSource, entity.level(), packedLight, OverlayTexture.NO_OVERLAY, entity.getId());
                            poseStack.popPose();
                        }
                        break first;
                    }
                    second:
                    {
                        ItemStack displayGun = bagcap.getStackInSlot(1);
                        if (displayGun.getItem() instanceof IGun gun && !TimelessAPI.getClientGunIndex(gun.getGunId(displayGun)).get().getType().equals("pistol")) {
                            poseStack.pushPose();
                            mat.identity();
                            mat.translate(0.85f, 0.3f, 0.55f);
                            mat.rotate(Math.toRadians(90), 0, 1, 0);
                            mat.rotate(Math.toRadians(85), 0, 0, 1);
                            mat.rotate(Math.toRadians(180), 1, 0, 0);
                            mat.scale(0.4f);
                            poseStack.mulPoseMatrix(mat);
                            mc.getItemRenderer().renderStatic(entity, displayGun, ItemDisplayContext.FIXED, false, poseStack, bufferSource, entity.level(), packedLight, OverlayTexture.NO_OVERLAY, entity.getId());
                            poseStack.popPose();
                        }
                        break second;
                    }
                    third:
                    {
                        ItemStack displayGun = bagcap.getStackInSlot(2);
                        if (displayGun.getItem() instanceof IGun gun && !TimelessAPI.getClientGunIndex(gun.getGunId(displayGun)).get().getType().equals("pistol")) {
                            poseStack.pushPose();
                            mat.identity();
                            mat.translate(0.15f, 0.4f, 0.9f);
                            mat.rotate(Math.toRadians(90), 0, 1, 0);
                            mat.scale(0.4f);
                            poseStack.mulPoseMatrix(mat);
                            mc.getItemRenderer().renderStatic(entity, displayGun, ItemDisplayContext.FIXED, false, poseStack, bufferSource, entity.level(), packedLight, OverlayTexture.NO_OVERLAY, entity.getId());
                            poseStack.popPose();
                        }
                        break third;
                    }  
                }
            });
            poseStack.popPose();
        }));
    }

    private static ModelPart getBodyPart(HorseModel model) {
        try {
            return (ModelPart)bodyField.get(model);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
