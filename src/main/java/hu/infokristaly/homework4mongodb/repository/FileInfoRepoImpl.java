package hu.infokristaly.homework4mongodb.repository;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.transaction.annotation.Transactional;

import com.mongodb.client.result.UpdateResult;
import hu.infokristaly.homework4mongodb.entity.FileInfo;

@Transactional
public class FileInfoRepoImpl {
     
    @Autowired
    MongoTemplate mongoTemplate;

    public List<FileInfo> findByDate(Date date) {
        Query query = new Query(Criteria.where("createdAt").gt(date));
        return mongoTemplate.find(query, FileInfo.class);
    }

    public UpdateResult updateFileInfo(FileInfo fileInfo) {
        Query query = new Query(Criteria.where("id").is(fileInfo.getId()));
        Update update = new Update();
        update.set("name", fileInfo.getName());
        update.set("size", fileInfo.getSize());
        update.set("contentType", fileInfo.getContentType());
        update.set("directory", fileInfo.getDirectory());

        UpdateResult result = mongoTemplate.updateFirst(query, update, FileInfo.class);
        return result;
    }

}
