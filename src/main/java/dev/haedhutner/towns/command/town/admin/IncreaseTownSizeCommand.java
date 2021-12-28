package dev.haedhutner.towns.command.town.admin;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.haedhutner.core.command.ParameterizedCommand;
import dev.haedhutner.core.command.annotation.Aliases;
import dev.haedhutner.core.command.annotation.Description;
import dev.haedhutner.core.command.annotation.Permission;
import dev.haedhutner.towns.HunterTowns;
import dev.haedhutner.towns.facade.TownAdminFacade;
import dev.haedhutner.towns.model.entity.Town;
import dev.haedhutner.towns.util.TownsElementsFactory;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.text.Text;

@Aliases("increase")
@Description("Increases the size of the town.")
@Permission("atherystowns.admin.town.increase")
@Singleton
public class IncreaseTownSizeCommand implements ParameterizedCommand {

    @Inject
    private TownsElementsFactory townsElementsFactory;

    @Inject
    private TownAdminFacade townAdminFacade;

    @Override
    public CommandElement[] getArguments() {
        return new CommandElement[]{
                townsElementsFactory.createTownCommandElement(),
                GenericArguments.integer(Text.of("amount"))
        };
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        townAdminFacade.increaseTownSize(
                src, args.<Town>getOne("town").get(), args.<Integer>getOne("amount").get()
        );
        return CommandResult.success();
    }
}
