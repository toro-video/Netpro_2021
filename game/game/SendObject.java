package game;


import game.Mouse.AutoHashMap;
import game.OutsideModel.EnemyOutsideModel;
import game.OutsideModel.MyOutsideModel;

import java.io.Serializable;

public class SendObject  implements Serializable {
    final static int size=5;
    AutoHashMap<Piece> Pieces;//自駒を格納
    AutoHashMap<Piece> EnemyPieces;//敵駒を格納
    int[][] position;
    Outside[] outside=new Outside[2];
    boolean turn;
    public int wilo=0;//0勝負未定1勝利2敗北3引き分け



    void setSend(AutoHashMap<Piece> Pieces, AutoHashMap<Piece> EnemyPiece, int[][] position, Outside[] outside, boolean turn, int wilo){
        this.Pieces=Pieces;
        this.EnemyPieces=EnemyPiece;
        this.position=position;
        this.outside=outside;
        this.turn=turn;
        this.wilo=wilo;
    }

    SendObject reverse(){
        SendObject reverse=new SendObject();
        reverse.Pieces=EnemyPieces;
        reverse.EnemyPieces=Pieces;

        for(Piece piece:reverse.Pieces.values()){
            piece.piece.setP();
        }
        for(Piece piece:reverse.EnemyPieces.values()){
            piece.piece.setE();
        }

        reverse.position= reversePosition(position);
        reverse.consistPosition();
        reverse.outside=reverseOutside(outside);
        reverse.turn=!turn;
        if(wilo%3==0)reverse.wilo=wilo;
        else reverse.wilo=3-wilo;
        return reverse;
    }

    int[][] reversePosition(int [][] position){
        int[][] reverse=new int[size][size];
        for(int x=0;x<size;x++){
            for(int y=0;y<size;y++){
                reverse[x][y]=-1;
            }
        }
        for(int x=0;x<size;x++){
            for(int y=0;y<size;y++){
                if(position[x][y]>-1){
                    if(position[x][y]<100)reverse[4-x][4-y]=position[x][y]+100;
                    else reverse[4-x][4-y]=position[x][y]-100;
                }
            }
        }
        return reverse;
    }

    Outside[] reverseOutside(Outside[] outside){
        Outside[] reverse=new Outside[2];
        reverse[0]=new Outside(new MyOutsideModel());
        reverse[1]=new Outside(new EnemyOutsideModel());
        for(Piece p:outside[1].pieces){
            p.piece.setP();
            reverse[0].addPiece(p);
        }
        for(Piece p:outside[0].pieces){
            p.piece.setE();
            reverse[1].addPiece(p);
        }

        return reverse;
    }

    void consistPosition(){
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
}
