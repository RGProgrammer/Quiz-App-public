package com.rgpro.quizapp.dto.user;

import com.rgpro.quizapp.dto.user.Choice;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.net.URI;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Question {
    private Long id ;
    private String text;
    private URI resource;
    private Long quizId ;
    private List<Choice> choices;

    public Question(String text, URI resource, Long quizId,List<Choice> choices) {
        this(0L,text,resource,quizId,choices);
    }
    public Question(Long id, String text, URI resource, Long quizId) {
        this(id,text,resource,quizId,null);
    }

    public Question(String text, URI resource, Long quizId) {
        this(0L, text, resource, quizId , null);
    }


}
