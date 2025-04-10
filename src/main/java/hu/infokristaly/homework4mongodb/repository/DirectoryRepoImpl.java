package hu.infokristaly.homework4mongodb.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.client.result.UpdateResult;

import hu.infokristaly.homework4mongodb.entity.Directory;

public class DirectoryRepoImpl {
    
    @Autowired
    MongoTemplate mongoTemplate;
    
    public UpdateResult updateDirectory(Directory directory) {
        
        Query query = new Query(Criteria.where("id").is(directory.getId()));
        Update update = new Update();
        update.set("name", directory.getName());

        UpdateResult result = mongoTemplate.updateFirst(query, update, Directory.class);
        return result;
    }

    public List<Directory> findByName(String directoryName) {
        Query query = new Query(Criteria.where("name").is(directoryName));
        return mongoTemplate.find(query, Directory.class);
    }
    
}
