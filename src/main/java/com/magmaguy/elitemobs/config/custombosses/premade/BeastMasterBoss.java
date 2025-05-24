package com.magmaguy.elitemobs.config.custombosses.premade;

import com.magmaguy.elitemobs.config.custombosses.CustomBossesConfigFields;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;

public class BeastMasterBoss extends CustomBossesConfigFields {
    public BeastMasterBoss() {
        super("beast_master",
                EntityType.VINDICATOR,
                true,
                "$eventBossLevel &6Beast Master",
                "dynamic");
        setHealthMultiplier(4);
        setDamageMultiplier(1.25);
        setSpawnMessage("&cThe Beast Master has been sighted!");
        setDeathMessage("&aThe Best Master has been slain by $players!");
        setDeathMessages(new ArrayList<>(List.of(
                "&e&l---------------------------------------------",
                "&eThe Beast Master has been savaged!",
                "&c&l    1st Damager: $damager1name &cwith $damager1damage damage!",
                "&6&l    2nd Damager: $damager2name &6with $damager2damage damage!",
                "&e&l    3rd Damager: $damager3name &ewith $damager3damage damage!",
                "&aSlayers: $players",
                "&e&l---------------------------------------------")));
        setEscapeMessage("&4The Beast Master has returned to the wilds!");
        setLocationMessage("&cBeast Master: $distance blocks away!");
        setAnnouncementPriority(2);
        setPersistent(true);
        setPowers(new ArrayList<>(List.of("attack_poison.yml", "ground_pound.yml", "bonus_loot.yml",
                "summonable:summonType=ON_HIT:filename=wild_wolf.yml:spawnNearby=true:inheritAggro=true:inheritLevel=true:chance=0.2")));
        setUniqueLootList(List.of("summon_wolf_scroll.yml:1"));
        setFollowDistance(100);
    }
}
