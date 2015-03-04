package com.prajna.demo;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

public class MultiNodeApplication {
	
	 public static void main( String[] args )
	 {
		 Config cfg = new Config();
	     HazelcastInstance newInstance = Hazelcast.newHazelcastInstance(cfg);
	     IMap<String, Boolean> printStatusMap = newInstance.getMap("customers");
	     	     
	     if (!printStatusMap.containsKey("printStatus"))
	    	 printStatusMap.put("printStatus", false);	     
	     else
	     {
	    	 if (printStatusMap.tryLock("printStatus"))
	    	 {
		    	 if (printStatusMap.get("printStatus") == false)
		    	 {
		    		 System.out.println("We are started !!");	
		    		 printStatusMap.put("printStatus", true);
		    	 }
		    	 printStatusMap.unlock("printStatus");
	    	 }
	     }	     	     
	 }
}
