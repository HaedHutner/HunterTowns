package dev.haedhutner.towns.permission;

import dev.haedhutner.towns.HunterTowns;
import dev.haedhutner.towns.service.TownsPermissionService;
import org.spongepowered.api.service.context.Context;
import org.spongepowered.api.service.context.ContextCalculator;
import org.spongepowered.api.service.permission.Subject;

import java.util.Set;

public class TownsContextCalculator implements ContextCalculator<Subject> {
    @Override
    public void accumulateContexts(Subject calculable, Set<Context> accumulator) {
        HunterTowns.getInstance().getPermissionService().accumulateContexts(calculable, accumulator);
    }

    @Override
    public boolean matches(Context context, Subject calculable) {
        return TownsPermissionService.NATION_CONTEXT_KEY.equals(context.getKey()) || TownsPermissionService.TOWN_CONTEXT_KEY.equals(context.getKey());
    }
}
