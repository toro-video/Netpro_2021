package game;

import game.Mouse.Mouse;

import javax.swing.*;
import java.io.IOException;

public class Game extends Phase{//main


    public static void main(String[] args){
        Mouse mouse=new Mouse();
        Board board=new Board(mouse);
        Title title=new Title(mouse);
        Home home=new Home(mouse);
        Win win=new Win(mouse);
        Lose lose=new Lose(mouse);
        Setting setting=new Setting(mouse,board);
        Play play=new Play(mouse,board);
        Draw draw=new Draw(mouse,board,title,home,setting,play,win,lose);
        draw.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        draw.setSize( 900, 600 );
        draw.setVisible( true );




        while(true){
            switch(phase){
                case Title_Phase:
                    phase=title.nextPhase(phase,Home_Phase);
                    break;
                case Home_Phase:
                    phase=home.nextPhase(phase,Connecting_Phase,Setting_Phase);
                    break;
                case Setting_Phase:
                    phase=setting.setting(phase,Home_Phase);
                    break;
                case Connecting_Phase:
                    phase=play.connecting(phase,Play_Phase);
                    break;
                case Play_Phase:

                    if(play.client.turn)play.myTurn();
                    else play.enemyTurn();
                    if(play.client.receive){
                        play.readReceive();
                        play.client.receive=false;
                    }
                    if(play.wilo!=0){
                        phase+= play.wilo;

                    }
                    break;
                case Win_Phase:
                    if(play.client.loop)play.client.loop=false;
                    if(!play.client.socket.isClosed()) {
                        try {
                            play.client.socket.shutdownInput();
                            play.client.socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    phase=win.nextPhase(phase,Reset_Phase);;
                    break;
                case Lose_Phase:
                    if(play.client.loop)play.client.loop=false;
                    if(!play.client.socket.isClosed()) {
                        try {
                            play.client.socket.shutdownInput();
                            play.client.socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    phase=lose.nextPhase(phase,Reset_Phase);;
                    break;
                case Reset_Phase:
                    System.out.println("Reset");
                    setting.restart();
                    phase=Home_Phase;
                    break;
            }
            draw.setPhase(phase);
            draw.repaint();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
