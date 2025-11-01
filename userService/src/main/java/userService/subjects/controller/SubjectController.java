package userService.subjects.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import userService.subjects.dtos.SubjectRequestDto;
import userService.subjects.dtos.SubjectResponseDto;
import userService.subjects.servie.SubjectService;

import java.util.List;

@RestController
@RequestMapping("/api/subject")
public class SubjectController {
@Autowired
    private SubjectService subjectService;
    @PostMapping("/addSubjects")
    public ResponseEntity<SubjectResponseDto> addsubjectToSubject(@RequestBody SubjectRequestDto dto){
        return ResponseEntity.ok(subjectService.addSubjectByYear(dto));
    }
    @GetMapping("/getByYear/{year}")
    public ResponseEntity<List<SubjectResponseDto>>findBYyEAR(@PathVariable ("year")String year){
        return ResponseEntity.ok(subjectService.getSubhectByYear(year));
    }
}
