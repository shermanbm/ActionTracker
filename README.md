ActionTracker utility.

Requires a minimum of Maven 3.6.2 and Java 1.8 to build and run.
Clone repository and run `mvn clean install` to build the package.

Accepts actions and times and stores the average time for the action.
Results are returned in a JSON array.

To add an action:
Call the method `void addAction(String jsonValue) throws IOException` 
The JOSN string is in the format `{"action":"jump", "time":100}` 
Where jump is the action being saved and 100 is the time being added
This method throws an IOException if there is an error parsing the JSON.

To retrieve the stats:
Call the method `String getStats() throws JsonProcessingException`
The resulting JSON array is in the format:
```
[
    {"action":"jump", "avg":150},
    {"action":"run", "avg":75}
]
```
This method throws JsonProcessingException if there is an error creating the JSON string.