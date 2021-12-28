package dev.haedhutner.towns.command.plot;

import dev.haedhutner.core.command.PlayerCommand;
import dev.haedhutner.core.command.annotation.*;
import dev.haedhutner.towns.HunterTowns;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;

import javax.annotation.Nonnull;

@Aliases({"plot", "p"})
@Description("Base plot command.")
@Children({
        PlotInfoCommand.class,
        PlotSelectCommand.class,
        SetPlotNameCommand.class,
        RevokePlotCommand.class,
        GrantPlotCommand.class,
        BordersCommand.class,
        PlotPermissionCommand.class
})
@Permission("atherystowns.plot.base")
@HelpCommand(title = "Plot Help", command = "help")
public class PlotCommand implements PlayerCommand {

    @Nonnull
    @Override
    public CommandResult execute(@Nonnull Player source, @Nonnull CommandContext args) throws CommandException {
        HunterTowns.getInstance().getPlotFacade().sendInfoOnPlotAtPlayerLocation(source);
        return CommandResult.success();
    }
}
