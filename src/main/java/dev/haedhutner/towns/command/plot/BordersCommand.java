package dev.haedhutner.towns.command.plot;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.haedhutner.core.command.ParameterizedCommand;
import dev.haedhutner.core.command.PlayerCommand;
import dev.haedhutner.core.command.annotation.Aliases;
import dev.haedhutner.core.command.annotation.Description;
import dev.haedhutner.core.command.annotation.Permission;
import dev.haedhutner.towns.HunterTowns;
import dev.haedhutner.towns.facade.PlotBorderFacade;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import javax.annotation.Nonnull;

@Aliases({"border", "borders"})
@Description("Displays outline for town plots.")
@Permission("atherystowns.plot.border")
@Singleton
public class BordersCommand implements PlayerCommand, ParameterizedCommand {

    @Inject
    private PlotBorderFacade plotBorderFacade;

    @Override
    public CommandElement[] getArguments() {
        return new CommandElement[]{
                GenericArguments.bool(Text.of("border"))
        };
    }

    @Nonnull
    @Override
    public CommandResult execute(@Nonnull Player source, @Nonnull CommandContext args) throws CommandException {
        plotBorderFacade.setPlayerViewBorderStatus(source, args.<Boolean>getOne("border").orElse(false));
        return CommandResult.success();
    }
}
