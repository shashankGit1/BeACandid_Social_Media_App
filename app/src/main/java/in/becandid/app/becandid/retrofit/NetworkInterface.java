package in.becandid.app.becandid.retrofit;

import in.becandid.app.becandid.data.network.ApiEndPoint;
import in.becandid.app.becandid.dto.LoginResponse;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface NetworkInterface {

    @FormUrlEncoded
    @POST(ApiEndPoint.POST_LOGIN_WITHOUT_BASE)
    Single<LoginResponse> skipUser(@Field("deviceId") String deviceId,
                                   @Field("socialNetwork") String socialNetwork);


  //  @GET("search/movie")
   // Observable<MovieResponse> getMoviesBasedOnQuery(@Query("api_key") String api_key, @Query("query") String q);

}
