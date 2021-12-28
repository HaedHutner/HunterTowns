package dev.haedhutner.towns.command.town;

import dev.haedhutner.core.command.PlayerCommand;
import dev.haedhutner.core.command.annotation.Aliases;
import dev.haedhutner.core.command.annotation.Description;
import dev.haedhutner.core.command.annotation.Permission;
import dev.haedhutner.towns.HunterTowns;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;

@Aliases("ruin")
@Description("Deletes your town.")
@Permission("atherystowns.town.ruin")
public class RuinTownCommand implements PlayerCommand {
    @Override
    public CommandResult execute(Player src, CommandContext args) throws CommandException {
        HunterTowns.getInstance().getTownFacade().ruinPlayerTown(src);
        return CommandResult.success();
    }
}
