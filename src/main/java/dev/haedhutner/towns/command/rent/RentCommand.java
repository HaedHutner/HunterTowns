package dev.haedhutner.towns.command.rent;

import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.haedhutner.core.command.ParentCommand;
import dev.haedhutner.core.command.PlayerCommand;
import dev.haedhutner.core.command.annotation.Aliases;
import dev.haedhutner.core.command.annotation.HelpCommand;
import dev.haedhutner.core.command.annotation.Permission;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;

import javax.annotation.Nonnull;
import java.util.Set;

@Aliases("rent")
@HelpCommand(title = "Renting Help")
@Permission("atherystowns.rent.base")
@Singleton
public class RentCommand implements PlayerCommand, ParentCommand {

    @Inject
    private CreateRentCommand createRentCommand;

    @Inject
    private EvictRentCommand evictRentCommand;

    @Inject
    private InfoRentCommand infoRentCommand;

    @Inject
    private ListRentCommand listRentCommand;

    @Inject
    private VacateRentCommand vacateRentCommand;

    @Inject
    private BuyRentCommand buyRentCommand;

    @Inject
    private ClearRentCommand clearRentCommand;

    @Nonnull
    @Override
    public CommandResult execute(@Nonnull Player source, @Nonnull CommandContext args) throws CommandException {
        return CommandResult.success();
    }

    @Override
    public Set<CommandExecutor> getChildren() {
        return Sets.newHashSet(
            createRentCommand,
            evictRentCommand,
            infoRentCommand,
            listRentCommand,
            vacateRentCommand,
            buyRentCommand,
            clearRentCommand
        );
    }
}
