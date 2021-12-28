package dev.haedhutner.towns.command.plot;

import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.haedhutner.core.command.ParentCommand;
import dev.haedhutner.core.command.PlayerCommand;
import dev.haedhutner.core.command.annotation.*;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;

import javax.annotation.Nonnull;
import java.util.Set;

@Aliases({"permission", "permissions"})
@Description("Base plot command.")
@Permission("atherystowns.plot.permission.base")
@HelpCommand(title = "Plot Permission Help", prefix = "plot")
@Singleton
public class PlotPermissionCommand implements PlayerCommand, ParentCommand {

    @Inject
    private PlotPermissionGrantCommand plotPermissionGrantCommand;

    @Inject
    private PlotPermissionRevokeCommand plotPermissionRevokeCommand;

    @Inject
    private PlotPermissionListCommand plotPermissionListCommand;

    @Inject
    private PlotPermissionInfoCommand plotPermissionInfoCommand;

    @Nonnull
    @Override
    public CommandResult execute(@Nonnull Player source, @Nonnull CommandContext args) throws CommandException {
        return CommandResult.success();
    }

    @Override
    public Set<CommandExecutor> getChildren() {
        return Sets.newHashSet(
                plotPermissionGrantCommand,
                plotPermissionRevokeCommand,
                plotPermissionListCommand,
                plotPermissionInfoCommand
        );
    }
}
