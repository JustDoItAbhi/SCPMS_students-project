package userService.teachers;

import userService.students.modals.StudentTopic;
import userService.students.stDto.TopicResponeDto;
import userService.teachers.modal.Teachers;
import userService.teachers.teachersDtos.ListOfTechersResponseDto;
import userService.teachers.teachersDtos.TeacherResponseDto;
import userService.teachers.teachersDtos.UpdateTeacherResponeDto;

public class TeacherMapper {
    public static UpdateTeacherResponeDto updateMapper(Teachers t){
      UpdateTeacherResponeDto dto=new UpdateTeacherResponeDto();
      dto.setTeacherId(t.getId());
      dto.setTeacherName(t.getTeacherName());
      dto.setTeacherEmail(t.getUserEmail());
      dto.setSubject(t.getSubject());
      dto.setTeacherYear(t.getTeacherYear());
      dto.setUsers(t.getUsers());
        return dto;
    }
    public static ListOfTechersResponseDto getListFromEntity(Teachers teachers){
        ListOfTechersResponseDto dto=new ListOfTechersResponseDto();
        dto.setTeacherId(teachers.getId());
        dto.setTeacherName(teachers.getTeacherName());
        dto.setTeacherYear(teachers.getTeacherYear());
        dto.setEmail(teachers.getUserEmail());
        dto.setTeacherSubject(teachers.getSubject());
        return dto;
    }
    public static TeacherResponseDto fromTeacherEntity(Teachers teachers){
        TeacherResponseDto dto=new TeacherResponseDto();
        dto.setTeacherId(teachers.getId());
        dto.setTeacherName(teachers.getTeacherName());
        dto.setSubject(teachers.getSubject());
        dto.setTeacherYear(teachers.getTeacherYear());
        dto.setTeacherEmail(teachers.getUserEmail());
        dto.setUsers(teachers.getUsers());
        /// wait for aproval
        return dto;
    }
}
