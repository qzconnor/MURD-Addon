package de.murd.utils;

import de.murd.listener.FoundEvent;
import net.labymod.api.LabyModAddon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;

public class PlayerScanner {
    private boolean active;
    private LabyModAddon labyModAddon;
    private ArrayList<FoundEvent> events;

    public PlayerScanner(LabyModAddon labyModAddon) {
        this.labyModAddon = labyModAddon;
        this.events = new ArrayList<>();

        labyModAddon.api.registerForgeListener(this);
    }

    public void addListener(FoundEvent event){
        events.add(event);
    }

    public void start(){
        this.active = true;
    }
    public void stop(){
        this.active = false;
    }

    @SubscribeEvent
    public void handle(TickEvent.ClientTickEvent event){
        if(!active) return;
        if(Minecraft.getMinecraft().thePlayer != null && Minecraft.getMinecraft().theWorld != null){
            for(Entity entity : Minecraft.getMinecraft().thePlayer.worldObj.loadedEntityList){
                if(entity instanceof AbstractClientPlayer){
                    AbstractClientPlayer player = (AbstractClientPlayer) entity;
                    sendEvent(player);
                }
            }
        }
    }

    private void sendEvent(AbstractClientPlayer clientPlayer){
        for(FoundEvent event : events){
            event.onFound(clientPlayer, labyModAddon.api);
        }
    }
}
