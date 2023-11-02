package game.BoardValidModel;

public class NullAttackValidModel extends AttackValidModel{
    public NullAttackValidModel(){
        super();
    }
    public void setValid(int x,int y,int[][] position){
        resetPosition();
    }
}