package com.rgpro.quizapp.dto.user;

import com.rgpro.quizapp.model.CandidateChoice;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Submission {
    private Long id ;
    private List<CandidateChoice> choices;
    private LocalDateTime submittedAt;
}
