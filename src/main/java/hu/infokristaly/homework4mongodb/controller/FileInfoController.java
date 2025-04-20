package hu.infokristaly.homework4mongodb.controller;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.format.annotation.DateTimeFormat;
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

import hu.infokristaly.homework4mongodb.entity.FileInfo;
import hu.infokristaly.homework4mongodb.service.FileInfoService;

@RestController
@RequestMapping("/file")
public class FileInfoController {

    @Autowired
    private FileInfoService fileInfoService;

    @Autowired
    MongoTemplate mongoTemplate;

    private static final Logger logger = Logger.getLogger("FileInfoController");

    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    @Value("${spring.data.mongodb.database}")
    private String mongodatabase;

    //@GetMapping("/getconfig")
    public String getCongig() {
        return mongoUri + mongodatabase;
    }
    

    @GetMapping("/")
    public ResponseEntity<List<FileInfo>> getFile(@RequestParam String fileName) {
        List<FileInfo> fileInfo = fileInfoService.findByName(fileName);

        if (!fileInfo.isEmpty()) {
            return ResponseEntity.ok(fileInfo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getbydatefrom")
    public ResponseEntity<List<FileInfo>> getFileByDateFrom(@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss") Date dateFrom) {
        List<FileInfo> fileInfo = fileInfoService.findByDateFrom(dateFrom);
        fileInfo.forEach(file -> {
            logger.info("File ID: " + file.getId());
            logger.info("File Name: " + file.getName());
            logger.info("File Directory: " + file.getDirectory());
            logger.info("File Content Type: " + file.getContentType());
            logger.info("File Size: " + file.getSize());
            logger.info("File Created At: " + file.getCreatedAt());
        });
        if (!fileInfo.isEmpty()) {
            return ResponseEntity.ok(fileInfo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/")
    public FileInfo saveFileInfo(@RequestBody FileInfo fileInfo) {
        return fileInfoService.save(fileInfo);
    }

    @PutMapping("/")
    public long updateFileInfo(@RequestBody FileInfo fileInfo) {
        UpdateResult result = fileInfoService.updateFileInfo(fileInfo);

        if (result == null) {
            return 0;
        } else {
            return result.getModifiedCount();
        }
    }

    @DeleteMapping("/{id}")
    public void deleteFileInfoById(@PathVariable String id) {
        try {
            fileInfoService.deleteById(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}