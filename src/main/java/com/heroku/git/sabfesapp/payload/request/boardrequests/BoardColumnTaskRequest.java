package com.heroku.git.sabfesapp.payload.request.boardrequests;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
public class BoardColumnTaskRequest {
    @Size(max=300)
    String task;
}
