package com.rgpro.quizapp.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Quiz {
    private Long id ;
    private String title ;
    private String content;
    public Quiz(String title, String content) {
        this.id = 0L ;
        this.title = title;
        this.content = content;

    }
}