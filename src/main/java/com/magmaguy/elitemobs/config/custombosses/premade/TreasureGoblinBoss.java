package com.magmaguy.elitemobs.config.custombosses.premade;

import com.magmaguy.elitemobs.config.custombosses.CustomBossesConfigFields;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TreasureGoblinBoss extends CustomBossesConfigFields {
    public TreasureGoblinBoss() {
        super("treasure_goblin",
                EntityType.ZOMBIE,
                true,
                "$eventBossLevel &eTreasure Goblin",
                "dynamic");
        setHealthMultiplier(4);
        setDamageMultiplier(1.25);
        setHelmet(new ItemStack(Material.GOLDEN_HELMET));
        setChestplate(new ItemStack(Material.GOLDEN_CHESTPLATE));
        setLeggings(new ItemStack(Material.GOLDEN_LEGGINGS));
        setBoots(new ItemStack(Material.GOLDEN_BOOTS));
        setBaby(true);
        setPowers(new ArrayList<>(List.of("gold_explosion.yml", "gold_shotgun.yml", "hyper_loot.yml", "spirit_walk.yml")));
        setSpawnMessage("&cA Treasure Goblin has been sighted!");
        setDeathMessage("&aA Treasure Goblin has been slain by $players!");
        setDeathMessages(new ArrayList<>(List.of(
                "&e&l---------------------------------------------",
                "&eThe Treasure Goblin has been pillaged!",
                "&c&l    1st Damager: $damager1name &cwith $damager1damage damage!",
                "&6&l    2nd Damager: $damager2name &6with $damager2damage damage!",
                "&e&l    3rd Damager: $damager3name &ewith $damager3damage damage!",
                "&aSlayers: $players",
                "&e&l---------------------------------------------")));
        setEscapeMessage("&4A Treasure Goblin has escaped!");
        setLocationMessage("&cTreasure Goblin: $distance blocks away!");
        setTrails(Collections.singletonList(Material.GOLD_NUGGET.toString()));
        setAnnouncementPriority(2);
        setPersistent(true);
        setFollowDistance(100);
        setCustomModel("em_goblin_treasure");
    }
}
