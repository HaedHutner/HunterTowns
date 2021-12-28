package dev.haedhutner.towns.command.town;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.haedhutner.core.command.ParameterizedCommand;
import dev.haedhutner.core.command.PlayerCommand;
import dev.haedhutner.core.command.annotation.Aliases;
import dev.haedhutner.core.command.annotation.Description;
import dev.haedhutner.core.command.annotation.Permission;
import dev.haedhutner.core.command.UserElement;
import dev.haedhutner.towns.HunterTowns;
import dev.haedhutner.towns.api.permission.town.TownPermission;
import dev.haedhutner.towns.facade.PermissionFacade;
import dev.haedhutner.towns.facade.TownFacade;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.text.Text;

import javax.annotation.Nonnull;

@Aliases("permit")
@Description("Gives an entity a permission.")
@Permission("atherystowns.town.permit")
@Singleton
public class TownAddActorPermissionCommand implements ParameterizedCommand, PlayerCommand {

    @Inject
    private TownFacade townFacade;

    @Inject
    private PermissionFacade permissionFacade;

    @Override
    public CommandElement[] getArguments() {
        return new CommandElement[]{
                new UserElement(Text.of("player")),
                GenericArguments.choices(
                        Text.of("permission"),
                        permissionFacade.TOWN_PERMISSIONS
                )
        };
    }

    @Nonnull
    @Override
    public CommandResult execute(@Nonnull Player source, @Nonnull CommandContext args) throws CommandException {
        townFacade.addTownPermission(
                source,
                args.<User>getOne("player").get(),
                args.<TownPermission>getOne("permission").get()
        );

        return CommandResult.success();
    }
}
