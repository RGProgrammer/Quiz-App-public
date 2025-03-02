package com.rgpro.quizapp.controllers;

import com.rgpro.quizapp.common.exceptions.CandidateNotFoundException;
import com.rgpro.quizapp.common.exceptions.TestNotFoundException;
import com.rgpro.quizapp.common.messages.ApiResponse;
import com.rgpro.quizapp.common.messages.ErrorCode;
import com.rgpro.quizapp.dto.user.Test;
import com.rgpro.quizapp.security.Utility;
import com.rgpro.quizapp.services.TestService;
import com.rgpro.quizapp.services.UserDetailsProviderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/tests")
public class TestController {
    private final TestService testService;
    private final UserDetailsProviderService userDetailsProviderService;

    @GetMapping
    ResponseEntity<ApiResponse<List<Test>>> getTestsList() {
        var auth = Utility.getUserAuthenticationToken();
        if (auth == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new ApiResponse<>(null,
                            ErrorCode.UNAUTHORIZED_ACCESS.getCode()));
        }
        return ResponseEntity.ok(
                new ApiResponse<>(testService.getTestListFromUserId(auth.getId()),
                        null));
    }

    @GetMapping("/{id}")
    ResponseEntity<ApiResponse<Test>> getTest(@PathVariable Long id) {
        var auth = Utility.getUserAuthenticationToken();
        if (auth == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new ApiResponse<>(null,
                            ErrorCode.UNAUTHORIZED_ACCESS.getCode()));
        }
        try {
            return ResponseEntity.ok(
                    new ApiResponse<>(testService.getTestById(id, auth.getId()),
                            null));
        } catch (TestNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiResponse<>(null,
                            ErrorCode.TEST_NOT_FOUND.getCode()));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Long>> createTest(@RequestBody Test dto) {
        var auth = Utility.getUserAuthenticationToken();
        if (auth == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new ApiResponse<>(null,
                            ErrorCode.UNAUTHORIZED_ACCESS.getCode()));
        }
        try {
            var user = userDetailsProviderService.loadUserByUserId(auth.getId());
            return ResponseEntity.ok(
                    new ApiResponse<>(testService.createNewTest(dto, user),
                            null));
        } catch (CandidateNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResponse<>(null,
                            ErrorCode.CANDIDATE_INVALID_ID.getCode()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Long>> deleteQuiz(@PathVariable Long id) {
        var auth = Utility.getUserAuthenticationToken();
        if (auth == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new ApiResponse<>(null,
                    ErrorCode.UNAUTHORIZED_ACCESS.getCode()));
        }
        try {
            return ResponseEntity.ok(new ApiResponse<>(
                    testService.deleteTest(id, auth.getId()),
                    null));
        } catch (TestNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiResponse<>(null,
                            ErrorCode.TEST_NOT_FOUND.getCode()));
        }
    }

}
