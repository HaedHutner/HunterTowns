package dev.haedhutner.towns.util;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.haedhutner.towns.TownsConfig;
import dev.haedhutner.towns.api.permission.TownsPermissionContext;
import dev.haedhutner.towns.model.entity.Nation;
import dev.haedhutner.towns.model.entity.Town;
import dev.haedhutner.towns.persistence.NationRepository;
import dev.haedhutner.towns.persistence.TownRepository;
import dev.haedhutner.towns.service.NationService;
import dev.haedhutner.towns.service.TownService;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.text.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Singleton
public final class TownsElementsFactory {

    @Inject
    private TownsConfig townsConfig;

    @Inject
    private NationService nationService;

    @Inject
    private TownService townService;

    @Inject
    private NationRepository nationRepository;

    @Inject
    private TownRepository townRepository;

    public CommandElement createTownCommandElement() {
        return GenericArguments.choices(
                Text.of("town"),
                () -> townRepository.getAll().stream().map(Town::getName).collect(Collectors.toList()),
                string -> townService.getTownFromName(string).orElse(null)
        );
    }

    public CommandElement createNationCommandElement() {
        return GenericArguments.choices(
                Text.of("nation"),
                () -> nationRepository.getAll().stream().map(Nation::getName).collect(Collectors.toList()),
                string -> nationService.getNationFromName(string).orElse(null)
        );
    }

    public static CommandElement townPermissionContext() {
        Map<String,TownsPermissionContext> contexts = new HashMap<>();
        Sponge.getRegistry().getAllOf(TownsPermissionContext.class).forEach(c -> {
            contexts.put(c.getCommandElementName(), c);
        });

        return GenericArguments.choices(
                Text.of("type"),
                contexts
        );
    }

    public CommandElement createTownRoleCommandElement() {
        return GenericArguments.choices(
                Text.of("role"),
                townsConfig.TOWN.ROLES.keySet().stream().collect(Collectors.toMap(s -> s, s -> s))
        );
    }

    public CommandElement createNationRoleCommandElement() {
        return GenericArguments.choices(
                Text.of("role"),
                townsConfig.NATION.ROLES.keySet().stream().collect(Collectors.toMap(s -> s, s -> s))
        );
    }
}
