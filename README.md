# 📚 Library Management System (Java)

A menu-driven console application that manages books, magazines and members of a college library.
Built as a **mini project for the Java Programming course** — **no database is used**; all data is stored in memory using Java Collections.

## Problem Statement
Many small college or department libraries still track books in registers. This makes it hard to know which books are available, who has borrowed what, and how much fine is due. This project automates these tasks in a simple Java program.

## Features
1. Display all items (books and magazines) with their availability status
2. Add a new book
3. Search items by title keyword
4. Issue an item to a member, with a borrowing limit (Student: 2, Faculty: 5)
5. Return an item and calculate the late fine automatically (Book: ₹5/day, Magazine: ₹2/day)
6. Display all members and how many items each has borrowed
7. Library report: total, issued and available items, and total fine collected

## OOP Concepts Used
| Concept | Where it is used |
|---|---|
| **Abstraction** | Abstract class `LibraryItem`, abstract class `Member`, interface `Borrowable` |
| **Inheritance** | `Book`, `Magazine` extend `LibraryItem`; `StudentMember`, `FacultyMember` extend `Member` |
| **Runtime Polymorphism** | Overridden `displayInfo()`, `calculateFine()`, `getLimit()` |
| **Compile-time Polymorphism** | Overloaded `searchItem(int id)` and `searchItem(String keyword)` |
| **Encapsulation** | Private fields of `LibraryItem`, `Book`, `Magazine` accessed through getter methods |
| **Exception Handling** | Custom exception `LibraryException` (item already issued, borrowing limit reached) |
| **Collections** | `ArrayList<LibraryItem>` for items, `HashMap<Integer, Member>` for members |

## Class Structure
```
Borrowable (interface)
   └── LibraryItem (abstract)
          ├── Book
          └── Magazine

Member (abstract)
   ├── StudentMember
   └── FacultyMember

Library  → manages ArrayList of items and HashMap of members
Main     → menu-driven user interface
```

## How to Run
**Option 1 – Command line (JDK 8 or above)**
```bash
javac Main.java
java Main
```
**Option 2 – Online compiler:** paste `Main.java` into any online Java compiler (e.g. OnlineGDB, Programiz) and run.

## Sample Output
```
1.Display  2.Add Book  3.Search  4.Issue  5.Return  6.Members  7.Report  0.Exit
Enter choice: 4
Enter member ID and item ID: 1 101
Issued 'Java: The Complete Reference' to Aarav Patil

Enter choice: 4
Enter member ID and item ID: 1 201
Error: Limit of 2 items reached for Aarav Patil.

Enter choice: 5
Enter member ID, item ID, late days: 1 101 3
Returned 'Java: The Complete Reference'. Fine: Rs 15.0
```
<!-- Add your own screenshots here: ![Menu](screenshots/menu.png) -->

## Future Scope
- Store data permanently using file handling or a database (MySQL / JDBC)
- Graphical interface using Java Swing / JavaFX
- Due-date tracking using `java.time.LocalDate`
- Login system for librarian and members

## Author
Sudarshan yadav– S.Y. B.Tech CSE, JSPM University Pune