package com.heroku.git.sabfesapp.payload.request.boardrequests;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
public class BoardColumnRequest {
    @Size(max=20)
    String columnName;

    Set<BoardColumnTaskRequest> tasks;
}
