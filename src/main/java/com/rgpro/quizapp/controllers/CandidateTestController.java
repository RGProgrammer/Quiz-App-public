package com.rgpro.quizapp.controllers;

import com.rgpro.quizapp.common.exceptions.TestNotFoundException;
import com.rgpro.quizapp.common.exceptions.TestSubmissionExpiredException;
import com.rgpro.quizapp.dto.candidate.Test;
import com.rgpro.quizapp.dto.candidate.Submission;
import com.rgpro.quizapp.services.CandidateTestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/test")
public class CandidateTestController {
    private final CandidateTestService testService ;

    @GetMapping("/{token}")
    ResponseEntity<Test> getTest(@PathParam("token") String testToken)
    {
        try {
            return ResponseEntity.ok( testService.getTestByToken(testToken));
        }catch (TestNotFoundException e){
            return ResponseEntity.notFound().build() ;
        }
    }

    @PostMapping
    ResponseEntity<Long> submit(@RequestBody Submission candidateSubmission)
    {
        try {
            return ResponseEntity.ok(testService.submit(candidateSubmission));
        }catch (TestSubmissionExpiredException e) {
            return ResponseEntity.ok(0L);
        }catch (TestNotFoundException e){
            return ResponseEntity.notFound().build() ;
        }
    }
}
