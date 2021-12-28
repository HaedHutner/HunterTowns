package dev.haedhutner.towns.command.resident;

import dev.haedhutner.core.command.PlayerCommand;
import dev.haedhutner.core.command.annotation.*;
import dev.haedhutner.towns.HunterTowns;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;

import javax.annotation.Nonnull;

@Aliases({"resident", "res"})
@Description("Base resident command.")
@Children({
        AddFriendCommand.class,
        RemoveFriendCommand.class,
        ResidentInfoCommand.class
})

@Permission("atherystowns.resident.base")
@HelpCommand(title = "Resident Help", command = "help")
public class ResidentCommand implements PlayerCommand {

    @Nonnull
    @Override
    public CommandResult execute(@Nonnull Player source, @Nonnull CommandContext args) throws CommandException {
        HunterTowns.getInstance().getResidentFacade().sendResidentInfo(source);
        return CommandResult.success();
    }
}
