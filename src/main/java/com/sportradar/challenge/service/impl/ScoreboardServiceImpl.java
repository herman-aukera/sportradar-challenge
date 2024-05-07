package com.sportradar.challenge.service.impl;

import com.sportradar.challenge.service.ScoreboardService;
import com.sportradar.challenge.model.Match;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
public class ScoreboardServiceImpl implements ScoreboardService {

    private final Map<String, Match> matchesInProgress;
    private final List<Match> finishedMatches;

    public ScoreboardServiceImpl() {
        matchesInProgress = new HashMap<>();
        finishedMatches = new ArrayList<>();
        log.info("Scoreboard service initialized");
    }

    @Override
    public void startNewMatch(String homeTeam, String awayTeam) {
        String matchKey = getMatchKey(homeTeam, awayTeam);

        log.debug("Starting new match: {}", matchKey);

        if (!matchesInProgress.containsKey(matchKey))
            matchesInProgress.put(matchKey, new Match(homeTeam, 0, awayTeam, 0, LocalDateTime.now(), null));
        else
            log.warn("Match already in progress: {}", matchKey);

    }

    @Override
    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        String matchKey = getMatchKey(homeTeam, awayTeam);
        Match match = matchesInProgress.get(matchKey);

        log.debug("Updating score for match: {}", matchKey);

        if (match != null)
            matchesInProgress.put(matchKey, new Match(homeTeam, homeScore, awayTeam, awayScore, match.startTime(), null));
        else
            log.warn("No ongoing match found for Updating: {}", matchKey);

    }

    @Override
    public void updateScore(Match match, int homeScore, int awayScore) {
        updateScore(match.homeTeam(), match.awayTeam(), homeScore, awayScore);
    }

    @Override
    public void finishMatch(String homeTeam, String awayTeam) {
        String matchKey = getMatchKey(homeTeam, awayTeam);
        Match match = matchesInProgress.remove(matchKey);

        log.debug("Finishing match: {}", matchKey);

        if (match != null)
            finishedMatches.add(new Match(match.homeTeam(), match.homeScore(), match.awayTeam(), match.awayScore(), match.startTime(), LocalDateTime.now()));
        else
            log.warn("No ongoing match found for Finishing: {}", matchKey);

    }

    @Override
    public void finishMatch(Match match) {
        finishMatch(match.homeTeam(), match.awayTeam());
    }

    @Override
    public List<Match> getSummary() {
        log.debug("Getting match summary");
        return matchesInProgress.values().stream()
                .sorted(Comparator.comparingInt(this::getTotalScore).thenComparing(Match::startTime).reversed())
                .toList();
    }

    @Override
    public Match getMatch(String homeTeam, String awayTeam) {
        return matchesInProgress.get(getMatchKey(homeTeam, awayTeam));
    }

    @Override
    public Match getFinishedMatch(String homeTeam, String awayTeam) {
        String matchKey = getMatchKey(homeTeam, awayTeam);

        log.debug("Getting finished match: {}", matchKey);

        return finishedMatches.stream()
                .filter(m -> getMatchKey(m.homeTeam(), m.awayTeam()).equals(matchKey))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean containsMatch(String homeTeam, String awayTeam) {
        return matchesInProgress.containsKey(getMatchKey(homeTeam, awayTeam));
    }

    private String getMatchKey(String homeTeam, String awayTeam) {
        return homeTeam + " vs " + awayTeam;
    }

    private int getTotalScore(Match match) {
        return match.homeScore() + match.awayScore();
    }
}
