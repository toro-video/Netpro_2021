package game.PieceKind;

import game.Board;
import game.BoardValidModel.AttackValidModel;
import game.BoardValidModel.MoveValidModel;
import game.Piece;
import game.Play;

import java.awt.*;
import java.io.Serializable;

public abstract class PieceBase implements Serializable {


    //static int cost;//拡張用
    MoveValidModel moveModel;
    AttackValidModel attackModel;
    boolean PE=true;

    boolean select=false;
    Font f=new Font("Serif", Font.PLAIN, 16);

    //public MoveValidModel getMoveModel(){
    //    return moveModel;
    //}
    public MoveValidModel setMoveModel(int x,int y,int[][] position){
        moveModel.setValid(x,y,position);
        return moveModel;
    }

    public AttackValidModel setAttackModel(int x,int y,int[][] position){
        attackModel.setValid(x,y,position);
        return attackModel;
    }

    public void setP(){PE=true;}
    public void setE(){PE=false;}

    public boolean move(Board board, Piece piece, int[] movePosition){//駒を動かした時の処理
        board.getPosition()[movePosition[0]][movePosition[1]]=board.getPosition()[piece.getPosition()[0]][piece.getPosition()[1]];
        board.getPosition()[piece.getPosition()[0]][piece.getPosition()[1]]=-1;
        board.consistPosition();
        select=false;
        return false;
    }
    public boolean attack(Board board, int[] attackPosition){//駒を攻撃した時の処理
        int n=board.getPosition()[attackPosition[0]][attackPosition[1]]-100;
        board.getEnemyPieces().get(n).skillSet(-1);
        board.getOutside(1).addPiece(board.getEnemyPieces().get(n));
        board.getPosition()[attackPosition[0]][attackPosition[1]]=-1;
        board.getEnemyPieces().remove(n);
        select=false;
        return false;
    }
    public int skill(Board board, Piece piece,int skillPhase){//駒のスキルを使った時の処理・スキルは駒種によって違うので各駒種で定義
        return Play.turnFinish;
    }

    public boolean skillCheck(Board board, Piece piece){
        return false;
    }

    public abstract PieceBase copy();

    public abstract int getSkillMax();
    public boolean getSelect(){return select; };
    public void setSelect(boolean select){
        this.select=select;
    }

    public abstract int kind();


    public abstract  void drawIn(Graphics g, int[] position);
    public abstract void drawOut(Graphics g,int[] position, int size);
    public abstract void drawSkill(Graphics g);
}
