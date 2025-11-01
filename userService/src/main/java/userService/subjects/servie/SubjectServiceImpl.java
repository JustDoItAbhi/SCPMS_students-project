package userService.subjects.servie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import userService.registrations.exceptions.UserExceptions;
import userService.subjects.Subjects;
import userService.subjects.dtos.SubjectRequestDto;
import userService.subjects.dtos.SubjectResponseDto;
import userService.subjects.repo.SubjectRepository;

import java.util.ArrayList;
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
    public List<SubjectResponseDto> getSubhectByYear(String Year) {
        Optional<Subjects>byYear=subjectRepository.findByCourseYear(Year);
        List<SubjectResponseDto>responseDtoList=new ArrayList<>();
        if(byYear.isEmpty()){
            throw new UserExceptions("PLEASE ENTER VALID YEAR "+Year);
        }else{
            List<Subjects>allSujects=subjectRepository.findAll();
            for(Subjects subjects:allSujects){
                responseDtoList.add(fromEntity(subjects));
            }
        }
        return responseDtoList;
    }

    private SubjectResponseDto fromEntity(Subjects subjects){
        SubjectResponseDto dto=new SubjectResponseDto();
        dto.setCourseYear(subjects.getCourseYear());
        dto.setSubjectsList(subjects.getSubject());
    return dto;
    }


}
