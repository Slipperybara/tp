package seedu.address.model.participation;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.person.Attendance;
import seedu.address.model.person.Person;
import seedu.address.model.tutorial.Tutorial;

/**
 * Represents participation of a student in a tutorial
 */
public class Participation {
    private final Person student;
    private final Tutorial tutorial;
    private final List<Attendance> attendanceList;


    /**
     * Every field must be present and not null
     * @param student Person object to be added
     * @param tutorial Tutorial object to be added
     */
    public Participation(Person student, Tutorial tutorial) {
        requireAllNonNull(student, tutorial);
        this.student = student;
        this.tutorial = tutorial;
        this.attendanceList = new ArrayList<>();
    }


    /**
     * Overloads constructor to include attendance list
     * @param student Person object to be added
     * @param tutorial Tutorial object to be added
     * @param attendanceList List of Attendance objects
     */
    public Participation(Person student, Tutorial tutorial, List<Attendance> attendanceList) {
        requireAllNonNull(student, tutorial, attendanceList);
        this.student = student;
        this.tutorial = tutorial;
        this.attendanceList = attendanceList;
    }

    /**
     * @return a copy of the student
     */
    public Person getStudent() {
        return student.copy();
    }

    /**
     * @return a copy of the tutorial
     */
    public Tutorial getTutorial() {
        return tutorial.copy();
    }
    public String getTutorialSubject() {
        return tutorial.getSubject();
    }

    /**
     * ensures the immutability of the class
     * @return a new List of attendance
     */
    public List<Attendance> getAttendanceList() {
        return attendanceList;
    }

    /**
     * Returns true if both participation are of the same subject.
     * This defines a weaker notion of equality between two tutorials.
     */
    public boolean isSameParticipation(Participation otherParticipation) {
        if (otherParticipation == this) {
            return true;
        }
        return otherParticipation != null && otherParticipation.getTutorial().equals(getTutorial())
                                        && otherParticipation.getStudent().equals(getStudent());
    }

    /**
     * @return this (not a copy)
     */
    public Participation copy() {
        return this;
    }

    @Override
    public String toString() {
        return String.format("Attends: %s", tutorial.toString());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Participation)) {
            return false;
        }

        Participation otherParticipation = (Participation) other;
        return student.equals(otherParticipation.student)
                && tutorial.equals(otherParticipation.tutorial)
                && attendanceList.equals(otherParticipation.attendanceList);
    }
}
