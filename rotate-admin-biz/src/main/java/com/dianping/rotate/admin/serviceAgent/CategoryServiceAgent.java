package com.dianping.rotate.admin.serviceAgent;

import com.dianping.combiz.entity.Category;

import java.util.List;

/**
 * Created by feipeng on 15/3/3.
 */
public interface CategoryServiceAgent {
    List<Category> getDistinctCategories();

    Category loadCategory(int categoryId);
}
