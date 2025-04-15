package hu.infokristaly.homework4mongodb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.client.result.UpdateResult;

import hu.infokristaly.homework4mongodb.entity.Directory;
import hu.infokristaly.homework4mongodb.repository.DirectoryRepo;

@Service
public class DirectoryService {
    @Autowired
    private DirectoryRepo directoryRepo;

    public Directory save(Directory directory) {
        return directoryRepo.save(directory);
    }
    public UpdateResult updateDirectory(Directory directory) {
        UpdateResult result = directoryRepo.updateDirectory(directory);
        return result;
    }
    public List<Directory> findByName(String directoryName) {
        List<Directory> fileInfo = directoryRepo.findByName(directoryName);
        return fileInfo;
    }
    public void deleteById(String id) {
        directoryRepo.deleteById(id);
    }
    public Optional<Directory> findById(String id) {
        return directoryRepo.findById(id);
    }

    public String buildDirectoryHierarchy(Directory directory) {
        String result = directoryRepo.buildDirectoryHierarchy(directory);
        return result;
    }
}
