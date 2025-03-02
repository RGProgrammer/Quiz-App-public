package com.rgpro.quizapp.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "tokens")
@Table(name = "tokens")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TokenRecord {
    @SequenceGenerator(name = "token_sequence", sequenceName = "token_sequence", allocationSize = 1)
    @GeneratedValue(generator = "token_sequence", strategy = GenerationType.SEQUENCE)
    @Id
    private Long id;

    @Column(length = 50 ,unique = true)
    private String token;

    @OneToOne(
            orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn( name = "user" , referencedColumnName = "id")
    private User user;

    private LocalDateTime creationDate ;
    private LocalDateTime expirationDate ;

    private boolean isExpired ; //can expire before expiration date

    public TokenRecord(User user, String token,LocalDateTime creationDate, LocalDateTime expirationDate, boolean isExpired) {
        this.user = user;
        this.token = token;
        this.creationDate = creationDate ;
        this.expirationDate = expirationDate ;
        this.isExpired = isExpired ;
    }
    public TokenRecord(User user, String token,LocalDateTime creationDate, LocalDateTime expirationDate) {
        this(user,token,creationDate,expirationDate,false);
    }
}
