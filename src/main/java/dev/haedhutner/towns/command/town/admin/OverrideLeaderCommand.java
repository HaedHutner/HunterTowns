package dev.haedhutner.towns.command.town.admin;

import dev.haedhutner.core.command.ParameterizedCommand;
import dev.haedhutner.core.command.annotation.Aliases;
import dev.haedhutner.core.command.annotation.Description;
import dev.haedhutner.core.command.annotation.Permission;
import dev.haedhutner.towns.HunterTowns;
import dev.haedhutner.towns.model.entity.Town;
import dev.haedhutner.towns.util.TownsElementsFactory;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.text.Text;

@Aliases("overrideLeader")
@Permission("atherystowns.admin.town.mayor")
@Description("Set the mayor of the town. If no resident is provided, the town will become mayorless.")
public class OverrideLeaderCommand implements ParameterizedCommand {
    @Override
    public CommandElement[] getArguments() {
        return new CommandElement[] {
                TownsElementsFactory.createTownCommandElement(),
                GenericArguments.optional(GenericArguments.user(Text.of("resident")))
        };
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        HunterTowns.getInstance().getTownFacade().overrideLeader(
                args.<Town>getOne("town").orElse(null),
                args.<User>getOne("resident").orElse(null)
        );

        return CommandResult.success();
    }
}
