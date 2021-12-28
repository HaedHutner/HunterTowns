package dev.haedhutner.towns.command.town;

import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.haedhutner.core.command.ParentCommand;
import dev.haedhutner.core.command.annotation.*;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;

import java.util.Set;

@Aliases("role")
@Description("Commands for town roles")
@Permission("atherystowns.town.role")
@HelpCommand(title = "Town Role Help", prefix = "town")
@Singleton
public class TownRoleCommand implements ParentCommand {

    @Inject
    private AddTownRoleCommand addTownRoleCommand;

    @Inject
    private RevokeTownRoleCommand revokeTownRoleCommand;

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        return CommandResult.success();
    }


    @Override
    public Set<CommandExecutor> getChildren() {
        return Sets.newHashSet(
                addTownRoleCommand,
                revokeTownRoleCommand
        );
    }
}
