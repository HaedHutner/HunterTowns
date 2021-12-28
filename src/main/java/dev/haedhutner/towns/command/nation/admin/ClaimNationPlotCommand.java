package dev.haedhutner.towns.command.nation.admin;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.haedhutner.core.command.ParameterizedCommand;
import dev.haedhutner.core.command.PlayerCommand;
import dev.haedhutner.core.command.annotation.Aliases;
import dev.haedhutner.core.command.annotation.Description;
import dev.haedhutner.core.command.annotation.Permission;
import dev.haedhutner.towns.HunterTowns;
import dev.haedhutner.towns.facade.NationFacade;
import dev.haedhutner.towns.model.entity.Nation;
import dev.haedhutner.towns.util.TownsElementsFactory;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.entity.living.player.Player;

import javax.annotation.Nonnull;

@Aliases("claim")
@Description("Claims the current plot selection for the specified nation.")
@Permission("atherystowns.nation.admin.plot.claim")
@Singleton
public class ClaimNationPlotCommand implements PlayerCommand, ParameterizedCommand {

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
    public CommandResult execute(@Nonnull Player source, @Nonnull CommandContext args) throws CommandException {
        nationFacade.claimNationPlotFromPlayerSelection(source, args.<Nation>getOne("nation").get());
        return CommandResult.success();
    }
}
