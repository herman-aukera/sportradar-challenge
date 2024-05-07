package com.sportradar.challenge.service.impl;

import com.sportradar.challenge.service.ScoreboardService;
import com.sportradar.challenge.model.Match;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class ScoreboardServiceImpl implements ScoreboardService {

    private final Map<String, Match> ongoingMatches;
    private final List<Match> finishedMatches;

    public ScoreboardServiceImpl() {
        ongoingMatches = new HashMap<>();
        finishedMatches = new ArrayList<>();
    }

    @Override
    public void startNewMatch(String homeTeam, String awayTeam) {
        String matchKey = getMatchKey(homeTeam, awayTeam);
        if (!ongoingMatches.containsKey(matchKey)) {
            Match newMatch = new Match(homeTeam, 0, awayTeam, 0, LocalDateTime.now(), null);
            ongoingMatches.put(matchKey, newMatch);
        }
    }

    @Override
    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        String matchKey = getMatchKey(homeTeam, awayTeam);
        Match match = ongoingMatches.get(matchKey);
        if (match != null) {
            ongoingMatches.put(matchKey, new Match(homeTeam, homeScore, awayTeam, awayScore, match.startTime(), match.endTime()));
        }
    }

    @Override
    public void updateScore(Match match, int homeScore, int awayScore) {
        String matchKey = getMatchKey(match.homeTeam(), match.awayTeam());
        ongoingMatches.put(matchKey, new Match(match.homeTeam(), homeScore, match.awayTeam(), awayScore, match.startTime(), match.endTime()));
    }

    @Override
    public void finishMatch(String homeTeam, String awayTeam) {
        String matchKey = getMatchKey(homeTeam, awayTeam);
        Match match = ongoingMatches.remove(matchKey);
        if (match != null) {
            finishedMatches.add(new Match(match.homeTeam(), match.homeScore(), match.awayTeam(), match.awayScore(), match.startTime(), LocalDateTime.now()));
        }
    }

    @Override
    public void finishMatch(Match match) {
        finishMatch(match.homeTeam(), match.awayTeam());
    }

    @Override
    public List<Match> getSummary() {
        return ongoingMatches.values().stream()
                .sorted((m1, m2) -> {
                    int totalScoreDiff = getTotalScore(m2) - getTotalScore(m1);
                    if (totalScoreDiff != 0) {
                        return -totalScoreDiff; // Descending order by total score
                    } else {
                        return m2.startTime().compareTo(m1.startTime()); // Ascending order by start time
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public Match getMatch(String homeTeam, String awayTeam) {
        return ongoingMatches.get(getMatchKey(homeTeam, awayTeam));
    }

    @Override
    public Match getFinishedMatch(String homeTeam, String awayTeam) {
        String matchKey = getMatchKey(homeTeam, awayTeam);
        return finishedMatches.stream()
                .filter(m -> getMatchKey(m.homeTeam(), m.awayTeam()).equals(matchKey))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean containsMatch(String homeTeam, String awayTeam) {
        return ongoingMatches.containsKey(getMatchKey(homeTeam, awayTeam));
    }

    private String getMatchKey(String homeTeam, String awayTeam) {
        return homeTeam + " vs " + awayTeam;
    }

    private int getTotalScore(Match match) {
        return match.homeScore() + match.awayScore();
    }
}
