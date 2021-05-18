package com.heroku.git.sabfesapp.repository.boardrepositories;

import com.heroku.git.sabfesapp.models.boardtask.BoardColumnTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardColTaskRepository extends JpaRepository<BoardColumnTask, String> {
    Optional<BoardColumnTask> findByIdAndBoardColumnId(String taskId, String columnId);
}
