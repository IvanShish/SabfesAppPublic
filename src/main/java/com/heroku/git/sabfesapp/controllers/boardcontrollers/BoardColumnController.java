package com.heroku.git.sabfesapp.controllers.boardcontrollers;

import com.heroku.git.sabfesapp.models.boardtask.Board;
import com.heroku.git.sabfesapp.models.boardtask.BoardColumn;
import com.heroku.git.sabfesapp.models.boardtask.BoardColumnTask;
import com.heroku.git.sabfesapp.payload.request.boardrequests.BoardColumnRequest;
import com.heroku.git.sabfesapp.payload.request.boardrequests.BoardColumnTaskRequest;
import com.heroku.git.sabfesapp.payload.response.MessageResponse;
import com.heroku.git.sabfesapp.repository.boardrepositories.BoardColRepository;
import com.heroku.git.sabfesapp.repository.boardrepositories.BoardColTaskRepository;
import com.heroku.git.sabfesapp.repository.boardrepositories.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/board/col")
public class BoardColumnController {
    BoardRepository boardRepository;
    BoardColTaskRepository boardColTaskRepository;
    BoardColRepository boardColRepository;

    @Autowired
    public void setBoardTaskRepository(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Autowired
    public void setBoardTaskColTaskRepository(BoardColTaskRepository boardColTaskRepository) {
        this.boardColTaskRepository = boardColTaskRepository;
    }

    @Autowired
    public void setBoardTaskColRepository(BoardColRepository boardColRepository) {
        this.boardColRepository = boardColRepository;
    }

    @GetMapping("{columnId}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public BoardColumn getColumn(@PathVariable String columnId) {
        return boardColRepository.findById(columnId)
                .orElseThrow(() -> new RuntimeException("Error: Board Column is not found."));
    }

    @PostMapping("/{boardId}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> addColumn(@PathVariable String boardId, @Valid @RequestBody BoardColumnRequest newColumn) {
        if (!boardRepository.existsById(boardId)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Cannot find board by id."));
        }

        Board board = boardRepository.getOne(boardId);

        BoardColumn col = new BoardColumn(newColumn.getColumnName());

        for (BoardColumnTaskRequest task : newColumn.getTasks()) {
            col.addBoardColumnTask(new BoardColumnTask(task.getTask()));
        }
        boardColRepository.save(col);
        board.addBoardColumn(col);
        boardRepository.save(board);

        return ResponseEntity.ok().body(new MessageResponse(boardId));
    }

    @PutMapping("/{columnId}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> editColumnName(@PathVariable String columnId, @Valid @RequestBody BoardColumnRequest newColumn) {
        BoardColumn boardColumn = boardColRepository.findById(columnId)
                .orElseThrow(() -> new RuntimeException("Error: Board Column is not found."));

        boardColumn.setColumnName(newColumn.getColumnName());
        boardColRepository.save(boardColumn);

        return ResponseEntity.ok().body(new MessageResponse(columnId));
    }

    @DeleteMapping ("/{columnId}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteColumn(@PathVariable String columnId) {
        BoardColumn boardColumn = boardColRepository.findById(columnId)
                .orElseThrow(() -> new RuntimeException("Error: Board Column is not found."));

        boardColumn.getBoard().removeBoardColumn(boardColumn);
        boardColRepository.deleteById(boardColumn.getId());

        return ResponseEntity.ok().body(new MessageResponse(columnId));
    }
}
