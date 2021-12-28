package dev.haedhutner.towns.command.plot;

import dev.haedhutner.core.command.PlayerCommand;
import dev.haedhutner.core.command.annotation.Aliases;
import dev.haedhutner.core.command.annotation.Children;
import dev.haedhutner.core.command.annotation.Description;
import dev.haedhutner.core.command.annotation.HelpCommand;
import dev.haedhutner.towns.HunterTowns;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;

import javax.annotation.Nonnull;

@Aliases("select")
@Description("Toggles 2D plot selection mode. Will toggle off either mode.")
@Children({
        PlotClearSelectionCommand.class,
        PlotSelectCuboidCommand.class
})
@HelpCommand(title = "Selection Help", prefix = "plot", command = "help")
public class PlotSelectCommand implements PlayerCommand {
    @Nonnull
    @Override
    public CommandResult execute(@Nonnull Player source, @Nonnull CommandContext args) throws CommandException {
        HunterTowns.getInstance().getPlotSelectionFacade().togglePlotSelectionMode(source, false);
        return CommandResult.success();
    }
}
