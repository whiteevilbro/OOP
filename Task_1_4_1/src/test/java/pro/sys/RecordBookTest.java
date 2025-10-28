package pro.sys;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;


class RecordBookTest {

    @Test
    void canGetHonorDiploma() {
        ArrayList<Course> courses1 = new ArrayList<>();
        courses1.add(new ExamedCourse("1", Grade.A));
        courses1.add(new ExamedCourse("2", Grade.A));
        courses1.add(new TestedCourse("3", Grade.C));

        ArrayList<Course> courses2 = new ArrayList<>();
        courses2.add(new ExamedCourse("4", Grade.A));
        courses2.add(new TestedCourse("3", Grade.B));

        SemesterRecord semester1 = new SemesterRecord(courses1);
        SemesterRecord semester2 = new SemesterRecord(courses2);

        RecordBook book = new RecordBook(Stream.of(semester1, semester2).toList());
        assertTrue(book.canGetHonorDiploma());
    }

    @Test
    void cantGetHonorDiploma() {
        ArrayList<Course> courses1 = new ArrayList<>();
        courses1.add(new ExamedCourse("1", Grade.A));
        courses1.add(new ExamedCourse("2", Grade.A));
        courses1.add(new TestedCourse("3", Grade.C));

        ArrayList<Course> courses2 = new ArrayList<>();
        courses2.add(new ExamedCourse("4", Grade.A));
        courses2.add(new TestedCourse("3", Grade.B));
        courses2.add(new QualificationWork("7", Grade.B));

        SemesterRecord semester1 = new SemesterRecord(courses1);
        SemesterRecord semester2 = new SemesterRecord(courses2);

        RecordBook book = new RecordBook(Stream.of(semester1, semester2).toList());
        assertFalse(book.canGetHonorDiploma());
    }

    @Test
    void canGetIncreasesScolarship() {
        RecordBook book = new RecordBook();
        assertTrue(book.canGetIncreasesScolarship());

        book.addSemesterRecord(new SemesterRecord());
        assertTrue(book.canGetIncreasesScolarship());

        SemesterRecord semesterRecord = new SemesterRecord();
        semesterRecord.addCourse(new ExamedCourse("1", Grade.A));
        book.addSemesterRecord(semesterRecord);
        assertTrue(book.canGetIncreasesScolarship());

        semesterRecord.addCourse(new ExamedCourse("2", Grade.C));
        book.addSemesterRecord(semesterRecord);
        assertFalse(book.canGetIncreasesScolarship());
    }

    @Test
    void canTransferToFundedTutition() {
        RecordBook book = new RecordBook();
        assertFalse(book.canTransferToFundedTutition());

        book.addSemesterRecord(new SemesterRecord());
        assertFalse(book.canTransferToFundedTutition());

        SemesterRecord failed = new SemesterRecord();
        failed.addCourse(new ExamedCourse("1", Grade.C));

        book.addSemesterRecord(failed);
        assertFalse(book.canTransferToFundedTutition());

        SemesterRecord passed = new SemesterRecord();
        passed.addCourse(new ExamedCourse("2", Grade.A));
        passed.addCourse(new TestedCourse("3", Grade.C));
        book.addSemesterRecord(passed);
        book.addSemesterRecord(passed);
        assertTrue(book.canTransferToFundedTutition());
    }

    @Test
    void getAverageGrade() {
        ArrayList<Course> courses1 = new ArrayList<>();
        courses1.add(new ExamedCourse("1", Grade.A));
        courses1.add(new ExamedCourse("2", Grade.A));
        courses1.add(new TestedCourse("3", Grade.C));

        ArrayList<Course> courses2 = new ArrayList<>();
        courses2.add(new ExamedCourse("4", Grade.A));
        courses2.add(new TestedCourse("3", Grade.B));

        SemesterRecord semester1 = new SemesterRecord(courses1);
        SemesterRecord semester2 = new SemesterRecord(courses2);

        RecordBook book = new RecordBook(Stream.of(semester1, semester2).toList());
        assertEquals((double) (5 + 5 + 3 + 5 + 4) / (double) 5, book.getAverageGrade());
    }

    @Test
    void getFinalGrades() {
        ArrayList<Course> courses1 = new ArrayList<>();
        courses1.add(new ExamedCourse("1", Grade.A));
        courses1.add(new ExamedCourse("2", Grade.A));
        courses1.add(new TestedCourse("3", Grade.C));

        ArrayList<Course> courses2 = new ArrayList<>();
        courses2.add(new ExamedCourse("3", Grade.A));
        courses2.add(new TestedCourse("2", Grade.B));
        courses2.add(new ExamedCourse("4", Grade.C));

        SemesterRecord semester1 = new SemesterRecord(courses1);
        SemesterRecord semester2 = new SemesterRecord(courses2);

        RecordBook book = new RecordBook(Stream.of(semester1, semester2).toList());

        assertEquals(2, book.getFinalGrades().stream().filter(g -> g == Grade.A).count());
        assertEquals(1, book.getFinalGrades().stream().filter(g -> g == Grade.B).count());
        assertEquals(1, book.getFinalGrades().stream().filter(g -> g == Grade.C).count());
    }
}