package pri.weiqiang.jet.sunshine.ui.viewmodel;

import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import pri.weiqiang.jet.sunshine.data.Repository;
import pri.weiqiang.jet.sunshine.data.database.ListWeatherEntry;
import pri.weiqiang.jet.sunshine.data.database.WeatherEntry;
import pri.weiqiang.jet.sunshine.data.network.WeatherResponse;
import pri.weiqiang.jet.sunshine.util.SunshineDateUtils;

public class WeatherViewModel extends ViewModel {

    private String TAG = WeatherViewModel.class.getSimpleName();
    private Repository repository;
    private LiveData<List<ListWeatherEntry>> mForecast;

    @ViewModelInject
    public WeatherViewModel(Repository repository) {
        this.repository = repository;
        mForecast = repository.getCurrentWeatherForecasts(SunshineDateUtils.getNormalizedUtcDateForToday());
    }

    public void getWeather() {
        Log.e(TAG, "repository:" + repository);
        repository.getWeather()
                .subscribeOn(Schedulers.io())
                .map(new Function<WeatherResponse, ArrayList<WeatherEntry>>() {
                    @Override
                    public ArrayList<WeatherEntry> apply(WeatherResponse weatherResponse) throws Throwable {
                        ArrayList<WeatherEntry> list = new ArrayList<>();
                        long normalizedUtcStartDay = SunshineDateUtils.getNormalizedUtcMsForToday();
                        for (int i = 0; i < weatherResponse.getList().size(); i++) {
                            WeatherResponse.ListBean listBean = weatherResponse.getList().get(i);
                            long dateTimeMillis = normalizedUtcStartDay + SunshineDateUtils.DAY_IN_MILLIS * i;
                            WeatherEntry entry = new WeatherEntry(listBean.getWeather().get(0).getId(),
                                    new Date(dateTimeMillis)
                                    , listBean.getTemp().getMax()
                                    , listBean.getTemp().getMin()
                                    , listBean.getHumidity()
                                    , listBean.getPressure()
                                    , listBean.getSpeed()
                                    , listBean.getDeg()
                            );
                            list.add(entry);
                            repository.bulkInsert(entry);
                        }
                        return list;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> getWeather(result),
                        error -> Log.e(TAG, "error:" + error.toString())

                );
    }


    public LiveData<List<ListWeatherEntry>> getForecast() {

        return mForecast;
    }

    public void getWeather(ArrayList<WeatherEntry> weatherEntryArrayList) {

        ArrayList<ListWeatherEntry> listWeatherEntryArrayList = new ArrayList<>();
        for (WeatherEntry weatherEntry : weatherEntryArrayList) {
            listWeatherEntryArrayList.add(new ListWeatherEntry(
                    weatherEntry.getId()
                    , weatherEntry.getWeatherIconId()
                    , weatherEntry.getDate()
                    , weatherEntry.getMin()
                    , weatherEntry.getMax())
            );
        }
        mForecast.getValue().clear();
        mForecast.getValue().addAll(listWeatherEntryArrayList);
    }
}
