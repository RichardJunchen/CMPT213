package ca.cmpt213.asn4.tictactoe.game;

import java.util.Arrays;

public class Game_info {

    private final int [][] array;
    final int row = 4;
    final int col = 4;

    public Game_info (){
        array = new int[row][col];
        for (int i = 0; i < row ; i++){
            for (int j = 0; j < col; j++)
                array[i][j] = 0;
        }
    }
    public int getElement (int i , int j){
        return array[i][j];
    }

    public void change_array (int i, int j ,boolean order){
        if (order)
            array[i][j] = 1;
        else
            array[i][j] = -1;
    }

    public boolean check_if_first_win (){
        for (int i = 0; i< row; i++){
            if (array[i][0] == 1 && array[i][1] == 1 &&array[i][2] == 1 &&array[i][3] == 1 )
                return true;
            if (array[0][i] == 1 && array[1][i] == 1 && array[2][i] == 1 && array[3][i] == 1)
                return true;
        }
        if (array[0][0] == 1 && array[1][1] == 1 && array[2][2] == 1 && array[3][3] == 1)
            return true;
        else if (array[0][3] == 1 && array[1][2] == 1 && array[2][1] == 1 && array[3][0] == 1)
            return true;
        else
            return false;
    }
    public boolean check_if_second_win (){
        for (int i = 0; i< row; i++){
            if (array[i][0] == -1 && array[i][1] == -1 &&array[i][2] == -1 &&array[i][3] == -1 )
                return true;
            if (array[0][i] == -1 && array[1][i] == -1 && array[2][i] == -1 && array[3][i] == -1)
                return true;
        }
        if (array[0][0] == -1 && array[1][1] == -1 && array[2][2] == -1 && array[3][3] == -1)
            return true;
        else if (array[0][3] == -1 && array[1][2] == -1 && array[2][1] == -1 && array[3][0] == -1)
            return true;
        else
            return false;
    }
}
