package pri.weiqiang.jet.sunshine.data;


import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;
import pri.weiqiang.jet.sunshine.data.network.WeatherResponse;
import pri.weiqiang.jet.sunshine.data.network.WeatherService;

public class Repository {
    private WeatherService weatherService;

    @Inject
    public Repository(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    public Observable<WeatherResponse> getWeather() {
        return this.weatherService.getWeather();
    }
}
