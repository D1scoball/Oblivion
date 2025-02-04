package net.oblivion.init;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.oblivion.OblivionMain;

public class SoundInit {

    public static SoundEvent DRILL_ON_EVENT = register("drill_on");
    public static SoundEvent DRILL_IDLE_EVENT = register("drill_idle");
    public static SoundEvent DRILL_OFF_EVENT = register("drill_off");

    private static SoundEvent register(String id) {
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(OblivionMain.identifierOf(id)));
    }

    public static void init() {
    }
}
