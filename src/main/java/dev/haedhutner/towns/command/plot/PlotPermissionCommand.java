package dev.haedhutner.towns.command.plot;

import dev.haedhutner.core.command.PlayerCommand;
import dev.haedhutner.core.command.annotation.*;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;

import javax.annotation.Nonnull;

@Aliases({"permission", "permissions"})
@Description("Base plot command.")
@Children({
        PlotPermissionGrantCommand.class,
        PlotPermissionRevokeCommand.class,
        PlotPermissionListCommand.class,
        PlotPermissionInfoCommand.class
})
@Permission("atherystowns.plot.permission.base")
@HelpCommand(title = "Plot Permission Help", prefix = "plot")
public class PlotPermissionCommand implements PlayerCommand {

    @Nonnull
    @Override
    public CommandResult execute(@Nonnull Player source, @Nonnull CommandContext args) throws CommandException {
        return CommandResult.success();
    }
}
