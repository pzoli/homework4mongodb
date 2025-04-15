package hu.infokristaly.homework4mongodb.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.mongodb.client.result.UpdateResult;

import hu.infokristaly.homework4mongodb.entity.Directory;

@Repository
public interface DirectoryRepo extends MongoRepository<Directory, String> {

    UpdateResult updateDirectory(Directory directory);
    
    String buildDirectoryHierarchy(Directory directory);

    @Query(value = "{ 'path' : {'$regex':?0 } }", fields = "{ 'id' : 1, 'path': 1, 'parentDirectory': 1 }")
    List<Directory> findByName(String path);

}

