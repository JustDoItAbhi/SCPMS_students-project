package userService.subjects.servie;

import userService.subjects.Subjects;
import userService.subjects.dtos.SubjectRequestDto;
import userService.subjects.dtos.SubjectResponseDto;

import java.util.List;

public interface SubjectService {
    SubjectResponseDto addSubjectByYear(SubjectRequestDto dto);
    SubjectResponseDto getSubhectByYear(String Year);
}
