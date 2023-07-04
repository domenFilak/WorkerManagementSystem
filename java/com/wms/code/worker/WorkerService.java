package com.wms.code.worker;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class WorkerService {

    private final WorkerRepository workerRepository;

    @Autowired
    public WorkerService(WorkerRepository workerRepository){
        this.workerRepository = workerRepository;
    }

    public List<Worker> getWorkers(){
        return this.workerRepository.findAll();
    }

    public void deleteWorker(Long id){
        boolean exists = this.workerRepository.existsById(id);
        if (exists == false){
            throw new IllegalStateException("worker with id " + id + " does not exist!");
        }
        this.workerRepository.deleteById(id);
    }

    public void addNewWorker(Worker worker){
        boolean exists = this.workerRepository.existsById(worker.getId());
        if (exists){
            throw new IllegalStateException("worker with id " + worker.getId() + " already exists!");
        }
        Optional<Worker> workerOptional = this.workerRepository.findWorkerByEmail(worker.getEmail());
        if (workerOptional.isPresent()){
            throw new IllegalStateException("worker with email " + worker.getEmail() + " already exists!");
        }
        this.workerRepository.save(worker);
    }

    @Transactional
    public void updateWorker(Long id, String firstName, String lastName, String email){
        boolean exists = this.workerRepository.existsById(id);
        if (exists == false){
            throw new IllegalStateException("worker with id " + id + " does not exist!");
        }
        Worker worker = this.workerRepository.getReferenceById(id);

        if (firstName != null && firstName.length() > 0 && !Objects.equals(worker.getFirstName(), firstName)){
            worker.setFirstName(firstName);
        }

        if (lastName != null && lastName.length() > 0 && !Objects.equals(worker.getLastName(), lastName)){
            worker.setLastName(lastName);
        }

        if (email != null && email.length() > 0 && !Objects.equals(worker.getEmail(), email)){
            Optional<Worker> workerOptional = this.workerRepository.findWorkerByEmail(email);
            if (workerOptional.isPresent()){
                throw new IllegalStateException("worker with email " + email + " already exists!");
            }
            worker.setEmail(email);
        }

    }

}
