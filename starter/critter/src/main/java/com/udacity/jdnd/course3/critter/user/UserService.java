package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    private final PetService petService;

    public UserService(CustomerRepository customerRepository,
                       EmployeeRepository employeeRepository,
                       PetService petService) {
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
        this.petService = petService;
    }

    public List<Customer> getAllCustomers() {

        return customerRepository.retrieveAllCustomers();
    }

    public Customer getCustomer(Long customerId) {
        return customerRepository.findById(customerId).orElseThrow(UserNotFoundException::new);
    }

    @Transactional
    public Customer saveCustomer(Customer customer) {
        Customer result = customerRepository.save(customer);
        return result;
    }

    public Customer getOwnerByPet(Long petId) {
        Pet pet = petService.getPet(petId);
        return customerRepository.findById(pet.getOwner().getId())
                .orElseThrow(UserNotFoundException::new);
    }

    public Employee getEmployee(Long employeeId) {
        return employeeRepository.findById(employeeId).orElseThrow(UserNotFoundException::new);
    }

    public List<Employee> findEmployeeForService(Set<EmployeeSkill> skills,
                                                 LocalDate date) {

        List<Employee> employees = employeeRepository
                .getEmployeeForService(DayOfWeek.from(date));
        return employees.stream().filter(e -> e.getSkills().containsAll(skills))
                .collect(Collectors.toList());
    }

    @Transactional
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Transactional
    public void setAvailability(Set<DayOfWeek> daysAvailable, Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(UserNotFoundException::new);
        employee.setDaysAvailable(daysAvailable);
        employeeRepository.save(employee);
    }


}
