package client.model.boardModels;

import com.google.gson.annotations.Expose;

/**
 * Created by Eric on 25.05.2016.
 */
public class Throw {

    @Expose
    private Roll roll1;

    @Expose
    private Roll roll2;

//    private int rollId;

//    public int getRollId() {
//        return rollId;
//    }

//    public void setRollId(int rollId) {
//        this.rollId = rollId;
//    }

    public Roll getRoll1() {
        return roll1;
    }

    public void setRoll1(Roll roll1) {
        this.roll1 = roll1;
    }

    public Roll getRoll2() {
        return roll2;
    }

    public void setRoll2(Roll roll2) {
        this.roll2 = roll2;
    }

}
