package com.badegrsy.TodoList;

class TaskNotFoundException extends RuntimeException {

    TaskNotFoundException(Long id) {
        super("Could not find task with id " + id);
    }
}
