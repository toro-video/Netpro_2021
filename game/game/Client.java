package game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

public class Client extends Thread{//クライアント
    static InetSocketAddress socketAddress= new InetSocketAddress("localhost", 10000);
    Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    SendObject sendObject;
    SendObject receiveObject;
    boolean send,receive,turn,loop;
    Client() {
        socket = new Socket();
        sendObject=new SendObject();
        receiveObject=new SendObject();
        send=false;
        receive=false;
        turn=false;
        loop=true;
    }

    public void run(){
        
        try {
            socket.connect(socketAddress, 10000);
            oos=new ObjectOutputStream(socket.getOutputStream());
            ois=new ObjectInputStream(socket.getInputStream());

            turn=ois.readBoolean();//先行はtrue,後攻はfalse
            oos.reset();
            oos.writeObject(sendObject);
            oos.flush();
            receiveObject = (SendObject) ois.readObject();
            receiveObject.turn=!turn;
            receive=true;

            while(loop) {
                if(turn){
                    if(send){//sendObjectを更新した時にsendもtrueになる
                        oos.reset();
                        oos.writeObject(sendObject);
                        oos.flush();
                        turn=sendObject.turn;
                        send=false;
                    }
                }
                else{
                    if(!socket.isClosed()) {
                        receiveObject = (SendObject) ois.readObject();
                        turn=!receiveObject.turn;
                        receive=true;
                    }
                }
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        } catch (SocketException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            /*if(!socket.isClosed()) {
                try {
                    socket.shutdownInput();
                    socket.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                System.out.println("close");
            }
            Game.phase=Game.Title_Phase;*/
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
