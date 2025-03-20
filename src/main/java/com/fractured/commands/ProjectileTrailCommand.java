package com.fractured.commands;

import com.fractured.FracturedCore;
import com.fractured.enums.Cosmetic;
import com.fractured.enums.ProjectileTrail;
import com.fractured.events.CosmeticManager;
import com.fractured.user.User;
import com.fractured.user.UserManager;
import com.fractured.util.globals.Messages;
import com.fractured.util.globals.Permissions;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ProjectileTrailCommand
{

    private ProjectileTrailCommand()
    {
    }

    public static boolean projectileTrail(final CommandSender sender, final Command cmd, final String label, final String[] args)
    {
        if (!(sender instanceof Player player))
        {
            sender.sendMessage(FracturedCore.getMessages().get(Messages.GENERAL_NO_CONSOLE));
            return true;
        }

        if (!player.hasPermission(Permissions.COMMAND_COSMETICS))
        {
            player.sendMessage(FracturedCore.getMessages().get(Messages.GENERAL_NO_PERMISSION));
            return true;
        }

        if (args.length == 0)
        {
            CosmeticManager.openGUI(player, "trails");
            return true;
        }

        ProjectileTrail projectileTrail;
        try
        {
            projectileTrail = ProjectileTrail.valueOf(args[0].toUpperCase());
        } catch (Exception ignored)
        {
            StringBuilder sb = new StringBuilder();

            for (ProjectileTrail projectileTrails : ProjectileTrail.values())
            {
                sb.append(projectileTrails.name()).append(" ");
            }

            player.sendMessage(FracturedCore.getMessages().get(Messages.COMMAND_PROJECTILE_TRAIL_INVALID_TYPE).replace("%tails%", sb.toString()));
            return true;
        }

        CosmeticManager.applyProjectileTrail(player, projectileTrail);
        return true;
    }
}
