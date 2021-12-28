package dev.haedhutner.towns.command.rent;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.haedhutner.core.command.PlayerCommand;
import dev.haedhutner.core.command.annotation.Aliases;
import dev.haedhutner.core.command.annotation.Description;
import dev.haedhutner.core.command.annotation.Permission;
import dev.haedhutner.towns.HunterTowns;
import dev.haedhutner.towns.facade.RentFacade;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;

import javax.annotation.Nonnull;

@Aliases("clear")
@Description("Clears a plot from being rented.")
@Permission("atherystowns.rent.clear")
@Singleton
public class ClearRentCommand implements PlayerCommand {

    @Inject
    private RentFacade rentFacade;

    @Nonnull
    @Override
    public CommandResult execute(@Nonnull Player player, @Nonnull CommandContext commandContext) throws CommandException {
        rentFacade.clearPlotRentable(player);
        return CommandResult.success();
    }
}
