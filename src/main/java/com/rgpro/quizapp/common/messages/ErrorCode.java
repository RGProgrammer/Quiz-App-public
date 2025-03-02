package com.rgpro.quizapp.common.messages;

public enum ErrorCode {
    //Common Errors
    VALIDATION_ERROR("VALIDATION_ERROR"),
    UNAUTHORIZED_ACCESS("UNAUTHORIZED_ACCESS"),

    //Signing Errors
    USERNAME_PASSWORD_INCORRECT("USERNAME_PASSWORD_INCORRECT"),
    USER_NOT_FOUND("USER_NOT_FOUND"),
    USERNAME_TAKEN("USERNAME_TAKEN"),
    EMAIL_ALREADY_USED("USERNAME_TAKEN"),
    TOKEN_EXPIRED("TOKEN_EXPIRED"),

    //Quiz Errors
    QUIZ_NOT_FOUND("QUIZ_NOT_FOUND"),
    QUIZ_INVALID_ID("QUIZ_INVALID_ID"),

    //Question errors
    QUESTION_NOT_FOUND("QUESTION_NOT_FOUND"),
    QUESTION_INVALID_ID("QUESTION_INVALID_ID"),

    //CANDIDATES errors
    CANDIDATE_NOT_FOUND("CANDIDATE_NOT_FOUND"),
    CANDIDATE_INVALID_ID("CANDIDATE_INVALID_ID"),

    //Tests errors
    TEST_NOT_FOUND("TEST_NOT_FOUND");

    private final String code;

    ErrorCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
