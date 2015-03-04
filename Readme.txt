MultiNodeApplication

This application can be run on 10 or any number of nodes . The application  coordinates among the nodes and make sure that one and only one of them does a System.out.println("We are started!"). Note: that not all 10 nodes shall start at the same time. It is possible that some will start couple of seconds/minutes later. Some will not at all. Still this message is printed only once. 

How To Run
-------------
For single node, 
$ java -jar target/MultiNodeApp-0.0.1-SNAPSHOT-jar-with-dependencies.jar

(See attached snapshot of 4 nodes running)