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
| UC1			| Anonymous/Customer  | User searches for an item | The application displays a page with all items that are in stock that match the search criteria |
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
For this application I will provide Unit Tests to cover every low level element including Models & Services. This will, by itself, provide a large test coverage of the system. Unit tests alone however will only provide testing of individual compenents on thier own. In order to provide integration testing - testing of components working together - I will perform manual User Interface tests. These tests not only make sure the UI is funcitonal but also test the system as a whole - integrating multiple components of the system into single tests.  

For this application I have decided to write manual tests from the point of view of each of the Roles in the system

### Anonymous Tests
| Test case ID 	| Action  	 | Expected Result | Date Passed |
| ----------- 	| ---------- | ----------- | ----------- |
| TC1			| New user loads up the site  | All products are available to be viewed on this page |  |
| TC2.0			| New user searches for a product  | All products matching the search criteria are displayed |  |
| TC2.1			| New user searches for a product that is out of stock  | The product is not displayed |  |
| TC3.0    		| New user adds an item to their cart  | Heading to the cart page shows the product in the cart |  |
| TC3.1    		| New user adds the same item to their cart again  | Heading to the cart page shows the product quantity has increased by 1 |  |
| TC4   		| New user removes item from cart  | Quantity of item in cart is decreased by 1 |  |
| TC5.0   		| New user clicks sign up  | Page loads containing all required inputs for a new account to be created |  |
| TC5.1   		| User enters account details and submits  | Account is created and user is logged in automatically |  |
| TC5.2 		| User clicks on cart  | Items are still stored in the cart and are not lost |  |
| TC6   		| User clicks logout  | Account is no longer assocaited with the session |  |
| TC7.0   		| User clicks login  | Page loads with all required inputs for sign in |  |
| TC7.1   		| User enters account details  | User is automatically logged in to their account |  |

### Customer Tests
The following tests must be performed by a logged in user. These tests must be completed as well as the following tests anonymous tests which must be re-tested as the customer role:  
-  `TC1`, `TC2`, `TC3`, `TC4`

| Test case ID 	| Action  	 | Expected Result | Date Passed |
| ----------- 	| ---------- | ----------- | ----------- |
| TC10			| User clicks on shopping cart and clicks checkout  | Page loads for user to enter card details |  |
| TC10.1		| User enters valid card details  | Success message is shown and link to order is displayed |  |
| TC10.2		| User enters valid card details  | Money in the Bank Rest API has been moved to the correct account |  |
| TC10.3		| User enters invalid card details  | Error message is shown and user is redirected back to shopping cart |  |
| TC11		    | User clicks on the orders page  | All the user's orders as well as their status are displayed |  |

//Check stock after order
### Admin Tests
| Test case ID 	| Action  	 | Expected Result | Date Passed |
| ----------- 	| ---------- | ----------- | ----------- |
| TC20			| Admin clicks add on the catalog page  | Page loads for admin to enter product details for item |  |
| TC20.1		| Admin clicks save  | The product is viewable on the catalog page |  |
| TC21	    	| Admin deletes an item  | The product is no longer viewable in the catalog page |  |
| TC21.1    	| Admin deletes an item  | The product is still visible in orders that contain it |  |
| TC22      	| Admin updates an item's detail | The product's details are updated in the catalog page |  |
| TC23      	| Admin changes a product's quantity to 0 | The product is no longer displayed on the catalog page  |  |
| TC23.1     	| Admin changes a product's quantity back to a number greater than 0 | The product is viewable again from the catalog page |  |
| TC24      	| Admin opens the orders page | A page loads containing all the orders  |  |
| TC25      	| Admin searches the orders page | A page results are limited based upon those that match the search query  |  |
| TC26      	| Admin clicks edit on an order | The order loads, allowing the Admin to change the order status  |  |
| TC26.1      	| Admin changes the status of the order and clicks save | The order on the user's account shows the new updated state  |  |
| TC27      	| Admin clicks on the users page | A page loads containing all the users in the system  |  |
| TC27.1     	| Admin clicks edit on the users page | A page loads containing all information about the user  |  |
| TC27.2     	| Admin edits user's details | The details are changed on the user's my profile page  |  |
| TC27.3     	| Admin edits user's state to de-activated | The user is unable to log in to the system  |  |

### De-activated Tests
| Test case ID 	| Action  	 | Expected Result | Date Passed |
| ----------- 	| ---------- | ----------- | ----------- |
| TC30			| De-activated user tries to login  | Message is shown to let the user know their account might be locked |  |

# Features
Below you can see the feature list for the application, this list is split into sections - grouped by either the pages the feature will be on or the group of pages they relate to:

### Home View
- List of all products
- Ability to add product to cart
- Ability to search products to filter list down

Potential Additional Features (for future phases):
- Display images for each product
- Advanced filtering/sorting of products
- Add pagination to result (don't load all products at once)

### Orders View
- View all orders associated to account
- View status & price of order as well as products it contains 
- Search orders to filter list donw
- As Admin - View all orders from all users
- As Admin - Edit status of order

### Shopping Cart View
- View all products & their quantity in the cart
- View total cost of shopping cart  

Potential Additional Features (for future phases):
- Be able to add coupon code to shopping cart to add discounts

### Checkout View
- Enter & Submit card details for transaction
- Card details are validated & rest client sends money

Potential Additional Features (for future phases):
- Load card details from account (only require entering of CVV)

### Users 
- Create a user
- Login to an account
- Edit account details
- Logout of account
- As Admin - Edit account details & state of any user


# UML Diagram
## Use Case Diagram
Below you can see the Use Case Diagram for the application. From the diagram you can see the 2 main actors, the User and the Admin, as well as a 3rd Party Actor - the bank.

![Use Cases](Diagrams/UseCaseDiagram.drawio.png "Use Case Diagram")

## Class Diagram
types & interfaces

## Data Model (For DB)
??

## Robustness Diagram
(Must include bank client)

Change "Tables" to repos and add bank client

## Sequence Diagram
