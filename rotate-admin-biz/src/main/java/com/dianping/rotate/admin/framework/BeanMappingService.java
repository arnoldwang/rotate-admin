package com.dianping.rotate.admin.framework;

/**
 * Created by feipeng on 15/1/4.
 */
public interface BeanMappingService {

    <T> T transform(Object o, Class<T> clazz);
}
