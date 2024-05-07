# Live Football World Cup Scoreboard Library

## Introduction
This is a simple Java library for managing a live football world cup scoreboard. The library allows users to start new matches, update scores, finish matches, and get a summary of matches in progress.


1. Start a new match, assuming an initial score of 0-0 and adding it to the scoreboard.
2. Update the score for a match.
3. Finish a match currently in progress, removing it from the scoreboard.
4. Get a summary of matches in progress, ordered by their total score, and for matches with the same total score, ordered by the most recently started match.

## Assumptions

The following assumptions were made while implementing this solution:

1. **In-Memory Store**: The solution uses in-memory data structures (HashMap and ArrayList) to store the ongoing and finished matches. This assumption simplifies the implementation and focuses on the core logic.

2. **Match Representation**: A `Match` record is used to represent a match between two teams, including their scores, start time, and end time (if applicable).

3. **Match Ordering**: The summary of ongoing matches is ordered by the following criteria:
- Matches with a higher total score (sum of home and away scores) come first.
- If two matches have the same total score, the most recently started match comes first.

4. **Match Key**: A match is uniquely identified by a string in the format `"Home Team vs Away Team"`. This key is used to store and retrieve matches from the in-memory store.

5. **Team Names**: Team names are assumed to be unique and case-sensitive. For example, "Mexico" and "mexico" are considered different teams.

6. **Score Updates**: When updating scores, absolute scores are provided for both the home team and the away team. Incremental score updates are not supported.


## Usage
1. **Starting a New Match**: Use the `startNewMatch(String homeTeam, String awayTeam)` method to start a new match with initial scores set to 0-0.
2. **Updating Score**: Use the `updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore)` method to update the score of an ongoing match.
3. **Finishing a Match**: Use the `finishMatch(String homeTeam, String awayTeam)` method to finish a match currently in progress.
4. **Getting Summary**: Use the `getSummary()` method to get a summary of matches in progress, ordered by their total score. Matches with the same total score are ordered by the most recently started match.

## Example
```java
// Create an instance of the ScoreboardServiceImpl
ScoreboardServiceImpl scoreboard = new ScoreboardServiceImpl();

// Start a new match
scoreboard.startNewMatch("Mexico", "Canada");

// Update score
scoreboard.updateScore("Mexico", "Canada", 0, 5);

// Finish match
scoreboard.finishMatch("Mexico", "Canada");

// Get summary of matches in progress
List<Match> summary = scoreboard.getSummary();
```

## Summary:

```
Summary:
1. Uruguay 6 - Italy 6
2. Spain 10 - Brazil 2
3. Mexico 0 - Canada 5
4. Argentina 3 - Australia 1
5. Germany 2 - France 2
```
Please refer to the `ScoreboardServiceImpl` class and the unit tests (`ScoreboardServiceImplTest`) for more details on the available methods and their usage.

## Dependencies

This solution has the following dependencies:

- Lombok: Used for generating boilerplate code (e.g., constructors, getters, setters).
- Log4j2: Used for logging purposes.
- JUnit 5: Used for writing and running unit tests.

Make sure to include these dependencies in your project to ensure the proper functioning of the library.


## Edge Cases
The test cases cover various edge cases, including:
- Matches with the same total score but different start times.
- Matches with different scores and start times.
- Matches with the same total score and start time but different teams.
- Matches with only one team scoring.

## License
This library is released under the MIT License. Feel free to use, modify, and distribute it as needed.