// package com.github.nikson.dpf

// Concrete command TestB
public class CommandTestB implements Command {

    Receiver receiver;

    public CommandTestB(Receiver r) {
        this.receiver = r;
    }

    public void Execute() {
        System.out.println("Executing command B");
        receiver.testB();
    }
}
