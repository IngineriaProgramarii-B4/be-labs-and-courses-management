package com.example.coursesmodule.service;

import com.example.coursesmodule.dao.CourseDao;
import com.example.coursesmodule.model.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResourceService {
    private final CourseDao courseDao;

    @Autowired
    public ResourceService(@Qualifier("postgres") CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    public int verifyResource(Resource resource){
        return resource.getTitle().isEmpty() || resource.getLocation().isEmpty() ? 0 : 1;
    }

    public int addResource(String title, String type, Resource resource) {
        if(verifyResource(resource) == 0)
            return 0;
        return courseDao.addResourceForComponentType(title, type, resource);
    }

    public List<Resource> getResources(String title, String type) {
        return courseDao.getResourcesForComponentType(title, type);
    }

    public Optional<Resource> getResourceByTitle(String title, String type, String resourceTitle) {
        return courseDao.getResourceByTitleForComponentType(title, type, resourceTitle);
    }

    public int deleteResourceByTitle(String title, String type, String resourceTitle) {
        return courseDao.deleteResourceByTitleForComponentType(title, type, resourceTitle);
    }

    public int updateResourceByTitle(String title, String type, String resourceTitle, Resource resource) {
        if(verifyResource(resource) == 0)
            return 0;
        return courseDao.updateResourceByTitleForComponentType(title, type, resourceTitle, resource);
    }
}
