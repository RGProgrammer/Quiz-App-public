package com.rgpro.quizapp.dto.user.mappers;

import com.rgpro.quizapp.dto.user.Candidate;

public final class CandidateMapper {
    private CandidateMapper(){}

    public static Candidate entityToDto(com.rgpro.quizapp.model.Candidate candidate)
    {
        return  new Candidate(
                candidate.getId(),
                candidate.getFirstName(),
                candidate.getLastName(),
                candidate.getEmail(),
                candidate.getPhone());
    }

    public static com.rgpro.quizapp.model.Candidate dtoToEntity(Candidate dto)
    {
        return new com.rgpro.quizapp.model.Candidate(
                dto.getId(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getEmail(),
                dto.getPhone(),
                null);
    }
}
