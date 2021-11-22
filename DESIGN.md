This design doc contains all the pre-development work needed in order to plan the project.

# Use Cases
Below are the use cases for the application from the point of view of 4 different users:  
`Anonymous` - A user who has not logged in  
`Customer` - A user who has logged in  
`Deactivated` - A user whose account is closed  
`Admin` - A user with elevated privileges

| Use case ID 	| Role  	 | Action | Software Reaction |
| ----------- 	| ---------- | ----------- | ----------- |
| UC1			| Anonymous/Customer  | User loads the site | The application displays a page with all items that are in stock |
| UC2			| Anonymous/Customer  | User adds item to cart | The application adds the item to the cart or increases the quantity of an existing item by 1 |
| UC3			| Anonymous/Customer  | User modifies cart | The application removes items from cart when selected by the customer to do so |
| UC4			| Anonymous  | User signs in  | The application stores the user in session storage |
| UC5			| Anonymous  | User signs up  | The application allows user to enter their details (name, address, card details (not cvv)) and creates a new account in the database |
| UC6.1			| Customer  | User purchases the items in the shopping cart  | The application sends a request to the Bank REST API to pay for the goods and moves money from customer to shop's account |
| UC6.2			| Customer  | User purchases the items in the shopping cart  | If the payment was successful the order is added to the database |
| UC6.3			| Customer  | User purchases the items in the shopping cart  | The items ordered have their stock count updated to match the new inventory |
| UC6.4			| Customer  | User enters incorrect card details  | The error is logged |
| UC6.5			| Customer  | User enters incorrect card details  | The user is returned to the shopping cart in order to re-try |
| UC7			| Customer  | User views their orders  | A page displaying all the user's orders is displayed along with their status |
| UC8			| Admin  | Admin adds a new item to the catalog  | The item is added to the database and is visible to customers |
| UC9			| Admin  | Admin removes an item from the catalog  | The item is no longer visible to customers |
| UC10			| Admin  | Admin updates an item from the catalog  | The item details change or the inventory count is updated |
| UC11			| Admin  | Admin views all orders  | The application displays an orders page showing all orders in the system along with their status |
| UC12			| Admin  | Admin searches all orders  | The application displays all orders that contain products matching the search query |
| UC13			| Admin  | Admin edits an order  | The admin is able to change the status of an order |
| UC14			| Admin  | Admin views all users  | The application shows a list of all users in the system |
| UC15			| Admin  | Admin modifies a user | The application updates user accounts based upon the changes made by the admin |
| UC16			| Customer/Admin  | User logs out  | The application removes the current user from session and turns role back to anonymous |
| UC17			| De-activated  | User attempts to login  | The application blocks the user from logging in |


# Test Plan
(Integration Tests)

# Features




# Class Diagram
types & interfaces

# Data Model (For DB)
??

# Robustness Diagram
(Must include bank client)

# Sequence Diagram
