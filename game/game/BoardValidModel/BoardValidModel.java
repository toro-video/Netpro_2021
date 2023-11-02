package game.BoardValidModel;

import game.Board;
import game.Mouse.Mouse;

import java.io.Serializable;
import java.util.ArrayList;

public class BoardValidModel implements Serializable {
    boolean[][] position=new boolean[5][5];
    BoardValidModel(){
        for(int i=0;i<position.length;i++){
            for(int j=0;j<position[i].length;j++){
                position[i][j]=false;
            }
        }
    }

    public boolean[][] getPosition() {
        return position;
    }

    public void resetPosition(){
        for(int i=0;i<position.length;i++){
            for(int j=0;j<position[i].length;j++){
                position[i][j]=false;
            }
        }
    }

    public ArrayList<int[]> validPosition(){
        ArrayList<int[]> valid=new ArrayList<int[]>();
        for(int x=0;x<position.length;x++){
            for(int y=0;y<position[x].length;y++){
                if(position[x][y])valid.add(new int[]{x,y});
            }
        }
        return valid;
    }

    public int[] boardSelect(Mouse mouse, Board board){
        for(int[] position:validPosition()){
            if(mouse.click(board.getAreas(position[0],position[1])))return position;
        }
        return null;
    }

    public int count(){
        int i=0;
        for(int x=0;x<position.length;x++){
            for(int y=0;y<position[x].length;y++){
                if(position[x][y])i++;
            }
        }
        return i;
    }


}
