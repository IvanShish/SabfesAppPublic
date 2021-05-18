package com.heroku.git.sabfesapp.models.boardtask;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "user_board_col_task")
@Getter
@Setter
@NoArgsConstructor
public class BoardColumnTask {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Size(max = 30)
    private String task;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private BoardColumn boardColumn;

    public BoardColumnTask(String task) {
        this.task = task;
    }

    @Override
    public String toString() {
        return "BoardColumnTask{" +
                "id='" + id + '\'' +
                ", task='" + task + '\'' +
                ", boardColumn=" + boardColumn +
                '}';
    }
}
