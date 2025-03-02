package com.rgpro.quizapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.net.URI;
import java.util.List;


@Entity(name = "questions")
@Table(name = "questions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    @SequenceGenerator(name = "question_sequence", sequenceName = "question_sequence", allocationSize = 1)
    @GeneratedValue(generator = "question_sequence", strategy = GenerationType.SEQUENCE)
    @Id
    private Long id;
    private String text;
    private URI resource;
    @OneToMany(
            orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "question_id",referencedColumnName = "id")
    private List<Choice> choices;
    private  Long quiz_Id ;
    public Question(String text, URI resource, List<Choice> choices , @NonNull Long quiz_Id) {
        this.text = text;
        this.resource = resource;
        this.choices = choices;
        this.quiz_Id = quiz_Id ;
    }
}


