// package com.github.nikson.dpf

import java.util.ArrayDeque;
import java.util.Queue;

// Invoker object: command invoke without knowing the concrete command
public class Invoker {
    // keep the execution order or track of commands
    private Queue<Command> history = new ArrayDeque<>();

    void addCommand(Command c) {
        history.add(c);
    }

    void invoke() {
        if (!history.isEmpty()) {
            history.remove().Execute();
        }
    }

    void invokeAll() {
        while (!history.isEmpty()) {
            history.remove().Execute();
        }
    }
}
