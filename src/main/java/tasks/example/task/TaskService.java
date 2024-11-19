package tasks.example.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository repo;

    public Task CreateTask(Task task) {
        task.setStatus(Task.Status.To_do);
        return repo.save(task);
    }

    public Map<Task.Status, List<Task>> getTasksByStatus() {
        Map<Task.Status, List<Task>> map = new HashMap<>();
        map.put(Task.Status.To_do, repo.findByStatus(Task.Status.To_do));
        map.put(Task.Status.In_progress, repo.findByStatus(Task.Status.In_progress));
        map.put(Task.Status.Done, repo.findByStatus(Task.Status.Done));

        return map;
    }
    public Task UpdateTask(int id, Task task) {
        Optional<Task> taskOptional = repo.findById(id);
        if (taskOptional.isPresent()) {
            Task updatedTask = taskOptional.get();

            updatedTask.setStatus(task.getStatus());
            updatedTask.setDescription(task.getDescription());
            updatedTask.setPriority(task.getPriority());
            updatedTask.setDeadline(task.getDeadline());

            if (updatedTask.getTitle() != null) {
                updatedTask.setTitle(task.getTitle());
            }
            return repo.save(updatedTask);

        }
        else{
            throw new RuntimeException("Task not found");
        }
    }

    public void DeleteTask(int id) {
        Optional<Task> taskOptional = repo.findById(id);

        if (taskOptional.isPresent()) {
            Task deletedTask = taskOptional.get();
            repo.delete(deletedTask);
        }
        else{
            throw new RuntimeException("Task not found");
        }
    }

    public Task MoveTask(int id) {
        Optional<Task> taskOptional = repo.findById(id);
        if (taskOptional.isPresent()) {
            Task movedTask = taskOptional.get();

            switch(movedTask.getStatus()){
                case To_do:
                    movedTask.setStatus(Task.Status.In_progress);
                    break;

                case In_progress:
                    movedTask.setStatus(Task.Status.Done);
                    break;

                case Done:
                    throw new IllegalStateException("A tarefa ja esta concluida e nao pode ser movida");
            }
            return repo.save(movedTask);
        }
        else{
            throw new RuntimeException("Task not found");
        }
    }
}
