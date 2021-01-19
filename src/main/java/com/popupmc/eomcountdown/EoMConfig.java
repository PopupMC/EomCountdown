package com.popupmc.eomcountdown;

import org.jetbrains.annotations.NotNull;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.util.List;

public class EoMConfig {
    // Ensure config file is created
    // This is actually and older and not as good way to do it
    public static void ensureCreated(@NotNull JavaPlugin plugin) {
        // Get config file the long way
        File configFile = new File(plugin.getDataFolder(), "config.yml");
        FileConfiguration configYml = YamlConfiguration.loadConfiguration(configFile);

        // Make sure it exists which is unesesary
        if(!configFile.exists()) {
            plugin.saveDefaultConfig();
        }
    }

    // Load config data
    public static void load(@NotNull JavaPlugin plugin) {
        // Get todays date
        LocalDate today = LocalDate.now();
        endDay = today.lengthOfMonth();
        endMonth = today.getMonthValue();
        endYear = today.getYear();

        // Get month when server would reboot
        rebootMonthName = monthNameFromOffset(today.getMonthValue(), +3);

        // Get message
        FileConfiguration config = plugin.getConfig();
        msgs = config.getStringList("msgs");
    }

    // Does some fancy trickery to get the name of the month when it'll reboot from the offset given
    public static String monthNameFromOffset(int curMonth, int offset) {
        // curMonth is in 1-based array, convert to 0-based
        curMonth -= 1;

        // Simply add offset on
        int newInd = curMonth + offset;

        // Adjust to a proper location in the array
        if(newInd > 11)
            newInd -= 12;
        else if(newInd < 0)
            newInd += 12;

        // Return month name
        return new DateFormatSymbols().getMonths()[newInd];
    }

    public static int endYear;
    public static int endMonth;
    public static int endDay;
    public static String rebootMonthName;
    public static List<String> msgs;
}
