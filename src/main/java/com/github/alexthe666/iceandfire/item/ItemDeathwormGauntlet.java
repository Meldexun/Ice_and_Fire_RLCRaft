package com.github.alexthe666.iceandfire.item;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.core.ModSounds;
import com.github.alexthe666.iceandfire.entity.MiscPlayerProperties;
import net.ilexiconn.llibrary.server.entity.EntityPropertiesHandler;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemDeathwormGauntlet extends Item {

    public ItemDeathwormGauntlet(String color) {
        this.setCreativeTab(IceAndFire.TAB_ITEMS);
        this.setTranslationKey("iceandfire.deathworm_gauntlet_" + color);
        this.maxStackSize = 1;
        this.setMaxDamage(500);
        this.setRegistryName(IceAndFire.MODID, "deathworm_gauntlet_" + color);
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 1;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.BOW;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
        ItemStack itemStackIn = playerIn.getHeldItem(hand);
        playerIn.setActiveHand(hand);
        return new ActionResult<ItemStack>(EnumActionResult.PASS, itemStackIn);
    }

    public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {
        MiscPlayerProperties properties = EntityPropertiesHandler.INSTANCE.getProperties(player, MiscPlayerProperties.class);
        if (stack.getTagCompound() != null) {
            if (properties.deathwormReceded || properties.deathwormLaunched) {
                return;
            } else {
                if(player instanceof EntityPlayer){
                    if(((EntityPlayer)player).getCooldownTracker().getCooldown(this, 0.0F) == 0) {
                        ((EntityPlayer)player).getCooldownTracker().setCooldown(this, 10);
                        player.playSound(ModSounds.DEATHWORM_ATTACK, 1F, 1F);
                        properties.deathwormReceded = false;
                        properties.deathwormLaunched = true;
                    }
                }
            }
        }
    }

    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
        MiscPlayerProperties properties = EntityPropertiesHandler.INSTANCE.getProperties(entityLiving, MiscPlayerProperties.class);
        if(properties != null && properties.gauntletDamage > 0){
            stack.damageItem(properties.gauntletDamage, entityLiving);
            properties.gauntletDamage = 0;
        }
    }

    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return !oldStack.isItemEqual(newStack);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int unused, boolean unused2) {
        boolean hitMob = false;
        if (stack.getTagCompound() == null) {
            stack.setTagCompound(new NBTTagCompound());
        } else {
            stack.getTagCompound().setInteger("HolderID", entity.getEntityId());
            MiscPlayerProperties properties = EntityPropertiesHandler.INSTANCE.getProperties(entity, MiscPlayerProperties.class);
            if (properties != null) {
                if (properties.deathwormReceded) {
                    if (properties.deathwormLungeTicks > 0) {
                        properties.deathwormLungeTicks = properties.deathwormLungeTicks - 2;
                    }
                    if (properties.deathwormLungeTicks <= 0) {
                        properties.deathwormLungeTicks = 0;
                        properties.deathwormReceded = false;
                        properties.deathwormLaunched = false;
                    }
                } else if (properties.deathwormLaunched) {
                    properties.deathwormLungeTicks = 2 + properties.deathwormLungeTicks;
                    if (properties.deathwormLungeTicks > 20 && !properties.deathwormReceded) {
                        properties.deathwormReceded = true;
                    }
                }

                if (properties.prevDeathwormLungeTicks == 20) {
                    if (entity instanceof EntityPlayer) {
                        EntityPlayer player = (EntityPlayer) entity;
                        Vec3d vec3d = player.getLook(1.0F).normalize();
                        double range = 5;
                        for (EntityLiving entityliving : world.getEntitiesWithinAABB(EntityLiving.class, new AxisAlignedBB((double) player.posX - range, (double) player.posY - range, (double) player.posZ - range, (double) player.posX + range, (double) player.posY + range, (double) player.posZ + range))) {
                            Vec3d vec3d1 = new Vec3d(entityliving.posX - player.posX, entityliving.posY - player.posY, entityliving.posZ - player.posZ);
                            double d0 = vec3d1.length();
                            vec3d1 = vec3d1.normalize();
                            double d1 = vec3d.dotProduct(vec3d1);
                            boolean canSee = d1 > 1.0D - 0.5D / d0 && player.canEntityBeSeen(entityliving);
                            if (canSee) {
                                properties.gauntletDamage++;
                                entityliving.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) entity), 2.5F);
                                entityliving.knockBack(entityliving, 0.5F, entityliving.posX - player.posX, entityliving.posZ - player.posZ);
                            }
                        }
                    }
                }
                properties.prevDeathwormLungeTicks = properties.deathwormLungeTicks;
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
        tooltip.add(I18n.format("item.iceandfire.legendary_weapon.desc"));
        tooltip.add(I18n.format("item.iceandfire.deathworm_gauntlet.desc_0"));
        tooltip.add(I18n.format("item.iceandfire.deathworm_gauntlet.desc_1"));
    }
}
