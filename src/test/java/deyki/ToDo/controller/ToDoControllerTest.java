package deyki.ToDo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import deyki.ToDo.entity.ToDo;
import deyki.ToDo.model.ToDoModel;
import deyki.ToDo.service.impl.ToDoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebMvcTest(ToDoController.class)
class ToDoControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ToDoServiceImpl toDoService;

    private List<ToDoModel> finishedToDos;

    private ToDoModel toDoModel;

    private ToDo toDo;
    @BeforeEach
    void setUp() {

        this.toDo = ToDo.builder().id(20L).toDo("Work hard").isFinished(true).build();

        this.toDoModel = ToDoModel.builder().toDo("Andrew Tate top G").isFinished(false).build();

        this.finishedToDos = new ArrayList<>(List.of(new ToDoModel("Keep hustle", false), new ToDoModel("Don't give up", false)));
    }

    @Test
    void whenCreateToDo_thenReturnToDoModel() throws Exception {
        Mockito.when(toDoService.createToDo(toDoModel)).thenReturn(toDoModel);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toDoModel)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void whenGetToDoById_thenReturnToDoModel() throws Exception {
        Mockito.when(toDoService.getToDoById(10L)).thenReturn(toDoModel);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/getToDo/10")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toDoModel)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void  whenDeleteToDoById_thenDoNothing() throws Exception {
        Mockito.doNothing().when(toDoService).deleteToDoById(10L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/deleteToDo/10"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(toDoService).deleteToDoById(10L);
    }

    @Test
    void whenGetListOfFinishedToDos_thenReturnFinishedToDos() throws Exception {
        Mockito.when(toDoService.listOfFinishedToDos()).thenReturn(finishedToDos);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/listOfFinished")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(finishedToDos)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void whenGetListOfNotFinishedToDos_thenReturnListOfNotFinishedToDos() throws Exception {
        Mockito.when(toDoService.listOfNotFinishedToDos()).thenReturn(List.of(toDoModel));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/listOfNotFinished")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toDoModel)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void whenUpdateToDoById_thenReturnToDoModel() throws Exception {
        ToDoModel newToDoModel = new ToDoModel("Andrew Tate > Jake Paul :D", false);
        Mockito.when(toDoService.updateToDoById(10L, newToDoModel)).thenReturn(newToDoModel);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/updateToDo/10")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newToDoModel)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void whenChangeStatusById_thenDoNothing() throws Exception {
        Mockito.doNothing().when(toDoService).changeStatusById(toDo.getId());

        mockMvc.perform(MockMvcRequestBuilders.put("/api/changeStatus/20"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}