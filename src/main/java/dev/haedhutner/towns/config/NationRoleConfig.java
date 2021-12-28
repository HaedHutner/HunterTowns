package dev.haedhutner.towns.config;

import dev.haedhutner.towns.api.permission.Permission;
import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

import java.util.HashSet;
import java.util.Set;

@ConfigSerializable
public class NationRoleConfig {

    @Setting("name")
    private String name;

    @Setting("nation-permissions")
    private Set<Permission> nationPermissions = new HashSet<>();

    public NationRoleConfig() {
    }

    public void setTownPermissions(Set<Permission> nationPermissions) {
        this.nationPermissions = nationPermissions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Permission> getNationPermissions() {
        return nationPermissions;
    }
}
