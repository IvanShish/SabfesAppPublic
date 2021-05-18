package com.heroku.git.sabfesapp.payload.request.boardrequests;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
public class BoardRequest {
    @Size(max=20)
    String boardName;

    private Set<BoardColumnRequest> columns;
}
