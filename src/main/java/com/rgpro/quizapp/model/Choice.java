package com.rgpro.quizapp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "choices")
@Table(name = "choices")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Choice {
    @SequenceGenerator(name = "choice_sequence", sequenceName = "choice_sequence", allocationSize = 1)
    @GeneratedValue(generator = "choice_sequence", strategy = GenerationType.SEQUENCE)
    @Id
    private Long id;
    private String text;
    private boolean isValid;

    @OneToOne(orphanRemoval = true,cascade = CascadeType.ALL)
    private Question question ;

    public Choice(String text, boolean isValid, Question question) {
        this.text = text;
        this.isValid = isValid;
        this.question =question ;
    }

}
