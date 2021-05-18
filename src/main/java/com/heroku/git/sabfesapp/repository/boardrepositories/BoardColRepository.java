package com.heroku.git.sabfesapp.repository.boardrepositories;

import com.heroku.git.sabfesapp.models.boardtask.BoardColumn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardColRepository extends JpaRepository<BoardColumn, String> {
    Optional<BoardColumn> findByIdAndBoardId(String columnId, String boardId);
}
