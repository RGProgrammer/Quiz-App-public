package com.rgpro.quizapp.common.messages;

public record ApiResponse<T>(T data, String error) {
}

