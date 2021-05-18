package com.heroku.git.sabfesapp;

import com.heroku.git.sabfesapp.models.User;
import com.heroku.git.sabfesapp.models.boardtask.Board;
import com.heroku.git.sabfesapp.models.boardtask.BoardColumn;
import com.heroku.git.sabfesapp.models.boardtask.BoardColumnTask;
import com.heroku.git.sabfesapp.models.role.ERole;
import com.heroku.git.sabfesapp.models.role.Role;
import com.heroku.git.sabfesapp.payload.request.SignupRequest;
import com.heroku.git.sabfesapp.payload.request.boardrequests.BoardColumnRequest;
import com.heroku.git.sabfesapp.payload.request.boardrequests.BoardColumnTaskRequest;
import com.heroku.git.sabfesapp.payload.request.boardrequests.BoardRequest;
import com.heroku.git.sabfesapp.repository.RoleRepository;
import com.heroku.git.sabfesapp.repository.UserRepository;
import com.heroku.git.sabfesapp.repository.boardrepositories.BoardColRepository;
import com.heroku.git.sabfesapp.repository.boardrepositories.BoardColTaskRepository;
import com.heroku.git.sabfesapp.repository.boardrepositories.BoardRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Set;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BoardTests {
    BoardColRepository boardColRepository;
    BoardColTaskRepository boardColTaskRepository;
    BoardRepository boardRepository;
    UserRepository userRepository;
    RoleRepository roleRepository;

    @Autowired
    public void setBoardColRepository(BoardColRepository boardColRepository) {
        this.boardColRepository = boardColRepository;
    }

    @Autowired
    public void setBoardColTaskRepository(BoardColTaskRepository boardColTaskRepository) {
        this.boardColTaskRepository = boardColTaskRepository;
    }

    @Autowired
    public void setBoardRepository(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @BeforeEach
    void addUser() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername("UserTest");
        signupRequest.setEmail("usertest@mail.ru");
        signupRequest.setPassword("usertest");
        Set<String> rolesStr = new HashSet<>();
        rolesStr.add("user");
        signupRequest.setRole(rolesStr);

        User user = new User(signupRequest.getUsername(),
                signupRequest.getEmail(),
                signupRequest.getPassword());

        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.getRoleByName(ERole.ROLE_USER));
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Test
    void injectedComponentsAreNotNull(){
        Assertions.assertThat(boardColRepository).isNotNull();
        Assertions.assertThat(boardColTaskRepository).isNotNull();
        Assertions.assertThat(boardRepository).isNotNull();
        Assertions.assertThat(userRepository).isNotNull();
        Assertions.assertThat(roleRepository).isNotNull();
    }

    @Test
    void addBoardTest() {
        BoardRequest newBoard = new BoardRequest();
        newBoard.setBoardName("testBoard");
        newBoard.setColumns(new HashSet<>());
        User user = userRepository.getByUsername("UserTest");
        Board board = new Board(newBoard.getBoardName());

        for (BoardColumnRequest column : newBoard.getColumns()) {
            BoardColumn col = new BoardColumn(column.getColumnName());

            for (BoardColumnTaskRequest task : column.getTasks()) {
                col.addBoardColumnTask(new BoardColumnTask(task.getTask()));
            }
            boardColRepository.save(col);
            board.addBoardColumn(col);
        }
        int wasSize = boardRepository.findAll().size();

        boardRepository.save(board);
        user.addBoard(board);
        userRepository.save(user);

        Assertions.assertThat(boardRepository.findAll()).hasSize(wasSize + 1);
        Assertions.assertThat(boardRepository.findAll()).contains(board);
    }
}
