package com.rgpro.quizapp.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Candidate {
    private Long id ;
    private String firstName ;
    private String lastName ;
    private String email ;
    private String phone ;
    public Candidate(String firstName, String lastName, String email, String phone)
    {
        this(0L,firstName,lastName, email,phone);
    }

    public Candidate(String firstName, String lastName, String email)
    {
        this(firstName,lastName, email,null);
    }
}
