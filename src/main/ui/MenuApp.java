package ui;

import model.Drink;
import model.Menu;
import model.Order;

import java.util.ArrayList;
import java.util.Scanner;

public class MenuApp {

    private Menu menu;
    private ArrayList<Order> orderLog;
    private int managerPasscode;
    private Scanner input;
    private String special1;
    private String special2;

    public MenuApp() {
        runMenu();
    }

    private void runMenu() {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayOptions();
            command = input.next().toLowerCase();
            ;

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nTill next time!");
    }

    private void init() {
        managerPasscode = 1234;
        orderLog = new ArrayList<Order>();
        menu = new Menu("","");
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    private void displayOptions() {
        System.out.println("\nChoose From:");
        System.out.println("\to -> Order Drinks");
        System.out.println("\tv -> View Stats");
        System.out.println("\ts -> Set New Specials");
        System.out.println("\tq -> Quit");
    }

    private void processCommand(String command) {
        if (command.equals("o")) {
            orderDrinks();
        } else if (command.equals("v")) {
            viewStats();
        } else  if (command.equals("s")) {
            setNewSpecials();
        } else {
            System.out.println("Option not available");
        }
    }

    private void orderDrinks() {
        Order currentOrder = new Order();
        boolean continueOrder = true;

        while (continueOrder) {
            Drink drink = getDrink();
            if (drink != null) {
                chooseDrinkOptions(drink, currentOrder);
            } else {
                System.out.println("\nDrink Not found");
            }

            System.out.println("\nAdd more drinks?");
            System.out.println("\ty -> Yes");
            System.out.println("\tn -> No");
            String selectedOption = input.next().toLowerCase();
            if (selectedOption.equals("n")) {
                continueOrder = false;
            }
        }

        printDrinksOrdered(currentOrder);

    }

    private Drink getDrink() {
        System.out.println("\nChoose Drink: ");
        for (Drink drink : menu.getDrinksOrdered()) {
            System.out.println(drink.getName());
        }
        String drinkName = input.next().toLowerCase();
        Drink drink = new Menu(special1, special2).findDrink(drinkName);
        return drink;
    }

    //Effects: Chooses the options for the given drink, orders the drink
    private void chooseDrinkOptions(Drink drink, Order order) {
        System.out.println("\nChoose Size (s,l): ");
        String size = input.next();
        System.out.println("\nChoose Ice (0,0.5,1): ");
        double ice = input.nextDouble();
        System.out.println("\nChoose Sugar (0,0.25,0.5,0.75,1): ");
        double sugar = input.nextDouble();

        ArrayList<String> exTop = new ArrayList<String>();
        boolean moreTop = true;
        while (exTop.size() <= 2 && moreTop == true) {
            System.out.println("\nAny extra toppings? (y,n) ");
            String option = input.next();
            if (option.equals("y")) {
                System.out.println("\n Choose Topping: ");
                String top = input.next();
                exTop.add(top);
            } else {
                moreTop = false;
            }
        }
        System.out.println(drink.getName());
        order.orderDrink(size, exTop, ice, sugar, drink);
    }

    //Effects: Prints the drinks in the current order, prints the price pre-tax
    //         Applies tax to the price, prints post-tax price, adds order to order log
    private void printDrinksOrdered(Order currentOrder) {
        System.out.println("\nDrinks Ordered: ");
        for (Drink drink : currentOrder.getDrinksOrdered()) {
            if (drink.getName().equals("classic milk tea")) {
                System.out.println("it worked");
            }
            System.out.println(drink.getName());
        }
        System.out.println("\nTotal Price Pre-tax: ");
        System.out.println(currentOrder.getTotalPrice());
        currentOrder.applyTax();
        System.out.println("\nTotal Price Post-tax: ");
        System.out.println(currentOrder.getTotalPrice());
        orderLog.add(currentOrder);
    }

    private void viewStats() {
        System.out.println("Manager Passcode:");
        int code = input.nextInt();
        if (code == managerPasscode) {
            System.out.println("\nWhich stat would you like to view?");
            System.out.println("\ti -> ingredients");
            System.out.println("\tp -> price");
            String selectedOption = input.next().toLowerCase();
            if (selectedOption.equals("i")) {
                displayIngredientStats();
            } else if (selectedOption.equals("p")) {
                displayPriceStats();
            } else {
                System.out.println("Selection not valid");
            }
        }
    }

    private void displayPriceStats() {
        int numDrinksOrdered = 0;
        double totalPriceAllDrinks = 0;
        for (Order order: orderLog) {
            numDrinksOrdered += order.getDrinksOrdered().size();
            totalPriceAllDrinks += order.getTotalPrice();
        }
        double averageOrderPrice = totalPriceAllDrinks / orderLog.size();
        System.out.println("\nNumber of Drinks Ordered:");
        System.out.println(numDrinksOrdered);
        System.out.println("\nTotal Revenue:");
        System.out.println(totalPriceAllDrinks);
        System.out.println("\nAverage Price per Order:");
        System.out.println(averageOrderPrice);
    }

    private void displayIngredientStats() {
        System.out.println("\nWhat ingredient would you like to view?");
        String ingredient = input.next().toLowerCase();
        int ingredientAmount = 0;
        for (Order order : orderLog) {
            ingredientAmount += order.getIngredientAmount(ingredient);
        }
        System.out.println("\nAmount used:");
        System.out.println(ingredientAmount);

    }

    private void setNewSpecials() {
        System.out.println("Manager Passcode:");
        int code = input.nextInt();
        if (code == managerPasscode) {
            System.out.println("First Special?");
            String firstSpecial = input.next().toLowerCase();
            System.out.println("Second Special?");
            String secondSpecial = input.next().toLowerCase();
            special1 = firstSpecial;
            special2 = secondSpecial;
        } else {
            System.out.println("Invalid Code");
        }
    }
}
