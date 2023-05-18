package com.lastone.apiserver.service.matching;

public interface MatchingService {

    void completeMatching(Long recruitmentId, Long applicationId, Long requesterId);

    void cancelMatching(Long recruitmentId, Long applicationId, Long requesterId);
}