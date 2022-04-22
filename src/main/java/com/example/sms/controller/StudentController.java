package com.example.sms.controller;

import com.example.sms.entity.Student;
import com.example.sms.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

//We make this class as spring MVC class, to handle the requests
@Controller
public class StudentController {

    private StudentService studentService;

    // We can avoid add autowired annotation here that helps in constructor level dependency injection, when we have
    // only one constructor
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // Handler method to handle list students and return model and view
    @GetMapping("/students")
    public String listStudents(Model model) {
        // Key-value pair addition in model
        model.addAttribute("students", studentService.getAllStudents());
        // returning a students view, will look for students.html in templates folder
        return "students";
    }

    @GetMapping("/students/new")
    public String createStudentForm(Model model) {
        // create empty student object to hold student form data
        Student student = new Student();
        model.addAttribute("student", student);
        return "create_student";
    }

    //Handler method to handle the form request
    //Bind ModelAttribute student to a class object
    @PostMapping("/students")
    public String saveStudent(@ModelAttribute("student") Student student) {
        studentService.saveStudent(student);
        return "redirect:/students";
        //this will redirect to listStudents method
    }

    //Handler method to handle update request
    @GetMapping("/students/edit/{id}")
    public String editStudentForm(@PathVariable Long id, Model model) {
        model.addAttribute("student", studentService.getStudentById(id));
        return "edit_student";
    }

    @PostMapping("/students/{id}")
    public String updateStudent(@PathVariable Long id, @ModelAttribute("student") Student student, Model model) {
        // get Student from database by id
        Student existingStudent = studentService.getStudentById(id);
        existingStudent.setId(id);
        existingStudent.setFirstName(student.getFirstName());
        existingStudent.setLastName(student.getLastName());
        existingStudent.setEmail(student.getEmail());

        //save updates student object
        studentService.updateStudent(existingStudent);
        return "redirect:/students";
    }

    //Handler to handle delete student request
    @GetMapping("/students/{id}")
    public String deleteStudent(@PathVariable Long id){
        studentService.deleteStudentById(id);
        return "redirect:/students";
    }

}
