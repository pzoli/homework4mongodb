package hu.infokristaly.homework4mongodb.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.client.result.UpdateResult;

import hu.infokristaly.homework4mongodb.entity.FileInfo;
import hu.infokristaly.homework4mongodb.repository.FileInfoRepo;

@Service
public class FileInfoService {
    @Autowired
    private FileInfoRepo fileInfoRepo;

    public void save(FileInfo fileInfo) {
        fileInfoRepo.save(fileInfo);
    }
    public UpdateResult updateFileInfo(FileInfo fileInfo) {
        UpdateResult result = fileInfoRepo.updateFileInfo(fileInfo);
        return result;
    }
    public List<FileInfo> findByName(String fileName) {
        List<FileInfo> fileInfo = fileInfoRepo.findByName(fileName);
        return fileInfo;
    }
    public void deleteById(String id) {
        fileInfoRepo.deleteById(id);
    }
    public Optional<FileInfo> findById(String id) {
        return fileInfoRepo.findById(id);
    }
    public List<FileInfo> findByDateFrom(Date date) {
        return fileInfoRepo.findByDate(date);
    }
}
