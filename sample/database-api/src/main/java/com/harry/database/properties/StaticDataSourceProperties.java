package com.harry.database.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "static.datasource")
public class StaticDataSourceProperties {

    //事务管理路径
    private String packageLocation = "com.harry.database.repository.default";
    //事务mapper路径
    private String mapperLocation = "classpath:mapper/default/*.xml";

    private SourceProperties source=new SourceProperties();

    public String getPackageLocation() {
        return packageLocation;
    }

    public void setPackageLocation(String packageLocation) {
        this.packageLocation = packageLocation;
    }

    public String getMapperLocation() {
        return mapperLocation;
    }

    public void setMapperLocation(String mapperLocation) {
        this.mapperLocation = mapperLocation;
    }

    public SourceProperties getSource() {
        return source;
    }

    public void setSource(SourceProperties source) {
        this.source = source;
    }
}
