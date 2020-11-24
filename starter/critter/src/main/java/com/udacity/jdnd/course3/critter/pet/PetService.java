package com.udacity.jdnd.course3.critter.pet;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PetService {

    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public List<Pet> getPets() {
        return petRepository.findAll();
    }

    public Pet getPet(Long petId) {
        return petRepository.findById(petId).orElseThrow(PetNotFoundException::new);
    }

    @Transactional
    public Pet savePet(Pet pet) {

        return petRepository.save(pet);
    }

    public List<Pet> getPetsByOwner(Long ownerId) {
        return petRepository.findPetsByOwnerId(ownerId);
    }
}
