package dev.haedhutner.towns.command.plot;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.haedhutner.core.command.ParameterizedCommand;
import dev.haedhutner.core.command.PlayerCommand;
import dev.haedhutner.core.command.annotation.Aliases;
import dev.haedhutner.core.command.annotation.Description;
import dev.haedhutner.core.command.annotation.Permission;
import dev.haedhutner.towns.HunterTowns;
import dev.haedhutner.towns.api.permission.TownsPermissionContext;
import dev.haedhutner.towns.api.permission.world.WorldPermission;
import dev.haedhutner.towns.facade.PermissionFacade;
import dev.haedhutner.towns.facade.PlotFacade;
import dev.haedhutner.towns.util.TownsElementsFactory;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import javax.annotation.Nonnull;

@Aliases("revoke")
@Description("Revoke permission from plot group")
@Permission("atherystowns.plot.permission.revoke")
@Singleton
public class PlotPermissionRevokeCommand implements ParameterizedCommand, PlayerCommand {

    @Inject
    private PermissionFacade permissionFacade;

    @Inject
    private PlotFacade plotFacade;

    @Override
    public CommandElement[] getArguments() {
        return new CommandElement[]{
                TownsElementsFactory.townPermissionContext(),
                GenericArguments.choices(
                        Text.of("permission"),
                        permissionFacade.WORLD_PERMISSIONS
                )
        };
    }

    @Nonnull
    @Override
    public CommandResult execute(@Nonnull Player source, @Nonnull CommandContext args) throws CommandException {
        plotFacade.removePlotPermission(
                source,
                args.<TownsPermissionContext>getOne("type").get(),
                args.<WorldPermission>getOne("permission").get()
        );
        return CommandResult.success();
    }
}
