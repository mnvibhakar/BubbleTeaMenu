package ui;

import model.*;

import model.Event;
import model.Menu;
import model.exceptions.DuplicateNameException;
import model.persistence.JsonWriter;
import model.persistence.JsonReader;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/*
Represents the menu app graphical user interface
Displays options and information on the screen, processes user input to execute the chosen tasks, including:
    -ordering drinks
    -viewing previous orders in the current orderLog
    -changing specials
    -viewing stats about price and ingredients
    -adding a new drink to the menu
    -beginning a new order log
 */
public class MenuAppGUI extends JFrame {

    private static final String MENU_JSON_STORE = "./data/menu.json";
    private static final String ORDERLOGLIST_JSON_STORE = "./data/orderLogList.json";
    private static final String ORDERLOG_JSON_STORE = "./data/orderLog.json";
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 600;

    private Menu menu = new Menu();
    private OrderLog orderLog;
    private OrderLogList orderLogList;
    private Drink currentDrink;
    private Order currentOrder;
    private Boolean orderInProgress;
    private int currentNumber;

    private JsonWriter menuWriter;
    private JsonWriter orderLogListWriter;
    private JsonWriter orderLogWriter;
    private JsonReader menuReader;
    private JsonReader orderLogListReader;

    private JPanel homePanel;
    private JPanel managerPanel;


    public static void main(String[] args) {
        new MenuAppGUI();
    }

    public MenuAppGUI() {
        initJsonHandling();
        initGUI();
        orderInProgress = false;
        currentOrder = new Order();
    }

    //Effects:  initializes the JSON reader and writer, opens the previous order log
    public void initJsonHandling() {
        menuWriter = new JsonWriter(MENU_JSON_STORE);
        menuReader = new JsonReader(MENU_JSON_STORE);
        orderLogListWriter = new JsonWriter(ORDERLOGLIST_JSON_STORE);
        orderLogListReader = new JsonReader(ORDERLOGLIST_JSON_STORE);
        orderLogWriter = new JsonWriter(ORDERLOG_JSON_STORE);
        JsonReader orderLogReader = new JsonReader(ORDERLOG_JSON_STORE);
        try {
            orderLog = orderLogReader.readOrderLog();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Unable to read from file: " + ORDERLOG_JSON_STORE, "ORDERLOG",
                    JOptionPane.ERROR_MESSAGE);
        }
        if (orderLog.getOrders().isEmpty()) {
            currentNumber = 1;
        } else {
            currentNumber = orderLog.getOrders().get(orderLog.getOrders().size() - 1).getOrderNumber() + 1;
        }
    }

    //Effects: initializes the GUI, sets initial content to the home panel, adds the menu bar
    public void initGUI() {
        homePanel = new HomePanel();
        managerPanel = new ManagerPanel();

        setTitle("Bubble Tea Menu");
        setSize(WIDTH, HEIGHT);
        addMenuBar();

        centreOnScreen();
        setContentPane(homePanel);
        setVisible(true);
    }

    //Effects: Saves the orderLog when the window is closed
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
            for (Event next : EventLog.getInstance()) {
                System.out.println(next.toString());
            }
            System.exit(0);
        }
    }

    //Effects: Creates the menu bar
    private void addMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(new JMenuItem(new GoToHomeAction()));
        menuBar.add(new JMenuItem(new GoToMenuAction()));
        menuBar.add(new JMenuItem(new GoToOrdersAction()));
        menuBar.add(new JMenuItem(new GoToManagerAction()));
        setJMenuBar(menuBar);
    }

    //Effects: Centers the window on the screen
    private void centreOnScreen() {
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        setLocation((width - getWidth()) / 2, (height - getHeight()) / 2);
    }

    //Effects: switches which panel is being viewed
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

    //Effects: opens the order log by reading it from the json file
    private void openOrderLogList() {
        try {
            orderLogList = orderLogListReader.readOrderLogList();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Unable to read from file: " + ORDERLOGLIST_JSON_STORE,
                    "OrderLogListReader", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Represents a JPanel for the home screen
    //Includes a welcome message and image
    private static class HomePanel extends JPanel {
        HomePanel() {
            this.setLayout(new BorderLayout());
            BufferedImage image;
            try {
                image = ImageIO.read(new File("./bubble_tea_image.jpg"));
                JLabel imageLabel = new JLabel(new ImageIcon(image));
                this.add(imageLabel, BorderLayout.CENTER);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Image not found", "HOME",
                        JOptionPane.ERROR_MESSAGE);
            }
            JTextArea welcomeMessage  = new JTextArea("Welcome to the bubble tea shop!");
            welcomeMessage.setFont(new Font("serif", Font.PLAIN, 36));
            this.add(welcomeMessage, BorderLayout.NORTH);

        }
    }

    //Represents a JPanel for the Menu
    //includes tasks for ordering drinks
    private class MenuPanel extends JPanel {
        MenuPanel() {
            setLayout(new BorderLayout());
            JPanel drinkPanel = new JPanel();
            drinkPanel.setPreferredSize(new Dimension(750, HEIGHT));
            JTextArea displayPanel = new JTextArea();
            displayPanel.setPreferredSize(new Dimension(235, HEIGHT));
            displayPanel.setFont(new Font("serif", Font.PLAIN, 18));
            openMenu();
            drinkPanel.setLayout(new GridLayout(menu.getDrinks().size() / 4, menu.getDrinks().size() / 4));
            for (Drink d : menu.getDrinks()) {
                drinkPanel.add(new JButton(new OrderDrinkAction(d)));
            }
            displayDrinksInOrder(displayPanel);
            add(drinkPanel, BorderLayout.WEST);
            add(displayPanel, BorderLayout.EAST);
            add(new JButton(new FinishOrderAction()), BorderLayout.SOUTH);
        }

        //Effects: writes text info about the drinks in currentOrder to displayPanel
        //Modifies: displayPanel
        private void displayDrinksInOrder(JTextArea displayPanel) {
            for (Drink d : currentOrder.getDrinksOrdered()) {
                displayPanel.append(d.getName());
                displayPanel.append("\nSize: " + Character.toString(d.getSize()));
                displayPanel.append("\nPrice: " + Double.toString(d.getPrice()));
                displayPanel.append("\n-------------\n");
            }
            displayPanel.append("Total: " + Double.toString(currentOrder.getTotalPrice()));
        }

        //Represents an action to order the selected Drink
        private class OrderDrinkAction extends AbstractAction {
            private final Drink drinkSelected;

            OrderDrinkAction(Drink drink) {
                super(drink.getName());
                drinkSelected = drink;
            }

            //Effects: selects the given drink and switches to drink panel for ordering
            //Modifies: currentDrink
            @Override
            public void actionPerformed(ActionEvent e) {
                currentDrink = drinkSelected;
                switchPanel(new DrinkPanel());
                orderInProgress = true;
            }
        }

        //Represents an action to complete the currentOrder
        private class FinishOrderAction extends AbstractAction {
            FinishOrderAction() {
                super("Finish Order");
            }

            //Effects: adds currentOrder to orderLog and starts a new order
            //Modifies: currentOrder, orderLog, currentNumber
            @Override
            public void actionPerformed(ActionEvent e) {
                currentOrder.setOrderNumber(currentNumber);
                orderLog.addOrder(currentOrder);
                currentOrder = new Order();
                currentNumber++;
            }
        }

        //Represents an JPanel that contains options for drink specifications
        //Includes tasks to change the specifications and order the drink
        private class DrinkPanel extends JPanel {

            private final JTabbedPane topBar;
            private final ArrayList<String> toppings;
            private double ice;
            private double sugar;
            private String size;

            DrinkPanel() {
                size = "s";
                toppings = new ArrayList<>();
                ice = 1;
                sugar = 1;
                topBar = new JTabbedPane();
                topBar.setTabPlacement(JTabbedPane.TOP);
                loadTabs();
                add(topBar);
                JMenuBar drinkOptions = new JMenuBar();
                drinkOptions.add(new JMenuItem(new FinishDrinkAction()));
                add(drinkOptions);
            }

            //Effects: creates the tabs representing the various specifications
            private void loadTabs() {
                JPanel sizeTab = new SizeTab();
                JPanel toppingsTab = new ToppingsTab();
                JPanel iceTab = new IceTab();
                JPanel sugarTab = new SugarTab();

                topBar.add(sizeTab);
                topBar.setTitleAt(0, "Size");
                topBar.add(toppingsTab);
                topBar.setTitleAt(1, "Toppings");
                topBar.add(iceTab);
                topBar.setTitleAt(2, "Ice");
                topBar.add(sugarTab);
                topBar.setTitleAt(3, "Sugar");
            }

            //Represents a JPanel for choosing the size of the drink
            private class SizeTab extends JPanel {
                SizeTab() {
                    add(new JButton(new ChooseSizeAction("s")));
                    add(new JButton(new ChooseSizeAction("l")));
                }

                //Represents an action for choosing the size
                private class ChooseSizeAction extends AbstractAction {
                    private final String chosenSize;

                    public ChooseSizeAction(String s) {
                        super(s);
                        chosenSize = s;
                    }

                    //Effects: sets size as the chosen size
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        size = chosenSize;
                    }
                }
            }

            //Represents a JPanel for choosing the toppings in the drink
            private class ToppingsTab extends JPanel {
                ToppingsTab() {
                    add(new JButton(new ChooseToppingAction("pearls")));
                    add(new JButton(new ChooseToppingAction("grass jelly")));
                    add(new JButton(new ChooseToppingAction("coconut jelly")));
                }

                //Represents an option for selecting the toppings
                private class ChooseToppingAction extends AbstractAction {
                    private final String toppingChoice;

                    public ChooseToppingAction(String t) {
                        super(t);
                        toppingChoice = t;
                    }

                    //Effects: adds toppings to the list of toppings, cannot add more than two
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (toppings.size() < 2) {
                            toppings.add(toppingChoice);
                        } else {
                            int removeToppingChoice = JOptionPane.showConfirmDialog(null,
                                    "2 toppings already in drink, remove all toppings?");
                            if (removeToppingChoice == JOptionPane.YES_OPTION) {
                                toppings.clear();
                            }
                        }
                    }
                }
            }

            //Represents a JPanel for choosing the ice of the drink
            private class IceTab extends JPanel {
                IceTab() {
                    add(new JButton(new ChooseIceAction(0)));
                    add(new JButton(new ChooseIceAction(0.5)));
                    add(new JButton(new ChooseIceAction(1)));
                }

                //Represents an action for choosing the ice
                private class ChooseIceAction extends AbstractAction {
                    private final double iceOption;

                    public ChooseIceAction(double i) {
                        super(Double.toString(i));
                        iceOption = i;
                    }

                    //Effects: sets ice as the chosen ice
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ice = iceOption;
                    }
                }
            }

            //Represents a JPanel for choosing the sugar of the drink
            private class SugarTab extends JPanel {
                SugarTab() {
                    add(new JButton(new ChooseSugarAction(0)));
                    add(new JButton(new ChooseSugarAction(0.25)));
                    add(new JButton(new ChooseSugarAction(0.5)));
                    add(new JButton(new ChooseSugarAction(0.75)));
                    add(new JButton(new ChooseSugarAction(1)));
                    add(new JButton(new ChooseSugarAction(1.25)));
                }

                //Represents an action for choosing the sugar of the drink
                private class ChooseSugarAction extends AbstractAction {
                    private final double sugarChoice;

                    public ChooseSugarAction(double s) {
                        super(Double.toString(s));
                        sugarChoice = s;
                    }

                    //Effects: sets sugar as the chosen sugar
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        sugar = sugarChoice;
                    }
                }
            }

            //Represents an action that orders the drink based on the current specifications and returns back to menu
            private class FinishDrinkAction extends AbstractAction {

                FinishDrinkAction() {
                    super("Done");
                }

                //Effects: orders currentDrink with chosen specifications, switches back to MenuPanel
                //Modifies: currentDrink, currentOrder
                @Override
                public void actionPerformed(ActionEvent e) {
                    currentOrder.orderDrink(size, toppings, ice, sugar, currentDrink);
                    orderInProgress = false;
                    switchPanel(new MenuPanel());

                }
            }
        }
    }

    //Represents a JPanel for a screen that displays previous orders
    //includes tasks for choosing which order to display
    private class OrdersPanel extends JPanel {
        private final JTextArea orderDisplay;

        OrdersPanel() {
            setLayout(new BorderLayout());
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
            addOrderButtons(buttonPanel);
            JScrollPane orderScroll = new JScrollPane(buttonPanel);
            orderScroll.setPreferredSize(new Dimension(300, HEIGHT));
            orderDisplay = new JTextArea();
            this.add(orderScroll, BorderLayout.WEST);
            this.add(orderDisplay);
        }

        //Effects: adds buttons to buttonPanel for each order in orderLog
        //Modifies: buttonPanel
        private void addOrderButtons(JPanel buttonPanel) {
            for (Order o : orderLog.getOrders()) {
                JButton orderButton = new JButton(new DisplayOrderAction(o));
                orderButton.setMaximumSize(new Dimension(280, 100));
                buttonPanel.add(orderButton);
            }
        }

        //Represents an action that displays the chosen order in orderDisplay
        private class DisplayOrderAction extends AbstractAction {
            Order displayedOrder;

            DisplayOrderAction(Order ord) {
                super(Integer.toString(ord.getOrderNumber()));
                displayedOrder = ord;
            }

            //Effects: writes text to orderDisplay describing the chosen order
            //Modifies: orderDisplay
            @Override
            public void actionPerformed(ActionEvent e) {
                orderDisplay.setText("");
                orderDisplay.append(Double.toString(displayedOrder.getTotalPrice()));
                orderDisplay.append("\n--------------------");
                for (Drink d : displayedOrder.getDrinksOrdered()) {
                    orderDisplay.append("\n" + d.getName());
                    orderDisplay.append("\nSize: " + Character.toString(d.getSize()));
                    orderDisplay.append("\nIce: " + Double.toString(d.getIce()));
                    orderDisplay.append("\nSugar: " + Double.toString(d.getSugar()));
                    for (String t : d.getToppings()) {
                        orderDisplay.append("\n" + t);
                    }
                    orderDisplay.append("\n--------------------");
                }
                add(orderDisplay);
            }
        }
    }

    //Represents a JPanel for the manager actions
    //Includes tasks for viewing stats, adding drinks to the menu, starting a new order log, and setting new specials
    private class ManagerPanel extends JPanel {
        ManagerPanel() {
            add(new JButton(new AddDrinkToMenuAction()));
            add(new JButton((new SetNewSpecialsAction())));
            add(new JButton(new ViewStatsAction()));
            add(new JButton(new StartNewOrderLogAction()));
        }

        //Represents an action to add a new drink to the menu
        private class AddDrinkToMenuAction extends AbstractAction {
            AddDrinkToMenuAction() {
                super("Add New Drink");
            }

            //Effects: prompts user for info about the drink to be created, opens the menu and adds a new drink with
            //the given specifications, saves the menu
            //Modifies: menu.json
            @Override
            public void actionPerformed(ActionEvent e) {
                String drinkName = JOptionPane.showInputDialog(null,
                        "Name of Drink", JOptionPane.QUESTION_MESSAGE).toLowerCase();
                double price = Double.parseDouble(JOptionPane.showInputDialog(null,
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

            //Effects: prompts the user for ingredients to be added to the drink
            private void chooseIngredients(ArrayList<Ingredient> ingredients) {
                boolean keepGoing = true;
                while (keepGoing) {
                    int addIngredient = JOptionPane.showConfirmDialog(null, "Add Ingredient?");
                    if (addIngredient == JOptionPane.YES_OPTION) {
                        String type = JOptionPane.showInputDialog(null,
                                "Type of Ingredient?", JOptionPane.QUESTION_MESSAGE).toLowerCase();
                        String name = JOptionPane.showInputDialog(null,
                                "Name of Ingredient?", JOptionPane.QUESTION_MESSAGE).toLowerCase();
                        int amount = Integer.parseInt(JOptionPane.showInputDialog(null,
                                "Amount?", JOptionPane.QUESTION_MESSAGE));
                        Ingredient ingredient = new Ingredient(type, name, amount);
                        ingredients.add(ingredient);
                    } else {
                        keepGoing = false;
                    }
                }
            }
        }

        //Represents an action to set new specials
        private class SetNewSpecialsAction extends AbstractAction {
            SetNewSpecialsAction() {
                super("Set New Specials");
            }

            //Effects: prompts the user for new specials, modifies the status of drinks in the menu accordingly
            //Modifies: menu.json
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

        //Represents an action to view stats about the desired orderLog
        private class ViewStatsAction extends AbstractAction {
            ViewStatsAction() {
                super("View Stats");
            }

            //Effects: chooses whether to view the current orderLog or a previous one
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] options1 = {"current order log", "previous order log"};
                int selectedChoice = JOptionPane.showOptionDialog(null,
                        "view current order log or previous order log", "STATS",
                        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options1, options1[0]);
                if (selectedChoice == 0) {
                    viewStats(orderLog);
                } else if (selectedChoice == 1) {
                    switchPanel(new OrderLogPanel());
                }
            }

            //Represents a JPanel to view stats about previous orderLogs
            private class OrderLogPanel extends JPanel {
                OrderLogPanel() {
                    openOrderLogList();
                    for (OrderLog o : orderLogList.getOrderLogList()) {
                        this.add(new JButton(new SelectOrderLogAction(o)));
                    }
                }
            }

            //Represents an action to view stats about the selected orderLog
            private class SelectOrderLogAction extends AbstractAction {
                private final OrderLog selectedOrderLog;

                public SelectOrderLogAction(OrderLog o) {
                    super(o.getName());
                    selectedOrderLog = o;
                }

                @Override
                public void actionPerformed(ActionEvent e) {
                    viewStats(selectedOrderLog);
                }
            }

            //Effects: prompts the user about which stat they want to view, provides them with the chosen info
            public void viewStats(OrderLog o) {
                String[] options2 = {"price stats", "ingredient stats"};
                int selectedChoice = JOptionPane.showOptionDialog(null,
                        "Which stat would you like to view?", "STATS",
                        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options2, options2[0]);
                if (selectedChoice == 0) {
                    double priceTotal = o.getTotalPrice();
                    double priceAverage = priceTotal / o.getOrders().size();
                    JOptionPane.showMessageDialog(null,
                            "Total revenue: " + Double.toString(priceTotal)
                                    + "\nAverage price per order: " + Double.toString(priceAverage));
                } else if (selectedChoice == 1) {
                    switchPanel(new IngredientsPanel(o));
                }
            }

            //Represents a JPanel with info about the ingredients in the chosen orderLog
            private class IngredientsPanel extends JPanel {


                public IngredientsPanel(OrderLog o) {
                    ArrayList<Ingredient> ingredients;
                    ingredients = new ArrayList<>();
                    boolean ingredientHandled = false;
                    for (Order order : o.getOrders()) {
                        for (Drink d : order.getDrinksOrdered()) {
                            for (Ingredient i : d.getIngredients()) {
                                for (Ingredient ing : ingredients) {
                                    if (i.getName().equals(ing.getName())) {
                                        ing.addAmount(i.getAmount());
                                        ingredientHandled = true;
                                    }
                                }
                                if (!ingredientHandled) {
                                    ingredients.add(new Ingredient(i.getType(), i.getName(), i.getAmount()));
                                }
                                ingredientHandled = false;
                            }
                        }
                    }
                    for (Ingredient i : ingredients) {
                        this.add(new JButton(new ViewIngredientStatAction(i)));
                    }
                }

                //Represents an action to view the stats about the chosen ingredient
                private class ViewIngredientStatAction extends AbstractAction {
                    private final Ingredient selectedIngredient;

                    public ViewIngredientStatAction(Ingredient i) {
                        super(i.getName());
                        selectedIngredient = i;
                    }

                    //Effects: Shows the user info about the chosen ingredient
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JOptionPane.showMessageDialog(null,
                                Integer.toString(selectedIngredient.getAmount()));
                    }
                }
            }
        }

        //Represents an action to start a new orderLog
        private class StartNewOrderLogAction extends AbstractAction {
            StartNewOrderLogAction() {
                super("Start New Order Log");
            }

            //Effects: prompts the user for the name of the new orderLog, adds orderLog to orderLogList
            //creates a new orderLog with given name
            //Modifies: orderLog
            @Override
            public void actionPerformed(ActionEvent e) {
                openOrderLogList();
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

            //Effects: returns true if the given name has not already been used by a previous orderLog
            private boolean checkName(String name) {
                boolean nameNotAlreadyUsed = true;
                for (OrderLog o : orderLogList.getOrderLogList()) {
                    if (o.getName().equals(name)) {
                        nameNotAlreadyUsed = false;
                        break;
                    }
                }
                return (nameNotAlreadyUsed && !orderLog.getName().equals(name));
            }

            //Effects: writes orderLog to json
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
    }

    //Represents an action that switches to the HomePanel
    private class GoToHomeAction extends AbstractAction {
        GoToHomeAction() {
            super("home");
        }

        //Effects: Switches to the home panel only if an order is not in progress
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!orderInProgress) {
                switchPanel(homePanel);
            } else {
                JOptionPane.showMessageDialog(null, "Finish Ordering Drink", "ORDER",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    //Represents an action that switches to the MenuPanel
    private class GoToMenuAction extends AbstractAction {
        GoToMenuAction() {
            super("Menu");
        }

        //Effects: switches to the MenuPanel only if an order is not in progress
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!orderInProgress) {
                switchPanel(new MenuPanel());
            } else {
                JOptionPane.showMessageDialog(null, "Finish Ordering Drink", "ORDER",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    //Represents an action that switches to the ManagerPanel
    private class GoToManagerAction extends AbstractAction {
        GoToManagerAction() {
            super("Manager Actions");
        }

        //Effects: switches to the ManagerPanel only if an order is not in progress and the correct code is entered
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!orderInProgress) {
                String inputCode = JOptionPane.showInputDialog(null,
                        "passcode?",
                        "Enter code",
                        JOptionPane.QUESTION_MESSAGE);
                String managerPasscode = "1234";
                if (inputCode.equals(managerPasscode)) {
                    switchPanel(managerPanel);
                } else {
                    JOptionPane.showMessageDialog(null, "Incorrect code", "MANAGER",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Finish ordering drink", "ORDER",
                        JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    //Represents an action that switches to the OrdersPanel
    private class GoToOrdersAction extends AbstractAction {
        GoToOrdersAction() {
            super("Orders");
        }

        //Effects: switches to the OrdersPanel only if an order is not in progress
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!orderInProgress) {
                switchPanel(new OrdersPanel());
            } else {
                JOptionPane.showMessageDialog(null, "Finish Ordering Drink", "ORDER",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
