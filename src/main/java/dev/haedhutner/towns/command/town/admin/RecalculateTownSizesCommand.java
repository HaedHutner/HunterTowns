package dev.haedhutner.towns.command.town.admin;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.haedhutner.core.command.annotation.Aliases;
import dev.haedhutner.core.command.annotation.Permission;
import dev.haedhutner.towns.HunterTowns;
import dev.haedhutner.towns.facade.TownFacade;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;

@Aliases("recalculateTownSizes")
@Permission("atherystowns.admin.town.recalculateTownSizes")
@Singleton
public class RecalculateTownSizesCommand implements CommandExecutor {

    @Inject
    private TownFacade townFacade;

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        townFacade.recalculateTownSizes();
        return CommandResult.success();
    }
}
