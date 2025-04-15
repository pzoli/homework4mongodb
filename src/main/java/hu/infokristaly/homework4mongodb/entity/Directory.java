package hu.infokristaly.homework4mongodb.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Document
public class Directory {
    @Id
    private String id;
    private String path;
    private Date createdAt;
    @DBRef
    private Directory parentDirectory;
}
