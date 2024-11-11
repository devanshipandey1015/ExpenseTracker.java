// we have used Swing for gui.

// this will import all swing packages.
import javax.swing.*;

// this will import empty borders, it helped us in keeping empty border around our components.
import javax.swing.border.EmptyBorder;

//  this will import the default table model class, we used this for managing data in our table.
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;



// Parent class for transactions
class Transaction 
{
    String date;
    double amount;

    // constructor for transaction
    Transaction(String date, double amount)
    {
        this.date = date;
        this.amount = amount;
    }
}

// Derived class for Expenses
class Expense extends Transaction 
{
    String type;

    // constructor for expenses
    Expense(String date, String type, double amount)
    {
        super(date, amount);
        this.type = type;
    }

    
    public String toString() 
    {
        return String.format("%-15s %-15s ₹%.2f", date, type, amount);


        // string.format is used to create a formatted string based on specified type.
        // - is for left aligned, 15 for characters, s for string : DATE
        // - again we used for left aligned, 15 for characaters, s for string: TYPE
        // .2 is for 2 decimal spaces. f is for float: AMOUNT
    }
}



// Interface for Expense operations
interface ExpenseOps
{
    // only declaring methods here. 
    
    void addExpense();
    void editExpense();
    void deleteExpense();
    void viewTotal();
    void viewByCategory();
}

// Expense Tracker that implements ExpenseOps interface

public class ExpenseTracker extends JFrame implements ExpenseOps 
{
    ArrayList<Expense> expenses = new ArrayList<>();

    // to make cells non-editable on clicking them.
    
    DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"Date", "Type", "Amount"}, 0)
    {
        public boolean isCellEditable(int row, int column)
        {
            return false; 
        }
    };


    
    JTable expenseTable = new JTable(tableModel);
    String[] expenseTypes = {"Food", "Grocery", "Electricity Bills", "Telephone Bills", "Travel", "Miscellaneous"};

    
    public ExpenseTracker() 
    {
        setTitle("Expense Tracker");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(mainPanel);

        // Button panel for adding new expenses
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 5, 5, 5));

        // Creating buttons for our ops
        JButton addButton = new JButton("Add Expense");
        addButton.setBackground(new Color(255, 182, 193));

        JButton editButton = new JButton("Edit Expense");
        editButton.setBackground(new Color(219, 179, 255));

        JButton deleteButton = new JButton("Delete Expense");
        deleteButton.setBackground(new Color(173, 216, 230));

        JButton viewButton = new JButton("View Total");
        viewButton.setBackground(new Color(255, 236, 179));

        JButton categoryButton = new JButton("View by Category");
        categoryButton.setBackground(new Color(144, 238, 144));


        
        // Adding buttons to the panel
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(categoryButton);

        mainPanel.add(buttonPanel, BorderLayout.NORTH);


        
        // Create a new JPanel for the table 
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        
        // Makes the table background fill the entire viewport height
        // viewport is basically what we seeing and scrolling component is
        expenseTable.setFillsViewportHeight(true);

        tablePanel.add(new JScrollPane(expenseTable), BorderLayout.CENTER);
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        // Add action listeners for the buttons
        
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addExpense();
            }
        });

        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editExpense();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteExpense();
            }
        });

        viewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewTotal();
            }
        });

        categoryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewByCategory();
            }
        });

        setVisible(true);
    }

    // Method to add a new expense
    public void addExpense()
    {
        String date = JOptionPane.showInputDialog("Enter Date (DD/MM/YYYY):");
        if (!isValidDate(date))
        {
            JOptionPane.showMessageDialog(this, "Invalid date format. Please use DD/MM/YYYY.");
            return; 
        }

        
        String type = (String) JOptionPane.showInputDialog(this, "Select Expense Type:", "Expense Type",
                JOptionPane.QUESTION_MESSAGE, null, expenseTypes, expenseTypes[0]);
        String amountStr = JOptionPane.showInputDialog("Enter Amount:");

        if (isValidAmount(amountStr) && type != null && !amountStr.isEmpty()) 
        {
            double amount = Double.parseDouble(amountStr);
            Expense expense = new Expense(date, type, amount);
            expenses.add(expense);
            tableModel.addRow(new Object[]{expense.date, expense.type, expense.amount});
            JOptionPane.showMessageDialog(this, "Expense added successfully!");
        } 
        else
        {
            JOptionPane.showMessageDialog(this, "Invalid input. Please try again.");
        }
    }

    // Method to edit an expense
    public void editExpense()
    {
        int selectedRow = expenseTable.getSelectedRow();
        if (selectedRow >= 0) 
        {
            String date = JOptionPane.showInputDialog("Enter new Date (DD/MM/YYYY):", expenses.get(selectedRow).date);
            if (!isValidDate(date)) 
            {
                JOptionPane.showMessageDialog(this, "Invalid date format. Please use DD/MM/YYYY.");
                return; 
                
            }

            
            String type = (String) JOptionPane.showInputDialog(this, "Select new Expense Type:", "Expense Type",
                    JOptionPane.QUESTION_MESSAGE, null, expenseTypes, expenses.get(selectedRow).type);
            String amountStr = JOptionPane.showInputDialog("Enter new Amount:", expenses.get(selectedRow).amount);

            
            if (isValidAmount(amountStr) && type != null && !amountStr.isEmpty()) 
            
            {
                double amount = Double.parseDouble(amountStr);
                expenses.set(selectedRow, new Expense(date, type, amount));
                tableModel.setValueAt(date, selectedRow, 0);
                tableModel.setValueAt(type, selectedRow, 1);
                tableModel.setValueAt(amount, selectedRow, 2);
                JOptionPane.showMessageDialog(this, "Expense updated successfully!");
            } 
            
            else
            
            {
                JOptionPane.showMessageDialog(this, "Invalid input. Please try again.");
            }
        } 
        
        else 
        {
            JOptionPane.showMessageDialog(this, "Please select an expense to edit.");
        }
    }

    // Method to delete an expense
    public void deleteExpense()
    
    {
        int selectedRow = expenseTable.getSelectedRow();
        if (selectedRow >= 0)
        
        {
            expenses.remove(selectedRow);
            tableModel.removeRow(selectedRow);
            JOptionPane.showMessageDialog(this, "Expense deleted successfully!");
        } 
        
        else
        {
            JOptionPane.showMessageDialog(this, "Please select an expense to delete.");
        }
    }

    // Method to view total expenses
    public void viewTotal()
    {
        double total = 0;
        for (Expense expense : expenses)
            
        {
            total += expense.amount;
        }
        JOptionPane.showMessageDialog(this, "Total Expenses: ₹" + total);
    }

    // Method to view expenses by category
    public void viewByCategory() 
    
    {
        String selectedType = (String) JOptionPane.showInputDialog(this, "Select Expense Type to View:", "View by Category",
                JOptionPane.QUESTION_MESSAGE, null, expenseTypes, expenseTypes[0]);
        if (selectedType != null)
        
        {
            StringBuilder categories = new StringBuilder();
            for (Expense expense : expenses)
                {
                if (expense.type.equals(selectedType)) 
                {
                    categories.append(expense).append("\n");
                }
            }
            if (categories.length() > 0)
            
            {
                JOptionPane.showMessageDialog(this, "Expenses in " + selectedType + ":\n" + categories.toString());
            } 
            
            else 
            
            
            {
                JOptionPane.showMessageDialog(this, "No expenses found for the selected category.");
            }
        }
    }

    // Method to validate the input date
    private boolean isValidDate(String date)
    
    {
        if (date == null || date.isEmpty()) return false;
        String[] parts = date.split("/");
        if (parts.length != 3) return false;
        int day, month, year;
        try
            
            {
            day = Integer.parseInt(parts[0]);
            month = Integer.parseInt(parts[1]);
            year = Integer.parseInt(parts[2]);
        } 
        
        catch (NumberFormatException e)
            
            {
            return false;
        }

        return (day > 0 && day <= 31 && month > 0 && month <= 12);
    }

    // Method to check negative amount input
    
    private boolean isValidAmount(String amountStr) 
    
    {
        try {
            double amount = Double.parseDouble(amountStr);
            return amount > 0; 
        } 
        
        catch (NumberFormatException e)
            
            {
            return false;
        }
    }

    // Main method to run the application
    public static void main(String[] args) {
        new ExpenseTracker();
    }
}
