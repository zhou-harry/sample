package com.harry.database.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageInterceptor;
import com.harry.database.context.DynamicDataSource;
import com.harry.database.properties.DynamicDataSourceProperties;
import com.harry.database.properties.SourceProperties;
import com.harry.database.scanner.MapperScanner;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 动态数据源配置
 */
@Configuration
@ConditionalOnProperty(name = "harry.datasource.dynamic",havingValue = "true")
@EnableConfigurationProperties(DynamicDataSourceProperties.class)
@MapperScanner(
        value = "${dynamic.datasource.packageLocation}",
        sqlSessionFactoryRef = "dynamicSqlSessionFactory"
)
public class DynamicDataSourceConfig {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private final DynamicDataSourceProperties dataSourceProperties;

    public DynamicDataSourceConfig(DynamicDataSourceProperties dataSourceProperties) {
        this.dataSourceProperties = dataSourceProperties;
    }

    @Bean(name = "defaultDataSource")
    public DynamicDataSource dynamicDataSource() {
        logger.debug("初始化动态数据源...");

        DynamicDataSource dynamicDataSource = DynamicDataSource.getInstance();

        List<SourceProperties> sources = dataSourceProperties.getSources();

        Map<Object,Object> map = new HashMap<>();

        boolean defaultDataSource=true;
        for (SourceProperties source : sources) {
            DruidDataSource dataSource = new DruidDataSource();
            dataSource.setUrl(source.getUrl());
            dataSource.setUsername(source.getUsername());
            dataSource.setPassword(source.getPassword());
            dataSource.setDriverClassName(source.getDriverClass());
            //具体配置
            dataSource.setInitialSize(source.getInitialSize());
            dataSource.setMinIdle(source.getMinIdle());
            dataSource.setMaxActive(source.getMaxActive());
            dataSource.setMaxWait(source.getMaxWait());
            dataSource.setTimeBetweenEvictionRunsMillis(source.getTimeBetweenEvictionRunsMillis());
            dataSource.setMinEvictableIdleTimeMillis(source.getMinEvictableIdleTimeMillis());
            dataSource.setValidationQuery(source.getValidationQuery());
            dataSource.setTestWhileIdle(source.isTestWhileIdle());
            dataSource.setTestOnBorrow(source.isTestOnBorrow());
            dataSource.setTestOnReturn(source.isTestOnReturn());
            dataSource.setPoolPreparedStatements(source.isPoolPreparedStatements());
            dataSource.setMaxPoolPreparedStatementPerConnectionSize(source.getMaxPoolPreparedStatementPerConnectionSize());
            try {
                dataSource.setFilters(source.getFilters());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            dataSource.setConnectionProperties(source.getConnectionProperties());

            map.put(source.getId(), dataSource);
            if (defaultDataSource){
                dynamicDataSource.setDefaultTargetDataSource(dataSource);
                defaultDataSource=false;
            }
        }
        dynamicDataSource.setTargetDataSources(map);

        return dynamicDataSource;
    }

    @Bean(name = "dynamicSqlSessionFactory")
    public SqlSessionFactory dynamicSqlSessionFactory(@Qualifier("defaultDataSource") DataSource defaultDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(defaultDataSource);
        //分页插件
        Interceptor interceptor = new PageInterceptor();
        Properties properties = new Properties();
        //数据库
        properties.setProperty("helperDialect", "mysql");
        //是否将参数offset作为PageNum使用
        properties.setProperty("offsetAsPageNum", "true");
        //是否进行count查询
        properties.setProperty("rowBoundsWithCount", "true");
        //是否分页合理化
        properties.setProperty("reasonable", "false");
        interceptor.setProperties(properties);
        sessionFactory.setPlugins(new Interceptor[]{interceptor});

        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(dataSourceProperties.getMapperLocation()));

        return sessionFactory.getObject();
    }


}
