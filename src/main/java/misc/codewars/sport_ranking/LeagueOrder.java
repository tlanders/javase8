package misc.codewars.sport_ranking;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Solution to https://www.codewars.com/kata/5e0baea9d772160032022e8c.
 */
public class LeagueOrder {
    public static int[] computeRanks(int number, int[][] games) {
        TeamResult[] teamResultArray = computeTeamResults(number, games);
        List<TeamResult> teamRanks = rankTeams(teamResultArray);

        assignPlaces(number, teamRanks);

        return Stream.of(teamResultArray)
                .mapToInt(TeamResult::getPlace)
                .toArray();
    }

    private static List<TeamResult> rankTeams(TeamResult[] teamResultArray) {
        return Arrays.stream(teamResultArray)
                    .sorted(Comparator.comparingInt(TeamResult::getPoints)
                            .thenComparingInt(TeamResult::getScoreDifferential)
                            .thenComparingInt(TeamResult::getGoalsScored).reversed())
                    .collect(Collectors.toList());
    }

    private static TeamResult[] computeTeamResults(int number, int[][] games) {
        return Arrays.stream(games)
                    .map(result -> Stream.of(
                                new TeamResult(result[0], result[2], result[3]),
                                new TeamResult(result[1], result[3], result[2])
                            ))
                    .flatMap(Function.identity())
                    .collect(initializeTeamResults(number),
                            accumulateTeamResults(),
                            combineTeamResults(number));
    }

    private static BiConsumer<TeamResult[], TeamResult> accumulateTeamResults() {
        return (arr, tr) -> {
            if(arr[tr.getTeam()] != null) {
                arr[tr.getTeam()].add(tr);
            } else {
                arr[tr.getTeam()] = tr;
            }
        };
    }

    private static Supplier<TeamResult[]> initializeTeamResults(int number) {
        return () -> {
            TeamResult[] tra = new TeamResult[number];
            for(int i = 0; i < tra.length; i++) {
                tra[i] = new TeamResult(i);
            }
            return tra;
        };
    }

    private static BiConsumer<TeamResult[], TeamResult[]> combineTeamResults(int number) {
        return (t1, t2) -> {
            for(int i = 0; i < number; i++) {
                if(t1[i] != null) {
                    t1[i].add(t2[i]);
                } else {
                    t1[i] = t2[i];
                }
            }
        };
    }

    private static void assignPlaces(int number, List<TeamResult> teamRanks) {
        int lastPlace = 1;
        int lastPoints = teamRanks.get(0).getPoints();
        int lastDiff = teamRanks.get(0).getScoreDifferential();
        int lastGoals = teamRanks.get(0).getGoalsScored();

        for(int i = 0; i < number; i++) {
            TeamResult currentTeam = teamRanks.get(i);
            if(currentTeam.getPoints() != lastPoints || currentTeam.getScoreDifferential() != lastDiff || currentTeam.getGoalsScored() != lastGoals) {
                currentTeam.setPlace(i + 1);
                lastPlace = i + 1;
                lastPoints = currentTeam.getPoints();
                lastDiff = currentTeam.getScoreDifferential();
                lastGoals = currentTeam.getGoalsScored();
            } else {
                currentTeam.setPlace(lastPlace);
            }
        }
    }

    private static void printTeams(TeamResult[] teamResultArray, List<TeamResult> teamRanks) {
        System.out.println("teams: " + teamResultArray.length);
        for(TeamResult tr : teamResultArray) {
            System.out.println("  " + tr);
        }

        System.out.println("ranks:");
        for(TeamResult tr : teamRanks) {
            System.out.println("  " + tr);
        }
    }

    private static class TeamResult {
        private int team;
        private int wins;
        private int losses;
        private int draws;
        private int scoreDifferential;
        private int goalsScored;
        private int place = -1;

        public TeamResult(int team) {
            this.team = team;
            this.wins = 0;
            this.losses = 0;
            this.draws = 0;
            this.scoreDifferential = 0;
            this.goalsScored = 0;
        }

        public TeamResult(int team, int teamScore, int opponentScore) {
            this.team = team;
            this.wins = teamScore > opponentScore ? 1 : 0;
            this.losses = opponentScore > teamScore ? 1 : 0;
            this.draws = teamScore == opponentScore ? 1 : 0;
            this.scoreDifferential = teamScore - opponentScore;
            this.goalsScored = teamScore;
        }

        @Override
        public String toString() {
            return "TeamResult{" +
                    "team=" + team +
                    ", place=" + place +
                    ", wins=" + wins +
                    ", losses=" + losses +
                    ", draws=" + draws +
                    ", scoreDifferential=" + scoreDifferential +
                    ", goalsScored=" + goalsScored +
                    ", points=" + getPoints() +
                    '}';
        }

        public int getPlace() {
            return place;
        }

        public void setPlace(int p) {
            place = p;
        }

        public void add(TeamResult t) {
            if(t != null) {
                wins += t.wins;
                losses += t.losses;
                draws += t.draws;
                scoreDifferential += t.scoreDifferential;
                goalsScored += t.goalsScored;
            }
        }

        public int getPoints() {
            return wins * 2 + draws;
        }

        public int getTeam() {
            return team;
        }

        public int getWins() {
            return wins;
        }

        public int getLosses() {
            return losses;
        }

        public int getDraws() {
            return draws;
        }

        public int getScoreDifferential() {
            return scoreDifferential;
        }

        public int getGoalsScored() {
            return goalsScored;
        }
    }
}
