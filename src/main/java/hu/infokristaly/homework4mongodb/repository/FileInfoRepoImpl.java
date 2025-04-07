package hu.infokristaly.homework4mongodb.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mongodb.client.result.UpdateResult;
import hu.infokristaly.homework4mongodb.entity.FileInfo;

@Component
@Transactional
public class FileInfoRepoImpl {
     
    @Autowired
    MongoTemplate mongoTemplate;

    public UpdateResult updateFileInfo(FileInfo fileInfo) {
        Query query = new Query(Criteria.where("id").is(fileInfo.getId()));
        Update update = new Update();
        update.set("name", fileInfo.getName());
        update.set("size", fileInfo.getSize());
        update.set("contentType", fileInfo.getContentType());
        update.set("path", fileInfo.getPath());

        UpdateResult result = mongoTemplate.updateFirst(query, update, FileInfo.class);
        return result;
    }

}
