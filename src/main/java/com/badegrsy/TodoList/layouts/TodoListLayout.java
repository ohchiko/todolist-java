/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.badegrsy.TodoList.layouts;

import javax.swing.*;
import com.badegrsy.TodoList.TodoListUI;

/**
 *
 * @author gregorio
 */
public final class TodoListLayout {

    protected TodoListUI frame;
    protected JButton btn;

    public TodoListLayout() {
        initFrame();
    }

    protected void initFrame() {
        this.frame = new TodoListUI();
        this.frame.setSize(500, 300);
        this.frame.setTitle("This is a JFrame");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setVisible(true);
    }

    public static void main(String[] args) {
        new TodoListLayout();
    }
}
