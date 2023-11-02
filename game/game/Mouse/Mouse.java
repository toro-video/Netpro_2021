package game.Mouse;

import game.PP;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.List;

public class Mouse extends MouseInputAdapter implements ActionListener, Serializable {
    public static AutoHashMap<MouseCheckArea> areasMap= new AutoHashMap<>();
    MouseStop mouseStop=new MouseStop(1000,this);


    public void clickReset(){
        areasMap.clear();
    }

    public void clickEnd(MouseCheckArea area){
        area.clickFlag=false;
        areasMap.remove(area.key);
        area.key=-1;
    }

    public void clickEnd(List<MouseCheckArea> areas){
        for(MouseCheckArea area:areas){
            clickEnd(area);
        }
    }

    public void clickEnd(MouseCheckArea[][] areas){
        for(int x=0;x< areas.length;x++){
            for(int y=0;y<areas[x].length;y++){
                clickEnd(areas[x][y]);
            }
        }
    }


    public boolean click(MouseCheckArea area){
        if(area.key==-1)area.key=areasMap.autoMapping(area);
        return area.checkFlag();
    }

    public void mouseClicked(MouseEvent e){
        for(MouseCheckArea area:areasMap.values()){
            area.clickFlag=PP.inPoint(e.getX(),e.getY(),area.area);
        }
    }



    public boolean stop(MouseCheckArea area){
        if(mouseStop.stop){
            return PP.inPoint(mouseStop.MouseX, mouseStop.MouseY, area.area);
        }
        return false;
    }




    public void mouseMoved(MouseEvent e){
        mouseStop.stop();
        mouseStop.stop=false;
        mouseStop.MouseX=e.getX();
        mouseStop.MouseY=e.getY();
        mouseStop.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        mouseStop.stop=true;
    }

    class MouseStop extends Timer{
        int MouseX,MouseY;
        boolean stop;
        public MouseStop(int delay, ActionListener listener) {
            super(delay, listener);
        }
    }
}
