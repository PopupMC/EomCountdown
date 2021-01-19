package com.popupmc.eomcountdown;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.List;

public class JoinListener implements Listener {

    // On player login
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(final PlayerJoinEvent e) {

        // Get the shutoff date (Last day of month)
        LocalDate target = LocalDate.of(EoMConfig.endYear, EoMConfig.endMonth, EoMConfig.endDay);

        // Get days remaining
        long left = calculateRemainingDays(target);

        // Create a message announcing this
        final List<String> localMsgs = new ArrayList<>();

        for(int i = 0; i < EoMConfig.msgs.size(); i++) {
            localMsgs.add(EoMConfig.msgs.get(i).replaceAll("%days%", left+"")
                    .replaceAll("%month%", EoMConfig.rebootMonthName));
        }

        JavaPlugin plugin = EoMCountdown.plugin;

        // We want the player to see this, schedule this to be shown on the next available server tick
        // This means the player sees a new message sometime very soon after logging in that's separate
        // from the other messages
        Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
            @Override
            public void run() {
                Player p = e.getPlayer();

                for(String msg : localMsgs)
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
            }
        });
    }

    // Do some math to get remaining days
    private long calculateRemainingDays(final Temporal target) {
        final LocalDate today = LocalDate.now();
        return today.until(target, ChronoUnit.DAYS);
    }
}
