package com.example.coursesmodule.service;

import com.example.coursesmodule.model.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ResourceService {

    public List<Resource> getResources() {
        return List.of(
                new Resource(
                        "0001",
                        "Java",
                        "location",
                        LocalDateTime.now()
                ),
                new Resource(
                        "0002",
                        "Crypto",
                        "location",
                        LocalDateTime.now()
                )
        );
    }
}
