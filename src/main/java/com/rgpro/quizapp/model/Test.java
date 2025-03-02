package com.rgpro.quizapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name ="tests")
@Table(name = "tests")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Test {
    @SequenceGenerator(name = "test_sequence", sequenceName = "test_sequence", allocationSize = 1)
    @GeneratedValue(generator = "test_sequence", strategy = GenerationType.SEQUENCE)
    @Id
    private Long id ;
    @OneToOne(orphanRemoval = true)
    private Quiz quiz ;
    @OneToOne(orphanRemoval = true)
    private Candidate target ;
    private LocalDateTime creationDate ;
    private LocalDateTime expirationDate ;
    private String token ;
    @OneToOne(orphanRemoval = true)
    private Submission candidateAnswers ;
    @OneToOne(orphanRemoval = true)
    private User owner ;


    public Test(Quiz quiz, Candidate target,
                LocalDateTime creationDate, LocalDateTime expirationDate,
                String token,Submission candidateAnswers,
                User user)
    {
        this(0L,quiz,target,creationDate,expirationDate,token,candidateAnswers,user);
    }
    public Test(Quiz quiz, Candidate target, LocalDateTime creationDate, LocalDateTime expirationDate,String token,User user)
    {
        this(quiz,target,creationDate,expirationDate,token,null,user);
    }
}
