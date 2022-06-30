package pri.weiqiang.jet.sunshine.data;


import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import io.reactivex.rxjava3.core.Observable;
import pri.weiqiang.jet.sunshine.data.database.ListWeatherEntry;
import pri.weiqiang.jet.sunshine.data.database.WeatherDao;
import pri.weiqiang.jet.sunshine.data.database.WeatherEntry;
import pri.weiqiang.jet.sunshine.data.network.WeatherResponse;
import pri.weiqiang.jet.sunshine.data.network.WeatherService;

public class Repository {

    private WeatherService weatherService;
    private WeatherDao weatherDao;

    @Inject
    public Repository(WeatherService weatherService,WeatherDao weatherDao) {
        this.weatherService = weatherService;
        this.weatherDao = weatherDao;
    }

    public Observable<WeatherResponse> getWeather() {
        return this.weatherService.getWeather();
    }

    public void bulkInsert(WeatherEntry... weather){
        weatherDao.bulkInsert(weather);
    }

    public LiveData<List<ListWeatherEntry>> getCurrentWeatherForecasts(Date date){
        return weatherDao.getCurrentWeatherForecasts(date);
    }

    public LiveData<WeatherEntry> getWeatherByDate(Date date){
        return weatherDao.getWeatherByDate(date);
    }
}
