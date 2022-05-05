package com.kuaipin.common.util;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static org.springframework.beans.BeanUtils.copyProperties;

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
        List<T> list = new ArrayList<>();
        if (obj instanceof List<?>) {
            for (Object o : (List<?>) obj) {
                list.add(cla.cast(o));
            }
            return list;
        }
        return new ArrayList<>();
    }

    /**
     * 集合数据的拷贝
     * @param sources: 数据源类
     * @param target: 目标类::new
     * @return  List<T>
     */
    public static <S, T> List<T> copyList(List<S> sources, Supplier<T> target) {
        List<T> list = new ArrayList<>(sources.size());
        for (S source : sources) {
            T t = target.get();
            copyProperties(source, t);
            list.add(t);
        }
        return list;
    }

    /**
     * 将对象的属性copy到另一个对象
     * @param startObj  初始对象
     * @param targetObj 目标对象
     * @param <T>   T
     * @return  T
     */
    public static  <T> T getTarget(T startObj, T targetObj){
        copyProperties(startObj, targetObj);
        return targetObj;
    }


}
