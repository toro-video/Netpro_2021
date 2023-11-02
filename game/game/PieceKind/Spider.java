package game.PieceKind;


import game.BoardValidModel.AttackValidModel;
import game.BoardValidModel.SpiderAttackValidModel;
import game.BoardValidModel.SpiderMoveValidModel;
import game.PP;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Spider  extends PieceBase {

    final static int kind = 3;
    static BufferedImage[] img =new BufferedImage[3];
    static String skillExp="最奥で斜め後方攻撃可";
    static int skillMax=-1;

    static{
        try {
            img[0] = ImageIO.read(new File("img/img_chara/spider.png"));
            img[1] = ImageIO.read(new File("img/img_chara/spider_enemy.png"));
            img[2]= ImageIO.read(new File("img/img_chara/spider_chosen.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Spider (){
        moveModel=new SpiderMoveValidModel();
        attackModel=new SpiderAttackValidModel();
    }


    public PieceBase copy(){
        PieceBase p=new Spider();
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

