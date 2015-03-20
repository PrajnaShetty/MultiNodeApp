package com.prajna.demo;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;


public class MultiNodeAppWithoutLock {
	
	 public static void main( String[] args ) throws InterruptedException {
		 
		 //Creating a new Hazelcast instance
		 Config cfg = new Config();
	     HazelcastInstance newInstance = Hazelcast.newHazelcastInstance(cfg);
	     IMap<String, Boolean> printStatusMap = newInstance.getMap("printStatusMap");
	    
	     //Checking if printStatus key exists	     
	     if (!printStatusMap.containsKey("printStatus"))
	    	 printStatusMap.put("printStatus", false);	     
	     else{
	    	 Thread.sleep(10);
	    	 boolean oldValue = printStatusMap.get("printStatus");
	    	 if (oldValue  == false)
			 {				 	
	    		 boolean newValue = true;
				 printStatusMap.replace("printStatus", oldValue, newValue); // atomic operation
				 System.out.println("We are started !! (Without Lock)");
			 }
	     }	     	     
	 }
}
