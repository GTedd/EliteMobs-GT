package com.magmaguy.elitemobs.commands;

import com.magmaguy.magmacore.command.AdvancedCommand;
import com.magmaguy.magmacore.command.CommandData;
import com.magmaguy.magmacore.command.SenderType;
import com.magmaguy.magmacore.command.arguments.ListStringCommandArgument;

import java.util.List;

public class DungeonTeleportCommand extends AdvancedCommand {
    public DungeonTeleportCommand() {
        super(List.of("dungeontp"));
        addArgument("dungeonID", new ListStringCommandArgument("Dungeon ID (you can't run this manually!)"));
        setPermission("elitemobs.dungeon.tp");
        setDescription("Teleports players to Lairs, Minidungeons and Dungeons.");
        setUsage("/em dungeontp <dungeonID>");
        setSenderType(SenderType.PLAYER);
    }

    @Override
    public void execute(CommandData commandData) {
        DungeonCommands.teleport(commandData.getPlayerSender(), commandData.getStringArgument("dungeonID"));
    }
}
