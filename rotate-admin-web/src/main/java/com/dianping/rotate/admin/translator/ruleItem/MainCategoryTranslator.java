package com.dianping.rotate.admin.translator.ruleItem;

import com.dianping.combiz.entity.Category;
import com.dianping.combiz.service.CategoryService;
import com.dianping.rotate.admin.controller.CommonDataController;
import com.dianping.rotate.admin.exceptions.ApplicationException;
import com.dianping.rotate.admin.serviceAgent.CategoryServiceAgent;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by feipeng on 15/1/28.
 */
@Component
public class MainCategoryTranslator extends AbstractRuleItemTranslator {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryServiceAgent categoryServiceAgent;

    @Override
    public Object encode(Integer v) {

        categoryServiceAgent.getDistinctCategories();
        Category me = categoryServiceAgent.loadCategory(v);
        if (me == null) {
            return buildNotFoundResult(v,"分类不存在");
        }
        Map map = Maps.newHashMap();
        map.put("categoryId",v);
        map.put("categoryName",me.getName());
        return map;
//        Category me = categoryService.loadCategory(CommonDataController.CATEGORY_CITY_ID, v);
//        if (me == null) {
//            throw new ApplicationException(String.format("找不到分类%s",v));
//        }
//        List<Category> categories = categoryService.getRootCategories(CommonDataController.CATEGORY_CITY_ID);
//
//
//        List<Category> ret = categoryService.getMainCategoryPath(CommonDataController.CATEGORY_CITY_ID, v);
//        if (ret.size() == 0) {
//            for(Category category:categories){
//                if(category.getId().equals(v)){
//                    ret.add(category);
//                }
//            }
//            if (ret.size() == 0){
//               List<List<Category>> list =  categoryService.getAllCategoryPath(v,CommonDataController.CATEGORY_CITY_ID);
//                if(CollectionUtils.isNotEmpty(list)){
//                    ret = list.get(0);
//                }else{
//                    throw new ApplicationException("找不到分类%s的上级",v);
//                }
//            }
//
//        }
//
//        Collections.reverse(ret);
//        return Lists.transform(ret, new Function<Category, Object>() {
//            @Override
//            public Object apply(Category category) {
//                Map map = Maps.newHashMap();
//                map.put("categoryId",category.getId());
//                map.put("categoryName",category.getName());
//                return map;
//            }
//        });
    }




    @Override
    public Integer decode(Object o) {
//        List<Map> list=(List<Map>)(((Map)o).get("items"));
//        if(CollectionUtils.isNotEmpty(list)){
//            Map last = list.get(list.size()-1);
//            if(last.get("categoryId")!=null){
//                return (Integer) last.get("categoryId");
//            }else {
//                if(list.size() >1 ){
//                    Map s = list.get(list.size()-2);
//                    if(s.get("categoryId")!=null){
//                        return (Integer) s.get("categoryId");
//                    }
//                }
//            }
//        }
        Integer v = (Integer)((Map)o).get("categoryId");
        if(v==null){
            throw new ApplicationException("请选择分类");
        }
        return v;
    }
}
