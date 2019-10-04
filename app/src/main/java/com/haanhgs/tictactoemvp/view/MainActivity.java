package com.haanhgs.tictactoemvp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.haanhgs.tictactoemvp.R;
import com.haanhgs.tictactoemvp.databinding.TictactoeBinding;
import com.haanhgs.tictactoemvp.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity{

    private MainViewModel viewModel = new MainViewModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TictactoeBinding binding = DataBindingUtil.setContentView(this, R.layout.tictactoe);
        binding.setViewModel(viewModel);
        viewModel.onCreate();
    }

    @Override
    protected void onPause() {
        super.onPause();
        viewModel.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModel.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mniReset){
            viewModel.onResetSelected();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
