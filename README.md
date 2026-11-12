# DIT044 Assignment 1

Group 11:
Joud Jalloul, Isak Behre, Mohammad Sohrabi

## About the project
We started with two duplicated classes, Volvo240 and Saab95, and turned them into a clean inheritance hierarchy. The cars can now move on a 2D plane, have unit tests, and follow speed rules.

## What did we do?
Task 0: Setup. We forked the repository, cloned it into IntelliJ, and added all group members as collaborators.

Task 1: We moved the shared code from Volvo240 and Saab95 into an abstract `Car` class. Each car keeps only what's special to it and has its own `speedFactor()`. Fields are `private`, and only the methods a driver needs are `public`.

Task 2: Movable. `Car` implements the `Movable` interface. Each car has a position and a direction, and `move()` changes the position by the current speed.

Task 3: Testing. We wrote tests for the engine, the turbo, doors, colors, movement, and turning.

Task 4: Sanity checks. `gas()` and `brake()` only accept values from 0 to 1, and the speed always stays between 0 and the engine power. We wrote each test first, saw it fail, then fixed the code.

## How did we split the work?
Joud: Task 0, Task 1, and Task 4.

Isak: Task 1, Task 3 and Task 4.

Mohammad: Task 2 and Task 4.

## Meetings

We had two group meetings. In the first, we went through the code and planned how to split the
tasks. In the second, we finished Task 4 together, merged everyone's work, and made sure all
tests pass.

## How to run

Open the project in IntelliJ with JDK 25 or newer, then run `CarTest.main()`.
All 22 tests should print `SUCCESS`.