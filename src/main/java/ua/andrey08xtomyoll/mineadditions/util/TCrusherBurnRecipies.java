package ua.andrey08xtomyoll.mineadditions.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ua.andrey08xtomyoll.mineadditions.ModMain;
import ua.andrey08xtomyoll.mineadditions.init.ModBlocks;
import ua.andrey08xtomyoll.mineadditions.init.ModItems;

public class TCrusherBurnRecipies
{

    // Исходные
	private static ArrayList<List<Item>> inputList 			= new ArrayList<List<Item>>();
	// Основной результат
	private static ArrayList<Item> resultList 				= new ArrayList<Item>();
	// Суб-продукты
	private static ArrayList<List<Item>> subOutputList 		= new ArrayList<List<Item>>();
	// Время плавки
    private static ArrayList<Integer> timeList 				= new ArrayList<Integer>();


    // Инициализируется в главном классе
	public static void init()
	{
		/* Рецепты состоят из:
		- int время плавления
		- Item[]{} список  исходных (до 5 соответсвенно)
		- Item основной результат
		- Item[]{} список  суб-результатов (до 4х соответсвенно)

		Ниже разобран как пример по строкам
		- Руда типа 1 + Руда типа 2 за 200 тиков дают Кирпич + Порох + Камень
		*/
		TCrusherBurnRecipies.addFurnaceRecipeWithCookTime(
			200,
			new Item[]{Item.getItemFromBlock(ModBlocks.LABATIUM_ORE), Item.getItemFromBlock(ModBlocks.MAZURIUM_ORE)},
			Items.BRICK,
			new Item[]{Items.GUNPOWDER, Item.getItemFromBlock(Blocks.STONE)}
		);

        TCrusherBurnRecipies.addFurnaceRecipeWithCookTime(200, new Item[]{Item.getItemFromBlock(ModBlocks.LABATIUM_ORE)}, ModItems.LABATIUM, new Item[]{});
        TCrusherBurnRecipies.addFurnaceRecipeWithCookTime(200, new Item[]{Item.getItemFromBlock(ModBlocks.LABATIUM_ORE), Items.COAL}, ModItems.LABATIUM, new Item[]{Items.GUNPOWDER});

	}

	// Добавление рецепта
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

		boolean confirm = false;
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

		boolean confirm = false;
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
    	for(int i = 0; i < input.size(); i++){
            if (input.get(i) != null){
            	isEmpty =  false;
            	break;
            }
    	}
    	if(isEmpty){
    		return null;
    	}

		boolean confirm = false;
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
    			return subOutputList.get(i);
    		}
    		else{
    			matches = 0;
    		}
        }
        return null;
    }

    // Получение суб-продукции по результату
    /*public static List<Item> getSubsForItems(Item result)
    {
    	if (result == null)
    		return null;
    	TomMain.log("[Субпродукты] Результат не равен нулю (" + result.getUnlocalizedName() + ")");

    	for (int i = 0; i < resultList.size(); i++){

        	TomMain.log("[Субпродукты] Результат " + i + " равен " + resultList.get(i));
    		if(resultList.get(i) == result){
    			TomMain.log("[Субпродукты] Он подходит, выдаём список " + i + ", равный " + subOutputList.get(i));
    			return subOutputList.get(i);
    		}
		}

        return null;
    }*/

}
