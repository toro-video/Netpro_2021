package game;

import game.Mouse.Mouse;
import game.Mouse.MouseCheckArea;
import game.OutsideModel.OutsideModel;
import game.PieceKind.PieceBase;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class Outside implements Serializable {
    ArrayList<Piece> pieces=new ArrayList<>();
    ArrayList<MouseCheckArea> areas=new ArrayList<>();
    OutsideModel model;
    Outside(OutsideModel model){
        this.model=model;
    }

    public void addPiece(Piece piece){
        pieces.add(piece);
        piece.setPosition(model.getX().get(pieces.size()-1),model.getY().get(pieces.size()-1));
        areas.add(new MouseCheckArea(model.getX().get(pieces.size()-1),model.getY().get(pieces.size()-1),model.getSize(),model.getSize()));
    }

    PieceBase selectPiece(Mouse mouse){
        for(Piece piece:pieces){
            if(piece.selectPieceOut(mouse,areas.get(pieces.indexOf(piece))))return piece.piece;
        }
        return null;
    }

    void removePiece(){

    }

    void draw(Graphics g,Mouse mouse){
        for(int i=0;i<pieces.size();i++){
            int[] position=new int[2];
            position[0]=model.getX().get(i);
            position[1]=model.getY().get(i);
            pieces.get(i).drawOut(g,position, model.getSize());
            if (mouse.stop(areas.get(i))||pieces.get(i).piece.getSelect()){
                pieces.get(i).drawSkill(g);
            }
        }
    }
}
