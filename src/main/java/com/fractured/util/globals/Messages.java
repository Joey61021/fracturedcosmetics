package com.fractured.util.globals;

import com.fractured.config.keys.TextConfigKey;

import static com.fractured.config.DataSupplier.textKey;

public final class Messages
{
    private Messages()
    {
    }

    public static final TextConfigKey GENERAL_NO_CONSOLE = textKey("general.messages.no_console");
    public static final TextConfigKey GENERAL_NO_PERMISSION = textKey("general.messages.no_permission");
    public static final TextConfigKey GENERAL_NO_PLAYER = textKey("general.messages.no_player");
    public static final TextConfigKey GENERAL_INVALID_ARGS = textKey("general.messages.invalid_args");

    public static final TextConfigKey COMMAND_COSMETICS_USAGE = textKey("commands.cosmetics.usage");
    public static final TextConfigKey COMMAND_COSMETICS_INVALID_TYPE = textKey("commands.cosmetics.invalid_type");

    public static final TextConfigKey COMMAND_PROJECTILE_TRAIL_USAGE = textKey("commands.projectile_trail.usage");
    public static final TextConfigKey COMMAND_PROJECTILE_TRAIL_INVALID_TYPE = textKey("commands.projectile_trail.invalid_type");
    public static final TextConfigKey COMMAND_PROJECTILE_TRAIL_ALREADY_SELECTED = textKey("commands.projectile_trail.already_selected");
    public static final TextConfigKey COMMAND_PROJECTILE_TRAIL_SELECTED = textKey("commands.projectile_trail.selected");
}
