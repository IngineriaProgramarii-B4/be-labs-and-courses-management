package com.example.coursesmodule.service;

import com.example.coursesmodule.dao.CourseDao;
import com.example.coursesmodule.model.Component;
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

    public boolean validateExistingResource(String title, String type, Resource resource){
        if (resource.getTitle().isEmpty() || resource.getLocation().isEmpty())
            return false;
        for(Component comp : courseDao.getComponents(title))
            if(comp.getType().equals(type))
                for(Resource res : courseDao.getResourcesForComponentType(title, type))
                    if(res.getTitle().equals(resource.getTitle()))
                        return true;
        return false;
    }

    public boolean validateNewResource(String title, String type, Resource resource){
        if (resource.getTitle().isEmpty() || resource.getLocation().isEmpty())
            return false;
        for(Resource res : courseDao.getResourcesForComponentType(title, type))
            if(res.getTitle().equals(resource.getTitle()))
                return false;
        for(Component comp : courseDao.getComponents(title))
            if(comp.getType().equals(type))
                return true;
        return false;
    }

    public int addResource(String title, String type, Resource resource) {
        if(!validateNewResource(title, type, resource))
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
        if(courseDao.getResourceByTitleForComponentType(title, type, resourceTitle).isEmpty())
            return 0;
        if(validateExistingResource(title, type, courseDao.getResourceByTitleForComponentType(title, type, resourceTitle).get()))
            return courseDao.deleteResourceByTitleForComponentType(title, type, resourceTitle);
        return 0;
    }

    public int updateResourceByTitle(String title, String type, String resourceTitle, Resource resource) {
        if(!validateExistingResource(title, type, resource))
            return 0;
        return courseDao.updateResourceByTitleForComponentType(title, type, resourceTitle, resource);
    }
}
