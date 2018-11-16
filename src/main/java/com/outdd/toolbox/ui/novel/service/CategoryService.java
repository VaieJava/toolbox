package com.outdd.toolbox.ui.novel.service;

import com.outdd.toolbox.reptile.novel.pojo.Category;

import java.util.List;

public interface CategoryService {
    public List<Category> findList(Category record);

    public List<Category> getFirstCategory(Category record);
}
