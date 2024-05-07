package com.sportradar.challenge.service;

import com.sportradar.challenge.model.Match;

import java.util.List;

public interface ScoreboardService {
    void startNewMatch(String homeTeam, String awayTeam);

    Match getMatch(String homeTeam, String awayTeam);

    boolean containsMatch(String homeTeam, String awayTeam);

    void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore);

    void updateScore(Match match, int homeScore, int awayScore);

    void finishMatch(String homeTeam, String awayTeam);

    Object getFinishedMatch(String homeTeam, String awayTeam);

    void finishMatch(Match match);

    List<Match> getSummary();
}
