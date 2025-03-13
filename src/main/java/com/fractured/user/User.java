package com.fractured.user;

import com.fractured.enums.ProjectileTrail;

import java.util.UUID;

public class User
{
    private final UUID uuid;
    private ProjectileTrail projectileTrail = null;

    public User(UUID uuid)
    {
        this.uuid = uuid;
    }

    public ProjectileTrail getProjectileTrail()
    {
        return projectileTrail;
    }

    public void setProjectileTrail(ProjectileTrail projectileTrail)
    {
        this.projectileTrail = projectileTrail;
    }
}
