package com.rgpro.quizapp.dto.candidate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Question {
        private Long id;
        private String text;
        private List<Choice> choices;
}
