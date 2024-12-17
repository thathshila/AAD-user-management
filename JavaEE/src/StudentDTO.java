public class StudentDTO {
   private int studentID;
   private String studentName;
   private String studentEmail;
   private int studentAge;

    public StudentDTO(int studentID, String studentName, String studentEmail, int studentAge) {
        this.studentID = studentID;
        this.studentName = studentName;
        this.studentEmail = studentEmail;
        this.studentAge = studentAge;
    }

    public StudentDTO() {
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public int getStudentAge() {
        return studentAge;
    }

    public void setStudentAge(int studentAge) {
        this.studentAge = studentAge;
    }
}
