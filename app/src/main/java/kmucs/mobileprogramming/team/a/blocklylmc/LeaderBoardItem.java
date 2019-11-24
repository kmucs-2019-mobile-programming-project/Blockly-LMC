package kmucs.mobileprogramming.team.a.blocklylmc;

/**
 * 작성자 : 20181617 박정현
 */

public class LeaderBoardItem {
    String submit_time;
    String username;
    int cycle;

    public LeaderBoardItem(String submit_time, String username, int cycle) {
        this.submit_time = submit_time;
        this.username = username;
        this.cycle = cycle;
    }

    public String getSubmit_time() {
        return submit_time;
    }

    public String getUsername() {
        return username;
    }

    public int getCycle() {
        return cycle;
    }

    @Override
    public String toString(){
        return submit_time + " " + username + " " + String.valueOf(cycle);
    }
}
