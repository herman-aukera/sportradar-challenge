package com.sportradar.challenge.service.impl;

import com.sportradar.challenge.model.Match;
import com.sportradar.challenge.service.ScoreboardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ScoreboardServiceImplTest {


    private ScoreboardService scoreboard;

    @BeforeEach
    void setUp() {
        scoreboard = new ScoreboardServiceImpl();
    }

    @Test
    void startNewMatchTest() {

        scoreboard.startNewMatch("Home Team", "Away Team");

        Match match = scoreboard.getMatch("Home Team", "Away Team");
        assertNotNull(match);

        assertTrue(scoreboard.containsMatch("Home Team", "Away Team"));
        assertFalse(scoreboard.containsMatch("Away Team", "Home Team"));

        assertEquals("Home Team", match.homeTeam());
        assertEquals("Away Team", match.awayTeam());
        assertEquals(0, match.homeScore());
        assertEquals(0, match.awayScore());

        match = scoreboard.getMatch("Away Team", "Home Team");
        assertNull(match);
    }

    @Test
    void updateScoreTest() {

        scoreboard.startNewMatch("Home Team", "Away Team");

        scoreboard.updateScore("Home Team", "Away Team", 1, 2);

        Match match = scoreboard.getMatch("Home Team", "Away Team");
        assertEquals(1, match.homeScore());
        assertEquals(2, match.awayScore());

        scoreboard.updateScore(match, 2, 3);

        match = scoreboard.getMatch("Home Team", "Away Team");
        assertEquals(2, match.homeScore());
        assertEquals(3, match.awayScore());
    }

    @Test
    void finishMatchTest() {

        scoreboard.startNewMatch("Home Team", "Away Team");
        assertNotNull(scoreboard.getMatch("Home Team", "Away Team"));

        scoreboard.finishMatch("Home Team", "Away Team");
        assertNull(scoreboard.getMatch("Home Team", "Away Team"));
        assertNotNull(scoreboard.getFinishedMatch("Home Team", "Away Team"));

        scoreboard.startNewMatch("Away Team", "Home Team");
        Match match = scoreboard.getMatch("Away Team", "Home Team");
        assertNotNull(match);

        scoreboard.finishMatch(match);
        assertNull(scoreboard.getMatch("Away Team", "Home Team"));
        assertNotNull(scoreboard.getFinishedMatch("Away Team", "Home Team"));
    }

    @Test
    void getSummaryTest() {

        scoreboard.startNewMatch("Mexico", "Canada");
        scoreboard.updateScore("Mexico", "Canada", 0, 5);

        scoreboard.startNewMatch("Spain", "Brazil");
        scoreboard.updateScore("Spain", "Brazil", 10, 2);

        scoreboard.startNewMatch("Germany", "France");
        scoreboard.updateScore("Germany", "France", 2, 2);

        scoreboard.startNewMatch("Uruguay", "Italy");
        scoreboard.updateScore("Uruguay", "Italy", 6, 6);

        scoreboard.startNewMatch("Argentina", "Australia");
        scoreboard.updateScore("Argentina", "Australia", 3, 1);

        List<Match> summary = scoreboard.getSummary();

        // Matches with the same total score should be ordered by the most recently started match
        assertEquals("Uruguay", summary.get(0).homeTeam());
        assertEquals("Italy", summary.get(0).awayTeam());
        assertEquals(6, summary.get(0).homeScore());
        assertEquals(6, summary.get(0).awayScore());

        assertEquals("Spain", summary.get(1).homeTeam());
        assertEquals("Brazil", summary.get(1).awayTeam());
        assertEquals(10, summary.get(1).homeScore());
        assertEquals(2, summary.get(1).awayScore());

        assertEquals("Mexico", summary.get(2).homeTeam());
        assertEquals("Canada", summary.get(2).awayTeam());
        assertEquals(0, summary.get(2).homeScore());
        assertEquals(5, summary.get(2).awayScore());

        assertEquals("Argentina", summary.get(3).homeTeam());
        assertEquals("Australia", summary.get(3).awayTeam());
        assertEquals(3, summary.get(3).homeScore());
        assertEquals(1, summary.get(3).awayScore());

        assertEquals("Germany", summary.get(4).homeTeam());
        assertEquals("France", summary.get(4).awayTeam());
        assertEquals(2, summary.get(4).homeScore());
        assertEquals(2, summary.get(4).awayScore());
    }
}
