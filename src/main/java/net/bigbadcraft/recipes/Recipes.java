package main.java.net.bigbadcraft.recipes;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

/**
 * User: Heinrich Quirit
 * Last Modified: 10/2/13
 * Time: 5:49 AM
 */
public class Recipes {
    public Recipes() {
        ItemStack saddle = new ItemStack(Material.SADDLE, 1);
        ShapedRecipe saddlerecipe = new ShapedRecipe(saddle);
        saddlerecipe.shape(new String[] {
                "LLL", "LIL", "I I"
        });
        saddlerecipe.setIngredient('L', Material.LEATHER);
        saddlerecipe.setIngredient('I', Material.IRON_INGOT);
        Bukkit.addRecipe(saddlerecipe);

        ItemStack chainh = new ItemStack(Material.CHAINMAIL_HELMET, 1);
        ShapedRecipe chainhrecipe = new ShapedRecipe(chainh);
        chainhrecipe.shape(new String[] {
                "   ", "FFF", "I I"
        });
        chainhrecipe.setIngredient('F', Material.FLINT);
        chainhrecipe.setIngredient('I', Material.IRON_INGOT);
        Bukkit.addRecipe(chainhrecipe);

        ItemStack chainc = new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1);
        ShapedRecipe chaincrecipe = new ShapedRecipe(chainc);
        chaincrecipe.shape(new String[] {
                "I I", "III", "FFF"
        });
        chaincrecipe.setIngredient('F', Material.FLINT);
        chaincrecipe.setIngredient('I', Material.IRON_INGOT);
        Bukkit.addRecipe(chaincrecipe);

        ItemStack chainl = new ItemStack(Material.CHAINMAIL_LEGGINGS, 1);
        ShapedRecipe chainlrecipe = new ShapedRecipe(chainl);
        chainlrecipe.shape(new String[] {
                "FFF", "I I", "I I"
        });
        chainlrecipe.setIngredient('F', Material.FLINT);
        chainlrecipe.setIngredient('I', Material.IRON_INGOT);
        Bukkit.addRecipe(chainlrecipe);

        ItemStack chainb = new ItemStack(Material.CHAINMAIL_BOOTS, 1);
        ShapedRecipe chainbrecipe = new ShapedRecipe(chainb);
        chainbrecipe.shape(new String[] {
                "   ", "F F", "I I"
        });
        chainbrecipe.setIngredient('F', Material.FLINT);
        chainbrecipe.setIngredient('I', Material.IRON_INGOT);
        Bukkit.addRecipe(chainbrecipe);
    }
}
