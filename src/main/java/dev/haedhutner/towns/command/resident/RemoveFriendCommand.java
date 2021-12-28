package dev.haedhutner.towns.command.resident;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.haedhutner.core.command.ParameterizedCommand;
import dev.haedhutner.core.command.PlayerCommand;
import dev.haedhutner.core.command.UserElement;
import dev.haedhutner.core.command.annotation.Aliases;
import dev.haedhutner.core.command.annotation.Description;
import dev.haedhutner.core.command.annotation.Permission;
import dev.haedhutner.towns.facade.ResidentFacade;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.text.Text;

import javax.annotation.Nonnull;

@Aliases("unfriend")
@Description("Removes a player as a friend.")
@Permission("atherystowns.resident.unfriend")
@Singleton
public class RemoveFriendCommand implements ParameterizedCommand, PlayerCommand {

    @Inject
    private ResidentFacade residentFacade;

    @Override
    public CommandElement[] getArguments() {
        return new CommandElement[]{
                new UserElement(Text.of("friend"))
        };
    }

    @Nonnull
    @Override
    public CommandResult execute(@Nonnull Player source, @Nonnull CommandContext args) throws CommandException {
        residentFacade.removeResidentFriend(
                source,
                args.<User>getOne("friend").get()
        );
        return CommandResult.success();
    }
}
