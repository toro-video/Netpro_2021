package game.OutsideModel;

public class EnemyOutsideModel extends OutsideModel {
    public EnemyOutsideModel(){
        super();
        size=80;
        for(int i=0;i<4;i++){
            add(20,70+80*i);
            add(100,70+80*i);
        }

    }
}
