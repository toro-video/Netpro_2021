package game.Mouse;

import java.io.Serializable;

public class MouseCheckArea implements Serializable {
    int[] area;
    boolean clickFlag;
    int key;

    public MouseCheckArea(int x,int y,int width,int height){
        area=new int[]{x,y,width,height};
        clickFlag=false;
        key=-1;
    }
    public MouseCheckArea(){
        this(0,0,900,600);
    }

    public void setArea(int x,int y,int width,int height){
        area[0]=x;
        area[1]=y;
        area[2]=width;
        area[3]=height;
    }
    boolean checkFlag(){
        if(clickFlag){
            clickFlag=false;
            return true;
        }
        return false;
    }
}
