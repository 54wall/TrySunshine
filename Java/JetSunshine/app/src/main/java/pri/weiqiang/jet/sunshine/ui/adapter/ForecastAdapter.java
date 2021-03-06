/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pri.weiqiang.jet.sunshine.ui.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;



import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import pri.weiqiang.jet.sunshine.App;
import pri.weiqiang.jet.sunshine.R;
import pri.weiqiang.jet.sunshine.data.database.ListWeatherEntry;
import pri.weiqiang.jet.sunshine.data.database.WeatherEntry;
import pri.weiqiang.jet.sunshine.util.SunshineDateUtils;
import pri.weiqiang.jet.sunshine.util.SunshineWeatherUtils;

/**
 * Exposes a list of weather forecasts from a list of {@link WeatherEntry} to a {@link RecyclerView}.
 */
public class ForecastAdapter extends ListAdapter<ListWeatherEntry,ForecastAdapter.ForecastAdapterViewHolder> {

    private static final int VIEW_TYPE_TODAY = 0;
    private static final int VIEW_TYPE_FUTURE_DAY = 1;


    /*
     * Below, we've defined an interface to handle clicks on items within this Adapter. In the
     * constructor of our ForecastAdapter, we receive an instance of a class that has implemented
     * said interface. We store that instance in this variable to call the onItemClick method whenever
     * an item is clicked in the list.
     */
    private final ForecastAdapterOnItemClickHandler mClickHandler;
    /*
     * Flag to determine if we want to use a separate view for the list item that represents
     * today. This flag will be true when the phone is in portrait mode and false when the phone
     * is in landscape. This flag will be set in the constructor of the adapter by accessing
     * boolean resources.
     */
    private final boolean mUseTodayLayout = true;


    /**
     * Creates a ForecastAdapter.
     * @param clickHandler The on-click handler for this adapter. This single handler is called
     *                     when an item is clicked.
     */
    public ForecastAdapter( ForecastAdapterOnItemClickHandler clickHandler) {
        super(DIFF_CALLBACK);
        mClickHandler = clickHandler;

    }

    private static final DiffUtil.ItemCallback<ListWeatherEntry> DIFF_CALLBACK= new DiffUtil.ItemCallback<ListWeatherEntry>() {
        @Override
        public boolean areItemsTheSame(@NonNull ListWeatherEntry oldItem, @NonNull ListWeatherEntry newItem) {
            return oldItem.getDate() == newItem.getDate();
        }

        @Override
        public boolean areContentsTheSame(@NonNull ListWeatherEntry oldItem, @NonNull ListWeatherEntry newItem) {
            return oldItem.getWeatherIconId() == newItem.getWeatherIconId();
        }
    };

    @Override
    public ForecastAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        int layoutId = getLayoutIdByType(viewType);
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(layoutId, viewGroup, false);
        view.setFocusable(true);
        return new ForecastAdapterViewHolder(view);
    }

    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified
     * position. In this method, we update the contents of the ViewHolder to display the weather
     * details for this particular position, using the "position" argument that is conveniently
     * passed into us.
     *
     * @param forecastAdapterViewHolder The ViewHolder which should be updated to represent the
     *                                  contents of the item at the given position in the data set.
     * @param position                  The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(ForecastAdapterViewHolder forecastAdapterViewHolder, int position) {
        ListWeatherEntry currentWeather = getItem(position);

        /****************
         * Weather Icon *
         ****************/
        int weatherIconId = currentWeather.getWeatherIconId();
        int weatherImageResourceId = getImageResourceId(weatherIconId, position);
        forecastAdapterViewHolder.iconView.setImageResource(weatherImageResourceId);

        /****************
         * Weather Date *
         ****************/
        long dateInMillis = currentWeather.getDate().getTime();
         /* Get human readable string using our utility method */
        String dateString = SunshineDateUtils.getFriendlyDateString(App.getInstance(), dateInMillis, false);

         /* Display friendly date string */
        forecastAdapterViewHolder.dateView.setText(dateString);

        /***********************
         * Weather Description *
         ***********************/
        String description = SunshineWeatherUtils.getStringForWeatherCondition(App.getInstance(), weatherIconId);
         /* Create the accessibility (a11y) String from the weather description */
        String descriptionA11y = App.getInstance().getString(R.string.a11y_forecast, description);

         /* Set the text and content description (for accessibility purposes) */
        forecastAdapterViewHolder.descriptionView.setText(description);
        forecastAdapterViewHolder.descriptionView.setContentDescription(descriptionA11y);

        /**************************
         * High (max) temperature *
         **************************/
        double highInCelsius = currentWeather.getMax();
         /*
          * If the user's preference for weather is fahrenheit, formatTemperature will convert
          * the temperature. This method will also append either ??C or ??F to the temperature
          * String.
          */
        String highString = SunshineWeatherUtils.formatTemperature(App.getInstance(), highInCelsius);
         /* Create the accessibility (a11y) String from the weather description */
        String highA11y = App.getInstance().getString(R.string.a11y_high_temp, highString);

         /* Set the text and content description (for accessibility purposes) */
        forecastAdapterViewHolder.highTempView.setText(highString);
        forecastAdapterViewHolder.highTempView.setContentDescription(highA11y);

        /*************************
         * Low (min) temperature *
         *************************/
        double lowInCelsius = currentWeather.getMin();
         /*
          * If the user's preference for weather is fahrenheit, formatTemperature will convert
          * the temperature. This method will also append either ??C or ??F to the temperature
          * String.
          */
        String lowString = SunshineWeatherUtils.formatTemperature(App.getInstance(), lowInCelsius);
        String lowA11y = App.getInstance().getString(R.string.a11y_low_temp, lowString);

         /* Set the text and content description (for accessibility purposes) */
        forecastAdapterViewHolder.lowTempView.setText(lowString);
        forecastAdapterViewHolder.lowTempView.setContentDescription(lowA11y);
    }

    /**
     * Converts the weather icon id from Open Weather to the local image resource id. Returns the
     * correct image based on whether the forecast is for today(large image) or the future(small image).
     *
     * @param weatherIconId Open Weather icon id
     * @param position      Position in list
     * @return Drawable image resource id for weather
     */
    private int getImageResourceId(int weatherIconId, int position) {
        int viewType = getItemViewType(position);

        switch (viewType) {

            case VIEW_TYPE_TODAY:
                return SunshineWeatherUtils
                        .getLargeArtResourceIdForWeatherCondition(weatherIconId);

            case VIEW_TYPE_FUTURE_DAY:
                return SunshineWeatherUtils
                        .getLargeArtResourceIdForWeatherCondition(weatherIconId);

            default:
                throw new IllegalArgumentException("Invalid view type, value of " + viewType);
        }
    }



    /**
     * Returns an integer code related to the type of View we want the ViewHolder to be at a given
     * position. This method is useful when we want to use different layouts for different items
     * depending on their position. In Sunshine, we take advantage of this method to provide a
     * different layout for the "today" layout. The "today" layout is only shown in portrait mode
     * with the first item in the list.
     *
     * @param position index within our RecyclerView and list
     * @return the view type (today or future day)
     */
    @Override
    public int getItemViewType(int position) {
        if (mUseTodayLayout && position == 0) {
            return VIEW_TYPE_TODAY;
        } else {
            return VIEW_TYPE_FUTURE_DAY;
        }
    }


    /**
     * Returns the the layout id depending on whether the list item is a normal item or the larger
     * "today" list item.
     *
     * @param viewType
     * @return
     */
    private int getLayoutIdByType(int viewType) {
        switch (viewType) {

            case VIEW_TYPE_TODAY: {
                return R.layout.list_item_forecast_today;
            }

            case VIEW_TYPE_FUTURE_DAY: {
                return R.layout.forecast_list_item;
            }

            default:
                throw new IllegalArgumentException("Invalid view type, value of " + viewType);
        }
    }

    /**
     * The interface that receives onItemClick messages.
     */
    public interface ForecastAdapterOnItemClickHandler {
        void onItemClick(Date date);
    }

    /**
     * A ViewHolder is a required part of the pattern for RecyclerViews. It mostly behaves as
     * a cache of the child views for a forecast item. It's also a convenient place to set an
     * OnClickListener, since it has access to the adapter and the views.
     */
    class ForecastAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView iconView;

        final TextView dateView;
        final TextView descriptionView;
        final TextView highTempView;
        final TextView lowTempView;

        ForecastAdapterViewHolder(View view) {
            super(view);

            iconView = view.findViewById(R.id.weather_icon);
            dateView = view.findViewById(R.id.date);
            descriptionView = view.findViewById(R.id.weather_description);
            highTempView = view.findViewById(R.id.high_temperature);
            lowTempView = view.findViewById(R.id.low_temperature);

            view.setOnClickListener(this);
        }

        /**
         * This gets called by the child views during a click. We fetch the date that has been
         * selected, and then call the onItemClick handler registered with this adapter, passing that
         * date.
         *
         * @param v the View that was clicked
         */
        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Date date = getItem(adapterPosition).getDate();
            mClickHandler.onItemClick(date);
        }
    }
}