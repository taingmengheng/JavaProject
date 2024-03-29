// Group4Member:
// Heng Taingmeng
// Kim Horlong
// Chea SokChan
// Yen Mom
// Oeng Zhileang  

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Subject {
    private String name;

    public Subject(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Student {
    private String name;
    private List<Subject> subjects;

    public Student(String name, List<Subject> subjects) {
        this.name = name;
        this.subjects = subjects;
    }

    public String getName() {
        return name;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }
}

class TimeSlot {
    private String day;
    private String time;

    public TimeSlot(String day, String time) {
        this.day = day;
        this.time = time;
    }

    public String getDay() {
        return day;
    }

    public String getTime() {
        return time;
    }
}

public class ExamTimetableGenerator {
    private List<Subject> subjects;
    private List<Student> students;
    private List<TimeSlot> timeSlots;
    private Map<Subject, List<TimeSlot>> subjectTimetable;

    public ExamTimetableGenerator(List<Subject> subjects, List<Student> students, List<TimeSlot> timeSlots) {
        this.subjects = subjects;
        this.students = students;
        this.timeSlots = timeSlots;
        this.subjectTimetable = new HashMap<>();
    }

    public void generateTimetable() {
        Collections.shuffle(subjects);
        Collections.shuffle(timeSlots);

        for (Student student : students) {
            List<TimeSlot> assignedSlots = new ArrayList<>();
            List<Subject> shuffledSubjects = new ArrayList<>(student.getSubjects());
            Collections.shuffle(shuffledSubjects);

            for (Subject subject : shuffledSubjects) {
                List<TimeSlot> availableTimeSlots = getAvailableTimeSlots(subject, assignedSlots);
                if (!availableTimeSlots.isEmpty()) {
                    Collections.shuffle(availableTimeSlots); // Randomize the available time slots
                    TimeSlot chosenTimeSlot = availableTimeSlots.get(0);
                    subjectTimetable.computeIfAbsent(subject, k -> new ArrayList<>()).add(chosenTimeSlot);
                    assignedSlots.add(chosenTimeSlot);
                    System.out.println("Assigned " + subject.getName() + " to " + student.getName() +
                            " on " + chosenTimeSlot.getDay() + " at " + chosenTimeSlot.getTime());
                } else {
                    System.out.println("No available time slots for " + subject.getName() + " for " +
                            student.getName());
                }
            }
        }
    }

    private List<TimeSlot> getAvailableTimeSlots(Subject subject, List<TimeSlot> assignedSlots) {
        List<TimeSlot> availableTimeSlots = new ArrayList<>(timeSlots);
        for (TimeSlot assignedSlot : assignedSlots) {
            availableTimeSlots.removeIf(slot -> slot.getDay().equals(assignedSlot.getDay())
                    && slot.getTime().equals(assignedSlot.getTime()));
        }
        return availableTimeSlots;
    }

    public static void main(String[] args) {
        List<Subject> subjects = new ArrayList<>();
        subjects.add(new Subject("Math"));
        subjects.add(new Subject("Physics"));
        subjects.add(new Subject("Chemistry"));
        subjects.add(new Subject("Biology"));
        subjects.add(new Subject("History"));

        List<Student> students = new ArrayList<>();
        students.add(new Student("Meng", subjects));
        students.add(new Student("Long", subjects));
        students.add(new Student("SokChan", subjects));
        students.add(new Student("Mom", subjects));
        students.add(new Student("Leang", subjects));

        List<TimeSlot> timeSlots = new ArrayList<>();
        timeSlots.add(new TimeSlot("Monday", "9:00 AM"));
        timeSlots.add(new TimeSlot("Monday", "1:00 PM"));
        timeSlots.add(new TimeSlot("Tuesday", "9:00 AM"));
        timeSlots.add(new TimeSlot("Tuesday", "1:00 PM"));
        timeSlots.add(new TimeSlot("Wednesday", "9:00 AM"));
        timeSlots.add(new TimeSlot("Wednesday", "1:00 PM"));
        timeSlots.add(new TimeSlot("Thursday", "9:00 AM"));
        timeSlots.add(new TimeSlot("Thursday", "1:00 PM"));
        timeSlots.add(new TimeSlot("Friday", "9:00 AM"));
        timeSlots.add(new TimeSlot("Friday", "1:00 PM"));

        ExamTimetableGenerator timetableGenerator = new ExamTimetableGenerator(subjects, students, timeSlots);
        timetableGenerator.generateTimetable();
    }
}
