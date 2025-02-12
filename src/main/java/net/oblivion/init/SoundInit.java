package net.oblivion.init;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.oblivion.OblivionMain;

public class SoundInit {

    public static SoundEvent DRILL_ON_EVENT = register("drill_on");
    public static SoundEvent DRILL_IDLE_EVENT = register("drill_idle");
    public static SoundEvent DRILL_OFF_EVENT = register("drill_off");

    public static SoundEvent GUIDELIGHT_TELEPORT_EVENT = register("guidelight_teleport");
    public static SoundEvent GUIDELIGHT_STOP_EVENT = register("guidelight_stop");

    public static SoundEvent BOAR_IDLE_EVENT = register("boar_idle");
    public static SoundEvent BOAR_HURT_EVENT = register("boar_hurt");
    public static SoundEvent BOAR_DEATH_EVENT = register("boar_death");

    public static SoundEvent TURKEY_IDLE_EVENT = register("turkey_idle");
    public static SoundEvent TURKEY_HURT_EVENT = register("turkey_hurt");
    public static SoundEvent TURKEY_DEATH_EVENT = register("turkey_death");

    public static SoundEvent SHROOM_IDLE_EVENT = register("shroom_idle");
    public static SoundEvent SHROOM_HURT_EVENT = register("shroom_hurt");
    public static SoundEvent SHROOM_DEATH_EVENT = register("shroom_death");

    private static SoundEvent register(String id) {
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(OblivionMain.identifierOf(id)));
    }

    public static void init() {
    }
}
