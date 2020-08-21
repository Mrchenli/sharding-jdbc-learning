package com.kiibos.sharding.jdbc.java;

import com.kiibos.sharding.jdbc.java.service.OrderServiceImpl;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.shardingsphere.api.config.sharding.KeyGeneratorConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @ClassName JavaConfigurationExampleMain
 * @Description TODO
 * @Author cl
 * @Date 2020/8/21 下午2:23
 **/
public class ShardingDataBaseExampleMain {

    private static final String HOST = "127.0.0.1";

    private static final int PORT = 3306;

    private static final String USER_NAME = "root";

    private static final String PASSWORD = "root";


    public static void main(String[] args) throws SQLException {
        DataSource dataSource = ShardingDataSourceFactory
                .createDataSource(createDateSourceMap(),createShardingRuleConfiguration(),new Properties());
        OrderServiceImpl orderService = new OrderServiceImpl(dataSource);
        ExampleExecuteTemplate.run(orderService);
    }


    public static KeyGeneratorConfiguration getKeyGeneratorConfiguration(){
        KeyGeneratorConfiguration configuration = new KeyGeneratorConfiguration("SNOWFLAKE","order_id");
        return configuration;
    }

    public static TableRuleConfiguration getOrderTableRuleConfiguration(){
        TableRuleConfiguration result = new TableRuleConfiguration("t_order","demo_ds_${0..1}.t_order");
        result.setKeyGeneratorConfig(getKeyGeneratorConfiguration());
        return result;
    }


    //默认使用snowflake
    public static TableRuleConfiguration getOrderItemTableRuleConfiguration(){
        TableRuleConfiguration result = new TableRuleConfiguration("t_order_item","demo_ds_${0..1}.t_order_item");
        return result;
    }


    public static ShardingRuleConfiguration createShardingRuleConfiguration(){
        ShardingRuleConfiguration result = new ShardingRuleConfiguration();
        result.getTableRuleConfigs().add(getOrderTableRuleConfiguration());
        result.getTableRuleConfigs().add(getOrderItemTableRuleConfiguration());
        result.getBindingTableGroups().add("t_order,t_order_item");
        result.getBroadcastTables().add("t_address");
        result.setDefaultDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("user_id","demo_ds_${user_id % 2}"));
        //table
        return result;
    }

    public static Map<String,DataSource> createDateSourceMap(){
        Map<String,DataSource> result = new HashMap<String, DataSource>();
        result.put("demo_ds_0",createDataSource("demo_ds_0"));
        result.put("demo_ds_1",createDataSource("demo_ds_1"));
        return result;
    }


    public static DataSource createDataSource(String dataSourceName){
        HikariDataSource result = new HikariDataSource();
        result.setDriverClassName("com.mysql.jdbc.Driver");
        result.setJdbcUrl(String.format("jdbc:mysql://%s:%s/%s?serverTimezone=UTC&useSSL=false&useUnicode=true&characterEncoding=UTF-8", HOST, PORT, dataSourceName));
        result.setUsername(USER_NAME);
        result.setPassword(PASSWORD);
        return result;
    }

}
