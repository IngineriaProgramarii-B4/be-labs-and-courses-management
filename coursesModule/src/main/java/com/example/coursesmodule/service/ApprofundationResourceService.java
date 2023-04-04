package com.example.coursesmodule.service;

import com.example.coursesmodule.dao.CourseDao;
import com.example.coursesmodule.model.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApprofundationResourceService {
    private final CourseDao courseDao;

    @Autowired
    public ApprofundationResourceService(@Qualifier("fakeDao")  CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    public int verifyResourceValid(Resource resource){
        return resource.getId() <= 0 || resource.getTitle().isEmpty() ? 0 : 1;
    }

    public List<Resource> getResourcesForAprofundationId(int id, int approfundationId) {
        return courseDao.getResourcesForApprofundationId(id, approfundationId);
    }

    public Optional<Resource> getResourceByIdForApprofundationId(int id, int approfundationId , int resourceId) {
        return courseDao.getResourceByIdForApprofundationId(id, approfundationId, resourceId);
    }

    public int addResourceForApprofundationId(int id, int approfundationId, Resource resource) {
        if(verifyResourceValid(resource) == 0)
            return 0;
        return courseDao.addResourceForApprofundationId(id, approfundationId, resource);
    }

    public int updateResourceForApprofundationId(int id, int approfundationId, int resourceId, Resource resource) {
        if(verifyResourceValid(resource) == 0)
            return 0;
        return courseDao.updateResourceByIdForApprofundationId(id, approfundationId, resourceId, resource);
    }

    public int deleteResourceForApprofundationId(int id, int approfundationId, int resourceId) {
        return courseDao.deleteResourceByIdForApprofundationId(id, approfundationId, resourceId);
    }
}
