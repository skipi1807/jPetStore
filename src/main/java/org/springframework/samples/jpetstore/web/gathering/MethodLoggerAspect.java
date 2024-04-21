package org.springframework.samples.jpetstore.web.gathering;


import org.aspectj.lang.JoinPoint;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MethodLoggerAspect {

    public MethodLoggerAspect() {
        System.out.println("Building method logger aspect...");
    }

    public void logMethodEntry(JoinPoint joinPoint) {
        System.out.println("got to log entry");
        logToFile("Entering", joinPoint);
    }

    public void logMethodExit(JoinPoint joinPoint) {
        System.out.println("got to log exit");
        logToFile("Exiting", joinPoint);
    }

    public void logToFile(String eventType, JoinPoint joinPoint) {
        System.out.println("logging to file");
        String useCase = System.getProperty("use.case");
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getName();
        long threadId = Thread.currentThread().getId();
        long timestamp = System.currentTimeMillis();
        String logMessage = timestamp + ",[" + threadId + "]," + eventType + " ... " + className + "::" + methodName;

        String fileName = useCase + ".log"; // File name based on use case

        Path directoryPath = Paths.get("/Users/skipi1807/School/diploma-thesis/sample-apps/apache-tomcat-6.0.9/logs/mono2micro");

        if (!Files.exists(directoryPath) || !Files.isDirectory(directoryPath)){
            // Directory doesn't exist, create it
            try {
                Files.createDirectory(directoryPath);
                System.out.println("Directory created successfully.");
            } catch (IOException e) {
                System.err.println("Failed to create directory: " + e.getMessage());
            }
        }

        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new FileWriter(directoryPath.resolve(fileName).toString(), true));
            writer.println(logMessage);
        } catch (IOException e) {
            // handle exception
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception e) {
                    // handle exception
                }
            }
        }
    }
}
