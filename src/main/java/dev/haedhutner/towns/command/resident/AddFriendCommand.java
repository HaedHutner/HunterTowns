package dev.haedhutner.towns.command.resident;

import dev.haedhutner.core.command.ParameterizedCommand;
import dev.haedhutner.core.command.PlayerCommand;
import dev.haedhutner.core.command.annotation.Aliases;
import dev.haedhutner.core.command.annotation.Description;
import dev.haedhutner.core.command.annotation.Permission;
import dev.haedhutner.core.utils.UserElement;
import dev.haedhutner.towns.HunterTowns;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.text.Text;

import javax.annotation.Nonnull;

@Aliases("friend")
@Description("Adds a player as a friend.")
@Permission("atherystowns.resident.friend")
public class AddFriendCommand implements ParameterizedCommand, PlayerCommand {
    @Override
    public CommandElement[] getArguments() {
        return new CommandElement[]{
                new UserElement(Text.of("player"))
        };
    }

    @Nonnull
    @Override
    public CommandResult execute(@Nonnull Player source, @Nonnull CommandContext args) throws CommandException {
        HunterTowns.getInstance().getResidentFacade().addResidentFriend(
                source,
                args.<User>getOne("player").get()
        );
        return CommandResult.success();
    }
}