package deyki.ToDo.service;

import deyki.ToDo.model.ToDoModel;

import java.util.List;

public interface ToDoService {

    ToDoModel createToDo(ToDoModel toDoModel);

    ToDoModel getToDoById(Long id);

    void deleteToDoById(Long id);

    List<ToDoModel> listOfFinishedToDos();

    List<ToDoModel> listOfNotFinishedToDos();

    ToDoModel updateToDoById(Long id, ToDoModel toDoModel);

    void changeStatusById(Long id);
}
