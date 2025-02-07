package com.magmaguy.elitemobs.commands;

import com.magmaguy.elitemobs.config.custombosses.CustomBossesConfig;
import com.magmaguy.elitemobs.mobconstructor.custombosses.transitiveblocks.TransitiveBlockCommand;
import com.magmaguy.magmacore.command.AdvancedCommand;
import com.magmaguy.magmacore.command.CommandData;
import com.magmaguy.magmacore.command.SenderType;
import com.magmaguy.magmacore.command.arguments.ListStringCommandArgument;

import java.util.List;

public class TransitiveBlocksRegisterCommand extends AdvancedCommand {
    public TransitiveBlocksRegisterCommand() {
        super(List.of("transitiveBlocks"));
        addLiteral("register");
        addArgument("filename", new ListStringCommandArgument(CustomBossesConfig.getCustomBosses().keySet().stream().toList(), "<filename>"));
        addArgument("type", new ListStringCommandArgument(List.of("ON_SPAWN", "ON_REMOVE"), "<type>"));
        setUsage("/em transitiveBlocks register <filename> <ON_SPAWN/ON_REMOVE>");
        setDescription("Registers transitive blocks for use by regional bosses.");
        setSenderType(SenderType.PLAYER);
        setPermission("elitemobs.transitiveblocks");
    }

    @Override
    public void execute(CommandData commandData) {
        TransitiveBlockCommand.processCommand(
                commandData.getPlayerSender(),
                commandData.getStringArgument("filename"),
                commandData.getStringArgument("type"), false);
    }
}