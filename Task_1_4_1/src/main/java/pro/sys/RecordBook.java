package pro.sys;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Record book class.
 */
public class RecordBook {

    private final ArrayList<SemesterRecord> semesterRecords;

    /**
     * Record book constructor.
     */
    public RecordBook() {
        this.semesterRecords = new ArrayList<>();
    }

    /**
     * Record book constructor.
     *
     * @param semesterRecords semesters to be added to book.
     */
    public RecordBook(List<SemesterRecord> semesterRecords) {
        this.semesterRecords = new ArrayList<>(semesterRecords);
    }

    /**
     * Add semester record to book.
     *
     * @param semesterRecord semester record to add.
     */
    public void addSemesterRecord(SemesterRecord semesterRecord) {
        semesterRecords.add(semesterRecord);
    }

    /**
     * Could this student get honor diploma.
     *
     * @return {@code true} if they could, {@code false} otherwise.
     */
    public boolean canGetHonorDiploma() {
        Collection<Grade> finalGrades = getFinalGrades();

        Optional<Grade> qualificationWorkGrade = semesterRecords.stream()
            .flatMap(s -> s.getGradedCourses().stream())
            .filter(c -> c instanceof QualificationWork)
            .map(q -> q.grade).findFirst();

        return
            (4 * finalGrades.stream().filter(c -> (c == Grade.A)).toList().size()
                >= 3 * finalGrades.size())
                && finalGrades.stream().noneMatch(c -> c == Grade.C)
                && (qualificationWorkGrade.isEmpty() || qualificationWorkGrade.get() == Grade.A);
    }

    /**
     * Can this student get increased scolarship.
     *
     * @return {@code true} if they could, {@code false} otherwise.
     */
    public boolean canGetIncreasesScolarship() {
        return semesterRecords.isEmpty() || semesterRecords.get(semesterRecords.size() - 1)
            .grantsIncreasedScolarship();
    }

    /**
     * Can this student transfet to state funded tutition.
     *
     * @return {@code true} if they could, {@code false} otherwise.
     */
    public boolean canTransferToFundedTutition() {
        return semesterRecords.size() >= 2
            && Stream.of(
                semesterRecords.get(semesterRecords.size() - 1),
                semesterRecords.get(semesterRecords.size() - 2))
            .flatMap(s -> s.getGradedCourses().stream())
            .filter(c -> c instanceof ExamedCourse)
            .allMatch(c -> c.grade != Grade.C);
    }

    /**
     * Get average grade.
     *
     * @return double average grade.
     */
    public double getAverageGrade() {
        int sum = semesterRecords.stream()
            .flatMap(s -> s.getGradedCourses().stream())
            .map(c -> c.grade.grade)
            .reduce(0, Integer::sum);
        int count = semesterRecords.stream()
            .map(s -> s.getGradedCourses().size())
            .reduce(0, Integer::sum);
        return (double) sum / (double) count;
    }

    /**
     * Get diploma grades.
     *
     * @return {@code Collection&lt;Grade&gt;} containing diploma grades.
     */
    public Collection<Grade> getFinalGrades() {
        Map<String, Grade> gradeMap = new HashMap<>();

        for (SemesterRecord semesterRecord : semesterRecords) {
            for (GradedCourse gradedCourse : semesterRecord.getGradedCourses()) {
                gradeMap.put(gradedCourse.name, gradedCourse.grade);
            }
        }
        return gradeMap.values();
    }
}
