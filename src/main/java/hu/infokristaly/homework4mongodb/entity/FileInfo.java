package hu.infokristaly.homework4mongodb.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class FileInfo {
    @Id
    private ObjectId id;
    private String name;
    private String contentType;
    private String path;
    private long size;

    public FileInfo(ObjectId id, String name, String path, String contentType, long size) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.contentType = contentType;
        this.size = size;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
