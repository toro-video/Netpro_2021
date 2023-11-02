package game.BoardValidModel;

public class AttackValidModel extends BoardValidModel {
    public AttackValidModel(){
        super();
    }
    public void setValid(int x,int y,int[][] position){
        resetPosition();
        if(y!=0)if(position[x][y-1]>=100) this.position[x][y-1]=true;
    }
}
