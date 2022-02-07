package me.alexirving.core.items
import org.bukkit.Color
import org.bukkit.DyeColor
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.BannerMeta
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.meta.LeatherArmorMeta
import org.bukkit.inventory.meta.SkullMeta


class ItemBuilder {
    private var stack: ItemStack

    constructor(mat: Material?) {
        stack = ItemStack(mat)
    }

    constructor(mat: Material?, sh: Short) {
        stack = ItemStack(mat, 1, sh)
    }

    fun getItemMeta(): ItemMeta {
        return stack.itemMeta
    }

    fun setColor(color: Color?): ItemBuilder {
        return when (stack.type) {
            Material.LEATHER_BOOTS, Material.LEATHER_CHESTPLATE, Material.LEATHER_HELMET, Material.LEATHER_LEGGINGS -> {
                val meta = stack.itemMeta as LeatherArmorMeta
                meta.color = color
                setItemMeta(meta)
                this
            }
            else -> {
                this
            }
        }

    }

    fun setGlow(glow: Boolean): ItemBuilder {
        if (glow) {
            addEnchant(Enchantment.KNOCKBACK, 1)
            addItemFlag(ItemFlag.HIDE_ENCHANTS)
        } else {
            val meta = getItemMeta()
            for (enchantment in meta.enchants.keys) {
                meta.removeEnchant(enchantment)
            }
        }
        return this
    }

    fun setUnbreakable(unbreakable: Boolean): ItemBuilder {
        val meta = stack.itemMeta
        meta.spigot().isUnbreakable = unbreakable
        stack.itemMeta = meta
        return this
    }

    fun setBannerColor(color: DyeColor?): ItemBuilder {
        if (stack.type != Material.BANNER) return this
        val meta = stack.itemMeta as BannerMeta
        meta.baseColor = color
        setItemMeta(meta)
        return this
    }

    fun setAmount(amount: Int): ItemBuilder {
        stack.amount = amount
        return this
    }

    fun setItemMeta(meta: ItemMeta?): ItemBuilder {
        stack.itemMeta = meta
        return this
    }

    fun setHead(owner: String?): ItemBuilder {
        if (stack.type != Material.SKULL_ITEM) return this
        val meta = stack.itemMeta as SkullMeta
        meta.setOwner(owner)
        setItemMeta(meta)
        return this
    }

    fun setDisplayName(displayname: String?): ItemBuilder {
        val meta = getItemMeta()
        meta.displayName = displayname
        setItemMeta(meta)
        return this
    }

    fun setItemStack(stack: ItemStack): ItemBuilder {
        this.stack = stack
        return this
    }

    fun setLore(lore: ArrayList<String?>?): ItemBuilder {
        val meta = getItemMeta()
        meta.lore = lore
        setItemMeta(meta)
        return this
    }

    fun setLore(lore: String): ItemBuilder {
        val loreList = ArrayList<String>()
        loreList.add(lore)
        val meta = getItemMeta()
        meta.lore = loreList
        setItemMeta(meta)
        return this
    }

    fun addEnchant(enchantment: Enchantment?, level: Int): ItemBuilder {
        val meta = getItemMeta()
        meta.addEnchant(enchantment, level, true)
        setItemMeta(meta)
        return this
    }

    fun addItemFlag(flag: ItemFlag?): ItemBuilder {
        val meta = getItemMeta()
        meta.addItemFlags(flag)
        setItemMeta(meta)
        return this
    }

    fun build(): ItemStack {
        if (stack.type == Material.SKULL_ITEM) {
            stack.durability = 3
        }
        return stack
    }
}