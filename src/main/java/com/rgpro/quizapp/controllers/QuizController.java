package com.rgpro.quizapp.controllers;


import com.rgpro.quizapp.common.exceptions.QuizNotFoundException;
import com.rgpro.quizapp.common.messages.ApiResponse;
import com.rgpro.quizapp.common.messages.ErrorCode;
import com.rgpro.quizapp.dto.user.Quiz;
import com.rgpro.quizapp.security.Utility;
import com.rgpro.quizapp.services.QuizService;
import com.rgpro.quizapp.services.UserDetailsProviderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/quizzes")
@AllArgsConstructor
public class QuizController {
    private final QuizService quizService;
    private final UserDetailsProviderService userDetailsProviderService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Quiz>>> getAllQuiz() {
        var auth = Utility.getUserAuthenticationToken();
        if (auth == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(
                            null,
                            ErrorCode.UNAUTHORIZED_ACCESS.getCode()));
        }
        var res = quizService.getAllQuizzes(auth.getId());
        if (!res.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse<>(res, null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(null, ErrorCode.QUIZ_NOT_FOUND.getCode()));
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ApiResponse<Quiz>> getQuizById(@PathVariable Long id) {
        var auth = Utility.getUserAuthenticationToken();
        if (auth == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(
                            null,
                            ErrorCode.UNAUTHORIZED_ACCESS.getCode()));
        }
        try {
            var res = quizService.getQuiz(id, auth.getId());
            return ResponseEntity.ok(
                    new ApiResponse<>(res, null));
        } catch (QuizNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(null, ErrorCode.QUIZ_NOT_FOUND.getCode()));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Long>> createNewQuiz(@RequestBody Quiz dto) {
        var auth = Utility.getUserAuthenticationToken();
        if (auth == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(
                            null,
                            ErrorCode.UNAUTHORIZED_ACCESS.getCode()));
        }
        var user = userDetailsProviderService.loadUserByUserId(auth.getId());
        var res = quizService.saveQuiz(dto, user);
        if(res == 0L){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(null, ErrorCode.VALIDATION_ERROR.getCode()));
        }
        return ResponseEntity.ok(new ApiResponse<>(res, null));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<Long>> updateQuiz(@RequestBody Quiz dto) {
        var auth = Utility.getUserAuthenticationToken();
        if (auth == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(
                            null,
                            ErrorCode.UNAUTHORIZED_ACCESS.getCode()));
        }
        var user = userDetailsProviderService.loadUserByUserId(auth.getId());
        try {
            var quiz = quizService.getQuiz(dto.getId(), auth.getId());
            quiz.setContent(dto.getContent());
            quiz.setTitle(dto.getTitle());
            var res = quizService.saveQuiz(quiz, user);
            if (res != 0) {
                return ResponseEntity.ok(new ApiResponse<>(res, null));
            }else{
                return ResponseEntity.internalServerError().build();
            }
        } catch (QuizNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(null, ErrorCode.QUIZ_INVALID_ID.getCode()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Long>> deleteQuiz(@PathVariable Long id) {
        var auth = Utility.getUserAuthenticationToken();
        if (auth == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(
                            null,
                            ErrorCode.UNAUTHORIZED_ACCESS.getCode()));
        }
        var res = quizService.deleteQuiz(auth.getId(), id);
        if (res != 0) {
            return ResponseEntity.ok(new ApiResponse<>(res, null));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>(null, ErrorCode.QUIZ_NOT_FOUND.getCode()));
    }
}
