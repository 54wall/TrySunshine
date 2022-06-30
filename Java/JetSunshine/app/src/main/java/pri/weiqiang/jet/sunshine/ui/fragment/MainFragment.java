package pri.weiqiang.jet.sunshine.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import dagger.hilt.android.AndroidEntryPoint;
import pri.weiqiang.jet.sunshine.R;
import pri.weiqiang.jet.sunshine.databinding.FragmentMainBinding;
import pri.weiqiang.jet.sunshine.ui.viewmodel.WeatherViewModel;
import pri.weiqiang.jet.sunshine.util.PermissionUtils;

@AndroidEntryPoint
public class MainFragment extends Fragment {
    private String TAG = MainFragment.class.getSimpleName();
    private FragmentMainBinding binding;
    private WeatherViewModel weatherViewModel;

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
        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(MainFragment.this)
                        .navigate(R.id.action_MainFragment_to_DetailFragment);
            }
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