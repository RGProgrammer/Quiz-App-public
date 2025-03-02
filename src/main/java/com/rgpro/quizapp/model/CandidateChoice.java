package com.rgpro.quizapp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity(name ="answers")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CandidateChoice {
    @SequenceGenerator(name = "answer_sequence", sequenceName = "answer_sequence", allocationSize = 1)
    @GeneratedValue(generator = "answer_sequence", strategy = GenerationType.SEQUENCE)
    @Id
    private Long id ;
    private Long questionId;
    private Long choiceId ;
    private boolean answer ;

    public CandidateChoice(@NonNull Long questionId, @NonNull Long choiceId, boolean answer){
        this(0L,questionId,choiceId,answer);
    }
}
