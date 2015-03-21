package com.prajna.demo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICountDownLatch;
import com.hazelcast.core.IMap;


public class MultiNodeApplication {
	
	 public static void main( String[] args ) {
		 
		 //Creating a new Hazelcast instance
		 Config cfg = new Config();
	     HazelcastInstance newInstance = Hazelcast.newHazelcastInstance(cfg);
	     IMap<String, Boolean> printStatusMap = newInstance.getMap("printStatusMap");
	    
	     Lock lock = newInstance.getLock( "myLock" );
	   	 
	     try {
	    	//obtaining lock on critical section
			if (lock.tryLock(5000, TimeUnit.MILLISECONDS)){
				
			     try {
			    	 
			    	  //Checking if printStatus key exists
				     if (!printStatusMap.containsKey("printStatus"))
				    	 printStatusMap.put("printStatus", false);	     
				     else{
				    	 	//obtaining lock on key	    	 
							if (printStatusMap.tryLock("printStatus", 5000, TimeUnit.MILLISECONDS)) {
								try {
									 if (printStatusMap.get("printStatus") == false)
									 {
										 System.out.println("We are started !!");	
										 printStatusMap.put("printStatus", true);
									 }
								}
							  finally{
										 printStatusMap.unlock("printStatus");
								}								
							}
				     }
			     }catch(InterruptedException e){
			    	 e.printStackTrace();
			     }finally{
			    	 lock.unlock();
			     }
			 }
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	 }
}
