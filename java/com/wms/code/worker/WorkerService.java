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

    public Worker getWorkerById(Long id){
        boolean exists = this.workerRepository.existsById(id);
        if (exists == false){
            throw new IllegalStateException("worker with id " + id + " does not exist");
        }
        return this.workerRepository.getReferenceById(id);
    }

    public void deleteWorker(Long id){
        boolean exists = this.workerRepository.existsById(id);
        if (exists == false){
            throw new IllegalStateException("worker with id " + id + " does not exist!");
        }
        this.workerRepository.deleteById(id);
    }

    public String addNewWorker(Worker worker){
        String err = "";
        boolean exists = this.workerRepository.existsById(worker.getId());
        if (err.isEmpty() && exists){
            err = "worker with id " + worker.getId() + " already exists!";
        }
        Optional<Worker> workerOptional = this.workerRepository.findWorkerByEmail(worker.getEmail());
        if (err.isEmpty() && workerOptional.isPresent()){
            err = "worker with email " + worker.getEmail() + " already exists!";
        }
        if (err.isEmpty()){
            this.workerRepository.save(worker);
        }
        return err;
    }

    @Transactional
    public String updateWorker(Long id, String firstName, String lastName, String email){
        String err = "";
        boolean exists = this.workerRepository.existsById(id);
        if (err.isEmpty() && exists == false){
            err = "worker with id " + id + " does not exist!";
        }
        else {
            Worker worker = this.workerRepository.getReferenceById(id);

            if (email != null && email.length() > 0 && !Objects.equals(worker.getEmail(), email)){
                Optional<Worker> workerOptional = this.workerRepository.findWorkerByEmail(email);
                if (err.isEmpty() && workerOptional.isPresent()){
                    err = "worker with email " + email + " already exists!";
                }
                if (err.isEmpty()){
                    worker.setEmail(email);
                }

            }

            if (firstName != null && firstName.length() > 0 && !Objects.equals(worker.getFirstName(), firstName)){
                if (err.isEmpty()){
                    worker.setFirstName(firstName);
                }

            }

            if (lastName != null && lastName.length() > 0 && !Objects.equals(worker.getLastName(), lastName)){
                if (err.isEmpty()){
                    worker.setLastName(lastName);
                }
            }
        }

        return err;

    }

}
