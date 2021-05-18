package com.heroku.git.sabfesapp.controllers.boardcontrollers;

import com.heroku.git.sabfesapp.models.boardtask.BoardColumn;
import com.heroku.git.sabfesapp.models.boardtask.BoardColumnTask;
import com.heroku.git.sabfesapp.payload.request.boardrequests.BoardColumnTaskRequest;
import com.heroku.git.sabfesapp.payload.response.MessageResponse;
import com.heroku.git.sabfesapp.repository.boardrepositories.BoardColRepository;
import com.heroku.git.sabfesapp.repository.boardrepositories.BoardColTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/board/task")
public class BoardColumnTaskController {
    BoardColTaskRepository boardColTaskRepository;
    BoardColRepository boardColRepository;

    @Autowired
    public void setBoardTaskColTaskRepository(BoardColTaskRepository boardColTaskRepository) {
        this.boardColTaskRepository = boardColTaskRepository;
    }

    @Autowired
    public void setBoardTaskColRepository(BoardColRepository boardColRepository) {
        this.boardColRepository = boardColRepository;
    }

    @GetMapping("/{taskId}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public BoardColumnTask getTask(@PathVariable String taskId) {
        return boardColTaskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Error: Board Column Task is not found."));
    }

    @PostMapping("/{columnId}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> addTask(@PathVariable String columnId, @Valid @RequestBody BoardColumnTaskRequest newTask) {
        if (!boardColRepository.existsById(columnId)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Cannot find board column by id."));
        }

        BoardColumn boardColumn = boardColRepository.getOne(columnId);

        boardColumn.addBoardColumnTask(new BoardColumnTask(newTask.getTask()));

        boardColRepository.save(boardColumn);

        return ResponseEntity.ok().body(new MessageResponse(columnId));
    }

    @PutMapping("/{taskId}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> editTask(@PathVariable String taskId, @Valid @RequestBody BoardColumnTaskRequest newTask) {
        BoardColumnTask boardColumnTask = boardColTaskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Error: Board Column Task is not found."));

        boardColumnTask.setTask(newTask.getTask());
        boardColTaskRepository.save(boardColumnTask);

        return ResponseEntity.ok().body(new MessageResponse(taskId));
    }

    @DeleteMapping ("/{taskId}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteTask(@PathVariable String taskId) {
        BoardColumnTask boardColumnTask = boardColTaskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Error: Board Column Task is not found."));

        boardColumnTask.getBoardColumn().removeBoardColumnTask(boardColumnTask);
        boardColTaskRepository.deleteById(boardColumnTask.getId());

        return ResponseEntity.ok().body(new MessageResponse(taskId));
    }
}
