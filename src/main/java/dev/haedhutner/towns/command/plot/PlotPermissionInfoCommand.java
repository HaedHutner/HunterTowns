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

@Aliases("info")
@Description("Lists all permissions for each group in the current plot.")
@Permission("atherystowns.plot.permission.info")
public class PlotPermissionInfoCommand implements CommandExecutor, PlayerCommand {

    @Nonnull
    @Override
    public CommandResult execute(@Nonnull Player player, @Nonnull CommandContext commandContext) throws CommandException {
        HunterTowns.getInstance().getPlotFacade().sendCurrentPlotPermissions(player);
        return CommandResult.success();
    }
}
