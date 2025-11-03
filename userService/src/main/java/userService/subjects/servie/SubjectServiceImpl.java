package userService.subjects.servie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import userService.registrations.exceptions.UserExceptions;
import userService.subjects.Subjects;
import userService.subjects.dtos.SubjectRequestDto;
import userService.subjects.dtos.SubjectResponseDto;
import userService.subjects.repo.SubjectRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class SubjectServiceImpl implements SubjectService{
@Autowired
    private SubjectRepository subjectRepository;

    @Override
    public SubjectResponseDto addSubjectByYear(SubjectRequestDto dto) {
        Optional<Subjects>subjects=subjectRepository.findBySubject(dto.getSubjct());
        if(subjects.isPresent()){
            throw new UserExceptions(" SUCH SUBJECT ALREADY AVAILABLE RIGHT NOW FOR YEAR "+dto.getCourseYear());
        }
        Subjects sub=new Subjects();
        sub.setCourseYear(dto.getCourseYear());
        sub.setSubject(dto.getSubjct());
        subjectRepository.save(sub);
        return fromEntity(sub);
    }

    @Override
    public SubjectResponseDto getSubhectByYear(String Year) {
        List<Subjects>subjectListYear=subjectRepository.findByCourseYear(Year);

        if(subjectListYear.isEmpty()){
            throw new UserExceptions("PLEASE ENTER VALID YEAR "+Year);
        }
        SubjectResponseDto dto=new SubjectResponseDto();
        dto.setCourseYear(Year);

        List<String>subjectName=new ArrayList<>();
        for(Subjects s:subjectListYear){
            subjectName.add(s.getSubject());
        }
        dto.setSubjectsList(subjectName);
        return dto;
    }



    private SubjectResponseDto fromEntity(Subjects subjects){
        SubjectResponseDto dto=new SubjectResponseDto();
        dto.setCourseYear(subjects.getCourseYear());
        List<String >subjectName=new ArrayList<>();
        subjectName.add(subjects.getSubject());
        dto.setSubjectsList(subjectName);
    return dto;
    }
// CREATE AN API WHERE STUDENT WILL SELECT THE SUBJECT WITH YEAR , AND  STUDENT ID
// AND FIND TEACHER AND THEN SELECT TEACHER AND THEN WRITE HIS THOUGHTS ABOUT CONFERENCE



}
