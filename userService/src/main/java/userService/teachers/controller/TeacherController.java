package userService.teachers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import userService.students.stDto.TopicResponeDto;
import userService.subjects.dtos.SubjectRequestDto;
import userService.subjects.dtos.SubjectResponseDto;
import userService.teachers.service.TeacherService;
import userService.teachers.teachersDtos.TeacherForStudentsResponseDto;
import userService.teachers.teachersDtos.TeacherRequestDto;
import userService.teachers.teachersDtos.TeacherResponseDto;
import userService.teachers.teachersDtos.TopicForTeacherResponseDto;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    //signup teacher
    @PostMapping("/finishSignUP/{id}")
    public ResponseEntity<TeacherResponseDto> completeSignup(@PathVariable ("id")long id, @RequestBody TeacherRequestDto dto){
        return ResponseEntity.ok(teacherService.completeSignup(id,dto));
    }
    //find list of teachers with subject name
    @GetMapping("/subject/{subject}")
    public ResponseEntity<List<TeacherForStudentsResponseDto>> findBySubject(@PathVariable ("subject")String subect){
        return ResponseEntity.ok(teacherService.listOfTeachersBySubject(subect));
    }
    // delete teacher by id
    @DeleteMapping("/deleteTeacher/{id}")
    public ResponseEntity<Boolean> deleteTeacher(@PathVariable ("id")long id){
        return ResponseEntity.ok(teacherService.deteteTeacher(id));
    }

    @GetMapping("/allTopicsByTeacherId/{teacherId}")
    public ResponseEntity<List<TopicForTeacherResponseDto>> findAllTopicByTeacherId(@PathVariable ("teacherId")long teacherId){
        return ResponseEntity.ok(teacherService.getAllTheTopicRequestByTeacherId(teacherId));
    }

}
