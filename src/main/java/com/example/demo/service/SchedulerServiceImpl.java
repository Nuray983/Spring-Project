package com.example.demo.service;

import com.example.demo.entity.Group;
import com.example.demo.entity.Student;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SchedulerServiceImpl implements SchedulerService {

    private final StudentService studentService;

    private final GroupService groupService;

    private static final Logger logger = LoggerFactory.getLogger(SchedulerServiceImpl.class);

    @Override
    @Transactional
    @Scheduled(initialDelay =  10 * 60 * 1000, fixedDelay =  10 * 60 * 1000)
    public void schedulerTask() {
        List<Group> allGroups = groupService.getAllGroups();
        for (Group group : allGroups) {
            List<Student> students = group.getStudentsList();
            if (students != null && !students.isEmpty()) {
                Student lastStudent = students.get(students.size() - 1);
                groupService.deleteStudentFromGroup(lastStudent.getId(), group.getId());
                studentService.deleteStudent(lastStudent.getId());
                logger.info("Deleted last student {} from group {}", lastStudent.getId(), group.getId());
            } else {
                logger.info("Skipping deletion from group {} - insufficient students", group.getId());
            }
        }
    }
}