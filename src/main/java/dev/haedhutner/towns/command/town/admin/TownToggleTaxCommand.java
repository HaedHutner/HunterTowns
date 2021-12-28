package dev.haedhutner.towns.command.town.admin;

import dev.haedhutner.core.command.ParameterizedCommand;
import dev.haedhutner.core.command.annotation.Aliases;
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
import org.spongepowered.api.text.Text;

@Aliases("taxable")
@Permission("atherystowns.admin.town.taxable")
public class TownToggleTaxCommand implements ParameterizedCommand {

    @Override
    public CommandElement[] getArguments() {
        return new CommandElement[] {
                TownsElementsFactory.createTownCommandElement(),
                GenericArguments.bool(Text.of("taxable"))
        };
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        HunterTowns.getInstance().getTownFacade().setTownTaxable(
                args.<Town>getOne("town").orElse(null),
                args.<Boolean>getOne("taxable").orElse(true)
        );
        return CommandResult.success();
    }
}
