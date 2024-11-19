package tasks.example.task;


import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task CreatedTask = taskService.CreateTask(task);
        return new ResponseEntity<>(CreatedTask, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Map<Task.Status, List<Task>>> getTasksByStatus() {
        return new ResponseEntity<>(taskService.getTasksByStatus(), HttpStatus.OK);
    }

    @PutMapping("/{id}/move")
    public ResponseEntity<Task> moveTask(@PathVariable int id) {
       Task task = taskService.MoveTask(id);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable int id, @RequestBody Task task) {
        Task UpdatedTask = taskService.UpdateTask(id, task);
        return new ResponseEntity<>(UpdatedTask, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Task> deleteTask(@PathVariable int id) {
        taskService.DeleteTask(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
