package game.BoardValidModel;

public class SpiderAttackValidModel  extends AttackValidModel {
    public SpiderAttackValidModel(){
        super();
    }
    public void setValid(int x,int y,int[][] position){
        resetPosition();
        if(y!=0){
            if(position[x][y-1]>=100) this.position[x][y-1]=true;
        }
        else{
            if(x>0)if(position[x-1][y+1]>=100) this.position[x-1][y+1]=true;
            if(x<4)if(position[x+1][y+1]>=100) this.position[x+1][y+1]=true;
        }
    }
}
