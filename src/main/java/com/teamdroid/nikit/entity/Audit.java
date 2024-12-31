package com.teamdroid.nikit.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "audit")
public class Audit {

    @Id
    private String id;
    private String creationDate;
    private String modificationDate;
    private String createdBy;
    private String modifiedBy;

}
