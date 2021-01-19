package com.popupmc.eomcountdown;

import org.bukkit.plugin.java.JavaPlugin;

public class EoMCountdown extends JavaPlugin {
    @Override
    public void onEnable() {
        plugin = this;

        // Ensure config exists which is unesesary
        EoMConfig.ensureCreated(this);

        // Load config
        EoMConfig.load(this);

        // Register login event
        getServer().getPluginManager().registerEvents(new JoinListener(), this);

        // Announce enabled
        getLogger().info("EoMCountdown is enabled.");
    }

    // Announce disabled
    @Override
    public void onDisable() {
        getLogger().info("EoMCountdown is disabled");
    }

    // Package-only access for security
    static JavaPlugin plugin;
}
