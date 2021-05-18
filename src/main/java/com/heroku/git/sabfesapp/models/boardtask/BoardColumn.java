package com.heroku.git.sabfesapp.models.boardtask;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user_board_col")
@Getter
@Setter
@NoArgsConstructor
public class BoardColumn {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Size(max = 20)
    private String columnName;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private Board board;

    @OneToMany(mappedBy = "boardColumn", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<BoardColumnTask> tasks = new HashSet<>();

    public BoardColumn(String name) {
        this.columnName = name;
    }

    public void addBoardColumnTask(BoardColumnTask task) {
        tasks.add(task);
        task.setBoardColumn(this);
//        this.getTasks().add(task);
    }

    public void removeBoardColumnTask(BoardColumnTask task) {
        tasks.remove(task);
        task.setBoardColumn(null);
    }

    @Override
    public String toString() {
        return "BoardColumn{" +
                "id='" + id + '\'' +
                ", name='" + columnName + '\'' +
                ", board=" + board +
                ", tasks=" + tasks +
                '}';
    }
}
