package pri.weiqiang.jet.sunshine.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.hilt.android.AndroidEntryPoint;
import pri.weiqiang.jet.sunshine.databinding.FragmentMainBinding;
import pri.weiqiang.jet.sunshine.ui.adapter.ForecastAdapter;
import pri.weiqiang.jet.sunshine.ui.viewmodel.WeatherViewModel;
import pri.weiqiang.jet.sunshine.util.PermissionUtils;

@AndroidEntryPoint
public class MainFragment extends Fragment {
    private String TAG = MainFragment.class.getSimpleName();
    private FragmentMainBinding binding;
    private WeatherViewModel weatherViewModel;
    private ForecastAdapter adapter;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentMainBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requestPermissions();
        weatherViewModel = new ViewModelProvider(this).get(WeatherViewModel.class);
        weatherViewModel.getWeather();
        adapter = new ForecastAdapter(new ForecastAdapter.ForecastAdapterOnItemClickHandler() {
            @Override
            public void onItemClick(Date date) {

            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.recyclerviewForecast.setLayoutManager(linearLayoutManager);
        binding.recyclerviewForecast.setHasFixedSize(true);
        binding.recyclerviewForecast.setAdapter(adapter);
        weatherViewModel = new ViewModelProvider(requireActivity()).get(WeatherViewModel.class);
        weatherViewModel.getForecast().observe(getViewLifecycleOwner(), listWeatherEntries ->
        {
            adapter.submitList(listWeatherEntries);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private final String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.INTERNET"};
    private final int REQUEST_CODE_PERMISSIONS = 2;

    private void requestPermissions() {
        PermissionUtils.checkAndRequestMorePermissions(getActivity(), REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS,
                new PermissionUtils.PermissionRequestSuccessCallBack() {
                    @Override
                    public void onHasPermission() {
                        // 权限已被授予
                        Log.i(TAG, "权限已被授予");
                    }
                });
    }

}