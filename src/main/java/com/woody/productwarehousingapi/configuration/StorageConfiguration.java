package com.woody.productwarehousingapi.configuration;

import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfiguration {

    /**
     * Folder location for storing files
     */
    private String location = "D:/Github/uploadDB";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
