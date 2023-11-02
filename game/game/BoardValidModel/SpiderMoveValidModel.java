package game.BoardValidModel;

public class SpiderMoveValidModel  extends MoveValidModel{
    public SpiderMoveValidModel(){
        super();
    }
    public void setValid(int x,int y,int[][] position){
        resetPosition();
        if(x!=0)if(position[x-1][y]==-1) this.position[x-1][y]=true;
        if(x!=4)if(position[x+1][y]==-1) this.position[x+1][y]=true;
        if(y!=0){if(position[x][y-1]==-1) this.position[x][y-1]=true;
            if(x!=0)if(position[x-1][y-1]==-1)this.position[x-1][y-1]=true;
            if(x!=4)if(position[x+1][y-1]==-1)this.position[x+1][y-1]=true;
        }
        if(y!=4)if(position[x][y+1]==-1) this.position[x][y+1]=true;

    }
}
