# Personal Project - Nolan Vibhakar

## Teadot Menu/Drink Tracker

### Overview

In my personal project, I will be designing an interactive menu for the bubble tea store Teadot. The application
will let users **view the various drinks**, and **log drink orders**. The application will then track these drink
orders, and compile useful information, including: **the quantity of ingredients used over certain time periods**,
**which times of day are busiest for different days**, and **the popularity of different drinks**.

Employees would use the application to log drinks as they are ordered. Then the manager will be able to view this
data at any time, and can then use it to inform decisions related to the operation of the store, such as how much
to prep every day, when to schedule workers, what drinks to put on special, and so forth.

As an employee of Teadot, this project holds interest to me as I would be a primary user. We often find ourselves
in the situation of not having enough prepped, or having too much prepped that we then need to throw out as it
goes bad. We also sometimes find ourselves without enough workers, especially during different times of the year.
Being able to gather and view data would help us be more efficient and improve the quality of our work.

### User Stories
- As an employee, I want to be able to view a list of drinks available to order
- As an employee, I want to be able to add a drink to the drink log for the current time period
- As a manager, I want to be able to change what drinks are on special
- As a manager, I want to be able to view stats about the drinks ordered during a given time period
- As a user, when I quit I want it to automatically save the current order log
- As a user, when I open the application, I want it to automatically open the previous order log
- As a manager, I want to be able to add the current order log to a list of all previous order logs, save that list, and start a new order log
- As a manager, I want to be able to open a list of all previous order logs and view stats about an order log of my choice
- As a manger, I want to be able to save changes to the menu, including the current specials and adding drinks from the menu

### Phase 4: task 2
Thu Apr 04 14:26:33 PDT 2024

Added drink to order, name: classic milk tea

Thu Apr 04 14:26:39 PDT 2024

Added drink to order, name: matcha latte

Thu Apr 04 14:26:41 PDT 2024

Order added to orderLog, total price: 11.5, Drinks: 2

Thu Apr 04 14:27:34 PDT 2024

Drink added to menu: thai milk tea

Thu Apr 04 14:27:42 PDT 2024

set specials: thai milk tea , taro milk tea

Thu Apr 04 14:28:00 PDT 2024

orderLog added to orderLogList, name: april 4th

### Phase 4: task 3
The main thing I could improve on with my design is revamping my gui file to make it cleaner and simpler. Currently, it
is just over 800 lines long, and most of that is wrapped up in internal classes that could probably be made into their
own classes in separate files. This would be an easy way to improve the readability of my program. Another thing I think
could be improved is the ingredient class, and specifically the way that drinks change the amount of ingredients in
themselves based on size/ice. I think instead of using a variable for the ingredient type and updating based on the type,
I could have more variables to represent the different ingredient amounts. This would help to future-proof the program
and reduce complexity later, so it would be a helpful change.