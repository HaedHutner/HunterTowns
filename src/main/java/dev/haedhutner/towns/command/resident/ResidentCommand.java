package dev.haedhutner.towns.command.resident;

import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.haedhutner.core.command.ParentCommand;
import dev.haedhutner.core.command.PlayerCommand;
import dev.haedhutner.core.command.annotation.*;
import dev.haedhutner.towns.HunterTowns;
import dev.haedhutner.towns.facade.ResidentFacade;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;

import javax.annotation.Nonnull;
import java.util.Set;

@Aliases({"resident", "res"})
@Description("Base resident command.")
@Permission("atherystowns.resident.base")
@HelpCommand(title = "Resident Help", command = "help")
@Singleton
public class ResidentCommand implements PlayerCommand, ParentCommand {

    @Inject
    private AddFriendCommand addFriendCommand;

    @Inject
    private RemoveFriendCommand removeFriendCommand;

    @Inject
    private ResidentInfoCommand residentInfoCommand;

    @Inject
    private ResidentFacade residentFacade;

    @Nonnull
    @Override
    public CommandResult execute(@Nonnull Player source, @Nonnull CommandContext args) throws CommandException {
        residentFacade.sendResidentInfo(source);
        return CommandResult.success();
    }

    @Override
    public Set<CommandExecutor> getChildren() {
        return Sets.newHashSet(
                addFriendCommand,
                removeFriendCommand,
                residentInfoCommand
        );
    }
}
