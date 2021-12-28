package dev.haedhutner.towns.command.nation;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.haedhutner.core.command.annotation.Aliases;
import dev.haedhutner.core.command.annotation.Description;
import dev.haedhutner.core.command.annotation.Permission;
import dev.haedhutner.towns.HunterTowns;
import dev.haedhutner.towns.facade.NationFacade;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;

import javax.annotation.Nonnull;

@Aliases("list")
@Description("Lists all nations.")
@Permission("atherystowns.nation.list")
@Singleton
public class NationListCommand implements CommandExecutor {

    @Inject
    private NationFacade nationFacade;

    @Nonnull
    @Override
    public CommandResult execute(@Nonnull CommandSource src, @Nonnull CommandContext args) throws CommandException {
        nationFacade.listNations(src);
        return CommandResult.success();
    }
}
