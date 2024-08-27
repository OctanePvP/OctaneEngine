package com.octanepvp.splityosis.octaneengine.utility;

import com.octanepvp.splityosis.octaneengine.OctaneEngine;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemBuilder {

    private ItemStack itemStack;
    private ItemMeta meta;


    /**
     * Default constructor for ItemBuilder
     */
    public ItemBuilder() {
        this.itemStack = new ItemStack(Material.BARRIER, 1);
        this.meta = itemStack.getItemMeta();
        this.meta.setDisplayName("Default ItemBuilder Displayname");
    }

    /**
     * Convert an ItemStack to an ItemBuilder for further manipulation
     * @param itemStack to build from
     * @return ItemBuilder from ItemStack
     */
    public static ItemBuilder toBuilder(ItemStack itemStack) {
        return new ItemBuilder().itemStack(itemStack);
    }

    private ItemBuilder itemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
        this.meta = itemStack.getItemMeta();
        return this;
    }

    /**
     * Builds the ItemStack from the ItemBuilder builder
     * @return ItemStack
     */
    public ItemStack build(){
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public ItemBuilder name(String name){
        meta.setDisplayName(ColorUtil.colorize(name));
        return this;
    }

    public ItemBuilder amount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder material(Material material) {
        itemStack.setType(material);
        return this;
    }

    public ItemBuilder lore(String... lore){
        meta.setLore(ColorUtil.colorize(Arrays.asList(lore)));
        return this;
    }

    public ItemBuilder lore(List<String> lore) {
        meta.setLore(ColorUtil.colorize(lore));
        return this;
    }








}
