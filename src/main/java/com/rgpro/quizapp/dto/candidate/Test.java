package com.rgpro.quizapp.dto.candidate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Test {
    private Long id ;
    private Quiz quiz ;
    private String testToken ;
    private LocalDateTime creationDate ;
    private LocalDateTime expirationDate ;
}
