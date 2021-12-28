package dev.haedhutner.towns.command.rent;

import dev.haedhutner.core.command.PlayerCommand;
import dev.haedhutner.core.command.annotation.Aliases;
import dev.haedhutner.core.command.annotation.Children;
import dev.haedhutner.core.command.annotation.HelpCommand;
import dev.haedhutner.core.command.annotation.Permission;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;

import javax.annotation.Nonnull;

@Aliases("rent")
@HelpCommand(title = "Renting Help")
@Children({
        CreateRentCommand.class,
        EvictRentCommand.class,
        InfoRentCommand.class,
        ListRentCommand.class,
        VacateRentCommand.class,
        BuyRentCommand.class,
        ClearRentCommand.class
})
@Permission("atherystowns.rent.base")
public class RentCommand implements PlayerCommand {
    @Nonnull
    @Override
    public CommandResult execute(@Nonnull Player source, @Nonnull CommandContext args) throws CommandException {
        return CommandResult.success();
    }
}
