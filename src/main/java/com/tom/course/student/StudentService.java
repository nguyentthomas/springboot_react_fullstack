package com.tom.course.student;

import com.tom.course.student.exception.BadRequestException;
import com.tom.course.student.exception.StudentNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@AllArgsConstructor
@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public void addStudent(Student student) {
        //check if email is taken -> throw an error
        Boolean existingEmail = studentRepository.selectExistsEmail(student.getEmail());
        if (existingEmail) {
            throw new BadRequestException("That email ("+ student.getEmail() + ") is already taken.");
        } //all other cases - else
        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId){
        //check if student exists
        if (!studentRepository.existsById(studentId)) {
            throw new StudentNotFoundException("User with that Id:" + studentId + "does not exist in this system.");
        } //else, if it exists...
        studentRepository.deleteById(studentId);
    }
}
