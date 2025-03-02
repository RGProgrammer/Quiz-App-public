package com.rgpro.quizapp.dto.user.mappers;


import com.rgpro.quizapp.dto.user.Submission;

public final class SubmissionMapper {
    private SubmissionMapper(){}

    public static Submission entityToDto(com.rgpro.quizapp.model.Submission submission)
    {
        return new Submission(
                submission.getId(),
                submission.getCandidateChoices(),
                submission.getSubmittedAt());
    }

    public static com.rgpro.quizapp.model.Submission dtoToEntity(Submission dto)
    {
        return new com.rgpro.quizapp.model.Submission(
                dto.getId(),
                dto.getChoices(),
                dto.getSubmittedAt());
    }

}
