package deyki.ToDo.repository;

import deyki.ToDo.entity.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToDoRepository extends JpaRepository<ToDo, Long> {

    @Query("SELECT t FROM ToDo t WHERE t.isFinished = true")
    List<ToDo> listOfFinishedToDos();

    @Query("SELECT t FROM ToDo t WHERE t.isFinished = false")
    List<ToDo> listOfNotFinishedToDos();
}
