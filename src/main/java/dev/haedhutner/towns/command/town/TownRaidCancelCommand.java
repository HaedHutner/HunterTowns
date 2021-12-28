package dev.haedhutner.towns.command.town;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.haedhutner.core.command.PlayerCommand;
import dev.haedhutner.core.command.annotation.Aliases;
import dev.haedhutner.core.command.annotation.Description;
import dev.haedhutner.core.command.annotation.Permission;
import dev.haedhutner.towns.HunterTowns;
import dev.haedhutner.towns.facade.TownFacade;
import dev.haedhutner.towns.facade.TownRaidFacade;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;

import javax.annotation.Nonnull;

@Aliases("Cancel")
@Description("Cancels your active town raid spawn point.")
@Permission("atherystowns.town.raid.cancel")
@Singleton
public class TownRaidCancelCommand implements PlayerCommand {

    @Inject
    private TownRaidFacade townRaidFacade;

    @Nonnull
    @Override
    public CommandResult execute(@Nonnull Player source, @Nonnull CommandContext args) throws CommandException {
        townRaidFacade.removeRaidPoint(source);
        return CommandResult.success();
    }

}
