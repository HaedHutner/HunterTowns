package dev.haedhutner.towns.command.town;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.haedhutner.core.command.PlayerCommand;
import dev.haedhutner.core.command.annotation.Aliases;
import dev.haedhutner.core.command.annotation.Description;
import dev.haedhutner.core.command.annotation.Permission;
import dev.haedhutner.towns.HunterTowns;
import dev.haedhutner.towns.facade.TownFacade;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;

@Aliases("paydebt")
@Description("Pay off your town debt early.")
@Permission("atherystowns.town.paydebt")
@Singleton
public class TownPayDebtCommand implements PlayerCommand {

    @Inject
    private TownFacade townFacade;

    @Override
    public CommandResult execute(Player src, CommandContext args) throws CommandException {
        townFacade.payTownDebt(src);
        return CommandResult.success();
    }
}
