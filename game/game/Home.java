package game;

import game.Mouse.Mouse;
import game.Mouse.MouseCheckArea;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Home {
    BufferedImage img;
    Mouse mouse;
    ArrayList<MouseCheckArea> area=new ArrayList<>();
    Home(Mouse mouse) {
        try {
            img = ImageIO.read(new File("img/bg_home.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.mouse=mouse;
        area.add(new MouseCheckArea(488,172,358,160));
        area.add(new MouseCheckArea(54,172,900,160));
    }

    int nextPhase(int current,int next1,int next2){
        for(int i=0;i<area.size();i++){
            if (mouse.click(area.get(i))) {
                mouse.clickEnd(area);
                if(i==0)return next1;
                if(i==1)return next2;
            }
        }
        return current ;
    }


    void draw(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage( img, null, 0, -30);
    }
}
