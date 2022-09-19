package com.cozycats.cozycatsbackend.admin;


import com.cozycats.cozycatsbackend.admin.Category.CategoryRepository;
import com.cozycats.cozycatscommon.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.stereotype.Component;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)

public class CategoryRepositoryTests {

    @Autowired
    private CategoryRepository repo;

    @Test
    public void testCreateRootCategery(){
        Category category = new Category("Toys");
        Category savedCategory = repo.save(category);
        assertThat(savedCategory.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateSubCategory(){
        Category parent = new Category(6);
        Category subCategory = new Category("Toys 1 1", parent);
        Category cat =  repo.save(subCategory);
        assertThat(cat.getId()).isGreaterThan(0);
    }

    @Test
    public void testGetCategory(){
        Category category = repo.findById(1).get();
        System.out.println(category.getName());
        Set<Category> children = category.getChildren();

        for (Category subCategory : children){
            System.out.println("--" + subCategory.getName());
        }

        assertThat(children.size()).isGreaterThan(0);
    }

    @Test
    public void testPrintHierarchialCategories(){
        Iterable<Category> categories = repo.findAll();

        for (Category category : categories){
            if(category.getParent() == null){
                System.out.println(category.getName());
                Set<Category> children = category.getChildren();
                for(Category subCategory : children){
                    System.out.println("--" + subCategory.getName());
                    printChildren(subCategory, 1);
                }
            }
        }
    }

    private void printChildren(Category parent, int sublevel){
        int newSubLevel = sublevel + 1;
        Set<Category> children = parent.getChildren();
        for(Category subCategory : children){
            for (int i=0;i<newSubLevel;i++){
                System.out.print("----");
            }
            System.out.println(subCategory.getName());
        }
    }

}








