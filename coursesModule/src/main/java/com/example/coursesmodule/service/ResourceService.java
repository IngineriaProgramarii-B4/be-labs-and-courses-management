package com.example.coursesmodule.service;

import com.example.coursesmodule.dao.CourseDao;
import com.example.coursesmodule.model.Component;
import com.example.coursesmodule.model.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@Service
public class ResourceService {
    private final CourseDao courseDao;
    private static final String RESOURCE_PATH = "savedResources/";

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
            if(res.getTitle().equals(resource.getTitle())||res.getLocation().equals(resource.getLocation()))
                return false;
        for(Component comp : courseDao.getComponents(title))
            if(comp.getType().equals(type))
                return true;
        return false;
    }
    public int addResource(MultipartFile file, String title, String type){
        String fileName = title + "_" + type + "_" + file.getOriginalFilename();
        Resource resource = new Resource(
                file.getOriginalFilename(),
                RESOURCE_PATH + fileName,
                file.getContentType(),
                false);
        if(!validateNewResource(title, type, resource))
            return 0;
        if(courseDao.addResourceForComponentType(title, type, resource) == 0)
            return 0;
        try {
            String absPath = new File("").getAbsolutePath();
            Path absolutePath = Path.of(absPath);
            Path folderPath = absolutePath.resolve(RESOURCE_PATH);
            File folder = new File(folderPath.toString());
            if (!folder.exists()) {
                folder.mkdir();
            }
            file.transferTo(new File(folderPath.resolve(fileName).toString()));
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List<Resource> getResources(String title, String type) {
        return courseDao.getResourcesForComponentType(title, type);
    }

    public Optional<Resource> getResourceByTitle(String title, String type, String resourceTitle) {
        return courseDao.getResourceByTitleForComponentType(title, type, resourceTitle);
    }
    public int deleteResourceByTitle(String title, String type, String resourceTitle) {
        Optional<Resource> optionalResource = courseDao.getResourceByTitleForComponentType(title, type, resourceTitle);
        if (optionalResource.isEmpty()) {
            return 0;
        }
        Resource resource = optionalResource.get();
        String newResLocation = resource.getLocation().substring(
                0,
                resource.getLocation().lastIndexOf("/") + 1
        ) + "DELETED_" + title + "_" + type + "_" + resource.getTitle();
        File file = new File(resource.getLocation());

        if (validateExistingResource(title, type, resource)) {
            boolean renameSuccessful = file.renameTo(new File(newResLocation));
            return courseDao.deleteResourceByTitleForComponentType(title, type, resourceTitle);
        }
        return 0;
    }
}
