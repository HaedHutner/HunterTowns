package dev.haedhutner.towns.command.nation;

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
@Description("Commands for nation roles")
@Permission("atherystowns.nation.role")
@HelpCommand(title = "Nation Role Help", prefix = "nation")
@Singleton
public class NationRoleCommand implements ParentCommand {

    @Inject
    private AddNationRoleCommand addNationRoleCommand;

    @Inject
    private RevokeNationRoleCommand revokeNationRoleCommand;

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        return CommandResult.success();
    }

    @Override
    public Set<CommandExecutor> getChildren() {
        return Sets.newHashSet(addNationRoleCommand, revokeNationRoleCommand);
    }
}
