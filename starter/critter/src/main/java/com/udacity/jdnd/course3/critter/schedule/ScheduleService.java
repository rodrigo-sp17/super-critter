package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final PetService petService;
    private final UserService userService;

    public ScheduleService(ScheduleRepository scheduleRepository,
                           PetService petService,
                           UserService userService) {
        this.scheduleRepository = scheduleRepository;
        this.petService = petService;
        this.userService = userService;
    }

    @Transactional
    public Schedule createSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> getScheduleForPet(Long petId) {
        Pet pet = petService.getPet(petId);
        return scheduleRepository.getScheduleByPet(pet);
    }

    public List<Schedule> getScheduleForEmployee(Long employeeId) {
        Employee employee = userService.getEmployee(employeeId);
        return scheduleRepository.getScheduleByEmployee(employee);
    }

    public List<Schedule> getScheduleForCustomer(Long customerId) {
        Customer customer = userService.getCustomer(customerId);
        return scheduleRepository.getScheduleByCustomer(customer);
    }
}
