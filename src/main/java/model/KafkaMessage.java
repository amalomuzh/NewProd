package model;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KafkaMessage {
    private ToDoModel data;
    private String type;
}
