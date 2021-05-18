package com.heroku.git.sabfesapp.repository.boardrepositories;

import com.heroku.git.sabfesapp.models.boardtask.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, String> {
    List<Board> findAllByUserId(String id);
}
