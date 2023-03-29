package com.example.coursesmodule.dao;

import com.example.coursesmodule.model.Resource;

import java.util.List;
import java.util.UUID;

public interface ResourceDao {
    int insertResource(UUID id, String title, String location);
    default int insertResource(Resource resource) {
        UUID id = UUID.randomUUID();
        return insertResource(id, resource.getTitle(), resource.getLocation());
    }

    List<Resource> selectAllResources();
}
