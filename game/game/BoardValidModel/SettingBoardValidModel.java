package game.BoardValidModel;

public class SettingBoardValidModel extends BoardValidModel{
    public SettingBoardValidModel(){
        super();
        for(int i=0;i<5;i++){
            position[i][3]=true;
            position[i][4]=true;
        }
    }
}
