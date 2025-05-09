package hu.infokristaly.homework4mongodb.controller;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.client.result.UpdateResult;

import hu.infokristaly.homework4mongodb.entity.Directory;
import hu.infokristaly.homework4mongodb.service.DirectoryService;

@RestController
@RequestMapping("/directory")
public class DirectoryController {

    @Autowired
    private DirectoryService directoryService;

    @Autowired
    MongoTemplate mongoTemplate;

    private static final Logger logger = Logger.getLogger("FileInfoController");

    @GetMapping("/")
    public ResponseEntity<List<Directory>> getDirectory(@RequestParam String path) {
        List<Directory> directory = directoryService.findByName(path);

        if (!directory.isEmpty()) {
            return ResponseEntity.ok(directory);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getFullPath/{id}")
    public ResponseEntity<String> getFullPathOfDirectory(@PathVariable String id) {
        Optional<Directory> directory = directoryService.findById(id);
        if (!directory.isEmpty()) {
            return ResponseEntity.ok(directoryService.buildDirectoryHierarchy(directory.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/")
    public Directory saveDirectory(@RequestBody Directory directory) {
       return directoryService.save(directory);
    }

    @PutMapping("/")
    public long updateDirectory(@RequestBody Directory directory) {
        UpdateResult result = directoryService.updateDirectory(directory);

        if (result == null) {
            return 0;
        } else {
            return result.getModifiedCount();
        }
    }

    @DeleteMapping("/{id}")
    public void deleteDirectoryById(@PathVariable String id) {
        try {
            directoryService.deleteById(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}