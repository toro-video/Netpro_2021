package game;

import java.util.ArrayList;

public class PP {
    public static boolean inPoint(int pointX, int pointY, int areaX, int areaY, int width, int height){
        return pointX>areaX&&pointX<areaX+width&&pointY>areaY&&pointY<areaY+height;
    }

    public static boolean inPoint(int pointX, int pointY, int[] area){
        return inPoint(pointX, pointY, area[0],area[1], area[2],area[3]);
    }
    int ctx(int x){return (x-200)/100;}
    int cty(int y){return (y-20)/100;}

    public static int rctx(int x){return x*100+200;}
    public static int rcty(int y){return y*100+20;}

    public static ArrayList<int[]> around(int x, int y){
        ArrayList<int[]> ans=new ArrayList<>();
        if(x>0&&y>0)ans.add(new int[]{x-1,y-1});
        if(y>0)ans.add(new int[]{x,y-1});
        if(x+1<Board.size&&y>0)ans.add(new int[]{x+1,y-1});
        if(x>0)ans.add(new int[]{x-1,y});
        if(x+1<Board.size)ans.add(new int[]{x+1,y});
        if(x>0&&y+1<Board.size)ans.add(new int[]{x-1,y+1});
        if(y+1<Board.size)ans.add(new int[]{x,y+1});
        if(x+1<Board.size&&y+1<Board.size)ans.add(new int[]{x+1,y+1});

        return ans;
    }

    public static ArrayList<int[]> around(int[] p){
        return around(p[0],p[1]);
    }
}
