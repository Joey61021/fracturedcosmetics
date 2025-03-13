package com.fractured.enums;

public enum Cosmetic
{

    PROJECTILE_TRAIL("Projectile Tail");

    private final String display;

    Cosmetic(String display)
    {
        this.display = display;
    }

    public String getDisplay()
    {
        return display;
    }
}
