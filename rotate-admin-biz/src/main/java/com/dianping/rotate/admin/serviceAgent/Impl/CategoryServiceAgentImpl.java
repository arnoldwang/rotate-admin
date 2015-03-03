package com.dianping.rotate.admin.serviceAgent.Impl;

import com.dianping.combiz.entity.Category;
import com.dianping.combiz.service.CategoryService;
import com.dianping.combiz.service.impl.CategoryServiceImpl;
import com.dianping.rotate.admin.serviceAgent.CategoryServiceAgent;
import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.aop.framework.Advised;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by feipeng on 15/3/3.
 */
@Component
public class CategoryServiceAgentImpl implements CategoryServiceAgent {
    @Autowired
    private CategoryService categoryService;

    static Method checkInitMethod;
    static Field  cityCategoryCacheField;

    static {
        try {
            checkInitMethod = CategoryServiceImpl.class.getDeclaredMethod("checkInit");
            checkInitMethod.setAccessible(true);
            cityCategoryCacheField = CategoryServiceImpl.class.getDeclaredField("cityCategoryCache");
            cityCategoryCacheField.setAccessible(true);
        }catch (Exception e){
            e.printStackTrace();
            System.exit(0);
        }
    }



    @Override
    public List<Category> getDistinctCategories() {
        Map<Integer,Category> result = getId2CategoryMap();
        return Lists.newArrayList(result.values());
    }

    @SneakyThrows
    private Map<Integer, Category> getId2CategoryMap() {
        Map<Integer,Category> result = new HashMap<Integer, Category>();
        Object target = ((Advised)categoryService).getTargetSource().getTarget();
        checkInitMethod.invoke(target);
        Map<Integer, Set<Category>> cityCategoryCache = (Map<Integer, Set<Category>>)cityCategoryCacheField.get(target);
        List<Integer> cityIds = Lists.newArrayList( cityCategoryCache.keySet() );
        Collections.sort(cityIds);
        for(Integer cityId:cityIds){
            Set<Category> set = cityCategoryCache.get(cityId);
            for(Category c:set) {
                if (result.containsKey(c.getId())){
                    continue;
                }else{
                    result.put(c.getId(),c);
                }
            }
        }
        return result;
    }


    @Override
    public Category loadCategory(int categoryId) {
        return getId2CategoryMap().get(categoryId);
    }

}
