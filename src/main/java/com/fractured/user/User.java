package com.fractured.user;

import com.fractured.enums.HitEffect;
import com.fractured.enums.ProjectileTrail;

import java.util.UUID;

public class User
{
    private final UUID uuid;
    private ProjectileTrail projectileTrail = null;
    private HitEffect hitEffect;

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

    public HitEffect getHitEffect()
    {
        return hitEffect;
    }

    public void setHitEffect(HitEffect hitEffect)
    {
        this.hitEffect = hitEffect;
    }
}
