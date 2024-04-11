import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Course {
    private String code;
    private String title;
    private String description;
    private int capacity;
    private String schedule;

    public Course(String code, String title, String description, int capacity, String schedule) {
        this.code = code;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getSchedule() {
        return schedule;
    }
}

class Student {
    private int id;
    private String name;
    private List<String> registeredCourses;

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getRegisteredCourses() {
        return registeredCourses;
    }

    public void registerCourse(String courseCode) {
        registeredCourses.add(courseCode);
    }

    public void dropCourse(String courseCode) {
        registeredCourses.remove(courseCode);
    }
}

public class CourseRegistrationSystem {
    private List<Course> courses;
    private List<Student> students;
    private Scanner scanner;

    public CourseRegistrationSystem() {
        this.courses = new ArrayList<>();
        this.students = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void displayCourseListing() {
        System.out.println("Available Courses:");
        for (Course course : courses) {
            int availableSlots = course.getCapacity() - countRegisteredStudents(course.getCode());
            System.out.println("Code: " + course.getCode() + ", Title: " + course.getTitle() + ", Description: "
                    + course.getDescription() + ", Schedule: " + course.getSchedule() + ", Available Slots: "
                    + availableSlots);
        }
    }

    public void registerStudentForCourse() {
        System.out.print("Enter student ID: ");
        int studentId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter course code to register: ");
        String courseCode = scanner.nextLine();

        Student student = findStudentById(studentId);
        Course course = findCourseByCode(courseCode);

        if (student != null && course != null) {
            if (countRegisteredStudents(courseCode) < course.getCapacity()) {
                student.registerCourse(courseCode);
                System.out.println(
                        "Registration successful for " + student.getName() + " in course " + course.getTitle());
            } else {
                System.out.println("Sorry, course " + course.getTitle() + " is full.");
            }
        } else {
            System.out.println("Invalid student ID or course code.");
        }
    }

    public void dropCourseForStudent() {
        System.out.print("Enter student ID: ");
        int studentId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter course code to drop: ");
        String courseCode = scanner.nextLine();

        Student student = findStudentById(studentId);

        if (student != null) {
            student.dropCourse(courseCode);
            System.out.println("Course " + courseCode + " dropped successfully for student " + student.getName());
        } else {
            System.out.println("Invalid student ID.");
        }
    }

    private int countRegisteredStudents(String courseCode) {
        int count = 0;
        for (Student student : students) {
            if (student.getRegisteredCourses().contains(courseCode)) {
                count++;
            }
        }
        return count;
    }

    private Student findStudentById(int id) {
        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }

    private Course findCourseByCode(String code) {
        for (Course course : courses) {
            if (course.getCode().equals(code)) {
                return course;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        CourseRegistrationSystem system = new CourseRegistrationSystem();

        // Adding sample courses
        system.addCourse(new Course("CSE101", "Introduction to Computer Science", "Fundamentals of programming", 30,
                "Mon/Wed/Fri 9:00-10:30"));
        system.addCourse(
                new Course("MAT201", "Calculus I", "Limits, derivatives, and integrals", 25, "Tue/Thu 11:00-12:30"));

        // Adding sample students
        system.addStudent(new Student(1, "Alice"));
        system.addStudent(new Student(2, "Bob"));

        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\nMenu:");
            System.out.println("1. Display Course Listing");
            System.out.println("2. Register Student for Course");
            System.out.println("3. Drop Course for Student");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    system.displayCourseListing();
                    break;
                case 2:
                    system.registerStudentForCourse();
                    break;
                case 3:
                    system.dropCourseForStudent();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 4);

        scanner.close();
    }
}
