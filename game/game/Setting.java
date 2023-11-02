package game;


import game.BoardValidModel.*;
import game.Mouse.Mouse;
import game.Mouse.MouseCheckArea;
import game.OutsideModel.*;
import game.PieceKind.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Setting{//初期配置に関する処理・ここを拡張すれば戦略性が増す
    Mouse mouse;
    Board board;
    SettingBoardValidModel model;
    Outside outside;
    PieceBase selectPiece;
    int boardPieceNum;
    MouseCheckArea save,remove;
    BufferedImage saveImg,removeImg;
    ArrayList<Piece> setPiece;
    boolean setFlag;



    Setting(Mouse mouse,Board board){
        this.mouse=mouse;
        this.board=board;
        model=new SettingBoardValidModel();
        outside=new Outside(new SettingOutsideModel());
        //board.position[2][4]=board.Pieces.outMapping(new Piece(2,4,new King()));
        outside.addPiece(new Piece(new Soldier()));
        outside.addPiece(new Piece(new Dia()));
        outside.addPiece(new Piece(new Spider()));
        outside.addPiece(new Piece(new Sniper()));
        boardPieceNum=-1;
        save=new MouseCheckArea(725,100,70,70);
        remove=new MouseCheckArea(805,100,70,70);
        try {
            saveImg=ImageIO.read(new File("img/save_button.png"));
            removeImg=ImageIO.read(new File("img/close_button.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setPiece=new ArrayList<>();
        setPiece.add(new Piece(2,4,new King()));
        board.position[2][4]=board.Pieces.autoMapping(setPiece.get(0).copy());
        setPiece.add(new Piece(0,4,new Soldier()));
        board.position[0][4]=board.Pieces.autoMapping(setPiece.get(1).copy());
        setPiece.add(new Piece(4,4,new Sniper()));
        board.position[4][4]=board.Pieces.autoMapping(setPiece.get(2).copy());
        setPiece.add(new Piece(1,3,new Spider()));
        board.position[1][3]=board.Pieces.autoMapping(setPiece.get(3).copy());
        setPiece.add(new Piece(3,3,new Dia()));
        board.position[3][3]=board.Pieces.autoMapping(setPiece.get(4).copy());
        setFlag=false;
    }


    int setting(int current,int next){//駒の初期配置設定
        PieceBase clickOutside=outside.selectPiece(mouse);
        if(clickOutside!=null){
            boardPieceNum=-1;
            if(selectPiece!=null){
                if(clickOutside==selectPiece){
                    selectPiece.setSelect(false);
                    selectPiece=null;
                }
                else {
                    selectPiece.setSelect(false);
                    selectPiece=clickOutside;
                    selectPiece.setSelect(true);
                }
            }
            else {
                selectPiece=clickOutside;
                selectPiece.setSelect(true);
            }
        }
        int[] clickPosition= model.boardSelect(mouse,board);
        if(clickPosition!=null){
            int i = board.pieceIn(clickPosition);
            if(selectPiece!=null){
                switch (i){
                    case -1:
                        if(boardPieceNum>0){
                            board.movePosition(boardPieceNum,clickPosition);
                            boardPieceNum=-1;
                        }
                        else
                            if(board.Pieces.size()<5)board.addPiece(new Piece(clickPosition[0],clickPosition[1],selectPiece));
                        selectPiece.setSelect(false);
                        selectPiece=null;
                        break;
                    case 0:
                        break;
                    default :
                        if(boardPieceNum>0){
                            if (boardPieceNum != i) {
                                board.tracePosition(i, boardPieceNum);
                            }
                            selectPiece.setSelect(false);
                            selectPiece=null;
                            boardPieceNum=-1;
                        }
                        else if(board.Pieces.get(i).piece.kind()!=selectPiece.kind()){
                            board.Pieces.remove(i);
                            board.addPiece(new Piece(clickPosition[0],clickPosition[1],selectPiece));
                            selectPiece.setSelect(false);
                            selectPiece=null;
                        }
                        break;
                }
            }
            else{
                switch (i){
                    case -1:
                    case 0:
                        break;
                    default :
                        selectPiece=board.Pieces.get(i).piece;
                        selectPiece.setSelect(true);
                        boardPieceNum=i;
                        break;
                }
            }
        }
        if(mouse.click(remove)&&boardPieceNum>0){
            board.removePiece(boardPieceNum);
            selectPiece.setSelect(false);
            selectPiece=null;
        }
        if(mouse.click(save)){
            mouse.clickEnd(save);
            mouse.clickEnd(remove);
            mouse.clickEnd(outside.areas);
            mouse.clickEnd(board.areas);
            setPiece.clear();
            if(selectPiece!=null){
                selectPiece.setSelect(false);
                selectPiece=null;
            }
            for(Piece piece:board.Pieces.values()){
                setPiece.add(piece.copy());
            }
            setFlag=false;
            return next;
        }
        return current;
    }

    void restart(){
        board.resetBoard();
        for(Piece piece:setPiece){
            board.position[piece.position[0]][piece.position[1]]=board.Pieces.autoMapping(piece.copy());
        }
    }
    void draw(Graphics g){
        board.drawBoard(g);
        board.drawPiece(g);
        outside.draw(g,mouse);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage( saveImg,  725, 100,70,70,null,null);
        g2d.drawImage( removeImg,  805, 100,70,70,null,null);
    }
}
