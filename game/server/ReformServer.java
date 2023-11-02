package server;

import game.SendObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ReformServer {
    private static final int maxConnection=4;
    private static ReformThread[] reformThreads;
    private static ObjectInputStream[] ois;
    private static ObjectOutputStream[] oos;


    public static void main(String[] args){
        reformThreads = new ReformThread[maxConnection];
        ois = new ObjectInputStream[maxConnection];
        oos = new ObjectOutputStream[maxConnection];

        try {
            ServerSocket server = new ServerSocket(10000);
            System.out.println("server is waiting present at 10000");

            int n=0;
            while (true) {

                Socket socket=server.accept();
                System.out.println("接続:" + n);
                oos[n] = new ObjectOutputStream(socket.getOutputStream());
                ois[n] =  new ObjectInputStream(socket.getInputStream());
                reformThreads[n] = new ReformThread(n, socket,oos[n],ois[n]);
                reformThreads[n] .start();
                n=(n+1)%maxConnection;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static int searchMatch(int Num){
        for(int i=0;i<maxConnection;i++){
            if(reformThreads[i]!=null){
                if(reformThreads[i].getEnemyNum()==-1&&reformThreads[i].getMyNum()>-1&&reformThreads[i].getMyNum()!=Num){


                    return reformThreads[i].getMyNum();
                }
            }
        }
        return -1;
    }
    
    public static void matching(int Num1,int Num2){
        reformThreads[Num2].setEnemyNum(Num1);
    }

    //Objectを送信
    public static void sendObject(SendObject obj,int num){
        try {
            oos[num].reset();
            oos[num].writeObject(obj);
            oos[num].flush();
        }
        catch (Exception e) {

        }
    }

}

class ReformThread extends Thread {
    private  int myNum;
    private int enemyNum;
    private final Socket socket;
    private final ObjectInputStream ois;
    private final ObjectOutputStream oos;


    ReformThread(int num,Socket socket, ObjectOutputStream oos,ObjectInputStream ois) {
        myNum = num;
        enemyNum = -1;
        this.socket=socket;
        this.oos = oos;
        this.ois = ois;
    }

    public int getMyNum() {
        return myNum;
    }
    public int getEnemyNum() {
        return enemyNum;
    }

    public void setEnemyNum(int enemyNum) {
        this.enemyNum = enemyNum;

    }

    public void run(){
        //先攻後攻 ランダムは後で
        try {
            while(enemyNum==-1){
                if(ReformServer.searchMatch(myNum)>-1){
                    enemyNum=ReformServer.searchMatch(myNum);
                    ReformServer.matching(myNum,enemyNum);
                }
                Thread.sleep(5);
            }
            oos.reset();
            oos.writeBoolean(myNum<enemyNum);
            oos.flush();

            Thread.sleep(5);

            while(myNum>-1) {
                //Objectを受信
                SendObject obj= (SendObject)ois.readObject();
                //if(obj!=null){
                ReformServer.sendObject(obj,enemyNum);
                //}
            }
            socket.close();
        } catch (Exception e) {
            System.out.println("切断:"+myNum);
            myNum=-1;
            try {
                Thread.sleep(10);
                socket.shutdownInput();
                socket.shutdownOutput();
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
