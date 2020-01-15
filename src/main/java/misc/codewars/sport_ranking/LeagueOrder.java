package misc.codewars.sport_ranking;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Solution to https://www.codewars.com/kata/5e0baea9d772160032022e8c.
 */
public class LeagueOrder {
    public static int[] computeRanks(int number, int[][] games) {
        TeamResult[] teamResultArray = Arrays.stream(games)
                .map(result -> Stream.of(
                            new TeamResult(result[0], result[2], result[3]),
                            new TeamResult(result[1], result[3], result[2])
                        ))
                .flatMap(Function.identity())
                .collect(() -> new TeamResult[number],
                        (arr, tr) -> arr[tr.getTeam()] = tr,
                        (t1, t2) -> {
                            for(int i = 0; i < number; i++) {
                                if(t1[i] != null) {
                                    t1[i].add(t2[i]);
                                } else {
                                    t1[i] = t2[i];
                                }
                            }
                        });

        List<TeamResult> teamRanks = Arrays.stream(teamResultArray)
                .sorted(Comparator.comparingInt(TeamResult::getPoints).reversed()
                        .thenComparingInt(TeamResult::getScoreDifferential).reversed()
                        .thenComparingInt(TeamResult::getGoalsScored).reversed())
                .collect(Collectors.toList());

        int place = 1;
        int lastPoints = teamResultArray[0].getPoints();
        int lastDiff = teamResultArray[0].getScoreDifferential();
        int lastGoals = teamResultArray[0].getGoalsScored();

        for(int i = 0; i < number; i++) {
            TeamResult currentTeam = teamResultArray[i];
            if(currentTeam.getPoints() != lastPoints || currentTeam.getScoreDifferential() != lastDiff || currentTeam.getGoalsScored() != lastGoals) {
                currentTeam.setPlace(++place);
                lastPoints = currentTeam.getPoints();
                lastDiff = currentTeam.getScoreDifferential();
                lastGoals = currentTeam.getGoalsScored();
            } else {
                currentTeam.setPlace(place);
            }
        }

        System.out.println("teams: " + teamResultArray.length);
        System.out.println(Arrays.toString(teamResultArray));
        System.out.println("ranks: " + teamRanks);

        // your solution


        return null;
    }

    private static class TeamResult {
        private int team;
        private int wins;
        private int losses;
        private int draws;
        private int scoreDifferential;
        private int goalsScored;
        private int place = -1;

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

        public void setPlace(int p) {
            place = p;
        }

        public void add(TeamResult t) {
            if(t != null) {
                wins += t.wins;
                losses += t.losses;
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