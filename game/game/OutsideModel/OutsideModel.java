package game.OutsideModel;

import game.Piece;

import java.io.Serializable;
import java.util.ArrayList;

public class OutsideModel implements Serializable {
    ArrayList<Integer> x;
    ArrayList<Integer> y;
    int size;


    public OutsideModel(){
        x=new ArrayList<Integer>();
        y=new ArrayList<Integer>();
    }
    void add(int x,int y){
        this.x.add(x);
        this.y.add(y);
    }

    public ArrayList<Integer>  getX(){ return x;  }
    public ArrayList<Integer>  getY(){
        return y;
    }

    public int getSize(){
        return size;
    }

}
