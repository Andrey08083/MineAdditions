package ua.andrey08xtomyoll.mineadditions.handlers;


import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ua.andrey08xtomyoll.mineadditions.util.Reference;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Головний конфігураційний клас модифікації
 */
@Config(modid = Reference.MOD_ID)
@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class ConfigHandler {

    @Config.LangKey(Reference.MOD_ID + ".cfg.labatium_category")
    public static LabatiumSettings labatiumSettings = new LabatiumSettings();

    @Config.LangKey(Reference.MOD_ID + ".cfg.tomium_category")
    public static TomiumSettings tomiumSettings = new TomiumSettings();

    @Config.LangKey(Reference.MOD_ID + ".cfg.general_category")
    public static GeneralSettings generalSettings = new GeneralSettings();

    @Config.LangKey(Reference.MOD_ID + ".cfg.ore_gen_category")
    public static OreGenSettings oreGenSettings = new OreGenSettings();

    /**
     * Подія, яка викликається при оновленні значень конфігурації в грі
     * @param event дані події
     */
    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(Reference.MOD_ID)) {
            ConfigManager.sync(Reference.MOD_ID, Config.Type.INSTANCE);
            initConfig();
        }
    }

    /**
     * Метод оновлення даних в масиві режимів глибини копання
     */
    public static void initConfig() {
        GeneralSettings.depthModesArray = new ArrayList<>();
        GeneralSettings.depthModesArray.add(1);
        for (String depth : ConfigHandler.generalSettings.depthModes.split("\\|")) {
            if (canBeParsed(depth))
                GeneralSettings.depthModesArray.add(Math.abs(Integer.parseInt(depth)));
        }
        Set<Integer> tempSet = new TreeSet<>(GeneralSettings.depthModesArray);
        GeneralSettings.depthModesArray = new ArrayList<>(tempSet);
    }

    /**
     * Метод перевірки числа на можливість парсингу
     * @param num число у форматі String
     * @return true, якщо це число у правильному форматі, або false, якщо це не число
     */
    public static boolean canBeParsed(String num) {
        try {
            Integer.parseInt(num);
        }
        catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * Вкладений клас спільних налаштувань
     */
    public static class GeneralSettings {
        @Config.LangKey(Reference.MOD_ID + ".cfg.depth_modes")
        @Config.Comment("Split values by | symbol")
        public String depthModes = "1|3|5";
        public static List<Integer> depthModesArray = new ArrayList<>();

        @Config.LangKey(Reference.MOD_ID + ".cfg.can_fly")
        public boolean canFly = true;

        @Config.LangKey(Reference.MOD_ID + ".cfg.can_fly")
        public int vanDamage = 5;
    }

    /**
     * Вкладений клас налаштувань Лабатіумової броні та інструментів
     */
    public static class LabatiumSettings {
        @Config.LangKey(Reference.MOD_ID + ".cfg.overall_resistance")
        public int overallResistance = 80;
        @Config.LangKey(Reference.MOD_ID + ".cfg.helmet_resistance")
        public int helmetResistance = 20;
        @Config.LangKey(Reference.MOD_ID + ".cfg.chestplate_resistance")
        public int chestplateResistance = 20;
        @Config.LangKey(Reference.MOD_ID + ".cfg.leggings_resistance")
        public int leggingsResistance = 20;
        @Config.LangKey(Reference.MOD_ID + ".cfg.boots_resistance")
        public int bootsResistance = 20;
        @Config.LangKey(Reference.MOD_ID + ".cfg.overall_toughness")
        public float overallToughness = 20;
    }

    /**
     * Вкладений клас налаштувань Томіумової броні та інструментів
     */
    public static class TomiumSettings {
        @Config.LangKey(Reference.MOD_ID + ".cfg.overall_resistance")
        public int overallResistance = 80;
        @Config.LangKey(Reference.MOD_ID + ".cfg.helmet_resistance")
        public int helmetResistance = 20;
        @Config.LangKey(Reference.MOD_ID + ".cfg.chestplate_resistance")
        public int chestplateResistance = 20;
        @Config.LangKey(Reference.MOD_ID + ".cfg.leggings_resistance")
        public int leggingsResistance = 20;
        @Config.LangKey(Reference.MOD_ID + ".cfg.boots_resistance")
        public int bootsResistance = 20;
        @Config.LangKey(Reference.MOD_ID + ".cfg.overall_toughness")
        public float overallToughness = 20;
    }

    /**
     * Вкладений клас налаштувань Генерації руди
     */
    public static class OreGenSettings {
        @Config.LangKey(Reference.MOD_ID + ".cfg.labatium_min_height")
        @Config.RangeInt(min = 1, max = 255)
        public int labatiumMinHeight = 8;
        @Config.LangKey(Reference.MOD_ID + ".cfg.labatium_max_height")
        @Config.RangeInt(min = 20, max = 255)
        public int labatiumMaxHeight = 20;
        @Config.LangKey(Reference.MOD_ID + ".cfg.labatium_per_chunk_spawn")
        public int labatiumPerChunkSpawn = 4;

        @Config.LangKey(Reference.MOD_ID + ".cfg.tomium_min_height")
        @Config.RangeInt(min = 1, max = 255)
        public int tomiumMinHeight = 8;
        @Config.LangKey(Reference.MOD_ID + ".cfg.tomium_max_height")
        @Config.RangeInt(min = 20, max = 255)
        public int tomiumMaxHeight = 20;
        @Config.LangKey(Reference.MOD_ID + ".cfg.tomium_per_chunk_spawn")
        public int tomiumPerChunkSpawn = 4;

    }

}