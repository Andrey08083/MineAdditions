package ua.andrey08xtomyoll.mineadditions.util;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import ua.andrey08xtomyoll.mineadditions.ModMain;
import ua.andrey08xtomyoll.mineadditions.init.ModBlocks;
import ua.andrey08xtomyoll.mineadditions.init.ModItems;

import java.util.ArrayList;
import java.util.List;

public class AlchemyExtractorRecipies {

    private static final ArrayList<List<Item>> inputList = new ArrayList<List<Item>>();
    // Основной результат
    private static final ArrayList<Item> resultList = new ArrayList<Item>();
    // Суб-продукты
    public static final ArrayList<List<Item>> subOutputList = new ArrayList<List<Item>>();
    // Время плавки
    private static final ArrayList<Integer> timeList = new ArrayList<Integer>();


    public static void init() {
		/* Рецепты состоят из:
		- int время плавления
		- Item[]{} список  исходных (до 5 соответсвенно)
		- Item основной результат
		- Item[]{} список  суб-результатов (до 4х соответсвенно)

		Ниже разобран как пример по строкам
		- Руда типа 1 + Руда типа 2 за 200 тиков дают Кирпич + Порох + Камень
		*/
        AlchemyExtractorRecipies.addFurnaceRecipeWithCookTime(500, new Item[]{(ModItems.LITTLE_LABATIUM_DUST), Items.WHEAT_SEEDS}, ModItems.LABATIUM_FRUIT_SEED, new Item[]{Items.GLASS_BOTTLE});
        AlchemyExtractorRecipies.addFurnaceRecipeWithCookTime(500, new Item[]{(ModItems.LITTLE_MAZURIUM_DUST), Items.WHEAT_SEEDS}, ModItems.LABATIUM_FRUIT_SEED, new Item[]{Items.GLASS_BOTTLE});

    }

    // Добавляем рецепт
    public static void addFurnaceRecipeWithCookTime(int cookTime, Item[] inputRaw, Item result, Item[] outputRaw)
    {
        List<Item> input = new ArrayList<Item>();
        for(int i = 0; i < inputRaw.length; i++)
            input.add(inputRaw[i]);

        List<Item> output = new ArrayList<Item>();
        for(int i = 0; i < outputRaw.length; i++)
            output.add(outputRaw[i]);

        // Проверяем, что входящие и результат существуют
        if (result == null)
            return;
        for(int i = 0; i < input.size(); i++){
            if (input.get(i) == null)
                return;
        }
        ModMain.log("Рецепт на " + new ItemStack(result).getDisplayName() + " зарегестрирован.");
        timeList.add(cookTime);
        inputList.add(input);
        resultList.add(result);
        subOutputList.add(output);
    }

    // Получение времени приготовления по входящим
    public static int getCookTimeForItems(List<Item> input)
    {
        boolean isEmpty = true;
        for(int i = 0; i < input.size(); i++){
            if (input.get(i) != null){
                isEmpty =  false;
                break;
            }
        }
        if(isEmpty){
            return 200;
        }

        int matches = 0;

        for (int i = 0; i < inputList.size(); i++){
            if(input.size() != inputList.get(i).size()){
                continue;
            }
            for(int n = 0; n < input.size(); n++){
                for(int m = 0; m < inputList.get(i).size(); m++){
                    if(inputList.get(i).get(m) == input.get(n)){
                        matches++;
                        continue;
                    }
                }
            }

            if(matches == input.size()){
                return timeList.get(i);
            }
            else{
                matches = 0;
            }
        }
        return 200;
    }

    // Получение результата по входящим
    public static Item getResultForItems(List<Item> input)
    {
        boolean isEmpty = true;
        for(int i = 0; i < input.size(); i++){
            if (input.get(i) != null){
                isEmpty =  false;
                break;
            }
        }
        if(isEmpty){
            return null;
        }

        int matches = 0;

        for (int i = 0; i < inputList.size(); i++){
            if(input.size() != inputList.get(i).size()){
                continue;
            }
            for(int n = 0; n < input.size(); n++){
                for(int m = 0; m < inputList.get(i).size(); m++){
                    if(inputList.get(i).get(m) == input.get(n)){
                        matches++;
                    }
                }
            }

            if(matches == input.size()){
                return resultList.get(i);
            }
            else{
                matches = 0;
            }
        }

        ModMain.log("После проверки всех рецептов совпадения не найдено.");
        return null;
    }

    public static List<Item> getSubsForItems(List<Item> input)
    {
        boolean isEmpty = true;
        for(int i = 0; i < input.size(); i++)
        {
            if (input.get(i) != null)
            {
                isEmpty =  false;
                break;
            }
        }
        if(isEmpty){
            return null;
        }

        int matches = 0;

        for (int i = 0; i < inputList.size(); i++)
        {
            if(input.size() != inputList.get(i).size())
            {
                continue;
            }
            for(int n = 0; n < input.size(); n++)
            {
                for(int m = 0; m < inputList.get(i).size(); m++)
                {
                    if(inputList.get(i).get(m) == input.get(n))
                    {
                        matches++;
                    }
                }
            }

            if(matches == input.size())
            {
                return subOutputList.get(i);
            }
            else{
                matches = 0;
            }
        }
        return null;
    }

}
