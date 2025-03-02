package com.rgpro.quizapp.services;

import com.rgpro.quizapp.common.exceptions.CandidateNotFoundException;
import com.rgpro.quizapp.model.Candidate;
import com.rgpro.quizapp.repositories.CandidateRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CandidateService {
    private CandidateRepository candidateRepository ;

    public List<Candidate> getCandidateList(Long userId) {
        return candidateRepository.findCandidatesByUserId(userId);
    }


    public Candidate getCandidate( Long candidateId,Long userId) throws CandidateNotFoundException {
        var candidate = candidateRepository.findById(candidateId,userId);
        if(candidate.isPresent())
            return candidate.get();
        else {
            throw new CandidateNotFoundException(userId,candidateId);
        }
    }

    public Long saveCandidate(Candidate candidate)
    {
        var res = candidateRepository.save(candidate);
        return res.getId();
    }

    public Long deleteCandidate(Long userId, Long candidateId) throws CandidateNotFoundException {
        var candidate = this.getCandidate(userId,candidateId);
        candidateRepository.deleteById(candidate.getId());
        return candidate.getId();
    }


}
