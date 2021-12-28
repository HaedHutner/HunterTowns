package dev.haedhutner.towns.command.nation;

import com.google.inject.Inject;
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
import org.spongepowered.api.text.format.TextColor;

import javax.annotation.Nonnull;

@Aliases("color")
@Description("Sets the nation's primary color")
@Permission("atherystowns.nation.color")
public class NationSetColorCommand implements ParameterizedCommand, PlayerCommand {

    @Inject
    private NationFacade nationFacade;

    @Override
    public CommandElement[] getArguments() {
        return new CommandElement[] {
                GenericArguments.catalogedElement(Text.of("color"), TextColor.class)
        };
    }

    @Nonnull
    @Override
    public CommandResult execute(@Nonnull Player source, @Nonnull CommandContext args) throws CommandException {
        nationFacade.setNationColor(source, args.<TextColor>getOne("color").orElse(null));
        return CommandResult.success();
    }
}
