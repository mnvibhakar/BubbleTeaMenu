package ui;

import model.*;

import model.Menu;
import model.exceptions.DuplicateNameException;
import model.persistence.JsonWriter;
import model.persistence.JsonReader;

import javax.swing.*;
import javax.swing.text.View;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/*
Represents the menu app, processes user input to execute the chosen tasks, including:
    -ordering drinks
    -changing specials
    -viewing stats about price and ingredients
 */
public class MenuAppGUI extends JFrame {

    private final String managerPasscode = "1234";
    private static final String MENU_JSON_STORE = "./data/menu.json";
    private static final String ORDERLOGLIST_JSON_STORE = "./data/orderLogList.json";
    private static final String ORDERLOG_JSON_STORE = "./data/orderLog.json";
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 600;

    private Menu menu = new Menu();
    private Drink currentDrink;
    private OrderLog orderLog;
    private OrderLogList orderLogList;

    private JsonWriter menuWriter;
    private JsonWriter orderLogListWriter;
    private JsonWriter orderLogWriter;
    private JsonReader menuReader;
    private JsonReader orderLogListReader;
    private JsonReader orderLogReader;

    private JPanel homePanel;
    private JPanel managerPanel;
    private JPanel ordersPanel;

    public static void main(String[] args) {
        new MenuAppGUI();
    }

    public MenuAppGUI() {
        initJsonHandling();
        initGUI();
    }

    public void initJsonHandling() {
        menuWriter = new JsonWriter(MENU_JSON_STORE);
        menuReader = new JsonReader(MENU_JSON_STORE);
        orderLogListWriter = new JsonWriter(ORDERLOGLIST_JSON_STORE);
        orderLogListReader = new JsonReader(ORDERLOGLIST_JSON_STORE);
        orderLogWriter = new JsonWriter(ORDERLOG_JSON_STORE);
        orderLogReader = new JsonReader(ORDERLOG_JSON_STORE);
        try {
            orderLog = orderLogReader.readOrderLog();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Unable to read from file: " + ORDERLOG_JSON_STORE, "ORDERLOG",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void initGUI() {
        homePanel = new HomePanel();
        ordersPanel = new OrdersPanel();
        managerPanel = new ManagerPanel();

        setTitle("Bubble Tea Menu");
        setSize(WIDTH, HEIGHT);
        addMenuBar();

        setContentPane(homePanel);
        setVisible(true);
    }

    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            try {
                orderLogWriter.open();
                orderLogWriter.writeOrderLog(orderLog);
                orderLogWriter.close();
            } catch (FileNotFoundException exception) {
                JOptionPane.showMessageDialog(null,
                        "Could not write to file " + ORDERLOG_JSON_STORE, "ORDERLOG",
                        JOptionPane.ERROR_MESSAGE);
            }
            System.exit(0);
        }
    }

    private void addMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(new JMenuItem(new GoToHomeAction()));
        menuBar.add(new JMenuItem(new GoToMenuAction()));
        menuBar.add(new JMenuItem(new GoToOrdersAction()));
        menuBar.add(new JMenuItem(new GoToManagerAction()));
        setJMenuBar(menuBar);
    }

    private void centreOnScreen() {
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        setLocation((width - getWidth()) / 2, (height - getHeight()) / 2);
    }

    private void switchPanel(JPanel panel) {
        setContentPane(panel);
        repaint();
        revalidate();
    }

    //Effects: opens the menu by reading it from the json file
    private void openMenu() {
        try {
            menu = menuReader.readMenu();
        } catch (IOException e) {
            System.out.println("Unable to read from file " + MENU_JSON_STORE);
        }
    }

    //Effects: Saves the menu to the selected json file
    private void saveMenu() {
        try {
            menuWriter.open();
            menuWriter.writeMenu(menu);
            menuWriter.close();
            System.out.println("Saved new menu to " + MENU_JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file " + MENU_JSON_STORE);
        }
    }

    private class HomePanel extends JPanel {
        HomePanel() {
        }
    }

    private class MenuPanel extends JPanel {
        MenuPanel() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            openMenu();
            for (Drink d : menu.getDrinks()) {
                currentDrink = d;
                add(new JButton(new OrderDrinkAction()));
            }
            currentDrink = null;
        }
    }

    private class OrdersPanel extends JPanel {
        OrdersPanel() {

        }
    }

    private class ManagerPanel extends JPanel {
        ManagerPanel() {
            add(new JButton(new AddDrinkToMenuAction()));
            add(new JButton((new SetNewSpecialsAction())));
            add(new JButton(new ViewStatsAction()));
            add(new JButton(new StartNewOrderLogAction()));
        }
    }

    private class GoToHomeAction extends AbstractAction {
        GoToHomeAction() {
            super("home");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            switchPanel(homePanel);
        }
    }

    private class DesktopFocusAction extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            MenuAppGUI.this.requestFocusInWindow();
        }
    }

    private class GoToMenuAction extends AbstractAction {
        GoToMenuAction() {
            super("Menu");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            switchPanel(new MenuPanel());
        }
    }

    private class GoToManagerAction extends AbstractAction {
        GoToManagerAction() {
            super("Manager Actions");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String inputCode = JOptionPane.showInputDialog(null,
                    "passcode?",
                    "Enter code",
                    JOptionPane.QUESTION_MESSAGE);
            if (inputCode.equals(managerPasscode)) {
                switchPanel(managerPanel);
            }
        }
    }

    private class GoToOrdersAction extends AbstractAction {
        GoToOrdersAction() {
            super("Orders");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            switchPanel(ordersPanel);
        }
    }

    private class OrderDrinkAction extends AbstractAction {
        OrderDrinkAction() {
            super(currentDrink.getName());
        }

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class AddDrinkToMenuAction extends AbstractAction {
        AddDrinkToMenuAction() {
            super("Add New Drink");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String drinkName = JOptionPane.showInputDialog(null,
                    "Name of Drink", JOptionPane.QUESTION_MESSAGE).toLowerCase();
            double price = Double.valueOf(JOptionPane.showInputDialog(null,
                    "Price of Drink?", JOptionPane.QUESTION_MESSAGE));
            openMenu();
            ArrayList<Ingredient> ingredients;
            ingredients = new ArrayList<>();
            chooseIngredients(ingredients);
            Drink newDrink = new Drink(drinkName, price, ingredients, false);
            openMenu();
            try {
                menu.addDrink(newDrink);
            } catch (DuplicateNameException exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage(),
                        "Menu", JOptionPane.ERROR_MESSAGE);
            }
            saveMenu();
            JOptionPane.showMessageDialog(null, "Drink added to menu!");
        }

        private void chooseIngredients(ArrayList<Ingredient> ingredients) {
            boolean keepGoing = true;
            while (keepGoing) {
                int addIngredient = JOptionPane.showConfirmDialog(null, "Add Ingredient?");
                if (addIngredient == JOptionPane.YES_OPTION) {
                    String type = JOptionPane.showInputDialog(null,
                            "Type of Ingredient?", JOptionPane.QUESTION_MESSAGE).toLowerCase();
                    String name = JOptionPane.showInputDialog(null,
                            "Name of Ingredient?", JOptionPane.QUESTION_MESSAGE).toLowerCase();
                    int amount = Integer.valueOf(JOptionPane.showInputDialog(null,
                            "Amount?", JOptionPane.QUESTION_MESSAGE));
                    Ingredient ingredient = new Ingredient(type, name, amount);
                    ingredients.add(ingredient);
                } else {
                    keepGoing = false;
                }
            }
        }
    }

    private class SetNewSpecialsAction extends AbstractAction {
        SetNewSpecialsAction() {
            super("Set New Specials");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String special1 = JOptionPane.showInputDialog(null, "First Special?",
                    "Name of Drink", JOptionPane.QUESTION_MESSAGE).toLowerCase();
            String special2 = JOptionPane.showInputDialog(null, "Second Special?",
                    "Name of Drink", JOptionPane.QUESTION_MESSAGE).toLowerCase();
            openMenu();
            menu.setSpecials(special1, special2);
            saveMenu();
        }
    }

    private class ViewStatsAction extends AbstractAction {
        ViewStatsAction() {
            super("View Stats");
        }

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class StartNewOrderLogAction extends AbstractAction {
        StartNewOrderLogAction() {
            super("Start New Order Log");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                orderLogList = orderLogListReader.readOrderLogList();
            } catch (IOException exception) {
                JOptionPane.showMessageDialog(null,
                        "Unable to read from file: " + ORDERLOGLIST_JSON_STORE,
                        "OrderLogListReader", JOptionPane.ERROR_MESSAGE);
            }
            String newName = JOptionPane.showInputDialog(null, "Name?",
                    JOptionPane.QUESTION_MESSAGE).toLowerCase();
            if (checkName(newName)) {
                saveOrderLog();
                orderLog = new OrderLog(newName);
                JOptionPane.showMessageDialog(null, "Created new order log: " + newName);
            } else {
                JOptionPane.showMessageDialog(null, "An Order Log with that name already exists",
                        "ORDERLOGLIST", JOptionPane.ERROR_MESSAGE);
            }
        }

        private boolean checkName(String name) {
            Boolean nameNotAlreadyUsed = true;
            for (OrderLog o : orderLogList.getOrderLogList()) {
                if (o.getName().equals(name)) {
                    nameNotAlreadyUsed = false;
                }
            }
            return (nameNotAlreadyUsed && !orderLog.getName().equals(name));
        }

        private void saveOrderLog() {
            orderLogList.addOrderLog(orderLog);
            try {
                orderLogListWriter.open();
                orderLogListWriter.writeOrderLogList(orderLogList);
                orderLogListWriter.close();
                JOptionPane.showMessageDialog(null,
                        "Added Previous order log to Order Log List"
                                + "\nSaved Order Log List to " + ORDERLOGLIST_JSON_STORE);
            } catch (FileNotFoundException exception) {
                JOptionPane.showMessageDialog(null,
                        "Unable to write to file " + ORDERLOGLIST_JSON_STORE,
                        "ORDERLOGLIST", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

/*
 //Effects: Runs the app
    private void runMenu() {
        boolean keepGoing = true;
        String command;

        while (keepGoing) {
            displayOptions();
            command = input.next().toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nTill next time!");
        try {
            orderLogWriter.open();
            orderLogWriter.writeOrderLog(orderLog);
            orderLogWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Could not write to file " + ORDERLOG_JSON_STORE);
        }
    }

    //Effects: displays the base options for the app
    private void displayOptions() {
        System.out.println("\nChoose From:");
        System.out.println("\to -> Order Drinks");
        System.out.println("\tm -> Manager Actions");
        System.out.println("\tq -> Quit");
    }

    //Effects: reads the user input and selects the corresponding action
    private void processCommand(String command) {
        if (command.equals("o")) {
            orderDrinks();
        } else if (command.equals("m")) {
            System.out.println("Manager Passcode: ");
            int code = input.nextInt();
            if (code == managerPasscode) {
                doManagerActions();
            } else {
                System.out.println("Incorrect Code");
            }

        } else {
            System.out.println("Option not available");
        }
    }

    //Effects: displays options for manager actions and reads user input
    private void doManagerActions() {
        boolean keepGoing = true;
        String managerCommand;

        while (keepGoing) {
            System.out.println("\tv -> View Stats");
            System.out.println("\ta -> Add new drink to menu");
            System.out.println("\ts -> Set New Specials");
            System.out.println("\tn -> Start New Order Log");
            System.out.println("\tq -> Back to first menu");
            managerCommand = input.next().toLowerCase();

            if (managerCommand.equals("q")) {
                keepGoing = false;
            } else {
                processManagerCommand(managerCommand);
            }
        }

        System.out.println("\nTill next time!");

    }

    //Effects: processes user input and executes chosen action
    private void processManagerCommand(String managerCommand) {
        if (managerCommand.equals("v")) {
            viewStats();
        } else if (managerCommand.equals("a")) {
            addDrinkToMenu();
        } else if (managerCommand.equals("s")) {
            setNewSpecials();
        } else if (managerCommand.equals("n")) {
            startNewOrderLog();
        } else {
            System.out.println("Option not available");
        }
    }

    //Effects: orders drinks as specified by the user
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

    //Effects: returns a new drink with the chosen name
    private Drink getDrink() {
        openMenu();
        System.out.println("\nChoose Drink: ");
        for (Drink drink : menu.getDrinks()) {
            System.out.println(drink.getName());
        }
        String drinkName = input.next().toLowerCase();
        return menu.findDrink(drinkName);
    }

    //Effects: Chooses the options for the given drink, orders the drink
    private void chooseDrinkOptions(Drink drink, Order order) {
        System.out.println("\nChoose Size (s,l): ");
        String size = input.next();
        System.out.println("\nChoose Ice (0,0.5,1): ");
        double ice = input.nextDouble();
        System.out.println("\nChoose Sugar (0,0.25,0.5,0.75,1): ");
        double sugar = input.nextDouble();

        ArrayList<String> exTop = new ArrayList<>();
        boolean moreTop = true;
        while (exTop.size() <= 2 && moreTop) {
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
    //Modifies: orderLog
    private void printDrinksOrdered(Order currentOrder) {
        System.out.println("\nDrinks Ordered: ");
        for (Drink drink : currentOrder.getDrinksOrdered()) {
            System.out.println(drink.getName());
        }
        System.out.println("\nTotal Price Pre-tax: ");
        System.out.println(currentOrder.getTotalPrice());
        currentOrder.applyTax();
        System.out.println("\nTotal Price Post-tax: ");
        System.out.println(currentOrder.getTotalPrice());
        orderLog.addOrder(currentOrder);
    }

    //Effects: opens the order log by reading it from the json file
    private void openOrderLogList() {
        try {
            orderLogList = orderLogListReader.readOrderLogList();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    //Effects: Selects an orderLog from the orderLogList by prompting user input for the name
    private OrderLog selectOrderLog() {
        for (OrderLog o : orderLogList.getOrderLogList()) {
            System.out.println(o.getName());
        }
        String selectedOrderLogName = input.next().toLowerCase();
        OrderLog selectedOrderLog = null;
        for (OrderLog o : orderLogList.getOrderLogList()) {
            if (o.getName().equals(selectedOrderLogName)) {
                selectedOrderLog = o;
            }
        }
        return selectedOrderLog;
    }

    //Effects: Displays the options for stats, reads user input, and selects the corresponding stat action
    private void viewStats() {
        System.out.println("which order log would you like stats for?");
        System.out.println("\nc -> Current Order Log");
        System.out.println("\nl -> load from memory");
        String response = input.next().toLowerCase();
        OrderLog selectedOrderLog = new OrderLog("");
        if (response.equals("c")) {
            selectedOrderLog = orderLog;
        } else if (response.equals("l")) {
            openOrderLogList();
            selectedOrderLog = selectOrderLog();
        }
        System.out.println("\nWhich stat would you like to view?");
        System.out.println("\ti -> ingredients");
        System.out.println("\tp -> price");
        String selectedOption = input.next().toLowerCase();
        if (selectedOption.equals("i")) {
            displayIngredientStats(selectedOrderLog);
        } else if (selectedOption.equals("p")) {
            displayPriceStats(selectedOrderLog);
        } else {
            System.out.println("Selection not valid");
        }
    }

    //Effects: displays stats about the price of all orders in orderLog
    private void displayPriceStats(OrderLog o) {
        if (o == null) {
            System.out.println("Order Log not found");
        } else {
            int numDrinksOrdered = 0;
            double totalPriceAllDrinks = 0;
            for (Order order: o.getOrders()) {
                numDrinksOrdered += order.getDrinksOrdered().size();
                totalPriceAllDrinks += order.getTotalPrice();
            }
            double averageOrderPrice = totalPriceAllDrinks / o.getOrders().size();
            System.out.println("\nNumber of Drinks Ordered:");
            System.out.println(numDrinksOrdered);
            System.out.println("\nTotal Revenue:");
            System.out.println(totalPriceAllDrinks);
            System.out.println("\nAverage Price per Order:");
            System.out.println(averageOrderPrice);
        }
    }

    //Effects: Displays stats about the ingredients of all orders in orderLog
    private void displayIngredientStats(OrderLog o) {
        if (o == null) {
            System.out.println("Order Log not found");
        } else {
            System.out.println("\nWhat ingredient would you like to view?");
            String ingredient = input.next().toLowerCase();
            int ingredientAmount = 0;
            for (Order order : o.getOrders()) {
                ingredientAmount += order.getIngredientAmount(ingredient);
            }
            System.out.println("\nAmount used:");
            System.out.println(ingredientAmount);
        }
    }

    //Effects: saves the order log to the json file and creates a new one


    //Effects: return true if the given name has already been used by
    // the current order log or one in the list

*/
}
