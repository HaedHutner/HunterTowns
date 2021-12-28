package dev.haedhutner.towns.command.town;

import dev.haedhutner.core.command.PlayerCommand;
import dev.haedhutner.core.command.annotation.*;
import dev.haedhutner.towns.HunterTowns;
import dev.haedhutner.towns.command.town.admin.*;
import dev.haedhutner.towns.command.town.admin.*;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;

import javax.annotation.Nonnull;

@Aliases({"town", "t"})
@Description("Base town command")
@Children({
        CreateTownCommand.class,
        RuinTownCommand.class,
        JoinTownCommand.class,
        LeaveTownCommand.class,
        DecreaseTownSizeCommand.class,
        IncreaseTownSizeCommand.class,
        ClaimPlotCommand.class,
        AbandonPlotCommand.class,
        TownInfoCommand.class,
        TownAddActorPermissionCommand.class,
        TownRemoveActorPermissionCommand.class,
        SetTownColorCommand.class,
        SetTownDescriptionCommand.class,
        SetTownMotdCommand.class,
        SetTownNameCommand.class,
        SetTownJoinableCommand.class,
        SetTownPvpCommand.class,
        InviteToTownCommand.class,
        TownKickCommand.class,
        JoinTownCommand.class,
        WithdrawTownCommand.class,
        DepositTownCommand.class,
        SetTownSpawnCommand.class,
        TownSpawnCommand.class,
        TownRoleCommand.class,
        TownRaidCommand.class,
        TownPayDebtCommand.class,
        GrantTownCommand.class,
        RecalculateTownSizesCommand.class,
        TownToggleTaxCommand.class,
        OverrideLeaderCommand.class
})
@Permission("atherystowns.town.base")
@HelpCommand(title = "Town Help", command = "help")
public class TownCommand implements PlayerCommand {
    @Nonnull
    @Override
    public CommandResult execute(@Nonnull Player source, @Nonnull CommandContext args) throws CommandException {
        HunterTowns.getInstance().getTownFacade().sendTownInfo(source);
        return CommandResult.success();
    }
}