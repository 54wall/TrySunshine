package pri.weiqiang.jet.sunshine.ui.viewmodel;

import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.ViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import pri.weiqiang.jet.sunshine.data.Repository;

public class WeatherViewModel extends ViewModel {

   private String TAG = WeatherViewModel.class.getSimpleName();
   private Repository repository;

   @ViewModelInject
   public WeatherViewModel(Repository repository){
      this.repository = repository;
   }

   public void getWeather(){
      Log.e(TAG,"repository:"+repository);
      repository.getWeather()
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe(result -> Log.e(TAG,"result:"+result.toString()),
                      error ->Log.e(TAG,"error:"+error.toString())

              );

   }
}
