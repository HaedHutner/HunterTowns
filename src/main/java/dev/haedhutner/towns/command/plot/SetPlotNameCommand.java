package dev.haedhutner.towns.command.plot;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.haedhutner.core.command.ParameterizedCommand;
import dev.haedhutner.core.command.PlayerCommand;
import dev.haedhutner.core.command.annotation.Aliases;
import dev.haedhutner.core.command.annotation.Description;
import dev.haedhutner.core.command.annotation.Permission;
import dev.haedhutner.towns.facade.PlotFacade;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

import javax.annotation.Nonnull;
import java.util.Optional;

@Aliases("rename")
@Permission("atherystowns.plot.rename")
@Description("Renames the plot at your location.")
@Singleton
public class SetPlotNameCommand implements ParameterizedCommand, PlayerCommand {

    @Inject
    private PlotFacade plotFacade;

    @Override
    public CommandElement[] getArguments() {
        return new CommandElement[]{
                GenericArguments.text(Text.of("name"), TextSerializers.FORMATTING_CODE, true)
        };
    }

    @Nonnull
    @Override
    public CommandResult execute(@Nonnull Player source, @Nonnull CommandContext args) throws CommandException {
        Optional<Text> newName = args.getOne("name");
        plotFacade.renameTownPlotAtPlayerLocation(source, newName.orElse(Text.EMPTY));
        return CommandResult.success();
    }
}
