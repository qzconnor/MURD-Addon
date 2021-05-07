package de.murd.listener;

import net.labymod.api.LabyModAPI;
import net.labymod.core.LabyModCore;
import net.labymod.core.WorldRendererAdapter;
import net.labymod.main.LabyMod;
import net.labymod.mojang.RenderPlayerHook;
import net.labymod.utils.Material;
import net.labymod.utils.ModColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public class FoundListener implements FoundEvent {
    private ArrayList<AbstractClientPlayer> found = new ArrayList<>();
    private ArrayList<String> possibleNames = new ArrayList<>();

    public FoundListener(){
        possibleNames.add(Material.IRON_SWORD.getItem().getRegistryName());
        possibleNames.add(Material.STONE_SWORD.getItem().getRegistryName());
        possibleNames.add(Material.IRON_SPADE.getItem().getRegistryName());
        possibleNames.add(Material.STICK.getItem().getRegistryName());
        possibleNames.add(Material.WOOD_AXE.getItem().getRegistryName());
        possibleNames.add(Material.WOOD_SWORD.getItem().getRegistryName());
        possibleNames.add(Material.DEAD_BUSH.getItem().getRegistryName());
        possibleNames.add(Material.STONE_SPADE.getItem().getRegistryName());
        possibleNames.add(Material.BLAZE_ROD.getItem().getRegistryName());
        possibleNames.add(Material.DIAMOND_SPADE.getItem().getRegistryName());
        possibleNames.add(Material.FEATHER.getItem().getRegistryName());
        possibleNames.add(Material.PUMPKIN_PIE.getItem().getRegistryName());
        possibleNames.add(Material.GOLD_PICKAXE.getItem().getRegistryName());
        possibleNames.add(Material.APPLE.getItem().getRegistryName());
        possibleNames.add(Material.NAME_TAG.getItem().getRegistryName());
        possibleNames.add(Material.SPONGE.getItem().getRegistryName());
        possibleNames.add(Material.CARROT_STICK.getItem().getRegistryName());
        possibleNames.add(Material.BONE.getItem().getRegistryName());
        possibleNames.add(Material.CARROT_ITEM.getItem().getRegistryName());
        possibleNames.add(Material.GOLDEN_CARROT.getItem().getRegistryName());
        possibleNames.add(Material.RED_ROSE.getItem().getRegistryName());
    }

    @Override
    public void onFound(AbstractClientPlayer clientPlayer, LabyModAPI api) {
        ArrayList<String> items = new ArrayList<>();
        for (int i = 0; i < clientPlayer.inventory.mainInventory.length ; i++) {
            if(clientPlayer.inventory.mainInventory[i] != null){
                items.add(clientPlayer.inventory.mainInventory[i].getItem().getRegistryName());
            }
        }
        for(String string : possibleNames){
            if(items.contains(string)  && !found.contains(clientPlayer)){
                found.add(clientPlayer);
                handle_player(clientPlayer, api);
            }
        }
    }

    public ArrayList<AbstractClientPlayer> getFound() {
        return found;
    }

    public void clearFound() {
        this.found.clear();
    }

    private void handle_player(AbstractClientPlayer clientPlayer, LabyModAPI api){
        api.displayMessageInChat("§6§l" + clientPlayer.getName() + " §cis a Murder!");
    }

}
