package kmucs.mobileprogramming.team.a.blocklylmc;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitService {
    // 서버 URL
    public static final String requestBaseURL = "http://mpserverapp.8ffbgpczpa.ap-southeast-1.elasticbeanstalk.com/api/";

    @GET("record/")
    Call<ResponseData> record(
            @Query("username") String username,
            @Query("level") int level,
            @Query("cycle") int cycle
    );

    @GET("leaderboard/")
    Call<ResponseData> leaderboard(
            @Query("level") int level
    );
}
