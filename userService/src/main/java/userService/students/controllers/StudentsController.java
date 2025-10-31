package userService.students.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import userService.registrations.dtos.reponseDtos.UserResponseDto;
import userService.students.service.StudentsService;
import userService.students.stDto.StudentRequestDto;
import userService.students.stDto.StudentsResponseDto;

@RestController
@RequestMapping("/api/students")
public class StudentsController {
    @Autowired
    private StudentsService studentsService;
    @PostMapping("completeStundentSignUp/{stId}")
    public ResponseEntity<StudentsResponseDto> completeStudentsSignup(@PathVariable("stId")long stId, @RequestBody StudentRequestDto dto){
        return ResponseEntity.ok(studentsService.finishSignUp(stId,dto));
    }
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Boolean> deleteSTD(@PathVariable("id")long id){
        return ResponseEntity.ok(studentsService.deleteStd(id));
    }
}
