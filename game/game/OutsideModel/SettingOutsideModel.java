package game.OutsideModel;

public class SettingOutsideModel extends OutsideModel{
    public SettingOutsideModel(){
        super();
        size=80;
        for(int i=0;i<4;i++){
            add(720,220+80*i);
            add(800,220+80*i);
        }
    }
}
