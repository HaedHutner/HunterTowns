package dev.haedhutner.towns.command.town;

import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.haedhutner.core.command.ParentCommand;
import dev.haedhutner.core.command.PlayerCommand;
import dev.haedhutner.core.command.annotation.*;
import dev.haedhutner.towns.facade.TownRaidFacade;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;

import javax.annotation.Nonnull;
import java.util.Set;

@Aliases("raid")
@Description("Base town raiding command.")
@Permission("atherystowns.town.raid.base")
@HelpCommand(title = "Town Raid Help", prefix = "town")
@Singleton
public class TownRaidCommand implements PlayerCommand, ParentCommand {

    @Inject
    private TownRaidStartCommand townRaidStartCommand;

    @Inject
    private TownRaidInfoCommand townRaidInfoCommand;

    @Inject
    private TownRaidCancelCommand townRaidCancelCommand;

    @Nonnull
    @Override
    public CommandResult execute(@Nonnull Player source, @Nonnull CommandContext args) throws CommandException {
        return CommandResult.empty();
    }

    @Override
    public Set<CommandExecutor> getChildren() {
        return Sets.newHashSet(
                townRaidCancelCommand,
                townRaidInfoCommand,
                townRaidCancelCommand
        );
    }
}
