package de.murd.utils;

import net.labymod.core.LabyModCore;
import net.labymod.core.WorldRendererAdapter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class RenderUtils {

    public static void renderLabel(Entity entityIn, String string, double x, double y, double z, int maxDistance ) {

        // Get distance to label
        RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();

        double distance = entityIn.getDistanceSqToEntity( renderManager.livingPlayer );

        // Is label in range
        if ( distance <= maxDistance * maxDistance ) {
            // Fix mc bug
            float fixedPlayerViewX = renderManager.playerViewX
                    * ( Minecraft.getMinecraft().gameSettings.thirdPersonView == 2 ? -1 : 1 );

            FontRenderer fontrenderer = Minecraft.getMinecraft().fontRendererObj;

            GlStateManager.pushMatrix();

            // Move to position
            GlStateManager.translate( ( float ) x + 0.0F, ( float ) y + entityIn.height + 0.5F, ( float ) z );

            GL11.glNormal3f( 0.0F, 1.0F, 0.0F );

            // Rotate to camera
            GlStateManager.rotate( -renderManager.playerViewY, 0.0F, 1.0F, 0.0F );
            GlStateManager.rotate( fixedPlayerViewX, 1.0F, 0.0F, 0.0F );

            float scale = 0.016666668F * 1.6F;
            GlStateManager.scale( -scale, -scale, scale );

            // Setup
            GlStateManager.disableLighting();
            GlStateManager.depthMask( false );
            GlStateManager.disableDepth();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate( 770, 771, 1, 0 );
            // Render label background
            Tessellator tessellator = Tessellator.getInstance();
            WorldRendererAdapter worldrenderer = LabyModCore.getWorldRenderer();
            int posX = fontrenderer.getStringWidth( string ) / 2;
            GlStateManager.disableTexture2D();
            worldrenderer.begin( 7, DefaultVertexFormats.POSITION_COLOR );
            worldrenderer.pos( -posX - 1, -1, 0.0D ).color( 0.0F, 0.0F, 0.0F, 0.25F ).endVertex();
            worldrenderer.pos( -posX - 1, 8, 0.0D ).color( 0.0F, 0.0F, 0.0F, 0.25F ).endVertex();
            worldrenderer.pos( posX + 1, 8, 0.0D ).color( 0.0F, 0.0F, 0.0F, 0.25F ).endVertex();
            worldrenderer.pos( posX + 1, -1, 0.0D ).color( 0.0F, 0.0F, 0.0F, 0.25F ).endVertex();
            tessellator.draw();

            // Render label text
            GlStateManager.enableTexture2D();
            fontrenderer.drawString( string, -fontrenderer.getStringWidth( string ) / 2, 0, 553648127 );
            GlStateManager.enableDepth();
            GlStateManager.depthMask( true );
            fontrenderer.drawString( string, -fontrenderer.getStringWidth( string ) / 2, 0, -1 );

            // Reset
            GlStateManager.enableLighting();
            GlStateManager.disableBlend();
            GlStateManager.color( 1.0F, 1.0F, 1.0F, 1.0F );
            GlStateManager.popMatrix();
        }
    }
}
