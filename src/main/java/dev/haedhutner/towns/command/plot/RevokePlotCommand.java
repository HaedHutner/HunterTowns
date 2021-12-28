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

import javax.annotation.Nonnull;

@Aliases("revoke")
@Permission("atherystowns.plot.revoke")
@Description("Revokes ownership of the plot you are standing on.")
@Singleton
public class RevokePlotCommand implements PlayerCommand {

    @Inject
    private PlotFacade plotFacade;

    @Nonnull
    @Override
    public CommandResult execute(@Nonnull Player source, @Nonnull CommandContext args) throws CommandException {
        plotFacade.revokePlotAtPlayerLocation(source);
        return CommandResult.success();
    }
}
