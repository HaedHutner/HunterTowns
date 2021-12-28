package dev.haedhutner.towns.command.town.admin;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.haedhutner.core.command.ParameterizedCommand;
import dev.haedhutner.core.command.annotation.Aliases;
import dev.haedhutner.core.command.annotation.Permission;
import dev.haedhutner.towns.HunterTowns;
import dev.haedhutner.towns.facade.TownFacade;
import dev.haedhutner.towns.model.entity.Town;
import dev.haedhutner.towns.util.TownsElementsFactory;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.text.Text;

@Aliases("taxable")
@Permission("atherystowns.admin.town.taxable")
@Singleton
public class TownToggleTaxCommand implements ParameterizedCommand {

    @Inject
    private TownFacade townFacade;

    @Inject
    private TownsElementsFactory townsElementsFactory;

    @Override
    public CommandElement[] getArguments() {
        return new CommandElement[] {
                townsElementsFactory.createTownCommandElement(),
                GenericArguments.bool(Text.of("taxable"))
        };
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        townFacade.setTownTaxable(
                args.<Town>getOne("town").orElse(null),
                args.<Boolean>getOne("taxable").orElse(true)
        );
        return CommandResult.success();
    }
}
