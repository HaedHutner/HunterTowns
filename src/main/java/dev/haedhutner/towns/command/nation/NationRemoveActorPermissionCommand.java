package dev.haedhutner.towns.command.nation;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.haedhutner.core.command.ParameterizedCommand;
import dev.haedhutner.core.command.PlayerCommand;
import dev.haedhutner.core.command.UserElement;
import dev.haedhutner.core.command.annotation.Aliases;
import dev.haedhutner.core.command.annotation.Description;
import dev.haedhutner.core.command.annotation.Permission;
import dev.haedhutner.towns.HunterTowns;
import dev.haedhutner.towns.api.permission.nation.NationPermission;
import dev.haedhutner.towns.facade.NationFacade;
import dev.haedhutner.towns.facade.PermissionFacade;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.text.Text;

import javax.annotation.Nonnull;

@Aliases("revoke")
@Description("Revokes a permission from an entity.")
@Permission("atherystowns.nation.permission")
@Singleton
public class NationRemoveActorPermissionCommand implements ParameterizedCommand, PlayerCommand {

    @Inject
    private PermissionFacade permissionFacade;

    @Inject
    private NationFacade nationFacade;

    @Override
    public CommandElement[] getArguments() {
        return new CommandElement[]{
                new UserElement(Text.of("player")),
                GenericArguments.choices(
                        Text.of("permission"),
                        permissionFacade.NATION_PERMISSIONS
                )
        };
    }

    @Nonnull
    @Override
    public CommandResult execute(@Nonnull Player source, @Nonnull CommandContext args) throws CommandException {
        nationFacade.removeNationPermission(
                source,
                args.<User>getOne("player").get(),
                args.<NationPermission>getOne("permission").get()
        );
        return CommandResult.success();
    }
}
