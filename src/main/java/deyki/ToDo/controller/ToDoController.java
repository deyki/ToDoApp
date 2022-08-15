package deyki.ToDo.controller;

import deyki.ToDo.model.ToDoModel;
import deyki.ToDo.service.impl.ToDoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ToDoController {

    private final ToDoServiceImpl toDoService;

    @Autowired
    public ToDoController(ToDoServiceImpl toDoService) {
        this.toDoService = toDoService;
    }

    @PostMapping("/create")
    public ResponseEntity<ToDoModel> createToDo(@RequestBody ToDoModel toDoModel) {
        return ResponseEntity.status(HttpStatus.CREATED).body(toDoService.createToDo(toDoModel));
    }

    @GetMapping("/getToDo/{id}")
    public ResponseEntity<ToDoModel> getToDoById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(toDoService.getToDoById(id));
    }

    @DeleteMapping("/deleteToDo/{id}")
    public ResponseEntity<String> deleteToDoById(@PathVariable Long id) {
        toDoService.deleteToDoById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Deleted.");
    }

    @GetMapping("/listOfFinished")
    public ResponseEntity<List<ToDoModel>> listOfFinishedToDos() {
        return ResponseEntity.status(HttpStatus.OK).body(toDoService.listOfFinishedToDos());
    }

    @GetMapping("/listOfNotFinished")
    public ResponseEntity<List<ToDoModel>> listOfNotFinishedToDos() {
        return ResponseEntity.status(HttpStatus.OK).body(toDoService.listOfNotFinishedToDos());
    }

    @PutMapping("/updateToDo/{id}")
    public ResponseEntity<ToDoModel> updateToDoById(@PathVariable Long id, @RequestBody ToDoModel toDoModel) {
        return ResponseEntity.status(HttpStatus.OK).body(toDoService.updateToDoById(id, toDoModel));
    }
}
