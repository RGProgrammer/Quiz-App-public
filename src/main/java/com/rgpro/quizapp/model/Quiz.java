package com.rgpro.quizapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "quizzes")
@Table(name = "quizzes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Quiz {
    @SequenceGenerator(name = "quiz_sequence", sequenceName = "quiz_sequence", allocationSize = 1)
    @GeneratedValue(generator = "quiz_sequence", strategy = GenerationType.SEQUENCE)
    @Id
    private Long id ;
    private String title ;
    private String content;
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name="quiz_id",referencedColumnName = "id")
    private List<Question> questions;
    @ManyToOne
    @JoinColumn(name="owner",referencedColumnName = "id")
    private  User owner ;

    public Quiz(@NonNull String title,@NonNull String content, List<Question> questions,User owner) {
        this.title = title;
        this.content = content;
        this.questions = questions;
        this.owner = owner ;
    }
    public Quiz(@NonNull String title,@NonNull String content,User owner) {
        this(title,content,null,owner);
    }
}
