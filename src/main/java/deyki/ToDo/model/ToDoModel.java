package deyki.ToDo.model;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ToDoModel {

    private String toDo;
    private Boolean isFinished;
}
