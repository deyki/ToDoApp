package deyki.ToDo.service.impl;


import deyki.ToDo.entity.ToDo;
import deyki.ToDo.model.ToDoModel;
import deyki.ToDo.repository.ToDoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ToDoServiceImplTest {

    @MockBean
    private ToDoRepository toDoRepository;

    @Autowired
    private ToDoServiceImpl toDoService;

    private final ModelMapper modelMapper;

    private ToDo toDo;
    private List<ToDo> finishedToDos;
    private ToDoModel toDoModel;

    @Autowired
    public ToDoServiceImplTest(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @BeforeEach
    void setUp() {
        this.toDo = ToDo.builder().id(10L).toDo("Keep hustle").isFinished(false).build();

        this.finishedToDos = new ArrayList<>
                (List.of(new ToDo(5L, "Give up", true), new ToDo(6L, "Procrastinate", true)));
    }

    @Test
    void whenCreateToDo_thenReturnToDoModel() {
        Mockito.when(toDoRepository.save(toDo)).thenReturn(toDo);

        this.toDoModel = toDoService.createToDo(modelMapper.map(toDo, ToDoModel.class));

        assertEquals(this.toDoModel.getToDo(), toDo.getToDo());
    }

    @Test
    void whenGetToDoById_thenReturnToDoModel() {
        Mockito.when(toDoRepository.findById(toDo.getId())).thenReturn(Optional.of(toDo));

        this.toDoModel = toDoService.getToDoById(toDo.getId());

        assertEquals(this.toDoModel.getToDo(), toDo.getToDo());
    }

    @Test
    void whenDeleteToDoById_thenDoNothing() {
        Mockito.when(toDoRepository.findById(toDo.getId())).thenReturn(Optional.of(toDo));
        Mockito.doNothing().when(toDoRepository).deleteById(toDo.getId());

        toDoService.deleteToDoById(toDo.getId());

        Mockito.verify(toDoRepository).deleteById(toDo.getId());
    }

    @Test
    void whenFindFinishedTodos_thenReturnListOfFinishedTodos() {
        Mockito.when(toDoRepository.listOfFinishedToDos()).thenReturn(finishedToDos);

        List<ToDoModel> finishedToDos = toDoService.listOfFinishedToDos();

        assertEquals(finishedToDos.size(), 2);
    }

    @Test
    void whenFindNotFinishedTodos_thenReturnListOfNotFinishedToDos() {
        Mockito.when(toDoRepository.listOfNotFinishedToDos()).thenReturn(List.of(toDo));

        List<ToDoModel> notFinishedTodos = toDoService.listOfNotFinishedToDos();

        assertEquals(notFinishedTodos.size(), 1);
    }

    @Test
    void whenUpdateToDoById_thenReturnToDoModel() {
        Mockito.when(toDoRepository.findById(toDo.getId())).thenReturn(Optional.of(toDo));
        Mockito.when(toDoRepository.save(toDo)).thenReturn(toDo);

        ToDoModel updated = toDoService.updateToDoById(toDo.getId(), new ToDoModel("Top G Andrew Tate :D", false));

        assertEquals(updated.getToDo(), toDo.getToDo());
    }
}