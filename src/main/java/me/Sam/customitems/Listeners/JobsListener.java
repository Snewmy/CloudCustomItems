package me.Sam.customitems.Listeners;

import com.gamingmesh.jobs.api.JobsPaymentEvent;
import me.Sam.customitems.items.DiggersShovel;
import me.Sam.customitems.items.MinersPickaxe;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class JobsListener implements Listener {

    @EventHandler
    public void onJobsPayment(JobsPaymentEvent event) {
        Player player = event.getPlayer().getPlayer();
        if (MinersPickaxe.breakingBlocks.contains(player) || DiggersShovel.breakingBlocks.contains(player)) {
            event.setCancelled(true);
        }
    }
}
