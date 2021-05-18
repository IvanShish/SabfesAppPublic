package com.heroku.git.sabfesapp.models.boardtask;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.heroku.git.sabfesapp.models.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user_board")
@Getter @Setter @NoArgsConstructor
public class Board {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @Size(max = 20)
    private String boardName;

    @OneToMany(mappedBy = "board", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<BoardColumn> columns = new HashSet<>();

    public Board(String boardName) {
        this.boardName = boardName;
    }

    public void addBoardColumn(BoardColumn column) {
        columns.add(column);
        column.setBoard(this);
//        this.getColumns().add(column);
    }

    public void removeBoardColumn(BoardColumn column) {
        columns.remove(column);
        column.setBoard(null);
    }

    @Override
    public String toString() {
        return "Board{" +
                "id='" + id + '\'' +
                ", user=" + user +
                ", columns=" + columns +
                '}';
    }
}
