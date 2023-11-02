package game.BoardValidModel;

public class MyPieceValidModel extends BoardValidModel {
    public MyPieceValidModel(){
        super();
    }
    public void setValid(int[][] position){
        resetPosition();
        for(int x=0;x<5;x++){
            for(int y=0;y<5;y++){
                if(position[x][y]>-1&&position[x][y]<100)this.position[x][y]=true;
            }
        }
    }
    public String toString(){
        String s="";
        for(int y=0;y<5;y++){
            for(int x=0;x<5;x++){
                s+=position[x][y]+",";
            }
            s+="\n";
        }
        return s;
    }
}
