package pri.weiqiang.jet.sunshine.di;


import android.app.Application;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import pri.weiqiang.jet.sunshine.data.database.SunshineDatabase;
import pri.weiqiang.jet.sunshine.data.database.WeatherDao;

@Module
@InstallIn(ApplicationComponent.class)
public class DatabaseModule {

    private static final String DATABASE_NAME = "vocab";

    @Provides
    @Singleton
    public SunshineDatabase provideDb(Application application){
        return Room.databaseBuilder(application,
                SunshineDatabase.class, DATABASE_NAME).build();
    }

    @Provides
    @Singleton
    public WeatherDao provideDao(SunshineDatabase sunshineDatabase){
        return sunshineDatabase.weatherDao();
    }


}
