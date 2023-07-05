package com.wms.code.worker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @GetMapping(path="/wms")
    public String showWelcomePage(){
        return "welcome_page";
    }

    @GetMapping(path="/wms/delete/{workerId}")
    public String deleteAndReloadPage(@PathVariable("workerId") Long id){
        deleteWorker(id);
        return "redirect:/wms/getAll";
    }

    @DeleteMapping
    public void deleteWorker(Long id){
        this.workerService.deleteWorker(id);
    }

    @PostMapping(path="/wms/add")
    public void addNewWorker(@RequestBody Worker worker){
        this.workerService.addNewWorker(worker);
    }

    @PutMapping(path="/wms/update/{workerId}")
    public void updateWorker(@PathVariable("workerId") Long id,
                             @RequestParam(required = true) String name,
                             @RequestParam(required = true) String surname,
                             @RequestParam(required = true) String email){
        this.workerService.updateWorker(id, name, surname, email);
    }

}
