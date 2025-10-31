package userService.teachers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import userService.teachers.service.TeacherService;
import userService.teachers.teachersDtos.TeacherForStudentsResponseDto;
import userService.teachers.teachersDtos.TeacherRequestDto;
import userService.teachers.teachersDtos.TeacherResponseDto;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    @PostMapping("/finishSignUP/{id}")
    public ResponseEntity<TeacherResponseDto> completeSignup(@PathVariable ("id")long id, @RequestBody TeacherRequestDto dto){
        return ResponseEntity.ok(teacherService.completeSignup(id,dto));
    }
    @GetMapping("/subject/{subject}")
    public ResponseEntity<List<TeacherForStudentsResponseDto>> findBySubject(@PathVariable ("subject")String subect){
        return ResponseEntity.ok(teacherService.listOfTeachersBySubject(subect));
    }
}
