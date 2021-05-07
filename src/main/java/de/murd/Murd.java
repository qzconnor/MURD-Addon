package de.murd;

import de.murd.listener.FoundListener;
import de.murd.utils.PlayerScanner;
import de.murd.utils.RenderUtils;
import net.labymod.api.LabyModAddon;
import net.labymod.api.events.MessageSendEvent;
import net.labymod.api.events.RenderEntityEvent;
import net.labymod.settings.elements.SettingsElement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.util.StringUtils;

import java.util.List;

public class Murd extends LabyModAddon {
    private PlayerScanner playerScanner;
    private boolean innerEnabled;
    @Override
    public void onEnable() {
        playerScanner = new PlayerScanner(this);
        FoundListener foundListener = new FoundListener();
        playerScanner.addListener(foundListener);
        api.getEventManager().register((entity, x, y, z, v3) -> {
            if(entity instanceof AbstractClientPlayer){
                if(foundListener.getFound().contains((AbstractClientPlayer) entity)){
                    RenderUtils.renderLabel(entity,"§c§lMURDER §7| " + entity.getName(), x,y,z, 50);
                }
            }

        });
        api.getEventManager().register((MessageSendEvent) s -> {
            if (s.equals("/hub") || s.equals("/l") || s.equals("/lobby")){
                playerScanner.stop();
                foundListener.clearFound();
            }
            return false;
        });
        api.getEventManager().register((unformat, format) -> {
            if(format.equals("Das Spiel beginnt in 1 Sekunde!") || format.equals("Der Mörder bekommt sein Schwert in 5 Sekunden!")){
                innerEnabled = true;
                playerScanner.start();
                foundListener.clearFound();
            }else if(format.equals("DU BIST GESTORBEN!")){
                innerEnabled = false;
                playerScanner.stop();
                foundListener.clearFound();
            }else if(format.contains("▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬")){
                innerEnabled = false;
                playerScanner.stop();
                foundListener.clearFound();
            }
            return false;
        });
        api.getEventManager().registerOnQuit(serverData -> {
            playerScanner.stop();
            foundListener.clearFound();
        });
        api.getEventManager().register((MessageSendEvent) msg ->{
            if(msg.startsWith("/scanStart")){
                playerScanner.start();
                return true;
            }else  if(msg.startsWith("/scanStop")){
                playerScanner.stop();
                return true;
            }
            return false;
        });
    }

    public boolean isInnerEnabled() {
        return innerEnabled;
    }

    @Override
    public void loadConfig() {

    }

    @Override
    protected void fillSettings(List<SettingsElement> list) {

    }

    public PlayerScanner getPlayerScanner() {
        return playerScanner;
    }
}
