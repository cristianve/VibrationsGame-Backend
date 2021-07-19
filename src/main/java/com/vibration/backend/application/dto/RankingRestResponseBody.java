package com.vibration.backend.application.dto;

import java.io.Serializable;
import java.util.Map;

public class RankingRestResponseBody implements Serializable {

    private static final long serialVersionUID = -6335164222120342013L;

    private final Map<String, Integer> ranking;

    public RankingRestResponseBody(Map<String, Integer> ranking) {
        this.ranking = ranking;
    }

    public Map<String, Integer> getRanking() {
        return ranking;
    }
}
