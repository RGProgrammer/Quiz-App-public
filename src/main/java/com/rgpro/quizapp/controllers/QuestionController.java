package com.rgpro.quizapp.controllers;

import com.rgpro.quizapp.common.messages.ApiResponse;
import com.rgpro.quizapp.common.messages.ErrorCode;
import com.rgpro.quizapp.dto.user.Choice;
import com.rgpro.quizapp.dto.user.Question;
import com.rgpro.quizapp.security.Utility;
import com.rgpro.quizapp.services.QuestionService;
import com.rgpro.quizapp.services.UserDetailsProviderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/quiz/{quizId}")
@AllArgsConstructor
public class QuestionController {
    QuestionService questionService;
    UserDetailsProviderService userDetailsProviderService ;
    @GetMapping("/questions")
     public ResponseEntity<ApiResponse<List<Question>>> getAllQuestion(@PathVariable Long quizId) {
        var auth = Utility.getUserAuthenticationToken();
        if (auth == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(
                            null,
                            ErrorCode.UNAUTHORIZED_ACCESS.getCode()));
        }
        var res = questionService.getAllOfQuestions(auth.getId(),quizId);
        if (!res.isEmpty()) {
            var list = res.stream().map(
                    question -> new Question(question.getId(),
                            question.getText(),null,
                            question.getQuiz_Id(),
                            question.getChoices().stream()
                                    .map(choice -> new Choice(choice.getId(),
                                            choice.getText(),
                                            choice.isValid())).toList()
                    ))
                    .toList();
            return ResponseEntity.ok(new ApiResponse<>(list,null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(null, ErrorCode.QUESTION_NOT_FOUND.getCode()));
        }
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<ApiResponse<Question>> getQuestionById(@PathVariable Long quizId, @PathVariable Long questionId) {
        var auth = Utility.getUserAuthenticationToken();
        if (auth == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(
                            null,
                            ErrorCode.UNAUTHORIZED_ACCESS.getCode()));
        }
        var res = questionService.getQuestion(auth.getId(),quizId,questionId);
        return res.map( question ->  ResponseEntity.ok(
                new ApiResponse<>(
                        new Question(question.getId(),
                                question.getText(),
                                null,
                                question.getQuiz_Id(),
                                question.getChoices().stream()
                                        .map(choice -> new Choice(choice.getId(),
                                                choice.getText(),
                                                choice.isValid())).toList()),
                        null)
        )).orElseGet(() ->  ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>(null, ErrorCode.QUESTION_NOT_FOUND.getCode())));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Long>> addNewQuestions(@PathVariable Long quizId ,@RequestBody Question question) {
        var auth = Utility.getUserAuthenticationToken();
        if (auth == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(
                            null,
                            ErrorCode.UNAUTHORIZED_ACCESS.getCode()));
        }

        var q = new com.rgpro.quizapp.model.Question(
                question.getId(),
                question.getText(),
                question.getResource(),
              null,
                quizId
        );
        var listChoices = question.getChoices().stream()
                .map(choice ->
                        new com.rgpro.quizapp.model.Choice(
                                choice.getId(),
                                choice.getText(),
                                choice.isValid(),
                                q
                                ))
                .toList() ;
        q.setChoices(listChoices);
        var addedQuestionsId = questionService.saveQuestion(q);
        return ResponseEntity.ok(new ApiResponse<>(addedQuestionsId,null));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<Long>> updateQuestion(@PathVariable Long quizId ,@RequestBody Question question) {
        var auth = Utility.getUserAuthenticationToken();
        if (auth == null && question == null ) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(
                            null,
                            ErrorCode.UNAUTHORIZED_ACCESS.getCode()));
        }
        if(question.getId() == 0 || quizId == 0 ){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(null, ErrorCode.QUESTION_INVALID_ID.getCode()));
        }
        var q = new com.rgpro.quizapp.model.Question(
                question.getId(),
                question.getText(),
                question.getResource(),
                null,
                question.getQuizId()
        );
        var listChoices = question.getChoices().stream()
                .map(choice ->
                        new com.rgpro.quizapp.model.Choice(
                                choice.getId(),
                                choice.getText(),
                                choice.isValid(),
                                q
                        ))
                .toList() ;
        q.setChoices(listChoices);
        var id = questionService.saveQuestion(q);
        if(id == 0L){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(null,ErrorCode.VALIDATION_ERROR.getCode()));
        }
        return ResponseEntity.ok(new ApiResponse<>(id,null));
    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity<ApiResponse<Long>> deleteQuestion(@PathVariable Long quizId,@PathVariable Long questionId) {
        var auth = Utility.getUserAuthenticationToken();
        if (auth == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(
                            null,
                            ErrorCode.UNAUTHORIZED_ACCESS.getCode()));
        }
        if(questionId == 0 || quizId == 0 ){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(null, ErrorCode.QUESTION_INVALID_ID.getCode()));
        }
        var id = questionService.deleteQuestion(questionId);
        if(id == 0L){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(null, ErrorCode.QUESTION_NOT_FOUND.getCode()));
        }
        return ResponseEntity.ok(new ApiResponse<>(id,null));
    }

}
