package com.harry.database.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "dynamic.datasource")
public class DynamicDataSourceProperties {

    //事务管理路径
    private String packageLocation = "com.harry.database.repository.dynamic";
    //事务mapper路径
    private String mapperLocation = "classpath:mapper/dynamic/*.xml";

    private List<SourceProperties> sources=new ArrayList<SourceProperties>();

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

    public List<SourceProperties> getSources() {
        return sources;
    }

    public void setSources(List<SourceProperties> sources) {
        this.sources = sources;
    }
}
