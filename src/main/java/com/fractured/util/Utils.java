package com.fractured.util;

import org.bukkit.ChatColor;

public class Utils
{

    public static String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
