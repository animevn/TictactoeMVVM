package com.haanhgs.tictactoemvvm.viewmodel;

import android.app.Application;
import android.content.Context;
import com.haanhgs.tictactoemvvm.model.Repo;
import com.haanhgs.tictactoemvvm.model.Game;
import com.haanhgs.tictactoemvvm.model.Move;
import com.haanhgs.tictactoemvvm.model.Player;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayMap;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import static com.haanhgs.tictactoemvvm.model.State.Draw;
import static com.haanhgs.tictactoemvvm.model.State.HasResult;
import static com.haanhgs.tictactoemvvm.model.State.InProgress;

public class GameModel extends AndroidViewModel {

    private final Repo repo;
    public final ObservableArrayMap<String, String> buttons = new ObservableArrayMap<>();
    public final ObservableField<String> text = new ObservableField<>();
    private final Context context;

    private void restart(){
        String string = repo.getCurrentPlayer().toString() + " to play";
        text.set(string);
    }

    public GameModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        repo = new Repo();
        restart();
    }

    public void onButtonNewGamePressed(){
        repo.restart();
        buttons.clear();
        restart();
    }

    private void showTextAfterEachMove(){
        if (repo.getState() == HasResult){
            String string = repo.getWinner() == null ? "" : repo.getWinner().toString();
            text.set(string + " is winner");
        }else if (repo.getState() == Draw){
            text.set("Draw");
        } else {
            String string =  repo.getCurrentPlayer().toString() + " to play";
            text.set(string);
        }
    }

    private void showTextAfterEachMove(Move move){
        if (move.getState() == HasResult){
            text.set(move.getPlayer().toString() + " is winner");
        }else if (repo.getState() == Draw){
            text.set("Draw");
        } else {
            String string =  repo.getCurrentPlayer().toString() + " to play";
            text.set(string);
        }
    }


    public void onClickedCellAt(int row, int col){
        if (repo.getState() == InProgress){
            Player player = repo.makeMove(row, col);
            buttons.put("" + row + col, player == null ? null : player.toString());
            showTextAfterEachMove();
        }
    }

    public void moveFirst(){
        int currentMove = repo.getGame().getCurrentMove();
        if (currentMove > 0){
            repo.getGame().setCurrentMove(0);
            repo.setCurrentPlayer(repo.getGame().getMoves().get(0).getPlayer());
            repo.setState(repo.getGame().getMoves().get(0).getState());
            buttons.clear();
            showTextAfterEachMove(repo.getGame().getMoves().get(0));
        }
    }

    public void moveNext(){
        int currentMove = repo.getGame().getCurrentMove();
        if (currentMove < repo.getGame().getMoves().size()){
            Move move = repo.getGame().getMoves().get(currentMove);
            buttons.put("" + move.getRow() + move.getColumn(), move.getPlayer().toString());
            repo.fillOneCell(move.getPlayer(), move.getRow(), move.getColumn());
            repo.getGame().setCurrentMove(++currentMove);
            repo.setState(move.getState());
            repo.flipSide();
            showTextAfterEachMove(move);
        }
    }

    public void moveBack(){
        int currentMove = repo.getGame().getCurrentMove();
        if (currentMove > 0){
            Move move = repo.getGame().getMoves().get(currentMove - 1);
            buttons.remove("" + move.getRow() + move.getColumn());
            repo.clearOneCell(move.getRow(), move.getColumn());
            repo.getGame().setCurrentMove(currentMove - 1);
            repo.setState(move.getState());
            repo.flipSide();
            showTextAfterEachMove(move);
        }
    }

    public void moveLast(){
        int currentMove = repo.getGame().getCurrentMove();
        int gameSize = repo.getGame().getMoves().size();
        if (currentMove < gameSize){
            for (int i = currentMove; i < gameSize; i++){
                Move move = repo.getGame().getMoves().get(i);
                buttons.put("" + move.getRow() + move.getColumn(), move.getPlayer().toString());
                repo.fillOneCell(move.getPlayer(), move.getRow(), move.getColumn());
                repo.flipSide();
                repo.setState(move.getState());
                showTextAfterEachMove(move);
            }
            repo.getGame().setCurrentMove(gameSize);
        }
    }

    public void saveGame(){
        try{
            FileOutputStream out = context.openFileOutput("save", Context.MODE_PRIVATE);
            ObjectOutputStream outputStream = new ObjectOutputStream(out);
            outputStream.writeObject(repo.getGame());
            outputStream.flush();
            outputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void loadGame(){
        File file = new File(context.getFilesDir(), "save");
        if (file.exists()){
            try{
                FileInputStream in = context.openFileInput("save");
                ObjectInputStream inputStream = new ObjectInputStream(in);
                Object object = inputStream.readObject();
                repo.setGame((Game)object);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void moveToCurrentMove(){
        int currentMove = repo.getGame().getCurrentMove();
        for (int i = 0; i < currentMove; i++){
            Move move = repo.getGame().getMoves().get(i);
            buttons.put("" + move.getRow() + move.getColumn(), move.getPlayer().toString());
            repo.fillOneCell(move.getPlayer(), move.getRow(), move.getColumn());
            repo.flipSide();
            repo.setState(move.getState());
            showTextAfterEachMove(move);
        }
    }

    public void onCreate(){
        buttons.clear();
        loadGame();
        moveToCurrentMove();
    }

    public void onDestroy(){
        saveGame();
    }

}
