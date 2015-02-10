package com.dianping.rotate.admin.utils;

import com.dianping.rotate.org.dto.Team;

/**
 * Created by shenyoujun on 15/2/10.
 */
public class MessageAssembleUtils {

    public static String assembleMessageWithTeam(String msgType,String errorMsg,Team...args){

        if(args==null) return assembleErrorMessage(msgType,errorMsg);

        int length = args.length;

        if(length<=0){
            return assembleErrorMessage(msgType,errorMsg);
        }

        String[] names = new String[length];

        for(int i =0;i<length;i++){
            if(args[i] == null || args[i].getTeamName()!=null){
                names[i] = args[i].getTeamName();
            }else {
                names[i] = errorMsg;
            }
        }

        String result = String.format(msgType,names);

        return result;
    }


    public static String assembleErrorMessage(String msgType,String errorMsg){
        return String.format(msgType,errorMsg);
    }


}
