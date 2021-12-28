package dev.haedhutner.towns.command.town;

import dev.haedhutner.core.command.ParameterizedCommand;
import dev.haedhutner.core.command.PlayerCommand;
import dev.haedhutner.core.command.annotation.Aliases;
import dev.haedhutner.core.command.annotation.Description;
import dev.haedhutner.core.command.annotation.Permission;
import dev.haedhutner.core.utils.UserElement;
import dev.haedhutner.towns.HunterTowns;
import dev.haedhutner.towns.api.permission.town.TownPermission;
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
@Description("Removes a permission from an entity.")
@Permission("atherystowns.town.revoke")
public class TownRemoveActorPermissionCommand implements ParameterizedCommand, PlayerCommand {
    @Override
    public CommandElement[] getArguments() {
        return new CommandElement[]{
                new UserElement(Text.of("player")),
                GenericArguments.choices(
                        Text.of("permission"),
                        HunterTowns.getInstance().getPermissionFacade().TOWN_PERMISSIONS
                )
        };
    }

    @Nonnull
    @Override
    public CommandResult execute(@Nonnull Player source, @Nonnull CommandContext args) throws CommandException {
        HunterTowns.getInstance().getTownFacade().removeTownPermission(
                source,
                args.<User>getOne("player").get(),
                args.<TownPermission>getOne("permission").get()
        );
        return CommandResult.success();
    }
}
