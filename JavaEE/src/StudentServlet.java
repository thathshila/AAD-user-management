import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/student")
public class StudentServlet extends HttpServlet {
    private final List<StudentDTO> studentsList = new ArrayList<>();

//    public StudentServlet() {
//    }

//    @Override
//    public void init(ServletConfig config) throws ServletException {
//        studentsList.add(new StudentDTO(1, "Ama", "ama@gmail.com", 20));
//        studentsList.add(new StudentDTO(2, "Mali", "mali@gmail.com", 23));
//        studentsList.add(new StudentDTO(3, "Amal", "amal@gmail.com", 25));
//        studentsList.add(new StudentDTO(4, "Amal", "amal@gmail.com", 25));
//    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String ageString = req.getParameter("age");

        if (name == null || name.isEmpty() || email == null || email.isEmpty() || ageString == null || ageString.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"Name and email are required\"}");
        } else {
            int id = studentsList.size() + 1;
            try {
                int age = Integer.parseInt(ageString);
                StudentDTO studentDTO = new StudentDTO(id, name, email, age);
                studentsList.add(studentDTO);
                resp.setStatus(HttpServletResponse.SC_CREATED);   //201 created
                resp.getWriter().write("{\"message\":\"Successfully added student!\"}");
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);      //400 bad request(frontend)
                resp.getWriter().write("{\"message\":\"Invalid age input!\"}");
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idString = req.getParameter("id");
        if (idString == null || idString.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"ID is required\"}");
        } else {
           try {
               int id = Integer.parseInt(idString);
               StudentDTO studentById = getStudentById(id);
               if (studentById == null) {
                   resp.setStatus(HttpServletResponse.SC_NOT_FOUND);   //404
                   resp.getWriter().write("{\"message\": \"Student not found!\"}");
               }else {
                   studentsList.remove(studentById);
                   resp.setStatus(HttpServletResponse.SC_OK); //200
                   resp.getWriter().write("{\"message\": \"Student deleted!\"}");
               }
           }catch (NumberFormatException e) {
               resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); //400
               resp.getWriter().write("{\"error\": \"ID is invalid\"}");
           }
        }
    }

    private StudentDTO getStudentById(int id) {
        for (StudentDTO student : studentsList) {
            if (student.getStudentID() == id) ;
            return student;
        }
    return null;
}

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter printWriter = resp.getWriter();
        resp.setContentType("application/json");

        //JSON       JAVA SCRIPT OBJECT NOTATION

        StringBuilder studentListStringBuilder = new StringBuilder();
        studentListStringBuilder.append("[");
        for (int i = 0; i < studentsList.size(); i++) {
            StudentDTO studentDTO = studentsList.get(i);

           String studentDTOJsonString =  String.format("{\"id\": %d, \"name\": \"%s\", \"email\": \"%s\", \"age\": %d}",
                    studentDTO.getStudentID(),
                    studentDTO.getStudentName(),
                    studentDTO.getStudentEmail(),
                    studentDTO.getStudentAge()
                    ).toString();

           studentListStringBuilder.append(studentDTOJsonString);

           if (i < studentsList.size() - 1) {
               studentListStringBuilder.append(",");
           }
        }
        studentListStringBuilder.append("]");

        String jsonObjectList = studentListStringBuilder.toString();
        printWriter.write(jsonObjectList);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idString = req.getParameter("id");
        String studentName = req.getParameter("studentName");
        String studentEmail = req.getParameter("studentEmail");
        String ageString = req.getParameter("studentAge");
        if (idString == null || idString.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"ID is required\"}");
        } else {
            try {
                int id = Integer.parseInt(idString);
                int studentAge = Integer.parseInt(ageString);

                StudentDTO studentById = getStudentById(id);
                if (studentById == null) {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.getWriter().write("{\"error\": \"Student not found!\"}");
                }else {
                    studentById.setStudentName(studentName);
                    studentById.setStudentEmail(studentEmail);
                    studentById.setStudentAge(studentAge);
                    System.out.println(studentsList);
                    resp.setStatus(HttpServletResponse.SC_OK);
                    resp.getWriter().write("{\"message\": \"Student updated!\"}");
                }
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error\": \"ID or age is invalid\"}");
            }
        }
    }
}
