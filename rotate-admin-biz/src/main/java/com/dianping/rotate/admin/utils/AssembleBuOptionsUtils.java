package com.dianping.rotate.admin.utils;


import com.dianping.rotate.admin.enums.BuOptionsEnum;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shenyoujun on 15/3/23.
 */
public class AssembleBuOptionsUtils {

    public static Map<String,List<Map<String,Object>>> getBuOptions(){

        try {
            String buOption = ConfigUtils.getBuAssnCheckBox();

            if(StringUtils.isEmpty(buOption)) return null;

            Map<String,Object> buOption_ = JsonUtils.fromStrToMap(buOption);

            Map<String,List<Map<String,Object>>> result = new HashMap<String, List<Map<String, Object>>>();

            final Map<Integer,String> buAssnDesc = BuOptionsEnum.idAssnDesc();

            generateResult(buOption_, result, buAssnDesc);

            return result;

        } catch (IOException e) {
            return null;
        }
    }

    private static void generateResult(Map<String, Object> buOption_, Map<String, List<Map<String, Object>>> result, final Map<Integer, String> buAssnDesc) {
        for(Map.Entry me : buOption_.entrySet()){
            List<Map<String,Object>> mapList = Lists.newArrayList();

            List<Integer> buList = (List<Integer>)me.getValue();

            if(CollectionUtils.isNotEmpty(buList)){
                mapList = Lists.newArrayList(Lists.transform(buList, new Function<Integer, Map<String,Object>>() {
                    @Override
                    public  Map<String,Object> apply(Integer input) {
                        Map<String,Object> result = new HashMap<String, Object>();

                        result.put("text",buAssnDesc.get(input));
                        result.put("value",input);
                        return result;
                    }
                }));
            }
            result.put((String) me.getKey(),mapList);
        }
    }

}
