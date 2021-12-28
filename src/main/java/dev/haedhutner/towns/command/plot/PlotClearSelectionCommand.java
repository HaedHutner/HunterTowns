package dev.haedhutner.towns.command.plot;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.haedhutner.core.command.PlayerCommand;
import dev.haedhutner.core.command.annotation.Aliases;
import dev.haedhutner.core.command.annotation.Description;
import dev.haedhutner.towns.facade.PlotSelectionFacade;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;

import javax.annotation.Nonnull;

@Aliases("clear")
@Description("Clears your currently selected points.")
@Singleton
public class PlotClearSelectionCommand implements PlayerCommand {

    @Inject
    private PlotSelectionFacade plotSelectionFacade;

    @Nonnull
    @Override
    public CommandResult execute(@Nonnull Player source, @Nonnull CommandContext args) throws CommandException {
        plotSelectionFacade.clearSelection(source);
        return CommandResult.success();
    }
}
