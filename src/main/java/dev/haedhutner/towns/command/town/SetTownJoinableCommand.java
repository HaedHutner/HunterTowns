package dev.haedhutner.towns.command.town;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.haedhutner.core.command.ParameterizedCommand;
import dev.haedhutner.core.command.PlayerCommand;
import dev.haedhutner.core.command.annotation.Aliases;
import dev.haedhutner.core.command.annotation.Description;
import dev.haedhutner.core.command.annotation.Permission;
import dev.haedhutner.towns.HunterTowns;
import dev.haedhutner.towns.facade.TownFacade;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import javax.annotation.Nonnull;

@Aliases("joinable")
@Description("Sets the town joinable status.")
@Permission("atherystowns.town.joinable")
@Singleton
public class SetTownJoinableCommand implements PlayerCommand, ParameterizedCommand {

    @Inject
    private TownFacade townFacade;

    @Override
    public CommandElement[] getArguments() {
        return new CommandElement[]{
                GenericArguments.bool(Text.of("joinable"))
        };
    }

    @Nonnull
    @Override
    public CommandResult execute(@Nonnull Player source, @Nonnull CommandContext args) throws CommandException {
        townFacade.setPlayerTownJoinable(
                source, args.<Boolean>getOne("joinable").get()
        );
        return CommandResult.success();
    }
}
