package ua.andrey08xtomyoll.mineadditions.init;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import ua.andrey08xtomyoll.mineadditions.util.Reference;

import java.util.ArrayList;
import java.util.List;


public class ModSounds {

    public static List<SoundEvent> sounds = new ArrayList();

    //Sounds
    public static SoundEvent untitled_record = initSound("untitled_record", "untitled_record");
    public static SoundEvent van_shoot_sound = initSound("van_shoot_sound", "van_shoot_sound");
    public static SoundEvent van_ambient_sound = initSound("van_ambient_sound", "van_ambient_sound");
    public static SoundEvent van_death_sound = initSound("van_death_sound", "van_death_sound");
    public static SoundEvent van_hurt_sound = initSound("van_hurt_sound", "van_hurt_sound");


    private static SoundEvent initSound(String resourcePath, String name) {
        SoundEvent soundEvent = new SoundEvent(new ResourceLocation(Reference.MOD_ID, resourcePath)).setRegistryName(name);
        sounds.add(soundEvent);
        return soundEvent;
    }

}
