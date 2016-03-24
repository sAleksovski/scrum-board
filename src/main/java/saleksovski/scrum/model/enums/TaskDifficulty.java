package saleksovski.scrum.model.enums;

/**
 * Created by stefan on 3/13/16.
 */
public enum TaskDifficulty {
    _0(0),
    _1(1),
    _2(2),
    _3(3),
    _5(5),
    _8(8),
    _13(13),
    _21(21),
    _34(34),
    _55(55),
    _89(89);

    private int numVal;

    TaskDifficulty(int numVal) {
        this.numVal = numVal;
    }

    public int getNumVal() {
        return numVal;
    }
}
