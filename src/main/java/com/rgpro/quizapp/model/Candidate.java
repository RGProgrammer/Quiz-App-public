package com.rgpro.quizapp.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity(name ="candidates")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Candidate {
    @SequenceGenerator(name = "candidate_sequence", sequenceName = "candidate_sequence", allocationSize = 1)
    @GeneratedValue(generator = "candidate_sequence", strategy = GenerationType.SEQUENCE)
    @Id
    private Long id ;
    private String firstName ;
    private String lastName ;
    @Column(nullable = false,unique = true ,length = 50)
    private String email ;
    private String phone ;
    @ManyToOne
    @JoinColumn(name="owner",referencedColumnName = "id")
    private  User owner ;

    public Candidate(String firstName, String lastName, String email,String phone, User user)
    {
        this(0L,firstName,lastName,email,phone,user);
    }
}
