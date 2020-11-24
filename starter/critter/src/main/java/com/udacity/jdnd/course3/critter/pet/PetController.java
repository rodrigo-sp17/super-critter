package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    private final UserService userService;
    private final PetService petService;

    public PetController(UserService userService, PetService petService) {
        this.userService = userService;
        this.petService = petService;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = petFromDto(petDTO);
        Customer owner = userService.getCustomer(petDTO.getOwnerId());
        pet.setOwner(owner);
        Pet savedPet = petService.savePet(pet);
        owner.getPets().add(savedPet);
        return dtoFromPet(savedPet);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {

        return dtoFromPet(petService.getPet(petId));
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<PetDTO> dtos = new ArrayList<>();
        petService.getPets().forEach(pet -> dtos.add(dtoFromPet(pet)));
        return dtos;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<PetDTO> dtos = new ArrayList<>();
        petService.getPetsByOwner(ownerId).forEach(pet -> dtos.add(dtoFromPet(pet)));
        return dtos;
    }

    private static Pet petFromDto(PetDTO dto) {
        Pet pet = new Pet();
        BeanUtils.copyProperties(dto, pet);
        return pet;
    }

    private static PetDTO dtoFromPet(Pet pet) {
        PetDTO dto = new PetDTO();
        BeanUtils.copyProperties(pet, dto);
        dto.setOwnerId(pet.getOwner().getId());
        return dto;
    }


}
