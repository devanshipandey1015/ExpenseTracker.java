import java.util.ArrayList;
import java.util.Scanner;

// Parent class that will be used for other classes. All classes will be derived from here. //

class Transaction {
    String Date;
    double Amount;

    // Constructor for transaction class // 

    Transaction(String Date, double Amount) {
        this.Date = Date;
        this.Amount = Amount;
    }
}

// Deriving a child class from Transaction, using super keyword to get get Date and Amount from base class. //

class Expense extends Transaction {
    String Type;

    // Constructor for child class //

    Expense(String Date, String Type, double Amount) 
{
        super(Date, Amount);
        this.Type = Type;
    }

    // Defining how the object's data appears for better readability //


    public String toString() 
{
        return String.format("%-15s %-15s ₹%.2f", Date, Type, Amount);
    }
}




// Interface for operations for defining methods. //


interface ExpenseOps 
{
    void addExpense(); // adding exp to the list
    void editExpense(); // editing exp from the list
    void deleteExpense(); // deleting exp from the list
    void viewExpense(); // viewing the list
    void viewTotal(); // getting total exp
    void giveFeedback();  // taking user input for feedback
}

// Expense manager that uses the interface  //


class ExpenseManager implements ExpenseOps
 {
    ArrayList<Expense> expenses = new ArrayList<>();
    Scanner sc = new Scanner(System.in);

    // Method to get input for expense. it does not allow negative value //


    public void addExpense() {
        System.out.println("Enter the date (dd/mm/yyyy): ");
        String Date = sc.nextLine();

        System.out.println("Enter the type of expense: ");
        String Type = sc.nextLine();

        double Amount;
        while (true) 
{
            System.out.println("Enter the amount in rupees: ");
            Amount = sc.nextDouble();
            sc.nextLine(); 

            // here we are checking for negative input to make sure user enters only positive number. if negative number is entered it will print the else block. //

            if (Amount >= 0)
 {
                break; 
            } 

else 
{
                System.out.println("Amount cannot be negative. Please enter a valid amount.");
            }
        }


        expenses.add(new Expense(Date, Type, Amount));
        System.out.println("Expense has been added.");
    }






    // Method to edit an expense. here also we have added negative checking because user will be entering expense here as well. so we should be able to restrict it. //


    public void editExpense() {
        viewExpense(); // View is called to display expense to user before editing so that the user chooses the right option to edit. //

        System.out.println("Enter the index of the expense to edit: ");
        int index = sc.nextInt() - 1;
        sc.nextLine();

        if (index >= 0 && index < expenses.size()) {
            System.out.println("Enter new date (dd/mm/yyyy): "); // we are working on restricting the date format also. right now it does not have any restrictions. //

            String Date = sc.nextLine();




            System.out.println("Enter new type of expense: ");
            String Type = sc.nextLine();

            double Amount;
            while (true) {
                System.out.println("Enter new amount: ");
                Amount = sc.nextDouble();
                sc.nextLine(); 



                // Check for negative input here also as the user is entering a new expense. //
                if (Amount >= 0) 
{
                    break;

                } 
else 
{
                    System.out.println("Amount cannot be negative. Please enter a valid amount.");
                }
            }

            expenses.set(index, new Expense(Date, Type, Amount));
            System.out.println("The expense has been updated.");
        } 
else 
{
            System.out.println("Invalid index.");
        }
    }

    // Method to delete an expense if the user chooses this option //


    public void deleteExpense() {
        viewExpense(); // So the user can view the expense before deleting it. //


        System.out.println("Enter the index of the expense to delete: ");
        int index = sc.nextInt() - 1;
        sc.nextLine(); 

        if (index >= 0 && index < expenses.size())
 {
            expenses.remove(index);
            System.out.println("Expense Deleted.");
        } 
else 
{
            System.out.println("Invalid index.");
        }
    }

    // Method to view all expenses. this method is called in the edit and delete methods also. so it is very useful. //


    public void viewExpense() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded.");
        } else {
            System.out.printf("%-5s %-15s %-15s %-15s\n", "No.", "Date", "Type", "Amount");
// this format is used for better readability of the code. //

            for (int i = 0; i < expenses.size(); i++) {
                System.out.printf("%-5d %s\n", (i + 1), expenses.get(i).toString());
            }
        }
    }

    // Method to get the total of all the expenses //



    public void viewTotal() {
        double total = 0;
        for (Expense expense : expenses) {
            total = total + expense.Amount;
        }
        System.out.println("Total Expense: ₹" + total);
    }

    // method to get feedback and rating from the user. //


    public void giveFeedback() {
        System.out.println("Enter your feedback: ");
        String feedback = sc.nextLine();

        int rating;
        while (true) {
            System.out.println("Rate your experience (1 to 5): ");
            rating = sc.nextInt();
            sc.nextLine(); 



            // taking rating input from the user //


            if (rating >= 1 && rating <= 5) {
                break; // Exit loop if valid input
            } else {
                System.out.println("Rating must be between 1 and 5. Please enter a valid rating.");
            }
        }

        // we are taking input in string format above and then we print out the feedback and the rating in the end of this method// 


        System.out.println("Thank you for your feedback: " + feedback + " | Rating: " + rating);
    }
}



// Main class
public class ExpenseTracker 
{
    public static void main(String[] args) 
{
        ExpenseManager manager = new ExpenseManager();
        Scanner sc = new Scanner(System.in);

        while (true) 
{
            System.out.println("\n1. Add Expense\n2. Edit Expense\n3. Delete Expense\n4. View Expenses\n5. View Total\n6. Give Feedback\n7. Exit");
            System.out.println("Choose an option.");
            int choice = sc.nextInt();
            sc.nextLine(); 

            switch (choice) 
{
                case 1: 
                    manager.addExpense(); 
                    break;
                case 2:
                    manager.editExpense();
                    break;
                case 3:
                    manager.deleteExpense();
                    break;
                case 4:
                    manager.viewExpense();
                    break;
                case 5:
                    manager.viewTotal();
                    break;
                case 6:
                    manager.giveFeedback(); 
                    break;
                case 7:
                    System.exit(0);
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}
