package pro.sys;

/**
 * Graded course class.
 */
public class GradedCourse extends Course {

    /**
     * Course grade.
     */
    public final Grade grade;

    /**
     * Graded course contstructor.
     *
     * @param name string name of course.
     * @param grade course grade.
     */
    public GradedCourse(String name, Grade grade) {
        super(name);
        this.grade = grade;
    }
}
