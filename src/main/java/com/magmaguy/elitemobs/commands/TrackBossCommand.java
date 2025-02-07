package com.magmaguy.elitemobs.commands;

import com.magmaguy.elitemobs.config.DefaultConfig;
import com.magmaguy.elitemobs.mobconstructor.custombosses.CustomBossEntity;
import com.magmaguy.magmacore.command.AdvancedCommand;
import com.magmaguy.magmacore.command.CommandData;
import com.magmaguy.magmacore.command.SenderType;
import com.magmaguy.magmacore.command.arguments.ListStringCommandArgument;
import com.magmaguy.magmacore.util.Logger;

import java.util.List;
import java.util.UUID;

public class TrackBossCommand extends AdvancedCommand {
    public TrackBossCommand() {
        super(List.of("track"));
        addLiteral("boss");
        addArgument("id", new ListStringCommandArgument("<id>"));
        setDescription("Tracks a Custom Boss.");
        setUsage("/em track boss <id>");
        setSenderType(SenderType.PLAYER);
        setPermission("elitemobs.boss.track");
    }

    @Override
    public void execute(CommandData commandData) {
        try {
            for (CustomBossEntity customBossEntity : CustomBossEntity.getTrackableCustomBosses())
                if (customBossEntity.getEliteUUID().equals(UUID.fromString(commandData.getStringArgument("id")))) {
                    customBossEntity.getCustomBossBossBar().addTrackingPlayer(commandData.getPlayerSender());
                    return;
                }
            Logger.sendMessage(commandData.getCommandSender(), DefaultConfig.getBossAlreadyGoneMessage());
        } catch (Exception ex) {
            //happens when players try to track an entity that has despawned for any reason
            Logger.sendMessage(commandData.getCommandSender(), DefaultConfig.getBossAlreadyGoneMessage());
        }
    }
}
