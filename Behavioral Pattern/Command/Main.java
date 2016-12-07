// package com.github.nikson.dpf

// Command pattern: command, receiver, invoker and client.

// client
public class Main {

    public static void main(String[] args) {
        System.out.println("Command Pattern");

        Receiver receiver = new Receiver();
        Invoker root = new Invoker();

        root.addCommand(new CommandTestA(receiver));
        root.addCommand(new CommandTestB(receiver));

        root.invoke();

        root.addCommand(new CommandTestA(receiver));
        root.addCommand(new CommandTestB(receiver));
        root.invokeAll();
    }
}