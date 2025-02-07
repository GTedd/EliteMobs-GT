package com.magmaguy.elitemobs.commands;

import com.magmaguy.elitemobs.powers.meta.ElitePower;
import com.magmaguy.magmacore.command.AdvancedCommand;
import com.magmaguy.magmacore.command.CommandData;
import com.magmaguy.magmacore.command.SenderType;
import com.magmaguy.magmacore.command.arguments.EntityTypeCommandArgument;
import com.magmaguy.magmacore.command.arguments.IntegerCommandArgument;
import com.magmaguy.magmacore.command.arguments.ListStringCommandArgument;
import org.bukkit.entity.EntityType;

import java.util.List;
import java.util.Optional;

public class SpawnElite extends AdvancedCommand {
    public SpawnElite() {
        super(List.of("spawn"));
        addLiteral("elite");
        addArgument("entityType", new EntityTypeCommandArgument());
        addArgument("level", new IntegerCommandArgument("<level>"));
        addArgument("powers", new ListStringCommandArgument(ElitePower.getElitePowers().keySet().stream().toList(),"<powers>"));
        setUsage("/em spawn elite <entityType> <level> <power1> <power2> <power3> <...>");
        setPermission("elitemobs.place.admin");
        setSenderType(SenderType.PLAYER);
        setDescription("Spawns an elite of the specified type and level, and optionally with the specified powers.");
    }

    @Override
    public void execute(CommandData commandData) {
        SpawnCommand.spawnEliteEntityTypeCommand(
                commandData.getPlayerSender(),
                EntityType.valueOf(commandData.getStringArgument("entityType")),
                commandData.getIntegerArgument("level"),
                Optional.of(commandData.getStringSequenceArgument("powers")));
    }
}