package com.fractured.events;

import com.fractured.FracturedCore;
import com.fractured.enums.ProjectileTrail;
import com.fractured.user.User;
import com.fractured.user.UserManager;
import com.fractured.util.Utils;
import com.fractured.util.globals.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class CosmeticManager implements Listener
{

    public static void openProjectileTrailGUI(Player player)
    {
        Inventory inventory = Bukkit.createInventory(null, 6 * 9, "Projectile Trails");

        for (ProjectileTrail trails : ProjectileTrail.values())
        {
            ItemStack item = new ItemStack(trails.getMaterial());
            ItemMeta meta = item.getItemMeta();

            if (meta != null)
            {
                meta.setDisplayName(trails.getColor() + trails.getDisplay());
                item.setItemMeta(meta);
            }

            inventory.addItem(item);
        }

        player.openInventory(inventory);
    }

    public static void applyProjectileTrail(Player player, ProjectileTrail projectileTrail)
    {
        User user = UserManager.getUser(player);
        if (user == null)
        {
            return;
        }

        if (user.getProjectileTrail() != null && user.getProjectileTrail().equals(projectileTrail))
        {
            player.sendMessage(FracturedCore.getMessages().get(Messages.COMMAND_PROJECTILE_TRAIL_ALREADY_SELECTED));
            return;
        }

        user.setProjectileTrail(projectileTrail);
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0F, 1.0F);
        player.sendMessage(FracturedCore.getMessages().get(Messages.COMMAND_PROJECTILE_TRAIL_SELECTED).replace("%trail%", projectileTrail.getDisplay()));
    }

    @EventHandler
    public static void onClick(InventoryClickEvent event)
    {
        Inventory inv = event.getClickedInventory();
        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();

        if (inv == null || item == null || item.getType().equals(Material.AIR) || !event.getView().getTitle().equalsIgnoreCase("Projectile Trails"))
        {
            return;
        }

        event.setCancelled(true);

        for (ProjectileTrail trails : ProjectileTrail.values())
        {
            if (item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equalsIgnoreCase(Utils.color(trails.getColor() + trails.getDisplay())))
            {
                applyProjectileTrail(player, trails);
                return;
            }
        }
    }

    @EventHandler
    public static void onShoot(ProjectileLaunchEvent event)
    {
        if (!(event.getEntity().getShooter() instanceof Player shooter))
        {
            return;
        }

        // TODO -- logic here, for now just run for ops;
        if (!shooter.hasPermission("testing"))
        {
            return;
        }

        User user = UserManager.getUser(shooter);
        if (user == null || user.getProjectileTrail() == null)
        {
            return;
        }

        assignEffect(event.getEntity(), user.getProjectileTrail());
    }

    private static void assignEffect(Projectile projectile, ProjectileTrail trailEffect)
    {
        new BukkitRunnable()
        {
            @Override
            public void run() {
                if (projectile.isDead() || projectile.isOnGround())
                {
                    this.cancel();
                    return;
                }

                projectile.getWorld().spawnParticle(trailEffect.getParticle(), projectile.getLocation(), 1, 0, 0, 0, 0);
            }
        }.runTaskTimer(JavaPlugin.getPlugin(FracturedCore.class), 0L, 1L); // every tick
    }
}
