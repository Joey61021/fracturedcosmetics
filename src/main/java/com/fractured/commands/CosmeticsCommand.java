package com.fractured.commands;

import com.fractured.FracturedCore;
import com.fractured.enums.Cosmetic;
import com.fractured.util.Utils;
import com.fractured.util.globals.Messages;
import com.fractured.util.globals.Permissions;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class CosmeticsCommand
{

    private CosmeticsCommand()
    {
    }

    public static boolean cosmetics(final CommandSender sender, final Command cmd, final String label, final String[] args)
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
            player.sendMessage(FracturedCore.getMessages().get(Messages.COMMAND_COSMETICS_USAGE));
            return true;
        }

        Cosmetic cosmetic;
        try
        {
            cosmetic = Cosmetic.valueOf(args[0].toUpperCase());
        } catch (Exception ignored)
        {
            StringBuilder sb = new StringBuilder();

            for (Cosmetic cosmetics : Cosmetic.values())
            {
                sb.append(cosmetics.getDisplay()).append(" ");
            }

            player.sendMessage(FracturedCore.getMessages().get(Messages.COMMAND_COSMETICS_INVALID_TYPE).replace("%cosmetics%", sb.toString()));
            return true;
        }
        return true;
    }
}
