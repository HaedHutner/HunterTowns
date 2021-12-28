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
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;

import javax.annotation.Nonnull;

@Aliases("info")
@Description("Lists all permissions for each group in the current plot.")
@Permission("atherystowns.plot.permission.info")
@Singleton
public class PlotPermissionInfoCommand implements CommandExecutor, PlayerCommand {

    @Inject
    private PlotFacade plotFacade;

    @Nonnull
    @Override
    public CommandResult execute(@Nonnull Player player, @Nonnull CommandContext commandContext) throws CommandException {
        plotFacade.sendCurrentPlotPermissions(player);
        return CommandResult.success();
    }
}
