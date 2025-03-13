package com.fractured.user;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Each {@link Player} has an associated {@link com.fractured.user.User} object.
 * This is to store additional data about the Player. This User object is
 * guaranteed to exist if the Player is online.
 */
public final class UserManager implements Listener
{
    private static final Map<UUID, User> users;

    static
    {
        users = new ConcurrentHashMap<>(); // concurrent for async reading and writing (onPreLogin for example)
    }

    /**
     * @apiNote threadsafe
     */
    public static User getUser(UUID uid)
    {
        return users.get(uid);
    }

    /**
     * @apiNote threadsafe
     */
    public static User getUser(HumanEntity player)
    {
        return getUser(player.getUniqueId());
    }

    @EventHandler(priority = EventPriority.LOW) // run first
    public static void onPreLogin(AsyncPlayerPreLoginEvent event)
    {
        UUID uid = event.getUniqueId();
        User user = new User(uid);

        users.put(uid, user);
    }

    @EventHandler(priority = EventPriority.HIGH) // run last
    public static void onQuit(PlayerQuitEvent event)
    {
        users.remove(event.getPlayer().getUniqueId());
    }
}
