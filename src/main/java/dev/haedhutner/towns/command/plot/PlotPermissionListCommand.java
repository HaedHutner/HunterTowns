package dev.haedhutner.towns.command.plot;

import dev.haedhutner.core.command.PlayerCommand;
import dev.haedhutner.core.command.annotation.Aliases;
import dev.haedhutner.core.command.annotation.Description;
import dev.haedhutner.core.command.annotation.Permission;
import dev.haedhutner.towns.HunterTowns;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;

import javax.annotation.Nonnull;

@Aliases("list")
@Description("Lists all world permissions.")
@Permission("atherystowns.plot.permission.list")
public class PlotPermissionListCommand implements CommandExecutor, PlayerCommand {

    @Nonnull
    @Override
    public CommandResult execute(@Nonnull Player player, @Nonnull CommandContext commandContext) throws CommandException {
        HunterTowns.getInstance().getPlotFacade().sendPlotPermissions(player);
        return CommandResult.success();
    }
}
