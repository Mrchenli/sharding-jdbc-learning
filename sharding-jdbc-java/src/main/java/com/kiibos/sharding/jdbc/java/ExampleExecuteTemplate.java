package com.kiibos.sharding.jdbc.java;

import com.kiibos.sharding.jdbc.java.service.ExampleService;

import java.sql.SQLException;

/**
 * @ClassName ExampleExecuteTemplate
 * @Description TODO
 * @Author cl
 * @Date 2020/8/21 下午6:19
 **/
public class ExampleExecuteTemplate {

    public static void run(final ExampleService exampleService) throws SQLException {
        try {
            exampleService.initEnvironment();
            exampleService.processSuccess();
        } finally {
            exampleService.cleanEnvironment();
        }
    }

    public static void runFailure(final ExampleService exampleService) throws SQLException {
        try {
            exampleService.initEnvironment();
            exampleService.processFailure();
        } finally {
            exampleService.cleanEnvironment();
        }
    }

}
