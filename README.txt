Preface

This app is a project that I made for my OOP class. It is a money management app that helps you keep track of your finances. You can enter your income, expenses, savings, and transactions and see how much money you have left. You can also view your spending habits and budget goals. The app is written in Java and uses a SQL database to store your data. It also uses AWT and Swing to create a graphical user interface. The app follows OOP principles and best practices to make the code easy to read and modify. I learned a lot from making this app and I hope you find it useful and fun.

========================================================

# Money Pakyaw App

Money Pakyaw is an app that helps you manage your money. You can track your income and expenses, stash savings, and monitor transactions. The app has 10 screens that let you do different things, such as:


- Splash screen: 
This is the loading screen that shows up when you open the app.

- Login screen: 
This is where you type your username and password to get into the app.

- Signup screen: This is where you make an account. You need to choose a username, password, age, and balance. You can't use a username that someone else already has.

- Main screen: This is where you see how much money you have. You can also see how much you saved, how much you earned, and how much you spent on expenses.

- Transactions screen: This is where you add, change, or remove transactions. A transaction is when you get or spend money. You can see a table of all your transactions.

- Savings screen: This is where you add, change, or remove savings. Savings are money that you put aside for later. You can see a table of all your savings.

- Income screen: This is where you add, change, or remove income sources. Income sources are things that give you money, like your salary or freelance work. You can see a table of all your income sources. There is also a button that says "Income Received". If you press it, the app will make a copy of each income source that you haven't earned yet and mark it as earned. The app will use the date that you pressed the button for the copies.

- Expenses screen: This is where you add, change, or remove expense categories. Expense categories are things that take your money, like rent or groceries. You can see a table of all your expense categories. There is also a button that says "Expenses Paid". If you press it, the app will make a copy of each expense category that you haven't paid yet and mark it as paid. The app will use the date that you pressed the button for the copies.

- Profile screen: This is where you can see and modify your account details and app theme. The app theme is how the app looks like. You can choose different colors for the app theme.

- About screen: This is where you find the app information and the development team. The app information tells you what version of the app you have and when it was made. The development team are the people who work on the app.


# How the App Works

The app uses 8 sets of actions to work with your data:

- Create - This is when you add new things to the app, like income sources, income received, expense categories, expenses paid, transactions, savings, theme, or a user account.

- Read - This is when you see the things that you added to the app, like income sources, income received, expense categories, expenses paid, transactions, savings, theme, or user account details.

- Update - This is when you change the things that you added to the app, like income sources, income received, expense categories, expenses paid, transactions, savings, theme, or user account details.

- Delete - This is when you remove the things that you added to the app, like income sources, income received, expense categories, expenses paid, transactions, savings, theme, or your user account.


# How the App Stores and Gets Data

The app uses JDBC and xampp SQL Database for Data Storage and Retrieval. This means that the app uses a special software called JDBC to connect to a database called xampp SQL Database. A database is a place where the app stores and gets data. The app uses SQL commands to store and get data from the database. SQL is a language that tells the database what to do with the data.


# How the App Looks and Feels

The app uses Java AWT and Swing to develop Money Pakyaw. This means that the app uses two libraries called Java AWT and Swing to make the app look and feel good. A library is a collection of code that helps you do something. Java AWT and Swing help you create GUI components and handle user events with AWT and Swing. GUI components are things that you see on the screen, like buttons, tables, or text fields. User events are things that you do with the app, like clicking a button or typing something.


# How the App Follows Good Practices

The app follows OOP principles and applies best practices. This means that the app follows a way of programming called OOP (Object-Oriented Programming) and uses some rules that make the code better. OOP is a way of programming that organizes data and actions into objects. Objects are things that have data (called attributes) and actions (called methods). For example, a user account object has data like username and password and actions like login or logout. Best practices are rules that make the code easier to read, understand, and maintain. For example, a best practice is to use meaningful names for variables and methods.


# The app adheres to these course requirements:

- Must use JDBC
- Must use java without frameworks
- Must use awt or swing or both

These are the requirements that the app must follow. A requirement is something that the app must do or have. If the app does not follow a requirement, it will not work properly or meet the expectations set for the course. For example, the app must use JDBC because it is the software that connects the app to the database. The app must use java without frameworks because frameworks are not to be used for this app. The app must use awt or swing or both because they are libraries that help create the GUI components and handle user events.