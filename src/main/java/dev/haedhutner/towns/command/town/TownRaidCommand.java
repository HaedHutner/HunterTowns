package dev.haedhutner.towns.command.town;

import dev.haedhutner.core.command.PlayerCommand;
import dev.haedhutner.core.command.annotation.*;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;

import javax.annotation.Nonnull;

@Aliases("raid")
@Description("Base town raiding command.")
@Children({
        TownRaidStartCommand.class,
        TownRaidInfoCommand.class,
        TownRaidCancelCommand.class
})
@Permission("atherystowns.town.raid.base")
@HelpCommand(title = "Town Raid Help", prefix = "town")
public class TownRaidCommand implements PlayerCommand {

    @Nonnull
    @Override
    public CommandResult execute(@Nonnull Player source, @Nonnull CommandContext args) throws CommandException {
        return CommandResult.empty();
    }
}
