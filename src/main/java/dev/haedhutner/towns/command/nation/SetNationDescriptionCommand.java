package dev.haedhutner.towns.command.nation;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.haedhutner.core.command.ParameterizedCommand;
import dev.haedhutner.core.command.PlayerCommand;
import dev.haedhutner.core.command.annotation.Aliases;
import dev.haedhutner.core.command.annotation.Description;
import dev.haedhutner.core.command.annotation.Permission;
import dev.haedhutner.towns.HunterTowns;
import dev.haedhutner.towns.facade.NationFacade;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

import javax.annotation.Nonnull;

@Aliases("description")
@Description("Sets the nation's description.")
@Permission("atherystowns.nation.description")
@Singleton
public class SetNationDescriptionCommand implements ParameterizedCommand, PlayerCommand {

    @Inject
    private NationFacade nationFacade;

    @Override
    public CommandElement[] getArguments() {
        return new CommandElement[] {
                GenericArguments.text(
                        Text.of("description"),
                        TextSerializers.FORMATTING_CODE,
                        true
                )
        };
    }

    @Nonnull
    @Override
    public CommandResult execute(@Nonnull Player source, @Nonnull CommandContext args) throws CommandException {
        nationFacade.setNationDescription(
                source, args.<Text>getOne("description").get()
        );
        return CommandResult.success();
    }
}