package client.model.boardModels;

import com.google.gson.annotations.Expose;

/**
 * Created by Eric on 25.05.2016.
 */
//@Entity
//@Table(name = "Throw")
public class Throw {

   // @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    int rollId;

    @Expose
    //@Column(name = "Roll1")
    Roll roll1;

    @Expose
    //@Column(name = "Roll2")
    Roll roll2;

    public int getRollId() {
        return rollId;
    }

    public void setRollId(int rollId) {
        this.rollId = rollId;
    }

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
