package dev.haedhutner.towns.command.town;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.haedhutner.core.command.ParameterizedCommand;
import dev.haedhutner.core.command.annotation.Aliases;
import dev.haedhutner.core.command.annotation.Description;
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

import javax.annotation.Nonnull;

@Aliases("info")
@Description("Displays information about a town.")
@Permission("atherystowns.town.info")
@Singleton
public class TownInfoCommand implements ParameterizedCommand {

    @Inject
    private TownFacade townFacade;

    @Inject
    private TownsElementsFactory townsElementsFactory;

    @Override
    public CommandElement[] getArguments() {
        return new CommandElement[]{
                townsElementsFactory.createTownCommandElement()
        };
    }

    @Nonnull
    @Override
    public CommandResult execute(@Nonnull CommandSource src, @Nonnull CommandContext args) throws CommandException {
        townFacade.sendTownInfo(args.<Town>getOne("town").get(), src);
        return CommandResult.success();
    }
}
