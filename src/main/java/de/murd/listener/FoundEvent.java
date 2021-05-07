package de.murd.listener;

import net.labymod.api.LabyModAPI;
import net.labymod.api.LabyModAddon;
import net.minecraft.client.entity.AbstractClientPlayer;

public interface FoundEvent {
    void onFound(AbstractClientPlayer clientPlayer, LabyModAPI api);
}
