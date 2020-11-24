package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query("select s from Schedule s where :customer member of s.customers")
    List<Schedule> getScheduleByCustomer(Customer customer);

    @Query("select s from Schedule s where :employee member of s.employees")
    List<Schedule> getScheduleByEmployee(Employee employee);

    @Query("select s from Schedule s where :pet member of s.pets")
    List<Schedule> getScheduleByPet(Pet pet);
}
