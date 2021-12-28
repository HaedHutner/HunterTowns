package dev.haedhutner.towns.command.plot;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.haedhutner.core.command.PlayerCommand;
import dev.haedhutner.core.command.annotation.Aliases;
import dev.haedhutner.core.command.annotation.Description;
import dev.haedhutner.core.command.annotation.Permission;
import dev.haedhutner.towns.HunterTowns;
import dev.haedhutner.towns.facade.PlotFacade;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;

@Aliases("info")
@Permission("atherystowns.plot.info")
@Description("Displays information about the plot you're standing on.")
@Singleton
public class PlotInfoCommand implements PlayerCommand {

    @Inject
    private PlotFacade plotFacade;

    @Override
    public CommandResult execute(Player source, CommandContext args) throws CommandException {
        plotFacade.sendInfoOnPlotAtPlayerLocation(source);
        return CommandResult.success();
    }
}
