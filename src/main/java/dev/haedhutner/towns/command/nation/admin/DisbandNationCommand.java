package dev.haedhutner.towns.command.nation.admin;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.haedhutner.core.command.ParameterizedCommand;
import dev.haedhutner.core.command.annotation.Aliases;
import dev.haedhutner.core.command.annotation.Description;
import dev.haedhutner.core.command.annotation.Permission;
import dev.haedhutner.towns.facade.NationFacade;
import dev.haedhutner.towns.model.entity.Nation;
import dev.haedhutner.towns.util.TownsElementsFactory;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;

import javax.annotation.Nonnull;

@Aliases("disband")
@Description("Disbands a nation.")
@Permission("atherystowns.nation.admin.disband")
@Singleton
public class DisbandNationCommand implements ParameterizedCommand {

    @Inject
    private NationFacade nationFacade;

    @Inject
    private TownsElementsFactory townsElementsFactory;

    @Override
    public CommandElement[] getArguments() {
        return new CommandElement[]{
                townsElementsFactory.createNationCommandElement()
        };
    }

    @Nonnull
    @Override
    public CommandResult execute(@Nonnull CommandSource src, CommandContext args) throws CommandException {
        nationFacade.disbandNation(
                src,
                args.<Nation>getOne("nation").get()
        );
        return CommandResult.success();
    }
}