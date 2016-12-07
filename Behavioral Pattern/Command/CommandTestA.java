// package com.github.nikson.dpf

// Concrete command TestA
public class CommandTestA implements Command {

    Receiver receiver;

    public CommandTestA(Receiver r) {
        this.receiver = r;
    }

    public void Execute() {
        System.out.println("Executing command A");
        receiver.testA();
    }
}
