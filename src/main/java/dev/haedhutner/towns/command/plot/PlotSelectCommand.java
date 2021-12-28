package dev.haedhutner.towns.command.plot;

import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.haedhutner.core.command.ParentCommand;
import dev.haedhutner.core.command.PlayerCommand;
import dev.haedhutner.core.command.annotation.Aliases;
import dev.haedhutner.core.command.annotation.Description;
import dev.haedhutner.core.command.annotation.HelpCommand;
import dev.haedhutner.towns.facade.PlotSelectionFacade;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;

import javax.annotation.Nonnull;
import java.util.Set;

@Aliases("select")
@Description("Toggles 2D plot selection mode. Will toggle off either mode.")
@HelpCommand(title = "Selection Help", prefix = "plot", command = "help")
@Singleton
public class PlotSelectCommand implements PlayerCommand, ParentCommand {

    @Inject
    private PlotClearSelectionCommand plotClearSelectionCommand;

    @Inject
    private PlotSelectCuboidCommand plotSelectCuboidCommand;

    @Inject
    private PlotSelectionFacade plotSelectionFacade;

    @Nonnull
    @Override
    public CommandResult execute(@Nonnull Player source, @Nonnull CommandContext args) throws CommandException {
        plotSelectionFacade.togglePlotSelectionMode(source, false);
        return CommandResult.success();
    }

    @Override
    public Set<CommandExecutor> getChildren() {
        return Sets.newHashSet(
                plotClearSelectionCommand,
                plotSelectCuboidCommand
        );
    }
}
