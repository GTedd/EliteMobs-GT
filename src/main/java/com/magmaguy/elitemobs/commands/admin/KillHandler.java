package com.magmaguy.elitemobs.commands.admin;

import com.magmaguy.elitemobs.api.internal.RemovalReason;
import com.magmaguy.elitemobs.entitytracker.EntityTracker;
import com.magmaguy.elitemobs.mobconstructor.EliteEntity;
import com.magmaguy.elitemobs.mobconstructor.mobdata.aggressivemobs.EliteMobProperties;
import com.magmaguy.magmacore.util.ChatColorConverter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class KillHandler {

    //TODO: FIX NPE ISSUE YOU FUCKING MORON

    public static void killAggressiveMobs(CommandSender commandSender) {
        int counter = 0;
        for (EliteEntity eliteEntity : new ArrayList<>(EntityTracker.getEliteMobEntities().values())) {
            eliteEntity.remove(RemovalReason.OTHER);
            counter++;
        }
        commandSender.sendMessage(ChatColorConverter.convert("&8[EliteMobs] &4Killed " + counter + " Elite Mobs."));
    }

    public static void killEntityType(CommandSender commandSender, EntityType entityType) {
        if (EliteMobProperties.getValidMobTypes().contains(entityType)) {
            int counter = 0;
            for (EliteEntity eliteEntity : EntityTracker.getEliteMobEntities().values()) {
                if (!eliteEntity.getLivingEntity().getType().equals(entityType)) continue;
                eliteEntity.remove(RemovalReason.OTHER);
                counter++;
            }
            commandSender.sendMessage(ChatColorConverter.convert("&8[EliteMobs] &4Killed " + counter + " Elite " + entityType.toString() + "."));
        } else
            commandSender.sendMessage(ChatColorConverter.convert("&8[EliteMobs] &cNot a valid entity type for EliteMobs!"));
    }

    public static void radiusKillAggressiveMobs(Player player, int radius) {
        int counter = 0;
        for (Entity entity : player.getNearbyEntities(radius, radius, radius)) {
            EliteEntity eliteEntity = EntityTracker.getEliteMobEntity(entity);
            if (eliteEntity != null) {
                eliteEntity.remove(RemovalReason.OTHER);
                counter++;
            }
        }
        player.sendMessage(ChatColorConverter.convert("&8[EliteMobs] &4Killed " + counter + " Elite Mobs."));
    }

    public static void radiusKillSpecificMobs(Player player, EntityType entityType, int radius) {
        int counter = 0;
        for (Entity entity : player.getNearbyEntities(radius, radius, radius)) {
            if (!entity.getType().equals(entityType)) continue;
            EliteEntity eliteEntity = EntityTracker.getEliteMobEntity(entity);
            if (eliteEntity != null) {
                ((EliteEntity) entity).remove(RemovalReason.OTHER);
                counter++;
            }
        }
        player.sendMessage(ChatColorConverter.convert("&8[EliteMobs] &4Killed " + counter + " Elite Mobs."));
    }

}
