package com.badegrsy.TodoList;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
class TaskResourceAssembler implements ResourceAssembler<Task, Resource<Task>> {

    @Override
    public Resource<Task> toResource(Task task) {

        return new Resource<>(task,
                              linkTo(methodOn(TaskController.class).one(task.getId())).withSelfRel(),
                              linkTo(methodOn(TaskController.class).all()).withRel("tasks"));
    }
}
