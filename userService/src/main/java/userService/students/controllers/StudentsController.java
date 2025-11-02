package userService.students.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import userService.registrations.dtos.reponseDtos.UserResponseDto;
import userService.students.service.StudentsService;
import userService.students.stDto.*;

@RestController
@RequestMapping("/api/students")
public class StudentsController {
    @Autowired
    private StudentsService studentsService;
    @PostMapping("completeStundentSignUp/{stId}")
    public ResponseEntity<StudentsResponseDto> completeStudentsSignup(@PathVariable("stId")long stId, @RequestBody StudentRequestDto dto){
        return ResponseEntity.ok(studentsService.finishSignUp(stId,dto));
    }
    @GetMapping("/getStudentById/{id}")
    public ResponseEntity<StudentsResponseDto> getById(@PathVariable("id")long id){
        return ResponseEntity.ok(studentsService.getStudentById(id));
    }
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Boolean> deleteSTD(@PathVariable("id")long id){
        return ResponseEntity.ok(studentsService.deleteStd(id));
    }
    @PostMapping("/selectSubject/{id}")
    public ResponseEntity<SelectSubjectAndStudentDetailsResponseDto> subjectSelection(@PathVariable("id")long id, @RequestBody SelectSubjectAndStudentDetailsReqDto dto){
        return ResponseEntity.ok(studentsService.selectYourSubject(id,dto));
    }
    @DeleteMapping("deleteSubject/{subjectId}")
    public ResponseEntity<Boolean> deleteSubjectBySTD(@PathVariable("subjectId")long subjectId){
        return ResponseEntity.ok(studentsService.deleteSelectedSubject(subjectId));
    }
    @PostMapping("/writeTopic")
    public ResponseEntity<TopicResponeDto> writeTopic( @RequestBody TopicRequestDto dto){
        return ResponseEntity.ok(studentsService.submitTopic(dto));
    }
    @GetMapping("/getStudentSubjectDetails/{userId}")
    public ResponseEntity<SelectSubjectAndStudentDetailsResponseDto> getSubjectAndStudentDetails(
            @PathVariable("userId")long userId){
        return ResponseEntity.ok(studentsService.getSubjectandStudentById(userId));
    }
    @DeleteMapping("/deleteFullUser/{userId}")
    public ResponseEntity<Boolean> deletefullUser(@PathVariable("userId")long userId){
        return ResponseEntity.ok(studentsService.deleteFullUserById(userId));
    }
}
