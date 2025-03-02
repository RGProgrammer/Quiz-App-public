package com.rgpro.quizapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name ="submissions")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Submission {
    @SequenceGenerator(name = "test_sequence", sequenceName = "test_sequence", allocationSize = 1)
    @GeneratedValue(generator = "test_sequence", strategy = GenerationType.SEQUENCE)
    @Id
    private Long id ;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CandidateChoice> candidateChoices ;
    private LocalDateTime submittedAt;
}
