package dev.haedhutner.towns.command.nation.admin;

import com.google.inject.Inject;
import dev.haedhutner.core.command.PlayerCommand;
import dev.haedhutner.core.command.annotation.Aliases;
import dev.haedhutner.core.command.annotation.Description;
import dev.haedhutner.core.command.annotation.Permission;
import dev.haedhutner.towns.HunterTowns;
import dev.haedhutner.towns.facade.NationFacade;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;

import javax.annotation.Nonnull;

@Aliases({"abandon", "unclaim"})
@Description("Deletes the nation plot you are currently standing in.")
@Permission("atherystowns.nation.admin.plot.unclaim")
public class UnclaimNationPlotCommand implements PlayerCommand {

    @Inject
    private NationFacade nationFacade;

    @Nonnull
    @Override
    public CommandResult execute(@Nonnull Player source, @Nonnull CommandContext args) throws CommandException {
        nationFacade.removeNationPlotAtPlayerLocation(source);
        return CommandResult.success();
    }
}
