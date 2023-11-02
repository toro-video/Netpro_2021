package game.PieceKind;


import game.BoardValidModel.DiaAttackValidModel;
import game.BoardValidModel.MoveValidModel;
import game.PP;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Dia  extends PieceBase {

    final static int kind = 2;
    static BufferedImage[] img =new BufferedImage[3];
    static String skillExp="斜め前方に攻撃可能";
    static int skillMax=-1;

    static{
        try {
            img[0] = ImageIO.read(new File("img/img_chara/dia.png"));
            img[1] = ImageIO.read(new File("img/img_chara/dia_enemy.png"));
            img[2]= ImageIO.read(new File("img/img_chara/dia_chosen.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Dia (){
        moveModel=new MoveValidModel();
        attackModel=new DiaAttackValidModel();
    }


    public PieceBase copy(){
        PieceBase p=new Dia();
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

