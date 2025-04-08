package hu.infokristaly.homework4mongodb.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mongodb.client.result.UpdateResult;

import hu.infokristaly.homework4mongodb.entity.FileInfo;

@Repository
public interface FileInfoRepo extends MongoRepository<FileInfo, String> {
    List<FileInfo> findByName(String name);
    List<FileInfo> findByDate(Date date);
    UpdateResult updateFileInfo(FileInfo fileInfo);
}

