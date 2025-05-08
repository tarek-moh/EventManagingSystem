package org.example.eventmanagingsystem.managers;

import org.example.eventmanagingsystem.models.myCategory;
import org.example.eventmanagingsystem.services.Database;

public class CategoryManager {

    public static boolean isValid(String category) {
        var categories = Database.getCategoryList();
        for (var cat : categories) {
            if (category.equals(cat.getName())) {
                return true;
            }
        }
        return false;
    }

    public static void createCategory(String newcategoryName) throws IllegalArgumentException
    {
        myCategory newCategory = new myCategory();
        newCategory.setName(newcategoryName);
        Database.getCategoryList().add(newCategory);
        System.out.println("Category created successfully");
    }

}