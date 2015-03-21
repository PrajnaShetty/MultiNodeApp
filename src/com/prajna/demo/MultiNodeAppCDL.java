package com.prajna.demo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICountDownLatch;
import com.hazelcast.core.IMap;


public class MultiNodeAppCDL {
	
	 public static void main( String[] args ) {
		 
		 //Creating a new Hazelcast instance
		 Config cfg = new Config();
	     HazelcastInstance newInstance = Hazelcast.newHazelcastInstance(cfg);
	     	    
	     //obtaining a countdownlatch	 
	     ICountDownLatch latch = newInstance.getCountDownLatch( "myCountDownLatch" );	
	     latch.trySetCount(1); 

	     latch.countDown();
	     
	     try{
		     boolean success = latch.await(10, TimeUnit.SECONDS );
		     if (success)
		     {
		    	 IMap<String, Boolean> printStatusMap = newInstance.getMap("printStatusMap");
		    	 
		    	  //Checking if printStatus key exists
			     if (!printStatusMap.containsKey("printStatus"))
			    	 printStatusMap.put("printStatus", false);	     
			     else{
			    	 	//obtaining lock on key	    	 
						if (printStatusMap.tryLock("printStatus", 5000, TimeUnit.MILLISECONDS)) {
							try {
								 if (printStatusMap.get("printStatus") == false)
								 {
									 System.out.println("We are started !! (With COUNTDOWNLATCH)");	
									 printStatusMap.put("printStatus", true);
								 }
							}
						  finally{
									 printStatusMap.unlock("printStatus");
							}
							
						}	    	 
			     }
			 }
	     }catch(InterruptedException e)
	     {
	    	 e.printStackTrace();
	     }
	 }
}
