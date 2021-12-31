package dev.haedhutner.towns.persistence;

import com.google.inject.Provider;
import dev.haedhutner.core.db.CachedHibernateRepository;
import dev.haedhutner.core.db.cache.SimpleCache;
import dev.haedhutner.towns.model.entity.NationPlot;
import dev.haedhutner.towns.model.entity.Town;
import dev.haedhutner.towns.persistence.cache.TownsCache;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.Collection;

@Singleton
public class NationPlotRepository extends CachedHibernateRepository<NationPlot, Long> {

    private ResidentRepository residentRepository;

    public NationPlotRepository() {
        super(NationPlot.class, new SimpleCache<>());
    }

    @Override
    public void initCache() {
        residentRepository.findAll().forEach(resident -> {
            Town town = resident.getTown();
            if (town != null && town.getNation() != null) {
                super.cache.addAll(town.getNation().getPlots());
            }
        });
    }

    public Collection<NationPlot> getAll() {
        return cache.getAll();
    }
}
