package com.teamdroid.nikit.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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
    private List<Topic> topics = new ArrayList<>();

    public void addTopicId(String topicId) {
        if (!Objects.isNull(topicId)) {
            if (Objects.isNull(this.topicIds)) {
                this.topicIds = new ArrayList<>();
            }
            if (!this.topicIds.contains(topicId)) {
                this.topicIds.add(topicId);
            }
        }
    }

    public void addTopics(List<Topic> topics) {
        if (!Objects.isNull(topics)) {
            if (Objects.isNull(this.topics)) {
                this.topics = new ArrayList<>();
            }
            topics.forEach(q -> {
                if (!this.topics.contains(q)) {
                    this.topics.add(q);
                    addTopicId(q.getId());
                }
            });
        }
    }

    public void addTopics(Topic... topics) {
        addTopics(Arrays.asList(topics));
    }

    public void initializeTopics(List<Topic> topics) {
        this.topics.clear();
        this.topicIds.clear();
        addTopics(topics);
    }

}
