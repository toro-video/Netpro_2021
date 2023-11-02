package game;

import game.BoardValidModel.AttackValidModel;
import game.BoardValidModel.BoardValidModel;
import game.BoardValidModel.MoveValidModel;
import game.BoardValidModel.MyPieceValidModel;
import game.Mouse.Mouse;
import game.Mouse.MouseCheckArea;

import java.awt.*;

public class Play {
    Mouse mouse;
    public Board board;
    Client client;
    boolean start;
    boolean skill;
    int wilo;
    Piece selectPiece;
    MyPieceValidModel selectModel;
    MoveValidModel moveModel;
    AttackValidModel attackModel;
    MouseCheckArea skillArea;
    int skillPhase=-1;//未発動-1発動0発動済100
    final static public int skillFinish=100;
    final static public int turnFinish=101;

    Play(Mouse mouse,Board board){
        this.board=board;
        this.mouse=mouse;
        skill=false;
        wilo=0;
        //selectArea=new MouseCheckArea();
        selectModel=new MyPieceValidModel();
        moveModel=new MoveValidModel();
        attackModel=new AttackValidModel();
    }


    int connecting(int phase,int next){
        if(!start){
            wilo=0;
            client=new Client();
            board.allSkillPlus();
            client.sendObject.setSend(board.Pieces, board.EnemyPieces, board.position, board.outside, true,0);
            skillPhase=-1;
            client.start();
            start=true;

        }
        if(client.receive){
            SendObject rrObject=client.receiveObject.reverse();
            board.EnemyPieces=rrObject.EnemyPieces;
            for(int x = 0; x< Board.size; x++){
                for(int y = 0; y< Board.size; y++){
                    if(rrObject.position[x][y]>=100)board.position[x][y]=rrObject.position[x][y];
                }
            }
            client.turn=rrObject.turn;
            client.receive=false;
            if(client.turn)selectModel.setValid(board.position);
            start=false;
            return next;
        }
        return phase;
    }


    void myTurn(){
        int[] clickSelectPosition= selectModel.boardSelect(mouse,board);
        if(clickSelectPosition!=null){
            if(selectPiece!=null)selectPiece.piece.setSelect(false);
            selectPiece=board.Pieces.get(board.pieceIn(clickSelectPosition));
            selectPiece.piece.setSelect(true);
            moveModel=selectPiece.piece.setMoveModel(clickSelectPosition[0],clickSelectPosition[1], board.position);
            attackModel=selectPiece.piece.setAttackModel(clickSelectPosition[0],clickSelectPosition[1], board.position);
            if(skillPhase==-1&&selectPiece.piece.skillCheck(board,selectPiece))skillArea=new MouseCheckArea(40,400,100,18);
        }
        if(selectPiece!=null){
            int[] clickMovePosition= moveModel.boardSelect(mouse,board);
            int[] clickAttackPosition= attackModel.boardSelect(mouse,board);
            if(clickMovePosition!=null){
                boolean t=selectPiece.piece.move(board,selectPiece,clickMovePosition);
                if(!t)board.allSkillPlus();
                moveModel.resetPosition();
                attackModel.resetPosition();
                selectModel.resetPosition();
                if(skillArea!=null)mouse.clickEnd(skillArea);
                skillArea=null;
                mouse.clickEnd(board.areas);
                client.sendObject.setSend(board.Pieces, board.EnemyPieces, board.position, board.outside, t,wilo);
                client.send=true;
            }
            if(clickAttackPosition!=null){
                boolean t=selectPiece.piece.attack(board,clickAttackPosition);
                if(!t)board.allSkillPlus();
                moveModel.resetPosition();
                attackModel.resetPosition();
                selectModel.resetPosition();
                if(skillArea!=null)mouse.clickEnd(skillArea);
                skillArea=null;
                mouse.clickEnd(board.areas);
                wilo=checkWilo();
                client.sendObject.setSend(board.Pieces, board.EnemyPieces, board.position, board.outside, t,wilo);
                client.send=true;
            }
            if(skillArea!=null){
                if(mouse.click(skillArea))skillPhase=0;
            }
            if(skillPhase>-1&&skillArea!=null)skill();
        }
    }
    void enemyTurn(){
    }

    void readReceive(){
        SendObject rrObject=client.receiveObject.reverse();
        board.EnemyPieces=rrObject.EnemyPieces;
        board.position= rrObject.position;
        board.outside= rrObject.outside;
        wilo=rrObject.wilo;
        if(client.turn){
            selectModel.setValid(board.position);
            skillPhase=-1;
        }
    }

    void skill(){
        switch (skillPhase){
            case 0:
                moveModel.resetPosition();
                attackModel.resetPosition();
                selectModel.resetPosition();
                mouse.clickEnd(board.areas);
                skillPhase=1;
                break;
            case  skillFinish:
                skillArea=null;
                selectModel.setValid(board.position);
                selectPiece.piece.setSelect(false);
                wilo=checkWilo();
                client.sendObject.setSend(board.Pieces, board.EnemyPieces, board.position, board.outside, true,wilo);
                client.send=true;
                break;
            case  turnFinish:
                skillArea=null;
                board.allSkillPlus();
                selectPiece.piece.setSelect(false);
                wilo=checkWilo();
                client.sendObject.setSend(board.Pieces, board.EnemyPieces, board.position, board.outside, false,wilo);
                client.send=true;
                break;
            default:
                skillPhase=selectPiece.piece.skill(board,selectPiece,skillPhase);
                break;

        }
    }

    int checkWilo(){
        int wilo=3;
        for(Piece piece:board.Pieces.values()){
            if(piece.piece.kind()==0)wilo-=2;
        }
        for(Piece piece:board.EnemyPieces.values()){
            if(piece.piece.kind()==0)wilo-=1;
        }
        if(wilo<0)wilo=0;
        return wilo;
    }


    void draw(Graphics g){
        drawBoard(g);
        board.drawPiece(g);
        //for(Outside outside:board.outside)outside.draw(g,mouse);
        drawSkill(g);

    }
    void drawBoard(Graphics g){
        board.drawBoard(g);

        for(int x = 0; x< Board.size; x++){
            for(int y = 0; y< Board.size; y++){
                if(moveModel.getPosition()[x][y]){
                    g.setColor(Color.BLUE);
                    g.fillRect(PP.rctx(x),PP.rcty(y),100,100);
                }
                if(attackModel.getPosition()[x][y]){
                    g.setColor(Color.RED);
                    g.fillRect(PP.rctx(x),PP.rcty(y),100,100);
                }

            }
        }
    }
    void drawSkill(Graphics g){
        if(skillArea!=null) {
            g.setColor(Color.WHITE);
            g.drawRect(40,400,100,18);
            if(skillPhase==-1)g.drawString("スキル使用",50,416);
            else g.drawString("スキル使用中",50,416);
        }
    }
}
