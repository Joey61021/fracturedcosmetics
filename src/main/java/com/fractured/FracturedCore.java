package com.fractured;

import com.fractured.commands.CosmeticsCommand;
import com.fractured.commands.HitEffectCommand;
import com.fractured.commands.ProjectileTrailCommand;
import com.fractured.config.Config;
import com.fractured.events.CosmeticManager;
import com.fractured.user.UserManager;
import com.fractured.util.globals.Messages;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class FracturedCore extends JavaPlugin
{

    private static Config messages;

    /**
     * Used for scheduling only
     */
    private static FracturedCore plugin;

    public static BukkitTask runAsync(Runnable runnable)
    {
        return Bukkit.getScheduler().runTaskAsynchronously(plugin, runnable);
    }

    public static BukkitTask runDelay(Runnable runnable, long delay)
    {
        return Bukkit.getScheduler().runTaskLater(plugin, runnable, delay);
    }

    public FracturedCore()
    {
        plugin = this;
    }

    @Override
    public void onEnable()
    {
        messages = new Config(this, Messages.class, "messages.yml");

        registerCommands();
        registerListeners();
    }

    void registerCommands()
    {
        getCommand("cosmetics").setExecutor(CosmeticsCommand::cosmetics);
        getCommand("projectiletrail").setExecutor(ProjectileTrailCommand::projectileTrail);
        getCommand("hiteffect").setExecutor(HitEffectCommand::hitEffect);
    }

    void registerListeners()
    {
        PluginManager manager = getServer().getPluginManager();
        manager.registerEvents(new UserManager(), this);
        manager.registerEvents(new CosmeticManager(), this);
    }

    public static Config getMessages()
    {
        return messages;
    }
}
