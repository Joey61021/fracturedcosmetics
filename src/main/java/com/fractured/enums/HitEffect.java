package com.fractured.enums;

import org.bukkit.ChatColor;
import org.bukkit.Material;

public enum HitEffect
{

    BLOOD(ChatColor.RED, "Blood", Material.REDSTONE_BLOCK);

    private final String display;
    private final ChatColor color;
    private final Material material;

    HitEffect(ChatColor color, String display, Material material)
    {
        this.color = color;
        this.display = display;
        this.material = material;
    }

    public ChatColor getColor()
    {
        return color;
    }

    public String getDisplay()
    {
        return display;
    }

    public Material getMaterial()
    {
        return material;
    }
}
