package game;

import game.Mouse.Mouse;
import game.Mouse.MouseCheckArea;
import game.OutsideModel.OutsideModel;
import game.PieceKind.*;

import java.awt.*;
import java.io.Serializable;

public class Piece implements Serializable {//一つ一つの駒の情報を格納
    PieceBase piece;//駒種
    int[] position=new int[2];//座標
    MouseCheckArea areaIn=new MouseCheckArea();
    int skillTurn=-1;

    public Piece(PieceBase piece){
        this.piece=piece.copy();

    }

    public Piece(int x, int y, PieceBase piece){
        this(piece);
        position[0]=x;
        position[1]=y;
    }

    public int[] getPosition() {
        return position;
    }
    public int getSkillTurn() {
        return skillTurn;
    }

    public void setPosition(int x, int y){
        position[0]=x;
        position[1]=y;
    }

    boolean selectPieceIn(Mouse mouse){
        areaIn.setArea(PP.rctx(position[0]), PP.rcty(position[1]), 100,100);
        return mouse.click(areaIn);
    }

    public boolean selectPieceOut(Mouse mouse, MouseCheckArea areas){
        return mouse.click(areas);
    }
    public Piece copy(){
        Piece p;
        switch(piece.kind()){
            case 0:
                p=new Piece(position[0],position[1],new King());
                break;
            case 1:
                p=new Piece(position[0],position[1],new Soldier());
                break;
            case 2:
                p=new Piece(position[0],position[1],new Dia());
                break;
            case 3:
                p=new Piece(position[0],position[1],new Spider());
                break;
            case 4:
                p=new Piece(position[0],position[1],new Sniper());
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + piece.kind());
        }
        return p;
    }

    public void skillPlus(){
        if(skillTurn<piece.getSkillMax())skillTurn++;
    }

    public void skillSet(int n){
        if(piece.getSkillMax()!=-1)skillTurn=n;
    }

    public void drawIn(Graphics g) {
        piece.drawIn(g,position);
    }
    public void drawOut(Graphics g, int[] position, int size) {
        piece.drawOut(g,position,size);
    }
    public void drawSkill(Graphics g){
        piece.drawSkill(g);
        int[] position;
        if(piece.getSelect())position= new int[]{50, 380};
        else position= new int[]{50, 460};
        if(piece.getSkillMax()==-1){g.drawString("PassiveSkill",position[0],position[1]+18);}
        else{
            if(skillTurn!=-1)g.drawString(skillTurn+"ターン/"+piece.getSkillMax()+"ターン",position[0],position[1]+18);
            else g.drawString(piece.getSkillMax()+"ターン",position[0],position[1]+18);
        }
    }
}
