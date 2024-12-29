package com.teamdroid.wise.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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

}
