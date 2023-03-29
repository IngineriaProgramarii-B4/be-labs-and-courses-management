package com.example.coursesmodule.dao;

import com.example.coursesmodule.model.Resource;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository("fakeDao")
public class FakeResourceDataAccessService implements ResourceDao{
    private static List<Resource> DB = new ArrayList<>();

    @Override
    public int insertResource(UUID id, String title, String location) {
        DB.add(new Resource(id, title, location));
        return 1;
    }

    @Override
    public List<Resource> selectAllResources() {
        return DB;
    }
}
