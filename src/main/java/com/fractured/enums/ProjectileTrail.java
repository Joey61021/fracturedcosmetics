package com.fractured.enums;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;

public enum ProjectileTrail
{

    ANGRY_VILLAGER(ChatColor.GREEN, "Angry Villager", Material.VILLAGER_SPAWN_EGG, Particle.ANGRY_VILLAGER),
    FLAME(ChatColor.GOLD, "Flame", Material.LAVA_BUCKET, Particle.FLAME),
    MUSIC_NOTE(ChatColor.DARK_PURPLE, "Music Note", Material.NOTE_BLOCK, Particle.NOTE),
    EXPLOSION(ChatColor.RED, "Explosion", Material.IRON_SWORD, Particle.EXPLOSION);

    private final String display;
    private final ChatColor color;
    private final Material material;
    private final Particle particle;

    ProjectileTrail(ChatColor color, String display, Material material, Particle particle)
    {
        this.color = color;
        this.display = display;
        this.material = material;
        this.particle = particle;
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

    public Particle getParticle()
    {
        return particle;
    }
}
