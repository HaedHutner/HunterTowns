package dev.haedhutner.towns.command.town;

import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.haedhutner.core.command.ParentCommand;
import dev.haedhutner.core.command.PlayerCommand;
import dev.haedhutner.core.command.annotation.*;
import dev.haedhutner.towns.HunterTowns;
import dev.haedhutner.towns.command.town.admin.*;
import dev.haedhutner.towns.command.town.admin.*;
import dev.haedhutner.towns.facade.TownFacade;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;

import javax.annotation.Nonnull;
import java.util.Set;

@Aliases({"town", "t"})
@Description("Base town command")
@Permission("atherystowns.town.base")
@HelpCommand(title = "Town Help", command = "help")
@Singleton
public class TownCommand implements PlayerCommand, ParentCommand {

    @Inject
    private CreateTownCommand createTownCommand;
    @Inject
    private RuinTownCommand ruinTownCommand;
    @Inject
    private JoinTownCommand joinTownCommand;
    @Inject
    private LeaveTownCommand leaveTownCommand;
    @Inject
    private DecreaseTownSizeCommand decreaseTownSizeCommand;
    @Inject
    private IncreaseTownSizeCommand increaseTownSizeCommand;
    @Inject
    private ClaimPlotCommand claimPlotCommand;
    @Inject
    private AbandonPlotCommand abandonPlotCommand;
    @Inject
    private TownInfoCommand townInfoCommand;
    @Inject
    private TownAddActorPermissionCommand townAddActorPermissionCommand;
    @Inject
    private TownRemoveActorPermissionCommand townRemoveActorPermissionCommand;
    @Inject
    private SetTownColorCommand setTownColorCommand;
    @Inject
    private SetTownDescriptionCommand setTownDescriptionCommand;
    @Inject
    private SetTownMotdCommand setTownMotdCommand;
    @Inject
    private SetTownNameCommand setTownNameCommand;
    @Inject
    private SetTownJoinableCommand setTownJoinableCommand;
    @Inject
    private SetTownPvpCommand setTownPvpCommand;
    @Inject
    private InviteToTownCommand inviteToTownCommand;
    @Inject
    private TownKickCommand townKickCommand;
    @Inject
    private WithdrawTownCommand withdrawTownCommand;
    @Inject
    private DepositTownCommand depositTownCommand;
    @Inject
    private SetTownSpawnCommand setTownSpawnCommand;
    @Inject
    private TownSpawnCommand townSpawnCommand;
    @Inject
    private TownRoleCommand townRoleCommand;
    @Inject
    private TownRaidCommand townRaidCommand;
    @Inject
    private TownPayDebtCommand townPayDebtCommand;
    @Inject
    private GrantTownCommand grantTownCommand;
    @Inject
    private RecalculateTownSizesCommand recalculateTownSizesCommand;
    @Inject
    private TownToggleTaxCommand townToggleTaxCommand;
    @Inject
    private OverrideLeaderCommand overrideLeaderCommand;

    @Inject
    private TownFacade townFacade;

    @Nonnull
    @Override
    public CommandResult execute(@Nonnull Player source, @Nonnull CommandContext args) throws CommandException {
        townFacade.sendTownInfo(source);
        return CommandResult.success();
    }

    @Override
    public Set<CommandExecutor> getChildren() {
        return Sets.newHashSet(

        );
    }
}
