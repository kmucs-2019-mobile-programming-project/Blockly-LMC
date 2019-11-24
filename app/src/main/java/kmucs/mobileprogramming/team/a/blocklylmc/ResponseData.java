package kmucs.mobileprogramming.team.a.blocklylmc;

/**
 * 작성자 : 20181617 박정현
 */

public class ResponseData {
    String status;
    String message;
    LeaderBoardItem[] leaderboard;

    public ResponseData(String status, String message, LeaderBoardItem[] leaderboard) {
        this.status = status;
        this.message = message;
        this.leaderboard = leaderboard;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public LeaderBoardItem[] getLeaderboard() {
        return leaderboard;
    }
}
