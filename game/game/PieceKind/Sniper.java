package game.PieceKind;

import game.Board;
import game.BoardValidModel.MoveValidModel;
import game.BoardValidModel.NullAttackValidModel;
import game.PP;
import game.Piece;
import game.Play;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Sniper extends PieceBase {
    final static int kind = 4;
    static BufferedImage[] img =new BufferedImage[3];
    static String skillExp="3マス前方を攻撃";
    static int skillMax=4;

    static{
        try {
            img[0] = ImageIO.read(new File("img/img_chara/sniper.png"));
            img[1] = ImageIO.read(new File("img/img_chara/sniper_enemy.png"));
            img[2]= ImageIO.read(new File("img/img_chara/sniper_chosen.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Sniper(){
        moveModel=new MoveValidModel();
        attackModel=new NullAttackValidModel();
    }

    public int skill(Board board, Piece piece,int skillPhase) {//駒のスキルを使った時の処理
        int[] p=piece.getPosition();
        int n=board.getPosition()[p[0]][p[1]-3]-100;
        board.getEnemyPieces().get(n).skillSet(-1);
        board.getOutside(1).addPiece(board.getEnemyPieces().get(n));
        board.getPosition()[p[0]][p[1]-3]=-1;
        board.getEnemyPieces().remove(n);
        piece.skillSet(0);
        select=false;
        return Play.turnFinish;
    }

    public boolean skillCheck(Board board, Piece piece){
        if(piece.getSkillTurn()<skillMax)return false;
        if(piece.getPosition()[1]>2){
            if(board.getPosition()[piece.getPosition()[0]][piece.getPosition()[1]-3]>=100)return true;
        }
        return false;
    }

    public PieceBase copy(){
        PieceBase p=new Sniper();
        return p;
    }

    @Override
    public int getSkillMax() {
        return skillMax;
    }

    public int kind(){return kind;}

    public void drawIn(Graphics g, int[] position){
        Graphics2D g2d = (Graphics2D) g;
        if(PE){
            if(select)g2d.drawImage( img[2], null, PP.rctx(position[0]), PP.rcty(position[1]));
            else g2d.drawImage( img[0], null, PP.rctx(position[0]), PP.rcty(position[1]));
        }
        else g2d.drawImage( img[1], null, PP.rctx(position[0]), PP.rcty(position[1]));

    }

    public void drawOut(Graphics g, int[] position, int size){
        Graphics2D g2d = (Graphics2D) g;
        if(PE){
            if(select)g2d.drawImage( img[2], position[0], position[1],size,size,null,null);
            else g2d.drawImage( img[0],position[0], position[1],size,size,null,null);
        }
        else g2d.drawImage( img[1], position[0], position[1],size,size,null,null);
    }


    public void drawSkill(Graphics g) {
        int[] position;
        if(select)position= new int[]{40, 380};
        else position= new int[]{40, 460};
        g.setFont(f);
        g.setColor(Color.PINK);
        g.drawString(skillExp,position[0],position[1]);
    }

}


