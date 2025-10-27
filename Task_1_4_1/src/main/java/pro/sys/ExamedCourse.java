package pro.sys;

/**
 * Examed course class.
 */
public class ExamedCourse extends GradedCourse {

    /**
     * Examed course constructor.
     *
     * @param name string name of the course.
     * @param grade exam grade.
     */
    public ExamedCourse(String name, Grade grade) {
        super(name, grade);
    }
}
