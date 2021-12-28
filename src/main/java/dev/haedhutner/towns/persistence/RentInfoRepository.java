package dev.haedhutner.towns.persistence;

import dev.haedhutner.core.db.CachedHibernateRepository;
import dev.haedhutner.towns.model.entity.RentInfo;
import dev.haedhutner.towns.persistence.cache.TownsCache;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.Collection;

@Singleton
public class RentInfoRepository extends CachedHibernateRepository<RentInfo, Long> {
    private TownsCache townsCache;

    @Inject
    public RentInfoRepository(TownsCache townsCache) {
        super(RentInfo.class);
        super.cache = townsCache.getRentInfoCache();
        this.townsCache = townsCache;
    }

    public Collection<RentInfo> getAll() {
        return townsCache.getRentInfoCache().getAll();
    }
}
