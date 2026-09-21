import java.util.List;
import java.util.Scanner;
 
/**
 * Console application for the Hospital Emergency Triage System.
 */
public class HospitalTriageSystem {
 
    private static final Scanner in = new Scanner(System.in);
    private static final TriageQueue triage = new TriageQueue();
 
    public static void main(String[] args) {
        int choice;
        do {
            printMenu();
            choice = readInt("Enter your choice: ", 1, 4);
            switch (choice) {
                case 1: addPatient();  break;
                case 2: treatNext();   break;
                case 3: viewWaiting(); break;
                case 4: System.out.println("\nExiting system."); break;
            }
        } while (choice != 4);
    }
 
    private static void printMenu() {
        System.out.println("\n===== HOSPITAL EMERGENCY TRIAGE SYSTEM =====");
        System.out.println("1. Add patient");
        System.out.println("2. Treat next patient");
        System.out.println("3. View waiting list");
        System.out.println("4. Exit");
        System.out.println("============================================");
    }
 
    private static void addPatient() {
        String name = readText("Patient name: ");
        int age = readInt("Age: ", 0, 120);
        String condition = readText("Condition: ");
        int severity = readInt("Severity (1=Minor ... 5=Critical): ", 1, 5);
 
        Patient p = new Patient(name, age, condition, severity);
        triage.addPatient(p);
        System.out.println("Added: " + p);
    }
 
    private static void treatNext() {
        Patient p = triage.treatNext();
        if (p == null) {
            System.out.println("No patients are waiting.");
        } else {
            System.out.println("Treating: " + p);
            System.out.println("Patients still waiting: " + triage.size());
        }
    }
 
    private static void viewWaiting() {
        List<Patient> list = triage.getWaitingList();
        if (list.isEmpty()) {
            System.out.println("The waiting list is empty.");
            return;
        }
        System.out.println("\n--- Waiting list (treatment order) ---");
        int pos = 1;
        for (Patient p : list) {
            System.out.println(pos++ + ". " + p);
        }
    }
 
    /** Reads a non-empty line of text, asking again if the input is blank. */
    private static String readText(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = in.nextLine().trim();
            if (!line.isEmpty()) return line;
            System.out.println("This field cannot be empty.");
        }
    }
 
    /** Reads an integer within [min, max], asking again on invalid input. */
    private static int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String line = in.nextLine().trim();
            try {
                int value = Integer.parseInt(line);
                if (value >= min && value <= max) return value;
            } catch (NumberFormatException ignored) { }
            System.out.println("Please enter a number between " + min
                    + " and " + max + ".");
        }
    }
}
