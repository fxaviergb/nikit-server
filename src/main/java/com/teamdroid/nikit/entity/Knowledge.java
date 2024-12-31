package com.teamdroid.nikit.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "knowledge")
public class Knowledge {

    @Id
    private String id;
    private String name;
    private Audit audit;
    private List<String> topicIds = new ArrayList<>();

    private List<Topic> topics;

}
