package hu.infokristaly.homework4mongodb.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Document
public class FileInfo {
    @Id
    private String id;
    private String name;
    private String contentType;
    private String path;
    private long size;
    private Date createdAt;
}
