package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTENDANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.participation.Participation;
import seedu.address.model.person.Attendance;
import seedu.address.model.person.Person;
import seedu.address.model.tutorial.Tutorial;

/**
 * Marks the attendance of a person identified using it's displayed index from the address book.
 */
public class MarkAttendanceByStudentCommand extends Command {

    public static final String COMMAND_WORD = "mas";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the attendance of the student identified "
            + "by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_ATTENDANCE + "ATTENDANCE]"
            + "[" + PREFIX_TUTORIAL + "TUTORIAL]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_ATTENDANCE + "20/10/2024"
            + PREFIX_TUTORIAL + "Math";

    public static final String MESSAGE_MARK_ATTENDANCE_STUDENT_SUCCESS =
            "Marked attendance of %1$s student for %2$s tutorial on %3$s";
    public static final String MESSAGE_INVALID_TUTORIAL_FOR_STUDENT =
            "The student does not take %1$s tutorial";

    private final Index targetIndex;
    private final Attendance attendance;
    private final Tutorial tutorial;

    /**
     * @param targetIndex Index of the person in the filtered person list to mark
     * @param attendance Attendance of the person specified by index
     * @param tutorial Tutorial the student attended
     */
    public MarkAttendanceByStudentCommand(Index targetIndex, Attendance attendance, Tutorial tutorial) {
        requireAllNonNull(targetIndex, attendance, tutorial);
        this.targetIndex = targetIndex;
        this.attendance = attendance;
        this.tutorial = tutorial;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person studentToMarkAttendance = lastShownList.get(targetIndex.getZeroBased());
        Participation currentParticipation = model.getParticipationList().stream()
                .filter(participation -> participation.getStudent().equals(studentToMarkAttendance)
                        && participation.getTutorial().equals(this.tutorial))
                .findFirst()
                .orElseThrow(() -> new CommandException(
                        String.format(MESSAGE_INVALID_TUTORIAL_FOR_STUDENT, tutorial)));

        List<Attendance> updatedAttendance = new ArrayList<>(currentParticipation.getAttendanceList());
        updatedAttendance.add(attendance);

        Participation updatedParticipation = new Participation(currentParticipation.getStudent(),
                currentParticipation.getTutorial(), updatedAttendance);

        model.setParticipation(currentParticipation, updatedParticipation);

        return new CommandResult(String.format(MESSAGE_MARK_ATTENDANCE_STUDENT_SUCCESS,
                studentToMarkAttendance.getName(), tutorial.getSubject(), attendance));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MarkAttendanceByStudentCommand)) {
            return false;
        }

        MarkAttendanceByStudentCommand otherMarkAttendanceCommand = (MarkAttendanceByStudentCommand) other;
        return targetIndex.equals(otherMarkAttendanceCommand.targetIndex)
                && attendance.equals(otherMarkAttendanceCommand.attendance)
                && tutorial.equals(otherMarkAttendanceCommand.tutorial);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .add("attendance", attendance)
                .add("tutorial", tutorial)
                .toString();
    }
}
