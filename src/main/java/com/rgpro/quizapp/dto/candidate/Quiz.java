package com.rgpro.quizapp.dto.candidate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Quiz {
    private Long id;
    private String title;
    private String content;
    private List<Question> questions;
}
