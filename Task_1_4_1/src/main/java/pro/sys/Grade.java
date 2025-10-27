package pro.sys;

/**
 * Grade enumeration.
 */
public enum Grade {
    /**
     * A mark.
     */
    A(5),
    /**
     * B mark.
     */
    B(4),
    /**
     * C mark.
     */
    C(3);

    /**
     * Int representation of grade.
     */
    public final int grade;

    Grade(int grade) {
        this.grade = grade;
    }

}
