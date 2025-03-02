package com.rgpro.quizapp.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Choice {
    private Long id;
    private String text;
    private boolean isValid;
}
