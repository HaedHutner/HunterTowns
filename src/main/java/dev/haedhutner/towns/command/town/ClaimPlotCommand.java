package dev.haedhutner.towns.command.town;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.haedhutner.core.command.PlayerCommand;
import dev.haedhutner.core.command.annotation.Aliases;
import dev.haedhutner.core.command.annotation.Description;
import dev.haedhutner.core.command.annotation.Permission;
import dev.haedhutner.towns.HunterTowns;
import dev.haedhutner.towns.facade.TownFacade;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;

@Aliases("claim")
@Description("Claims a plot in the town.")
@Permission("atherystowns.plot.claim")
@Singleton
public class ClaimPlotCommand implements PlayerCommand {

    @Inject
    private TownFacade townFacade;

    @Override
    public CommandResult execute(Player source, CommandContext args) throws CommandException {
        townFacade.claimTownPlotFromPlayerSelection(source);
        return CommandResult.success();
    }
}
