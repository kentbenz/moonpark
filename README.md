# MoonPark
MoonPark is a Norwegian parking company. They have given you in the task to develop a web service that calculates the price of a parking in their parking garages. 
The price of a parking is provided/defined via parking zones. Today, there are three zones: M1, M2 and M3. It is expected that the number of zones will grow rapidly in the near future.
 
Task 1:
Write the code that calculates the price for a parking in the zone M1.
Rates for parking in M1 is calculated as follows:
60 Norwegian kroners (NOK) per hour, calculated for each started minute.
To define the input and output values is part of the task. The same applies to tests.
 
Task 2:
Write the code that calculates the price for a parking in the zone M1 or M2 (zone to be input to the routine).
Price for parking in M2 is calculated as follows:
100 NOK per hour started on weekdays. 
In the weekend is the price double, which is 200 NOK per started hour.
 
Task 3:
Make the code from task 2 available as a web service.
Clients should be able to do a GET to the path/price with the necessary input parameters as part of the query string.
The response should be a JSON structure.
 
Task 4:
Expand the price calculation engine with support for zone M3:
Price for parking in M3 is calculated as follows:
Monday to Saturday between 08:00 and 16:00, the first hour is free of charge. Then it costs 2 NOK per minute started. 
Monday to Saturday outside of these times, it costs 3 NOK per minute. 
Sundays parking is free.
 
Task 5:
MoonPark has made the price calculation engine open available to everyone. This is proving very popular, and several third-party services take it quickly in use. Unfortunately, it turns out that some clients for various reasons makes extremely many queries, which at times leads to a service overload.
Propose a strategy to solve this problem. Give reasons for your answer.
