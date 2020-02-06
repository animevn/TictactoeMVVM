package com.haanhgs.tictactoemvvm.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import com.haanhgs.tictactoemvvm.R;
import com.haanhgs.tictactoemvvm.databinding.ActivityMainBinding;
import com.haanhgs.tictactoemvvm.viewmodel.GameModel;

//add databinding to build.gradle
public class MainActivity extends AppCompatActivity{

    private GameModel model;

//    private void hideActionBarInLandscape(){
//        if (getSupportActionBar() != null){
//            int rotation = getWindowManager().getDefaultDisplay().getRotation();
//            if (rotation == Surface.ROTATION_270 || rotation == Surface.ROTATION_90){
//                getSupportActionBar().hide();
//            }
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new GameModel(this.getApplication());
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setViewModel(model);
        model.onCreate();
//        hideActionBarInLandscape();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        model.onDestroy();
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
