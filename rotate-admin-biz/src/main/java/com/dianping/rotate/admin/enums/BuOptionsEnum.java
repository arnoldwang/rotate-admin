package com.dianping.rotate.admin.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shenyoujun on 15/3/23.
 */
public enum BuOptionsEnum {

    WEDDING(1,"结婚"),
    FILM(2,"电影"),
    HOTEL(3,"酒店"),
    JY(4,"交易平台"),
    TG(5,"推广"),
    YD(6,"预订");

    private int id;
    private String desc;

    BuOptionsEnum(int id, String desc) {
        this.id = id;
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(String text) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    private static final String CODE = "value";
    private static final String DESC = "text";

    public static List<Map<String,Object>> toList(){
        List<Map<String,Object>> resultList = new ArrayList<Map<String, Object>>();
        for(BuOptionsEnum enumItem: values()){
            Map<String,Object> enumInfo = new HashMap<String, Object>();
            enumInfo.put(CODE,enumItem.id);
            enumInfo.put(DESC,enumItem.desc);
            resultList.add(enumInfo);
        }
        return resultList;
    }

    public static Map<Integer,String> idAssnDesc(){

        Map<Integer,String> idAssnDesc = new HashMap<Integer, String>();

        for(BuOptionsEnum buOptionsEnum : values()){
            idAssnDesc.put(buOptionsEnum.getId(),buOptionsEnum.getDesc());
        }

        return idAssnDesc;
    }
}
