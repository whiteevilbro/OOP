package pro.sys;

import java.util.ArrayList;
import java.util.List;

/**
 * Semester record class.
 */
public class SemesterRecord {

    private final ArrayList<Course> courses;

    /**
     * Semester record constructor.
     */
    public SemesterRecord() {
        this.courses = new ArrayList<>();
    }

    /**
     * Semester record constructor.
     *
     * @param courses list of courses in created semester.
     */
    public SemesterRecord(List<Course> courses) {
        this.courses = new ArrayList<>(courses);
    }

    /**
     * Adds course to the semester.
     *
     * @param course course to add to the semester.
     */
    public void addCourse(Course course) {
        courses.add(course);
    }

    /**
     * Get semester courcses.
     *
     * @return {@code List&lt;Course&gt;} containing courses of this semester.
     */
    public List<Course> getCourses() {
        return courses;
    }

    /**
     * Get graded cources.
     *
     * @return {@code List&lt;Course&gt;} containing graded courses of this semester.
     */
    public List<GradedCourse> getGradedCourses() {
        return courses.stream()
            .filter(c -> c instanceof GradedCourse)
            .map(c -> (GradedCourse) c)
            .toList();
    }

    /**
     * Does this semester grand increased scolarship.
     *
     * @return {@code true} if it does, {@code false} otherwise.
     */
    public boolean grantsIncreasedScolarship() {
        return courses.stream().allMatch(
            c ->  !(c instanceof GradedCourse) || ((GradedCourse) c).grade == Grade.A);
    }

}
