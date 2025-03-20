
package com.fractured.commands;

import com.fractured.FracturedCore;
import com.fractured.enums.HitEffect;
import com.fractured.enums.ProjectileTrail;
import com.fractured.events.CosmeticManager;
import com.fractured.util.globals.Messages;
import com.fractured.util.globals.Permissions;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HitEffectCommand
{

    private HitEffectCommand()
    {
    }

    public static boolean hitEffect(final CommandSender sender, final Command cmd, final String label, final String[] args)
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
            CosmeticManager.openHitEffectGUI(player);
            return true;
        }

        HitEffect hitEffect;
        try
        {
            hitEffect = HitEffect.valueOf(args[0].toUpperCase());
        } catch (Exception ignored)
        {
            StringBuilder sb = new StringBuilder();

            for (ProjectileTrail projectileTrails : ProjectileTrail.values())
            {
                sb.append(projectileTrails.name()).append(" ");
            }

            player.sendMessage(FracturedCore.getMessages().get(Messages.COMMAND_HIT_EFFECT_INVALID_TYPE).replace("%effects%", sb.toString()));
            return true;
        }

        CosmeticManager.applyHitEffect(player, hitEffect);
        return true;
    }
}
