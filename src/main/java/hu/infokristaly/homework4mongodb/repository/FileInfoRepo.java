package hu.infokristaly.homework4mongodb.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.mongodb.client.result.UpdateResult;

import hu.infokristaly.homework4mongodb.entity.FileInfo;

@Repository
public interface FileInfoRepo extends MongoRepository<FileInfo, String> {
     @Query(value = "{ 'name' : {'$regex':?0 } }", fields = "{ 'id' : 1, 'name': 1, 'contentType': 1, 'size': 1, 'createdAt': 1, 'directory': 1, 'chksum': 1 }")
   List<FileInfo> findByName(String name);
    List<FileInfo> findByDate(Date date);
    UpdateResult updateFileInfo(FileInfo fileInfo);
}

