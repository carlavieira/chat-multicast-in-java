[![Work in Repl.it](https://classroom.github.com/assets/work-in-replit-14baed9a392b3a25080506f3b7b6d57f295ec2978f6f33ec97e36a161684cbe9.svg)](https://classroom.github.com/online_ide?assignment_repo_id=2965005&assignment_repo_type=AssignmentRepo)


# Chat Multicast in Java 

A proposed academic activity to practice network programming using sockets.

## Compilation

The javac tool reads class and interface definitions, written in the Java programming language, and compiles them into bytecode class files

```bash
javac *.java
```

## Server

To start the server, you must give the address of the network interface used for multicast packets, for example performing the command: 

```bash
java Server 228.5.6.7
```

The server will receive the messages from the clients and control the participants list.

## Client

For clients, you must give the the client identification name and the address of the network interface used for multicast packets, for example performing the command:

```bash
java Client "Client_name" 228.5.6.7
```

To leave the room the client should write "Sair".
