package dev.haedhutner.towns.command.plot;

import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.haedhutner.core.command.ParentCommand;
import dev.haedhutner.core.command.PlayerCommand;
import dev.haedhutner.core.command.annotation.*;
import dev.haedhutner.towns.HunterTowns;
import dev.haedhutner.towns.facade.PlotFacade;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;

import javax.annotation.Nonnull;
import java.util.Set;

@Aliases({"plot", "p"})
@Description("Base plot command.")
@Permission("atherystowns.plot.base")
@HelpCommand(title = "Plot Help", command = "help")
@Singleton
public class PlotCommand implements PlayerCommand, ParentCommand {

    @Inject
    private PlotInfoCommand plotInfoCommand;

    @Inject
    private PlotSelectCommand plotSelectCommand;

    @Inject
    private SetPlotNameCommand setPlotNameCommand;

    @Inject
    private RevokePlotCommand revokePlotCommand;

    @Inject
    private GrantPlotCommand grantPlotCommand;

    @Inject
    private BordersCommand bordersCommand;

    @Inject
    private PlotPermissionCommand plotPermissionCommand;

    @Inject
    private PlotFacade plotFacade;

    @Nonnull
    @Override
    public CommandResult execute(@Nonnull Player source, @Nonnull CommandContext args) throws CommandException {
        plotFacade.sendInfoOnPlotAtPlayerLocation(source);
        return CommandResult.success();
    }


    @Override
    public Set<CommandExecutor> getChildren() {
        return Sets.newHashSet(
                plotInfoCommand,
                plotSelectCommand,
                setPlotNameCommand,
                revokePlotCommand,
                grantPlotCommand,
                bordersCommand,
                plotPermissionCommand
        );
    }
}
