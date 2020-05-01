package com.github.alexthe666.iceandfire.client.render.entity;

import com.github.alexthe666.iceandfire.client.model.ModelCyclops;
import com.github.alexthe666.iceandfire.entity.EntityCyclops;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@OnlyIn(Dist.CLIENT)
public class RenderCyclops extends RenderLiving<EntityCyclops> {

    public static final ResourceLocation TEXTURE_0 = new ResourceLocation("iceandfire:textures/models/cyclops/cyclops_0.png");
    public static final ResourceLocation BLINK_0_TEXTURE = new ResourceLocation("iceandfire:textures/models/cyclops/cyclops_0_blink.png");
    public static final ResourceLocation BLINDED_0_TEXTURE = new ResourceLocation("iceandfire:textures/models/cyclops/cyclops_0_injured.png");
    public static final ResourceLocation TEXTURE_1 = new ResourceLocation("iceandfire:textures/models/cyclops/cyclops_1.png");
    public static final ResourceLocation BLINK_1_TEXTURE = new ResourceLocation("iceandfire:textures/models/cyclops/cyclops_1_blink.png");
    public static final ResourceLocation BLINDED_1_TEXTURE = new ResourceLocation("iceandfire:textures/models/cyclops/cyclops_1_injured.png");
    public static final ResourceLocation TEXTURE_2 = new ResourceLocation("iceandfire:textures/models/cyclops/cyclops_2.png");
    public static final ResourceLocation BLINK_2_TEXTURE = new ResourceLocation("iceandfire:textures/models/cyclops/cyclops_2_blink.png");
    public static final ResourceLocation BLINDED_2_TEXTURE = new ResourceLocation("iceandfire:textures/models/cyclops/cyclops_2_injured.png");
    public static final ResourceLocation TEXTURE_3 = new ResourceLocation("iceandfire:textures/models/cyclops/cyclops_3.png");
    public static final ResourceLocation BLINK_3_TEXTURE = new ResourceLocation("iceandfire:textures/models/cyclops/cyclops_3_blink.png");
    public static final ResourceLocation BLINDED_3_TEXTURE = new ResourceLocation("iceandfire:textures/models/cyclops/cyclops_3_injured.png");

    public RenderCyclops(RenderManager renderManager) {
        super(renderManager, new ModelCyclops(), 1.6F);
    }

    @Override
    public void preRenderCallback(EntityCyclops entitylivingbaseIn, float partialTickTime) {
        GL11.glScalef(2.25F, 2.25F, 2.25F);

    }

    @Override
    protected ResourceLocation getEntityTexture(EntityCyclops cyclops) {
        switch (cyclops.getVariant()) {
            case 0:
                if (cyclops.isBlinded()) {
                    return BLINDED_0_TEXTURE;
                } else if (cyclops.isBlinking()) {
                    return BLINK_0_TEXTURE;
                } else {
                    return TEXTURE_0;
                }
            case 1:
                if (cyclops.isBlinded()) {
                    return BLINDED_1_TEXTURE;
                } else if (cyclops.isBlinking()) {
                    return BLINK_1_TEXTURE;
                } else {
                    return TEXTURE_1;
                }
            case 2:
                if (cyclops.isBlinded()) {
                    return BLINDED_2_TEXTURE;
                } else if (cyclops.isBlinking()) {
                    return BLINK_2_TEXTURE;
                } else {
                    return TEXTURE_2;
                }
            case 3:
                if (cyclops.isBlinded()) {
                    return BLINDED_3_TEXTURE;
                } else if (cyclops.isBlinking()) {
                    return BLINK_3_TEXTURE;
                } else {
                    return TEXTURE_3;
                }
        }
        return TEXTURE_0;
    }

}
