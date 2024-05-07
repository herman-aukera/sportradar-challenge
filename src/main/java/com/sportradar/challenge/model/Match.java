package com.sportradar.challenge.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record Match(String homeTeam, int homeScore, String awayTeam, int awayScore, LocalDateTime startTime, LocalDateTime endTime) {}
