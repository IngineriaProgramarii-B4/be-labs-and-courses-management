package com.example.coursesmodule.service;

import com.example.coursesmodule.dao.ResourceDao;
import com.example.coursesmodule.model.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ResourceService {
    private final ResourceDao resourceDao;

    @Autowired
    public ResourceService(@Qualifier("fakeDao") ResourceDao resourceDao) {
        this.resourceDao = resourceDao;
    }

    public int addResource(Resource resource) {
        return resourceDao.insertResource(resource);
    }
    public List<Resource> getAllResources() {
       return resourceDao.selectAllResources();
    }
}
