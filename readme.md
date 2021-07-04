#To run Application
mvn spring-boot:run

#to view h2 console
set spring.h2.console.enabled=true

#Library data
file data.sql

#input commands
| Person              | Action            | details                            |
|---------------------|-------------------|------------------------------------|
| User                |borrow             | customerId, itemType, itemid       |
| User                |return             | customerId, itemType, itemid       |
| User                |createAccount      | name, address, contactNo           |
| User                |search             | customerId, itemType, itemName     |
| librarian           |customerDetail     | customerId                         |
| librarian           |calculateFine      | customerId                         |
| librarian           |overdueList        | customerId                         |
| librarian           |rentedList         | customerId                         |


#thread safe
- avoid creating mutiple accounts for same customer information
- avoid issuing same item to many customer