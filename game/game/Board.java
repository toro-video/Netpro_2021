package game;

import game.Mouse.*;
import game.OutsideModel.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Board {//盤面の情報を格納
    final static int size=5;
    Mouse mouse;
    AutoHashMap<Piece> Pieces;//自駒を格納
    AutoHashMap<Piece> EnemyPieces;//敵駒を格納
    int[][] position=new int[size][size];//size*sizeマス用意　マスに対応したPiesesの番号を入れる(敵駒はEnemyPieses+100)
    MouseCheckArea[][] areas = new MouseCheckArea[size][size];
    Outside[] outside=new Outside[2];
    BufferedImage img;

    Board(Mouse mouse){
        this.mouse=mouse;
        Pieces= new AutoHashMap<>();
        EnemyPieces=new AutoHashMap<>();
        for(int x=0;x<size;x++){
            for(int y=0;y<size;y++){
                position[x][y]=-1;
                areas[x][y]=new MouseCheckArea(PP.rctx(x),PP.rcty(y),100,100);
            }
        }
        outside[0]=new Outside( new MyOutsideModel());
        outside[1]=new Outside( new EnemyOutsideModel());
        try {
            img = ImageIO.read(new File("img/bg.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public MouseCheckArea getAreas(int x, int y){
        return  areas[x][y];
    }

    void addPiece(Piece piece){
        position[piece.position[0]][piece.position[1]]= Pieces.autoMapping(piece);
    }

    void removePiece(int i){
        if(i<100)position[Pieces.get(i).position[0]][Pieces.get(i).position[1]]= Pieces.autoRemoving(i);
        else position[EnemyPieces.get(i).position[0]][EnemyPieces.get(i).position[1]]= EnemyPieces.autoRemoving(i);
    }

    int pieceIn(int[] position){
        return this.position[position[0]][position[1]];
    }

    void movePosition(int num,int[] position){
        this.position[Pieces.get(num).position[0]][Pieces.get(num).position[1]] = -1;
        this.position[position[0]][position[1]] = num;
        consistPosition();
    }

    void tracePosition(int i,int j){
        this.position[Pieces.get(i).position[0]][Pieces.get(i).position[1]] = j;
        this.position[Pieces.get(j).position[0]][Pieces.get(j).position[1]] = i;
        consistPosition();
    }


    public void consistPosition(){
        for(int x=0;x<size;x++){
            for(int y=0;y<size;y++){
                int n=position[x][y];
                if(n>-1){
                    if(n<100){//自駒の描写
                        Pieces.get(n).setPosition(x,y);
                    }
                    else {//敵駒の描写
                        EnemyPieces.get(n-100).setPosition(x,y);
                    }
                }
            }
        }
    }


    public void allSkillPlus(){
        for(Piece piece:Pieces.values())piece.skillPlus();
    }

    public AutoHashMap<Piece> getEnemyPieces(){
        return EnemyPieces;
    }
    public int[][] getPosition() {
        return position;
    }
    public Outside getOutside(int n){
        return outside[n];
    }

    public void resetBoard(){
        Pieces.clear();
        EnemyPieces.clear();
        outside[0].pieces.clear();
        outside[0].areas.clear();
        outside[1].pieces.clear();
        outside[1].areas.clear();
        for(int x=0;x<size;x++){
            for(int y=0;y<size;y++){
                position[x][y]=-1;
            }
        }
    }






    void draw(Graphics g){
        drawBoard(g);
        drawPiece(g);
        for(Outside outside:this.outside)outside.draw(g,mouse);
    }
    void drawBoard(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage( img, null, 0, -30);
        g.setColor(Color.BLACK);
        for(int n=0;n<600;n+=100){
            g.drawLine(n+200,20,n+200,520);
            g.drawLine(200,n+20,700,n+20);
        }
    }
    void drawPiece(Graphics g){
        for(int x=0;x<size;x++){
            for(int y=0;y<size;y++){
                int n=position[x][y];
                if(n>-1){
                    if(n<100){//自駒の描写
                        g.setColor(Color.blue);
                        Pieces.get(n).drawIn(g);
                        if(mouse.stop(areas[x][y])||Pieces.get(n).piece.getSelect())Pieces.get(n).drawSkill(g);
                    }
                    else {//敵駒の描写
                        g.setColor(Color.red);
                        EnemyPieces.get(n-100).drawIn(g);

                        if(mouse.stop(areas[x][y])||EnemyPieces.get(n-100).piece.getSelect())EnemyPieces.get(n-100).drawSkill(g);
                    }
                }
            }
        }
    }
}
