package com.dianping.rotate.admin.translator.ruleItem;

import com.dianping.combiz.entity.Category;
import com.dianping.combiz.service.CategoryService;
import com.dianping.rotate.admin.controller.CommonDataController;
import com.dianping.rotate.admin.exceptions.ApplicationException;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by feipeng on 15/1/28.
 */
@Component
public class MainCategoryTranslator extends AbstractRuleItemTranslator {

    @Autowired
    private CategoryService categoryService;

    @Override
    public Object encode(Integer v) {
        Category me = categoryService.loadCategory(CommonDataController.CATEGORY_CITY_ID, v);
        if (me == null) {
            return Lists.newArrayList();
        }
        List<Category> ret = categoryService.getMainCategoryPath(CommonDataController.CATEGORY_CITY_ID, v);
        if (ret.size() == 0) {
            ret.add(me);
        }
        return Lists.transform(ret, new Function<Category, Object>() {
            @Override
            public Object apply(Category category) {
                Map map = Maps.newHashMap();
                map.put("categoryId",category.getId());
                map.put("categoryName",category.getName());
                return map;
            }
        });
    }




    @Override
    public Integer decode(Object o) {
        List<Map> list=(List<Map>)o;
        if(CollectionUtils.isNotEmpty(list)){
            Map last = list.get(list.size()-1);
            if(last.get("categoryId")!=null){
                return (Integer) last.get("categoryId");
            }else {
                if(list.size() >1 ){
                    Map s = list.get(list.size()-2);
                    if(s.get("categoryId")!=null){
                        return (Integer) s.get("categoryId");
                    }
                }
            }
        }
        throw new ApplicationException("请选择分类");
    }
}
