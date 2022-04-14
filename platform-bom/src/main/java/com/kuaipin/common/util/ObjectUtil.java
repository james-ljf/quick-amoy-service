package com.kuaipin.common.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 泛型转换工具类
 * @Author: ljf
 * @DateTime: 2022/3/25 0:43
 */
public class ObjectUtil {

    private ObjectUtil(){

    }

    /**
     * 将 object 对象转换为指定泛型的 list 对象
     *
     * @param obj   需要转换的对象
     * @param cla   list 对象的泛型
     * @param <T>   list 对象的泛型类型
     * @return      转换的 list 对象
     */
    public static <T> List<T> objToList(Object obj, Class<T> cla){
        List<T> list = new ArrayList<T>();
        if (obj instanceof List<?>) {
            for (Object o : (List<?>) obj) {
                list.add(cla.cast(o));
            }
            return list;
        }
        return new ArrayList<>();
    }

}
