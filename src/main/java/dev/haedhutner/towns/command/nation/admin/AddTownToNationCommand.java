package dev.haedhutner.towns.command.nation.admin;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.haedhutner.core.command.ParameterizedCommand;
import dev.haedhutner.core.command.annotation.Aliases;
import dev.haedhutner.core.command.annotation.Description;
import dev.haedhutner.core.command.annotation.Permission;
import dev.haedhutner.towns.facade.NationFacade;
import dev.haedhutner.towns.model.entity.Nation;
import dev.haedhutner.towns.model.entity.Town;
import dev.haedhutner.towns.util.TownsElementsFactory;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;

import javax.annotation.Nonnull;

@Singleton
@Aliases("add")
@Description("Adds a town to a nation.")
@Permission("atherystowns.nation.admin.add")
public class AddTownToNationCommand implements ParameterizedCommand {

    @Inject
    private NationFacade nationFacade;

    @Inject
    private TownsElementsFactory townsElementsFactory;

    @Override
    public CommandElement[] getArguments() {
        return new CommandElement[]{
                townsElementsFactory.createNationCommandElement(),
                townsElementsFactory.createTownCommandElement()
        };
    }

    @Nonnull
    @Override
    public CommandResult execute(@Nonnull CommandSource src, CommandContext args) throws CommandException {
        nationFacade.addTownToNation(
                args.<Nation>getOne("nation").get(),
                args.<Town>getOne("town").get()
        );
        return CommandResult.success();
    }

}
