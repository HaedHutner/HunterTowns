package dev.haedhutner.towns.persistence;

import com.google.inject.Provider;
import dev.haedhutner.core.db.CachedHibernateRepository;
import dev.haedhutner.towns.model.entity.Resident;
import dev.haedhutner.towns.persistence.cache.TownsCache;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.UUID;

@Singleton
public class ResidentRepository extends CachedHibernateRepository<Resident, UUID> {

    @Inject
    private Provider<TownsCache> townsCache;

    @Inject
    protected ResidentRepository(TownsCache townsCache) {
        super(Resident.class);
        super.cache = townsCache.getResidentCache();
        this.townsCache = townsCache;
    }


}
