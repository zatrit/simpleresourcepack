package net.zatrit.srp;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static org.apache.commons.lang.StringUtils.isNotEmpty;

public class EventListener implements Listener {
    private SimpleResourcePack srp;

    public EventListener(SimpleResourcePack ostTool){
        this.srp = ostTool;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        if (isNotEmpty(srp.getConfigData().url)) {
            event.getPlayer()
                    .setResourcePack(
                            srp.getConfigData().url,
                            srp.getConfigData().hash, true);
        }
    }
}
