# SpringBootOpenTelemetry

This project is an example of using OpenTelemetry autoinstrumentation for java in a SpringBoot application.

# QuickStart:
Run
    
docker-compose up -d

in  agent_collector_manifests/

Run from the root of the project.

java -javaagent:/libs/opentelemetry-javaagent-all-1.3.1.jar -jar build/libs/demo-0.0.1-SNAPSHOT.jar

go to 

http://localhost:8080/usermanager/hello

go to 

http://localhost:16686/search

and view the spans created.

# Info

This project uses an in memory DB to showcase Span generation for SQL requests.
It provides a set of endpoints that create Spans.

InvoiceController.java

    manualSpan: Showcases how to use manual instrumentation. Creating a Span, Adding all header information and finishing it.
    paramAutoSpam: Shows a basic endpoint with a Request param using autoinstrumentation.
    requestBodyAutoSpam: Shows a basic endpoint with a requestBody param using autoinstrumentation.
    getAllUsers: Shows span of a SQL query to query for all users
    saveUser: Shows span of saving to a random user in the db
    500, 401, 404: Shows span for basic http errors

SpanFilter.java

    This class intercepts all requests and automatically adds all the Requests headers into a Span that's added into the current trace.
    Not fully tested

User, UserRepository

    Classes to allow to save the in memoryDB.

This application uses the opentelemetry-javaagent lib located in: 
/libs/opentelemetry-javaagent-all-1.3.1.jar