package dev.haedhutner.towns.persistence;

import com.google.inject.Provider;
import dev.haedhutner.core.db.CachedHibernateRepository;
import dev.haedhutner.towns.model.entity.Town;
import dev.haedhutner.towns.model.entity.TownPlot;
import dev.haedhutner.towns.persistence.cache.TownPlotCache;
import dev.haedhutner.towns.persistence.cache.TownsCache;
import dev.haedhutner.towns.util.MathUtils;
import com.flowpowered.math.vector.Vector2i;
import com.flowpowered.math.vector.Vector3i;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.Collection;

@Singleton
public class TownPlotRepository extends CachedHibernateRepository<TownPlot, Long> {

    @Inject
    private Provider<TownsCache> townsCache;

    private TownPlotCache townPlotCache;

    @Inject
    protected TownPlotRepository(TownsCache townsCache) {
        super(TownPlot.class);
        super.cache = townsCache.getTownPlotCache();
        this.townPlotCache = townsCache.getTownPlotCache();
        this.townsCache = townsCache;
    }

    public Collection<TownPlot> getPlotsIntersectingChunk(Vector3i chunkPosition) {
        return townPlotCache.getPlotsOverlappingChunk(MathUtils.vec3iToVec2i(chunkPosition));
    }

    public Collection<TownPlot> getPlotsIntersectingChunk(Vector2i chunkPosition) {
        return townPlotCache.getPlotsOverlappingChunk(chunkPosition);
    }

    public Collection<TownPlot> getAll() {
        return cache.getAll();
    }

    @Override
    public void initCache() {
        townsCache.getResidentCache().getAll().forEach(resident -> {
            Town town = resident.getTown();
            if (town != null) {
                cache.addAll(town.getPlots());
            }
        });
    }
}
