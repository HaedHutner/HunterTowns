package dev.haedhutner.towns.command.resident;

import com.google.inject.Inject;
import dev.haedhutner.core.command.ParameterizedCommand;
import dev.haedhutner.core.command.UserElement;
import dev.haedhutner.core.command.annotation.Aliases;
import dev.haedhutner.core.command.annotation.Description;
import dev.haedhutner.core.command.annotation.Permission;
import dev.haedhutner.towns.facade.ResidentFacade;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.text.Text;

@Aliases("info")
@Description("Displays information about a resident")
@Permission("atherystowns.resident.info")
public class ResidentInfoCommand implements ParameterizedCommand {

    @Inject
    private ResidentFacade residentFacade;

    @Override
    public CommandElement[] getArguments() {
        return new CommandElement[]{
                new UserElement(Text.of("player"))
        };
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        residentFacade.sendResidentInfo(
                src, args.<User>getOne("player").get()
        );
        return CommandResult.success();
    }
}
