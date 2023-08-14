package com.wms.code.worker;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class WorkerController {

    private final WorkerService workerService;

    @Autowired
    public WorkerController(WorkerService workerService){
        this.workerService = workerService;
    }

    @GetMapping(path="/wms/getAll")
    public String showAllWorkersPage(Model model){
        model.addAttribute("workers", this.workerService.getWorkers());
        return "all_workers";
    }

    @GetMapping(path="/wms/getById")
    public String showSearchIdPage(Model model){
        Worker worker = new Worker();
        model.addAttribute("workerOnlyId", worker);
        return "search_id";
    }

    @GetMapping(path="/wms/getWorkerById")
    public String showWorkerById(@ModelAttribute(value="workerOnlyId") Worker worker, Model model){
        model.addAttribute("workerById", this.workerService.getWorkerById(worker.getId()));
        return "show_id";
    }

    @GetMapping(path="/wms")
    public String showWelcomePage(){
        return "welcome_page";
    }

    @GetMapping(path="/wms/delete/{workerId}")
    public String deleteAndReloadPage(@PathVariable("workerId") Long id){
        deleteWorker(id);
        return "redirect:/wms/getAll";
    }

    @GetMapping(path="/wms/add")
    public String showAddPage(Model model){
        Worker worker = new Worker();
        model.addAttribute("worker", worker);
        return "add_worker";
    }

    @DeleteMapping
    public void deleteWorker(Long id){
        this.workerService.deleteWorker(id);
    }

    @PostMapping(path="/wms/addToDb")
    public String addNewWorker(@Valid @ModelAttribute(value="worker") Worker worker, BindingResult result){
        if (result.hasErrors()){
            return "add_worker";
        }
        String err = this.workerService.addNewWorker(worker);
        if (err.startsWith("worker with id")){
            result.rejectValue("id", "error.id", err);
            return "add_worker";
        }
        else if (err.startsWith("worker with email")){
            result.rejectValue("email", "error.email", err);
            return "add_worker";
        }
        return "redirect:/wms/getAll";
    }

    @GetMapping(path="/wms/update/{workerId}")
    public String showUpdatePage(@PathVariable("workerId") Long id, Model model){
        model.addAttribute("updateWorker", this.workerService.getWorkerById(id));
        return "update_worker";
    }

    @PostMapping(path="/wms/update")
    public String updateWorker(@Valid @ModelAttribute(value="updateWorker") Worker worker, BindingResult result){
        if (result.hasErrors()){
            return "update_worker";
        }
        String err = this.workerService.updateWorker(worker.getId(), worker.getFirstName(), worker.getLastName(), worker.getEmail());
        if (err.startsWith("worker with id")){
            result.rejectValue("id", "error.id", err);
            return "update_worker";
        }
        else if (err.startsWith("worker with email")){
            result.rejectValue("email", "error.email", err);
            return "update_worker";
        }
        return "redirect:/wms/getAll";
    }

}
