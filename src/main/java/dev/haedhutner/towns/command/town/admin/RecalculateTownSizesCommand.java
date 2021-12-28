package dev.haedhutner.towns.command.town.admin;

import dev.haedhutner.core.command.annotation.Aliases;
import dev.haedhutner.core.command.annotation.Permission;
import dev.haedhutner.towns.HunterTowns;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;

@Aliases("recalculateTownSizes")
@Permission("atherystowns.admin.town.recalculateTownSizes")
public class RecalculateTownSizesCommand implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        HunterTowns.getInstance().getTownFacade().recalculateTownSizes();
        return CommandResult.success();
    }
}
