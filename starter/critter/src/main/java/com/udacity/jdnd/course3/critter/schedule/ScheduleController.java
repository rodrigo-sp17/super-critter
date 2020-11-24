package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.User;
import com.udacity.jdnd.course3.critter.user.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final PetService petService;
    private final UserService userService;

    public ScheduleController(ScheduleService scheduleService,
                              PetService petService,
                              UserService userService) {
        this.scheduleService = scheduleService;
        this.petService = petService;
        this.userService = userService;
    }

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = scheduleFromDto(scheduleDTO);
        List<Pet> pets = scheduleDTO.getPetIds().stream()
                .map(id -> petService.getPet(id))
                .collect(Collectors.toList());
        schedule.setPets(pets);

        List<Employee> employees = scheduleDTO.getEmployeeIds().stream()
                .map(id -> userService.getEmployee(id))
                .collect(Collectors.toList());
        schedule.setEmployees(employees);

        List<Customer> customers = pets.stream()
                .map(Pet::getOwner)
                .collect(Collectors.toList());
        schedule.setCustomers(customers);

        return dtoFromSchedule(scheduleService.createSchedule(schedule));
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<ScheduleDTO> dtos = scheduleService.getAllSchedules().stream()
                .map(s -> dtoFromSchedule(s))
                .collect(Collectors.toList());
        return dtos;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<ScheduleDTO> dtos = scheduleService.getScheduleForPet(petId).stream()
                .map(s -> dtoFromSchedule(s))
                .collect(Collectors.toList());
        return dtos;
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<ScheduleDTO> dtos = scheduleService.getScheduleForEmployee(employeeId).stream()
                .map(s -> dtoFromSchedule(s))
                .collect(Collectors.toList());
        return dtos;
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<ScheduleDTO> dtos = scheduleService.getScheduleForCustomer(customerId).stream()
                .map(s -> dtoFromSchedule(s))
                .collect(Collectors.toList());
        return dtos;
    }

    private static Schedule scheduleFromDto(ScheduleDTO dto) {
        Schedule sc = new Schedule();
        BeanUtils.copyProperties(dto, sc);
        return sc;
    }

    private static ScheduleDTO dtoFromSchedule(Schedule schedule) {
        ScheduleDTO dto = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, dto);

        List<Long> employeeIds = schedule.getEmployees().stream()
                .map(User::getId)
                .collect(Collectors.toList());
        dto.setEmployeeIds(employeeIds);

        List<Long> petIds = schedule.getPets().stream()
                .map(Pet::getId)
                .collect(Collectors.toList());
        dto.setPetIds(petIds);

        return dto;
    }
}
