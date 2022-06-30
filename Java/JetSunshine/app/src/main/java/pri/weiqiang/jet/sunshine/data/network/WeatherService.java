package pri.weiqiang.jet.sunshine.data.network;


import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface WeatherService {
    @GET("staticweather")
    Observable<WeatherResponse> getWeather();
}
