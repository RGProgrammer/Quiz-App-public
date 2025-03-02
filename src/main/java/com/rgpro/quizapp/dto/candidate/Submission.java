package com.rgpro.quizapp.dto.candidate;

import com.rgpro.quizapp.model.CandidateChoice;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Submission {
    private String testToken;
    private List<CandidateChoice> choices;
}
