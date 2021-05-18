package com.heroku.git.sabfesapp.controllers.boardcontrollers;

import com.heroku.git.sabfesapp.models.User;
import com.heroku.git.sabfesapp.models.boardtask.Board;
import com.heroku.git.sabfesapp.models.boardtask.BoardColumn;
import com.heroku.git.sabfesapp.models.boardtask.BoardColumnTask;
import com.heroku.git.sabfesapp.payload.request.boardrequests.BoardColumnRequest;
import com.heroku.git.sabfesapp.payload.request.boardrequests.BoardColumnTaskRequest;
import com.heroku.git.sabfesapp.payload.request.boardrequests.BoardRequest;
import com.heroku.git.sabfesapp.payload.response.MessageResponse;
import com.heroku.git.sabfesapp.repository.boardrepositories.BoardColRepository;
import com.heroku.git.sabfesapp.repository.boardrepositories.BoardColTaskRepository;
import com.heroku.git.sabfesapp.repository.boardrepositories.BoardRepository;
import com.heroku.git.sabfesapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/board")
public class BoardController {
    BoardRepository boardRepository;
    BoardColTaskRepository boardColTaskRepository;
    BoardColRepository boardColRepository;
    UserRepository userRepository;

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

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<Board> getBoardTasks(@PathVariable String userId) {
        return boardRepository.findAllByUserId(userId);
    }

//    @GetMapping("/{boardId}")
//    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
//    public Board getBoard(@PathVariable String boardId) {
//        return boardRepository.findById(boardId)
//                .orElseThrow(() -> new RuntimeException("Error: Board is not found."));
//    }

    @PostMapping("/{userId}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> addBoard(@PathVariable String userId, @Valid @RequestBody BoardRequest newBoard) {
        if (!userRepository.existsById(userId)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Cannot find user by id."));
        }

        User user = userRepository.getOne(userId);
        Board board = new Board(newBoard.getBoardName());

        for (BoardColumnRequest column : newBoard.getColumns()) {
            BoardColumn col = new BoardColumn(column.getColumnName());

            for (BoardColumnTaskRequest task : column.getTasks()) {
                col.addBoardColumnTask(new BoardColumnTask(task.getTask()));
            }
            boardColRepository.save(col);
            board.addBoardColumn(col);
        }

        boardRepository.save(board);
        user.addBoard(board);
        userRepository.save(user);

        return ResponseEntity.ok().body(new MessageResponse(userId));
    }

    @PutMapping("/{boardId}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> editBoardName(@PathVariable String boardId, @Valid @RequestBody BoardRequest newBoard) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("Error: Board is not found."));

        board.setBoardName(newBoard.getBoardName());
        boardRepository.save(board);

        return ResponseEntity.ok().body(new MessageResponse(boardId));
    }

    @DeleteMapping("/{boardId}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteBoard(@PathVariable String boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("Error: Board is not found."));

        board.getUser().removeBoard(board);
        boardRepository.deleteById(board.getId());

        return ResponseEntity.ok().body(new MessageResponse(boardId));
    }
}
