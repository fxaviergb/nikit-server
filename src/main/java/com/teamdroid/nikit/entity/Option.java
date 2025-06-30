package com.teamdroid.nikit.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "option")
public class Option {

    @Id
    private String id;
    private String option;
    private String questionId;
    private Answer answer;
    private String userId;
    private Audit audit;

}
