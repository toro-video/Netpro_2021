package game;



import game.Mouse.Mouse;

import javax.swing.*;
import java.awt.*;


public class Draw extends JFrame {//描画
    DrawPanel drawPanel;
    Draw(Mouse mouse, Board board, Title title, Home home, Setting setting,Play play,Win win,Lose lose){
        drawPanel=new DrawPanel(board,title,home,setting,play,win,lose);
        JPanel panel=(JPanel)getContentPane();
        panel.add(drawPanel);
        drawPanel.addMouseListener(mouse);
        drawPanel.addMouseMotionListener(mouse);
    }

    void setDrawPlay(Play play){
        drawPanel.setDrawPlay(play);
    }

    void setPhase(int phase){
        drawPanel.phase= phase;
    }



    class DrawPanel extends JPanel { // 描画用

        Board board;
        Title title;
        Home home;
        Win win;
        Lose lose;
        Setting setting;
        Play play;
        int phase=0;

        DrawPanel(Board board, Title title, Home home,Setting setting,Play play, Win win,Lose lose) {
            this.board=board;
            this.title=title;
            this.home=home;
            this.setting=setting;
            this.play=play;
            this.win=win;
            this.lose=lose;
        }

        void setDrawPlay(Play play){
            this.play=play;
        }


        public void paintComponent(Graphics g) { // ここに描画したい内容を書く
            super.paintComponent(g); // 背景を再描画するかどうか
            switch(phase){
                case Phase.Title_Phase:
                    title.draw(g);
                    break;
                case Phase.Home_Phase:
                    home.draw(g);
                    break;
                case Phase.Setting_Phase:
                    setting.draw(g);
                    break;

                case Phase.Connecting_Phase:
                    board.draw(g);
                    break;
                case Phase.Play_Phase:
                    play.draw(g);
                    break;
                case Phase.Win_Phase:
                    win.draw(g);
                    break;
                case Phase.Lose_Phase:
                    lose.draw(g);
                    break;
                case Phase.Reset_Phase:
                    home.draw(g);
                    break;
            }

        }
    }


}
