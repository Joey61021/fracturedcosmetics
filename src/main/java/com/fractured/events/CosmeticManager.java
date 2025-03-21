package com.fractured.events;

import com.fractured.FracturedCore;
import com.fractured.enums.HitEffect;
import com.fractured.enums.ProjectileTrail;
import com.fractured.user.User;
import com.fractured.user.UserManager;
import com.fractured.util.Menu;
import com.fractured.util.Utils;
import com.fractured.util.globals.Messages;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class CosmeticManager implements Listener
{

    public static Set<Menu> menus = new HashSet<>();

    static
    {
        /* Projectile trails */
        ArrayList<ItemStack> items = new ArrayList<>();

        for (ProjectileTrail trails : ProjectileTrail.values())
        {
            ItemStack item = new ItemStack(trails.getMaterial());
            ItemMeta meta = item.getItemMeta();

            if (meta != null)
            {
                meta.setDisplayName(trails.getColor() + trails.getDisplay());
                item.setItemMeta(meta);
            }

            items.add(item);
        }

        menus.add(new Menu("trails", "Projectile Trails", items));

        /* Hit effects */
        items.clear(); // being resourceful

        for (HitEffect hitEffects : HitEffect.values())
        {
            ItemStack item = new ItemStack(hitEffects.getMaterial());
            ItemMeta meta = item.getItemMeta();

            if (meta != null)
            {
                meta.setDisplayName(hitEffects.getColor() + hitEffects.getDisplay());
                item.setItemMeta(meta);
            }

            items.add(item);
        }

        menus.add(new Menu("hit", "Hit Effects", items));
    }

    public static void openGUI(Player player, String uid)
    {
        for (Menu menu : menus)
        {
            if (!menu.getUid().equalsIgnoreCase(uid))
            {
                continue;
            }

            player.openInventory(menu.getInventory());
        }

        // TODO -- not found message
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

    public static void applyHitEffect(Player player, HitEffect hitEffect)
    {
        User user = UserManager.getUser(player);
        if (user == null)
        {
            return;
        }

        if (user.getHitEffect() != null && user.getHitEffect().equals(hitEffect))
        {
            player.sendMessage(FracturedCore.getMessages().get(Messages.COMMAND_HIT_EFFECT_ALREADY_SELECTED));
            return;
        }

        user.setHitEffect(hitEffect);
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0F, 1.0F);
        player.sendMessage(FracturedCore.getMessages().get(Messages.COMMAND_HIT_EFFECT_SELECTED).replace("%effect%", hitEffect.getDisplay()));
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
    public static void onHit(EntityDamageByEntityEvent event)
    {
        if (!(event.getDamager() instanceof Player) || !(event.getEntity() instanceof Player victim))
        {
            return;
        }

        User user = UserManager.getUser(victim);
        if (user == null)
        {
            return; // why
        }

        if (user.getHitEffect() == HitEffect.BLOOD)
        {
            victim.getWorld().playEffect(victim.getLocation(), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
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
