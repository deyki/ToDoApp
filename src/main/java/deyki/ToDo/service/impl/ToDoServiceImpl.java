package deyki.ToDo.service.impl;

import deyki.ToDo.entity.ToDo;
import deyki.ToDo.model.ToDoModel;
import deyki.ToDo.repository.ToDoRepository;
import deyki.ToDo.service.ToDoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ToDoServiceImpl implements ToDoService {

    private final ToDoRepository toDoRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ToDoServiceImpl(ToDoRepository toDoRepository, ModelMapper modelMapper) {
        this.toDoRepository = toDoRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ToDoModel createToDo(ToDoModel toDoModel) {
        toDoRepository.save(modelMapper.map(toDoModel, ToDo.class));

        return toDoModel;
    }

    @Override
    public ToDoModel getToDoById(Long id) {
        ToDo toDo = toDoRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("ToDo with id: %d not found!", id)));

        return modelMapper.map(toDo, ToDoModel.class);
    }

    @Override
    public void deleteToDoById(Long id) {
        ToDo toDo = toDoRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("ToDo with id: %d not found!", id)));

        toDoRepository.deleteById(toDo.getId());
    }

    @Override
    public List<ToDoModel> listOfFinishedToDos() {
        return toDoRepository.listOfFinishedToDos()
                .stream()
                .map(toDo -> modelMapper.map(toDo, ToDoModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ToDoModel> listOfNotFinishedToDos() {
        return toDoRepository.listOfNotFinishedToDos()
                .stream()
                .map(toDo -> modelMapper.map(toDo, ToDoModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public ToDoModel updateToDoById(Long id, ToDoModel toDoModel) {
        ToDo toDo = toDoRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("ToDo with id: %d not found!", id)));

        toDo.setToDo(toDoModel.getToDo());
        toDo.setIsFinished(toDoModel.getIsFinished());
        toDoRepository.save(toDo);

        return modelMapper.map(toDo, ToDoModel.class);
    }
}
