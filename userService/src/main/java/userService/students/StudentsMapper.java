package userService.students;

import userService.registrations.mappers.UserMapper;
import userService.students.modals.Students;
import userService.students.stDto.StudentsResponseDto;

public class StudentsMapper {
    public static StudentsResponseDto fromStudents(Students students){
        StudentsResponseDto dto=new StudentsResponseDto();
        dto.setUsers(students.getUser());
        dto.setCurrentYear(students.getCurrentYear());
        dto.setStudentIdCardNumber(students.getStudentIdCardNumber());
        dto.setGroupNumber(students.getGroupNumber());
        dto.setMonitorName(students.getMonitorName());
        dto.setPassportNumber(students.getPassportNumber());
        dto.setSubGroupNumber(students.getSubGroupNumber());
        return dto;
    }
}
