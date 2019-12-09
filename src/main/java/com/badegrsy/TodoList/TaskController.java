package com.badegrsy.TodoList;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;

import java.util.stream.Collectors;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
class TaskController {

    private final TaskRepository repository;
    private final TaskResourceAssembler assembler;

    TaskController(TaskRepository repository,
                       TaskResourceAssembler assembler) {

        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/tasks")
    Resources<Resource<Task>> all() {

        List<Resource<Task>> tasks = repository.findAll().stream()
            .map(assembler::toResource)
            .collect(Collectors.toList());

        return new Resources<>(tasks,
                               linkTo(methodOn(TaskController.class).all()).withSelfRel());
    }

    @PostMapping("/tasks")
    Task newTask(@RequestBody Task newTask) {

        return repository.save(newTask);
    }

    @GetMapping("/tasks/{id}")
    Resource<Task> one(@PathVariable Long id) {

        Task task = repository.findById(id)
            .orElseThrow(() -> new TaskNotFoundException(id));

        return assembler.toResource(task);
    }

    @PutMapping("/tasks/{id}")
    ResponseEntity<?> replaceTask(@RequestBody Task newTask, @PathVariable Long id) throws URISyntaxException {

        Task updatedTask = repository.findById(id)
            .map(task -> {
                    task.setName(newTask.getName());
                    task.setStatus(newTask.getStatus());
                    return repository.save(task);
                })
            .orElseGet(() -> {
                    newTask.setId(id);
                    return repository.save(newTask);
                });

        Resource<Task> resource = assembler.toResource(updatedTask);

        return ResponseEntity
            .created(new URI(resource.getId().expand().getHref()))
            .body(resource);
    }

    @DeleteMapping("/tasks/{id}")
    void deleteTask(@PathVariable Long id) {

        repository.deleteById(id);
    }
}
