package dev.haedhutner.towns.command.plot;

import dev.haedhutner.core.command.ParameterizedCommand;
import dev.haedhutner.core.command.PlayerCommand;
import dev.haedhutner.core.command.annotation.Aliases;
import dev.haedhutner.core.command.annotation.Description;
import dev.haedhutner.core.command.annotation.Permission;
import dev.haedhutner.towns.HunterTowns;
import dev.haedhutner.towns.api.permission.TownsPermissionContext;
import dev.haedhutner.towns.api.permission.world.WorldPermission;
import dev.haedhutner.towns.util.TownsElementsFactory;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import javax.annotation.Nonnull;

@Aliases("grant")
@Description("Grant permission to plot group")
@Permission("atherystowns.plot.permission.grant")
public class PlotPermissionGrantCommand implements ParameterizedCommand, PlayerCommand {
    @Override
    public CommandElement[] getArguments() {
        return new CommandElement[]{
                TownsElementsFactory.townPermissionContext(),
                GenericArguments.choices(
                        Text.of("permission"),
                        HunterTowns.getInstance().getPermissionFacade().WORLD_PERMISSIONS
                )
        };
    }

    @Nonnull
    @Override
    public CommandResult execute(@Nonnull Player source, @Nonnull CommandContext args) throws CommandException {
        HunterTowns.getInstance().getPlotFacade().addPlotPermission(
                source,
                args.<TownsPermissionContext>getOne("type").get(),
                args.<WorldPermission>getOne("permission").get()
        );
        return CommandResult.success();
    }
}
