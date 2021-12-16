package com.kuaipin.search.server.service;


import com.kuaipin.search.server.SearchApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 创建商品数据Test
 * @Author: ljf
 * @DateTime: 2021/12/16 0:37
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SearchApplication.class)
public class CreateDataService {


    @Test
    public void createDataToMysql(){


        System.out.println("成功");
    }

}
