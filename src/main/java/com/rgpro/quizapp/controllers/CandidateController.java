package com.rgpro.quizapp.controllers;

import com.rgpro.quizapp.common.messages.ApiResponse;
import com.rgpro.quizapp.common.messages.ErrorCode;
import com.rgpro.quizapp.dto.user.mappers.CandidateMapper;
import lombok.AllArgsConstructor;
import com.rgpro.quizapp.common.exceptions.CandidateNotFoundException;
import com.rgpro.quizapp.dto.user.Candidate;
import com.rgpro.quizapp.security.Utility;
import com.rgpro.quizapp.services.CandidateService;
import com.rgpro.quizapp.services.UserDetailsProviderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/candidates")
public class CandidateController {
    private final CandidateService candidateService;
    private final UserDetailsProviderService userDetailsProviderService;

    @GetMapping
    ResponseEntity<ApiResponse<List<Candidate>>> getCandidates() {
        var auth = Utility.getUserAuthenticationToken();
        if (auth == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(
                            null,
                            ErrorCode.UNAUTHORIZED_ACCESS.getCode()));
        }
        var list = candidateService.getCandidateList(auth.getId())
                .stream()
                .map(CandidateMapper::entityToDto).toList();
        return ResponseEntity.ok(new ApiResponse<>(list,null));
    }

    @GetMapping(value = "/{id}")
    ResponseEntity<ApiResponse<Candidate>> getCandidate(@PathVariable Long id) {
        var auth = Utility.getUserAuthenticationToken();
        if (auth == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(
                            null,
                            ErrorCode.UNAUTHORIZED_ACCESS.getCode()));
        }
        try {
            var candidate = candidateService.getCandidate(auth.getId(), id);
            return ResponseEntity.ok(
                   new ApiResponse<>(CandidateMapper.entityToDto(candidate),null));
        } catch (CandidateNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(null, ErrorCode.CANDIDATE_NOT_FOUND.getCode()));
        }
    }

    @PostMapping
    ResponseEntity<ApiResponse<Long>> createNewCandidate(@RequestBody Candidate dto) {
        var auth = Utility.getUserAuthenticationToken();
        if (auth == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(
                            null,
                            ErrorCode.UNAUTHORIZED_ACCESS.getCode()));
        }
        var user = userDetailsProviderService.loadUserByUserId(auth.getId());
        var candidate =  CandidateMapper.dtoToEntity(dto);
        candidate.setOwner(user);
        var res = candidateService.saveCandidate(candidate);
        if (res == 0L) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(null,ErrorCode.VALIDATION_ERROR.getCode()));
        } else {
            return ResponseEntity.ok(new ApiResponse<>(res,null));
        }
    }

    @PutMapping
    ResponseEntity<ApiResponse<Long>> updateCandidate(@RequestBody Candidate dto) {
        var auth = Utility.getUserAuthenticationToken();
        if (auth == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(
                            null,
                            ErrorCode.UNAUTHORIZED_ACCESS.getCode()));
        }
        if (dto.getId() == 0L) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(null, ErrorCode.CANDIDATE_INVALID_ID.getCode()));
        }
        var user = userDetailsProviderService.loadUserByUserId(auth.getId());
        var candidate =  CandidateMapper.dtoToEntity(dto);
        candidate.setOwner(user);
        var res = candidateService.saveCandidate(candidate);
        if (res == 0L) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(null,ErrorCode.VALIDATION_ERROR.getCode()));
        } else {
            return ResponseEntity.ok(new ApiResponse<>(res,null));
        }
    }

    @DeleteMapping(value = "/{id}")
    ResponseEntity<ApiResponse<Long>> deleteCandidate(@PathVariable Long id) {
        var auth = Utility.getUserAuthenticationToken();
        if (auth == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(
                            null,
                            ErrorCode.UNAUTHORIZED_ACCESS.getCode()));
        }
        try {
            var res = candidateService.deleteCandidate(auth.getId(), id);
            return ResponseEntity.ok(new ApiResponse<>(res,null));
        } catch (CandidateNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(null, ErrorCode.CANDIDATE_NOT_FOUND.getCode()));
        }
    }
}
