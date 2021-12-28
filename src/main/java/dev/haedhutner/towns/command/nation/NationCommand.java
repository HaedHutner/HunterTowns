package dev.haedhutner.towns.command.nation;

import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.haedhutner.core.command.ParentCommand;
import dev.haedhutner.core.command.PlayerCommand;
import dev.haedhutner.core.command.annotation.Aliases;
import dev.haedhutner.core.command.annotation.Description;
import dev.haedhutner.core.command.annotation.HelpCommand;
import dev.haedhutner.core.command.annotation.Permission;
import dev.haedhutner.towns.command.nation.admin.*;
import dev.haedhutner.towns.facade.NationFacade;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;

import javax.annotation.Nonnull;
import java.util.Set;

@Aliases({"nation", "n"})
@Description("Base nation command.")
@Permission("atherystowns.nation.base")
@HelpCommand(title = "Nation Help", command = "help")
@Singleton
public class NationCommand implements PlayerCommand, ParentCommand {

    @Inject
    private CreateNationCommand createNationCommand;

    @Inject
    private DisbandNationCommand disbandNationCommand;

    @Inject
    private AddNationAllyCommand addNationAllyCommand;

    @Inject
    private AddNationEnemyCommand addNationEnemyCommand;

    @Inject
    private AddNationNeutralCommand addNationNeutralCommand;

    @Inject
    private NationAddActorPermissionCommand nationAddActorPermissionCommand;

    @Inject
    private NationInfoCommand nationInfoCommand;

    @Inject
    private NationRemoveActorPermissionCommand nationRemoveActorPermissionCommand;

    @Inject
    private NationListCommand nationListCommand;

    @Inject
    private DepositNationCommand depositNationCommand;

    @Inject
    private WithdrawNationCommand withdrawNationCommand;

    @Inject
    private SetNationCapitalCommand setNationCapitalCommand;

    @Inject
    private SetNationNameCommand setNationNameCommand;

    @Inject
    private SetNationDescriptionCommand setNationDescriptionCommand;

    @Inject
    private NationRoleCommand nationRoleCommand;

    @Inject
    private NationSetColorCommand setColorCommand;

    @Inject
    private SetNationTaxCommand setNationTaxCommand;

    @Inject
    private AddTownToNationCommand addTownToNationCommand;

    @Inject
    private RemoveTownFromNationCommand removeTownFromNationCommand;

    @Inject
    private NationPlotCommand nationPlotCommand;

    @Inject
    private ClaimNationPlotCommand claimNationPlotCommand;

    @Inject
    private UnclaimNationPlotCommand unclaimNationPlotCommand;

    @Inject
    private NationFacade nationFacade;

    @Nonnull
    @Override
    public CommandResult execute(@Nonnull Player source, @Nonnull CommandContext args) throws CommandException {
        nationFacade.sendPlayerNationInfo(source);
        return CommandResult.success();
    }

    @Override
    public Set<CommandExecutor> getChildren() {
        return Sets.newHashSet(
            unclaimNationPlotCommand,
            claimNationPlotCommand,
            nationAddActorPermissionCommand,
            removeTownFromNationCommand,
            addTownToNationCommand,
            setNationTaxCommand,
            nationRoleCommand,
            setNationDescriptionCommand,
            setNationNameCommand,
            setColorCommand,
            setNationCapitalCommand,
            nationPlotCommand,
            withdrawNationCommand,
            depositNationCommand,
            nationListCommand,
            nationRemoveActorPermissionCommand,
            nationInfoCommand,
            addNationNeutralCommand,
            addNationEnemyCommand,
            disbandNationCommand,
            addNationAllyCommand,
            createNationCommand
        );
    }
}
