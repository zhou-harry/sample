package com.harry.database.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageInterceptor;
import com.harry.database.properties.SourceProperties;
import com.harry.database.properties.StaticDataSourceProperties;
import com.harry.database.scanner.MapperScanner;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 数据源配置
 */
@Configuration
@ConditionalOnMissingBean({DynamicDataSourceConfig.class})
@EnableConfigurationProperties(StaticDataSourceProperties.class)
@MapperScanner(value = "${static.datasource.packageLocation}",sqlSessionFactoryRef = "defaultSqlSessionFactory")
public class StaticDataSourceConfig {


    private final StaticDataSourceProperties dataSourceProperties;

    public StaticDataSourceConfig(StaticDataSourceProperties dataSourceProperties) {
        this.dataSourceProperties = dataSourceProperties;
    }

    @Primary
    @Bean(name = "defaultDataSource")
    public DataSource defaultDataSource() {
        System.out.println("初始化静态数据源...");
        DruidDataSource dataSource = new DruidDataSource();
        SourceProperties source = dataSourceProperties.getSource();

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
        return dataSource;
    }

    @Bean(name = "defaultTransactionManager")
    public DataSourceTransactionManager defaultTransactionManager() {
        return new DataSourceTransactionManager(defaultDataSource());
    }

    @Bean(name = "defaultSqlSessionFactory")
    public SqlSessionFactory defaultSqlSessionFactory(@Qualifier("defaultDataSource") DataSource defaultDataSource)
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
