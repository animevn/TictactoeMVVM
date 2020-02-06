package com.haanhgs.tictactoemvvm.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.haanhgs.tictactoemvvm.R;
import com.haanhgs.tictactoemvvm.databinding.ActivityMainBinding;
import com.haanhgs.tictactoemvvm.viewmodel.GameModel;

//add databinding to build.gradle
public class MainActivity extends AppCompatActivity{

    private final GameModel model = new GameModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setViewModel(model);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mniReset){
            model.onButtonNewGamePressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
