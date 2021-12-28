package dev.haedhutner.towns.command.rent;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.haedhutner.core.command.ParameterizedCommand;
import dev.haedhutner.core.command.PlayerCommand;
import dev.haedhutner.core.command.annotation.Aliases;
import dev.haedhutner.core.command.annotation.Description;
import dev.haedhutner.core.command.annotation.Permission;
import dev.haedhutner.towns.HunterTowns;
import dev.haedhutner.towns.api.permission.TownsPermissionContext;
import dev.haedhutner.towns.api.permission.TownsPermissionContexts;
import dev.haedhutner.towns.facade.RentFacade;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.time.Duration;

@Aliases("create")
@Permission("atherystowns.rent.create")
@Description("Makes the plot you're standing in a rented one.")
@Singleton
public class CreateRentCommand implements PlayerCommand, ParameterizedCommand {

    @Inject
    private RentFacade rentFacade;

    @Nonnull
    @Override
    public CommandResult execute(@Nonnull Player source, @Nonnull CommandContext args) throws CommandException {
        rentFacade.makePlotRentable(
                source,
                args.<BigDecimal>getOne("price").get(),
                args.<Duration>getOne("period").get(),
                args.<TownsPermissionContext>getOne("context").orElse(TownsPermissionContexts.TOWN)
        );
        return CommandResult.success();
    }

    @Override
    public CommandElement[] getArguments() {
        return new CommandElement[] {
                GenericArguments.bigDecimal(Text.of("price")),
                GenericArguments.duration(Text.of("period")),
                GenericArguments.optional(GenericArguments.catalogedElement(Text.of("context"), TownsPermissionContext.class))
        };
    }
}
