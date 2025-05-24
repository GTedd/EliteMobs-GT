package com.magmaguy.elitemobs.items;

import com.magmaguy.elitemobs.MetadataHandler;
import com.magmaguy.elitemobs.config.ItemSettingsConfig;
import com.magmaguy.elitemobs.config.enchantments.EnchantmentsConfig;
import com.magmaguy.elitemobs.config.enchantments.EnchantmentsConfigFields;
import com.magmaguy.elitemobs.items.customenchantments.CustomEnchantment;
import com.magmaguy.elitemobs.items.potioneffects.ElitePotionEffect;
import com.magmaguy.elitemobs.items.potioneffects.ElitePotionEffectContainer;
import com.magmaguy.elitemobs.mobconstructor.EliteEntity;
import com.magmaguy.elitemobs.utils.PersistentVanillaData;
import com.magmaguy.magmacore.util.ChatColorConverter;
import com.magmaguy.magmacore.util.Logger;
import lombok.Getter;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.tags.ItemTagType;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nullable;
import java.util.*;

public class ItemTagger {

    public static final NamespacedKey eliteMobsItemNamespacedKey = new NamespacedKey(MetadataHandler.PLUGIN, "EliteMobsItem");
    public static final NamespacedKey customLore = new NamespacedKey(MetadataHandler.PLUGIN, "CustomLore");
    @Getter
    private static final NamespacedKey ELITE_DAMAGE = new NamespacedKey(MetadataHandler.PLUGIN, "eliteDamage");
    @Getter
    private static final NamespacedKey ELITE_DEFENSE = new NamespacedKey(MetadataHandler.PLUGIN, "eliteDefense");

    public static String itemValue = "ItemValue";

    public static NamespacedKey onHitPotionEffectKey = new NamespacedKey(MetadataHandler.PLUGIN, "onHitPotionEffect");
    public static NamespacedKey continuousPotionEffectKey = new NamespacedKey(MetadataHandler.PLUGIN, "continuousPotionEffect");
    public static NamespacedKey itemSource = new NamespacedKey(MetadataHandler.PLUGIN, "itemSource");
    public static NamespacedKey enchantmentCount = new NamespacedKey(MetadataHandler.PLUGIN, "enchantmentCount");

    public static void registerEliteItem(ItemMeta itemMeta) {
        itemMeta.getPersistentDataContainer().set(eliteMobsItemNamespacedKey, PersistentDataType.BYTE, (byte) 1);
    }

    public static void registerEliteItem(ItemStack itemStack) {
        if (itemStack == null || itemStack.getItemMeta() == null) return;
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(eliteMobsItemNamespacedKey, PersistentDataType.BYTE, (byte) 1);
        itemStack.setItemMeta(itemMeta);
        new EliteItemLore(itemStack, false);
        return;
    }


    /**
     * Used to register custom lore as a string to the item. This is necessary for the lore updater as it redraws the custom
     * lore portion of the item based on these contents.
     *
     * @param itemMeta ItemStack to which custom lore should be added
     * @param lore     Custom part of the lore to add
     */
    public static void registerCustomLore(ItemMeta itemMeta, List<String> lore) {
        StringBuilder parsedLore = new StringBuilder();
        for (int i = 0; i < lore.size(); i++) {
            parsedLore.append(lore.get(i));
            if (i < lore.size() - 1)
                parsedLore.append("\n");
        }
        itemMeta.getPersistentDataContainer().set(customLore, PersistentDataType.STRING, parsedLore.toString());
    }

    /**
     * Returns any custom lore stored in an item. Returns null if there isn't any.
     *
     * @param itemMeta
     * @return
     */
    public static List<String> getCustomLore(ItemMeta itemMeta) {
        String rawLore = itemMeta.getPersistentDataContainer().get(customLore, PersistentDataType.STRING);
        if (rawLore == null)
            return new ArrayList<>();
        ArrayList<String> parsedLore = new ArrayList();
        Collections.addAll(parsedLore, rawLore.split("\n"));
        return parsedLore;
    }

    public static boolean isEliteItem(ItemStack itemStack) {
        if (itemStack == null) return false;
        if (!itemStack.hasItemMeta()) return false;
        if (itemStack.getItemMeta().getCustomTagContainer().hasCustomTag(eliteMobsItemNamespacedKey, ItemTagType.BYTE))
            return true;
        return itemStack.getItemMeta().getPersistentDataContainer().has(eliteMobsItemNamespacedKey, PersistentDataType.BYTE);
    }

    /**
     * Registers all enchantments as they'd appear when constructing an item in EliteMobs
     *
     * @param itemMeta
     * @param enchantmentMap
     */
    public static void registerEnchantments(ItemMeta itemMeta, HashMap<Enchantment, Integer> enchantmentMap) {
        for (Enchantment enchantment : enchantmentMap.keySet())
            if (EliteEnchantments.isPotentialEliteEnchantment(enchantment))
                registerEnchantment(itemMeta, enchantment.getKey(), enchantmentMap.get(enchantment));
    }

    /**
     * For minecraft enchantments
     *
     * @param itemMeta
     * @param enchantmentKey
     * @param enchantmentLevel
     */
    public static void registerEnchantment(ItemMeta itemMeta, NamespacedKey enchantmentKey, int enchantmentLevel) {
        itemMeta.getPersistentDataContainer().set(enchantmentKey, PersistentDataType.INTEGER, enchantmentLevel);
    }

    public static void registerCustomEnchantments(ItemMeta itemMeta, HashMap<String, Integer> customEnchantments) {
        for (String subString : customEnchantments.keySet())
            registerCustomEnchantment(itemMeta, subString, customEnchantments.get(subString));
    }

    /**
     * For custom enchantments
     *
     * @param itemMeta
     * @param enchantmentKey
     * @param enchantmentLevel
     */
    public static void registerCustomEnchantment(ItemMeta itemMeta, String enchantmentKey, int enchantmentLevel) {
        itemMeta.getPersistentDataContainer().set(new NamespacedKey(MetadataHandler.PLUGIN, enchantmentKey), PersistentDataType.INTEGER, enchantmentLevel);
    }

    public static void registerCustomEnchantment(ItemMeta itemMeta, String enchantmentKey, String uuid) {
        itemMeta.getPersistentDataContainer().set(new NamespacedKey(MetadataHandler.PLUGIN, enchantmentKey), PersistentDataType.STRING, uuid);
    }

    public static int getEnchantment(ItemMeta itemMeta, String enchantmentKey) {
        return getEnchantment(itemMeta, new NamespacedKey(MetadataHandler.PLUGIN, enchantmentKey));
    }

    /**
     * Returns the level of that enchantment on the item. 0 means the enchantment is not present.
     *
     * @param itemMeta
     * @param enchantmentKey
     * @return
     */
    public static int getEnchantment(ItemMeta itemMeta, NamespacedKey enchantmentKey) {
        if (itemMeta == null)
            return 0;
        Integer level = itemMeta.getPersistentDataContainer().get(enchantmentKey, PersistentDataType.INTEGER);
        if (level == null) {
            Enchantment enchantment = Enchantment.getByKey(enchantmentKey);
            if (enchantment != null)
                return itemMeta.getEnchantLevel(enchantment);
            else
                return 0;
        } else
            return level;
    }

    public static boolean hasEnchantment(ItemMeta itemMeta, NamespacedKey enchantmentKey) {
        if (!itemMeta.hasLore()) //early performance tweak
            return false;
        if (itemMeta.getCustomTagContainer().hasCustomTag(enchantmentKey, ItemTagType.INTEGER))
            return true;
        return itemMeta.getPersistentDataContainer().has(enchantmentKey, PersistentDataType.INTEGER);
    }

    public static boolean hasKey(ItemStack itemStack, String key) {
        if (itemStack == null) return false;
        if (!itemStack.hasItemMeta()) return false;
        return Objects.requireNonNull(itemStack.getItemMeta()).getPersistentDataContainer().has(new NamespacedKey(MetadataHandler.PLUGIN, key), PersistentDataType.STRING);
    }

    public static boolean hasEnchantment(ItemMeta itemMeta, String keyString) {
        NamespacedKey enchantmentKey = new NamespacedKey(MetadataHandler.PLUGIN, keyString);
        if (!itemMeta.hasLore()) //early performance tweak
            return false;
        if (itemMeta.getCustomTagContainer().hasCustomTag(enchantmentKey, ItemTagType.INTEGER))
            return true;
        return itemMeta.getPersistentDataContainer().has(enchantmentKey, PersistentDataType.INTEGER);
    }

    public static ArrayList<ElitePotionEffect> getPotionEffects(ItemMeta itemMeta, NamespacedKey namespacedKey) {
        return ElitePotionEffectContainer.getElitePotionEffectContainer(itemMeta, namespacedKey);
    }

    public static void registerItemSource(EliteEntity eliteEntity, ItemMeta itemMeta) {
        if (eliteEntity == null) {
            itemMeta.getPersistentDataContainer().set(itemSource, PersistentDataType.STRING, ChatColorConverter.convert(ItemSettingsConfig.getShopItemSource()));
            return;
        }
        itemMeta.getPersistentDataContainer().set(itemSource, PersistentDataType.STRING, ChatColorConverter.convert(ItemSettingsConfig.getMobItemSource().replace("$mob", eliteEntity.getName())));
    }

    public static String getItemSource(ItemMeta itemMeta) {
        return itemMeta.getPersistentDataContainer().get(itemSource, PersistentDataType.STRING);
    }

    /**
     * Writes the value into the persistent data container. This is used to only ever calculate item worth once.
     * The weakness with this system is that if admins change the price scaling, the changes will not affect older items.
     * This is still worth it to only ever calculate the price of items once, as mass calculating item prices can be costly.
     * Also, item values will be updated with each lore refresh.
     * NOTE: This is the item value, not the resale value. These are two very different values!
     *
     * @param itemStack ItemStack to register the value onto.
     * @param player    Player associated to the ItemStack. This is necessary because different prestige tiers will result in
     *                  different prices.
     */
    public static void writeItemValue(ItemStack itemStack, Player player) {
        PersistentVanillaData.write(itemStack, itemValue, ItemWorthCalculator.writeItemWorth(itemStack, player));
    }

    /**
     * Gets the item value for the selected Elite Item.
     *
     * @param itemStack ItemStack to get value from
     * @return Value. Will be -1 if no values is registered!
     */
    public static double getItemValue(ItemStack itemStack) {
        return PersistentVanillaData.getDouble(itemStack, itemValue);
    }

    public static double getEliteDamageAttribute(@Nullable ItemStack itemStack) {
        double value = 0;
        if (itemStack != null &&
                itemStack.getItemMeta() != null &&
                itemStack.getItemMeta().getPersistentDataContainer().get(ELITE_DAMAGE, PersistentDataType.DOUBLE) != null)
            return itemStack.getItemMeta().getPersistentDataContainer().get(ELITE_DAMAGE, PersistentDataType.DOUBLE);
        return value;
    }

    public static double getEliteDamageAttribute(@Nullable Projectile projectile) {
        if (projectile == null) return 0D;
        if (projectile.getPersistentDataContainer().get(ELITE_DAMAGE, PersistentDataType.DOUBLE) == null) return 0D;
        return projectile.getPersistentDataContainer().get(ELITE_DAMAGE, PersistentDataType.DOUBLE);
    }

    public static void setEliteDamageAttribute(ItemStack itemStack, double damageValue) {
        if (itemStack == null || itemStack.getItemMeta() == null) return;
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(ELITE_DAMAGE, PersistentDataType.DOUBLE, damageValue);
        itemStack.setItemMeta(itemMeta);
    }

    public static void setEliteDamageAttribute(@Nullable Projectile projectile, double damageValue) {
        if (projectile == null) return;
        if (ItemSettingsConfig.isUseEliteEnchantments())
            projectile.getPersistentDataContainer().set(ELITE_DAMAGE, PersistentDataType.DOUBLE, damageValue);
        else {
            if (projectile.getType().equals(EntityType.ARROW)) {
                Arrow arrow = (Arrow) projectile;
                arrow.setDamage(arrow.getDamage() + damageValue);
            } else if (projectile.getType().equals(EntityType.TRIDENT)) {
                Trident trident = (Trident) projectile;
                trident.setDamage(trident.getDamage() + damageValue);
            }
        }
    }

    public static double getEliteDefenseAttribute(@Nullable ItemStack itemStack) {
        if (itemStack == null) return 0D;
        if (itemStack.getItemMeta() == null) return 0D;
        if (itemStack.getItemMeta().getPersistentDataContainer().get(ELITE_DEFENSE, PersistentDataType.DOUBLE) == null)
            return 0D;
        return itemStack.getItemMeta().getPersistentDataContainer().get(ELITE_DEFENSE, PersistentDataType.DOUBLE);
    }

    public static void setEliteDefenseAttribute(@Nullable ItemStack itemStack, double defenseValue) {
        if (itemStack == null || itemStack.getItemMeta() == null) return;
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(ELITE_DEFENSE, PersistentDataType.DOUBLE, defenseValue);
        itemStack.setItemMeta(itemMeta);
    }

    public static void registerEnchantmentCount(@Nullable ItemMeta itemMeta, int count) {
        if (itemMeta == null) return;
        itemMeta.getPersistentDataContainer().set(enchantmentCount, PersistentDataType.INTEGER, count);
    }

    public static int getEnchantmentCount(@Nullable ItemStack itemStack) {
        if (itemStack == null || itemStack.getItemMeta() == null) return 0;
        Integer value = itemStack.getItemMeta().getPersistentDataContainer().get(enchantmentCount, PersistentDataType.INTEGER);
        return value == null ? 0 : value;
    }

    public static HashMap<NamespacedKey, Integer> getItemEnchantments(@Nullable ItemStack itemStack) {
        HashMap<NamespacedKey, Integer> itemEnchantmentFilenames = new HashMap<>();
        if (itemStack == null || itemStack.getItemMeta() == null) return itemEnchantmentFilenames;
        ItemMeta itemMeta = itemStack.getItemMeta();
        for (Enchantment enchantment : Enchantment.values()) {
            int enchantmentLevel = getEnchantment(itemMeta, enchantment.getKey());
            if (enchantmentLevel > 0)
                itemEnchantmentFilenames.put(enchantment.getKey(), enchantmentLevel);
        }
        for (CustomEnchantment customEnchantment : CustomEnchantment.getCustomEnchantmentMap().values()) {
            int enchantmentLevel = getEnchantment(itemMeta, customEnchantment.getKey());
            if (enchantmentLevel > 0)
                itemEnchantmentFilenames.put(new NamespacedKey(MetadataHandler.PLUGIN, customEnchantment.getKey()), enchantmentLevel);
        }
        return itemEnchantmentFilenames;
    }

    public static HashMap<EnchantmentsConfigFields, Integer> getItemEnchantmentConfigFields(@Nullable ItemStack itemStack) {
        HashMap<EnchantmentsConfigFields, Integer> itemEnchantmentFilenames = new HashMap<>();
        if (itemStack == null || itemStack.getItemMeta() == null) return itemEnchantmentFilenames;
        ItemMeta itemMeta = itemStack.getItemMeta();
        for (Enchantment enchantment : Enchantment.values()) {
            int enchantmentLevel = getEnchantment(itemMeta, enchantment.getKey());
            if (enchantmentLevel > 0) {
                EnchantmentsConfigFields enchantmentsConfigFields = EnchantmentsConfig.getEnchantment(enchantment.getName().toLowerCase(Locale.ROOT) + ".yml");
                if (enchantmentsConfigFields == null) {
                    Logger.warn("Failed to get configuration file for enchantment called " + enchantment.getName().toLowerCase(Locale.ROOT) + ".yml");
                    continue;
                }
                itemEnchantmentFilenames.put(enchantmentsConfigFields, enchantmentLevel);
            }
        }
        for (CustomEnchantment customEnchantment : CustomEnchantment.getCustomEnchantmentMap().values()) {
            int enchantmentLevel = getEnchantment(itemMeta, customEnchantment.getKey());
            if (enchantmentLevel > 0)
                itemEnchantmentFilenames.put(customEnchantment.getEnchantmentsConfigFields(), enchantmentLevel);
        }
        return itemEnchantmentFilenames;
    }
}
