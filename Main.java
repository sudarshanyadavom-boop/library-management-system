import java.util.*;

class LibraryException extends Exception {
    LibraryException(String msg) { super(msg); }
}

interface Borrowable {
    void issueItem() throws LibraryException;
    void returnItem();
    double calculateFine(int lateDays);
}

abstract class LibraryItem implements Borrowable {
    private int id;
    private String title;
    private boolean issued = false;

    LibraryItem(int id, String title) { this.id = id; this.title = title; }

    int getId() { return id; }
    String getTitle() { return title; }
    boolean isIssued() { return issued; }

    public void issueItem() throws LibraryException {
        if (issued) throw new LibraryException("'" + title + "' is already issued.");
        issued = true;
    }

    public void returnItem() { issued = false; }

    abstract void displayInfo();
}

class Book extends LibraryItem {
    private String author;
    Book(int id, String title, String author) { super(id, title); this.author = author; }

    void displayInfo() {
        System.out.printf("%-4d %-9s %-30s %-18s %s%n", getId(), "Book", getTitle(), author, isIssued() ? "Issued" : "Available");
    }
    public double calculateFine(int lateDays) { return lateDays * 5; }
}

class Magazine extends LibraryItem {
    private String month;
    Magazine(int id, String title, String month) { super(id, title); this.month = month; }

    void displayInfo() {
        System.out.printf("%-4d %-9s %-30s %-18s %s%n", getId(), "Magazine", getTitle(), month, isIssued() ? "Issued" : "Available");
    }
    public double calculateFine(int lateDays) { return lateDays * 2; }
}

abstract class Member {
    String name;
    ArrayList<LibraryItem> borrowed = new ArrayList<>();
    Member(String name) { this.name = name; }
    abstract int getLimit();
}

class StudentMember extends Member {
    StudentMember(String name) { super(name); }
    int getLimit() { return 2; }
}

class FacultyMember extends Member {
    FacultyMember(String name) { super(name); }
    int getLimit() { return 5; }
}

class Library {
    ArrayList<LibraryItem> items = new ArrayList<>();
    HashMap<Integer, Member> members = new HashMap<>();
    double totalFine = 0;

    void displayAll() {
        for (LibraryItem item : items) item.displayInfo();
    }

    LibraryItem searchItem(int id) {
        for (LibraryItem item : items)
            if (item.getId() == id) return item;
        return null;
    }

    void searchItem(String keyword) {
        for (LibraryItem item : items)
            if (item.getTitle().toLowerCase().contains(keyword.toLowerCase())) item.displayInfo();
    }

    void issue(int memberId, int itemId) {
        Member m = members.get(memberId);
        LibraryItem item = searchItem(itemId);
        try {
            if (m.borrowed.size() >= m.getLimit())
                throw new LibraryException("Limit of " + m.getLimit() + " items reached for " + m.name + ".");
            item.issueItem();
            m.borrowed.add(item);
            System.out.println("Issued '" + item.getTitle() + "' to " + m.name);
        } catch (LibraryException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    void returnItem(int memberId, int itemId, int lateDays) {
        Member m = members.get(memberId);
        LibraryItem item = searchItem(itemId);
        item.returnItem();
        m.borrowed.remove(item);
        double fine = item.calculateFine(lateDays);
        totalFine += fine;
        System.out.println("Returned '" + item.getTitle() + "'. Fine: Rs " + fine);
    }

    void displayMembers() {
        for (int id : members.keySet()) {
            Member m = members.get(id);
            System.out.println(id + "  " + m.name + "  Borrowed: " + m.borrowed.size() + "/" + m.getLimit());
        }
    }

    void report() {
        int issued = 0;
        for (LibraryItem item : items)
            if (item.isIssued()) issued++;
        System.out.println("Total items: " + items.size() + ", Issued: " + issued + ", Available: " + (items.size() - issued));
        System.out.println("Fine collected: Rs " + totalFine);
    }
}

public class Main {
    public static void main(String[] args) {
        Library lib = new Library();
        lib.items.add(new Book(101, "Java: The Complete Reference", "Herbert Schildt"));
        lib.items.add(new Book(102, "Head First Java", "Kathy Sierra"));
        lib.items.add(new Book(103, "Let Us C", "Yashavant Kanetkar"));
        lib.items.add(new Magazine(201, "Digit", "October 2026"));
        lib.members.put(1, new StudentMember("Aarav Patil"));
        lib.members.put(2, new FacultyMember("Prof. R. Deshmukh"));

        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n1.Display  2.Add Book  3.Search  4.Issue  5.Return  6.Members  7.Report  0.Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            switch (choice) {
                case 1: lib.displayAll(); break;
                case 2:
                    System.out.print("Enter ID, title, author (one per line): ");
                    int id = sc.nextInt(); sc.nextLine();
                    lib.items.add(new Book(id, sc.nextLine(), sc.nextLine()));
                    System.out.println("Book added.");
                    break;
                case 3:
                    System.out.print("Enter keyword: ");
                    lib.searchItem(sc.next());
                    break;
                case 4:
                    System.out.print("Enter member ID and item ID: ");
                    lib.issue(sc.nextInt(), sc.nextInt());
                    break;
                case 5:
                    System.out.print("Enter member ID, item ID, late days: ");
                    lib.returnItem(sc.nextInt(), sc.nextInt(), sc.nextInt());
                    break;
                case 6: lib.displayMembers(); break;
                case 7: lib.report(); break;
                case 0: System.out.println("Thank you!"); break;
                default: System.out.println("Invalid choice.");
            }
        } while (choice != 0);
           sc.close();
    }
}
