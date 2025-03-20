package com.fractured.util;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class Menu
{

    private String uid;
    private String title;
    private Inventory inventory;

    public Menu(String uid, String title, ArrayList<ItemStack> items)
    {
        this.uid = uid;
        this.title = title;

        Inventory inv = Bukkit.createInventory(null, 6 * 9, title);

        for (ItemStack item : items)
        {
            inv.addItem(item);
        }
    }

    public String getUid()
    {
        return uid;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public Inventory getInventory()
    {
        return inventory;
    }
}
