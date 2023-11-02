package game;

import game.Mouse.Mouse;
import game.Mouse.MouseCheckArea;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Title {
    BufferedImage img;
    Mouse mouse;
    MouseCheckArea area;
    Title(Mouse mouse) {
        try {
            img = ImageIO.read(new File("img/bg_home.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.mouse=mouse;
        area=new MouseCheckArea();
    }

    int nextPhase(int current,int next){
       if (mouse.click(area)){
           mouse.clickEnd(area);
           return next;
       }
       return current;
    }

    void draw(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage( img, null, 0, 0);
    }
}
