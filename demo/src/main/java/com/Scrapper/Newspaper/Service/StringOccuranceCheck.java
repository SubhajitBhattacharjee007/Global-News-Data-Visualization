package com.Scrapper.Newspaper.Service;

import java.util.HashMap;

public class StringOccuranceCheck {

public static void checkOccurance(String string) {
	//String str = "ILOVEMY INDIAI LOVEMYINDIAILOVEMY INDIA".replaceAll(" ", "");
	
	String str = string.replaceAll(" ", "");
	System.out.println("STRING LENGTH : " + str.length());
	int length = 1;
	
	while((length)<str.length()) {
	int len = str.length();
	int k = 0;
	int p ;
	int j = 0;
	String[] arr1 = new String[len-length];
	String[] arr2 = new String[len-length];
	String temp = "";
	
	while((len-length) > 0) {
	p = (k + length);
		
		for(int i=k; i<=p; i++) {
		temp = temp + str.charAt(i);
	    }
		
		if(temp!=null && !temp.isEmpty()) {
			arr1[j] = temp; 
			arr2[j] = temp;
		}
	j ++;
	k ++;
	len --;
	temp = "";
   }
	
   HashMap<String,Integer> map = new HashMap<String, Integer>();
   for(int k1=0; k1<arr1.length; k1++) {
   	   int count=0;
   	   for(int k2=0; k2<arr1.length; k2++) {
		   if(arr1[k1].toLowerCase().equalsIgnoreCase(arr2[k2])) {   
			   count ++;
		   }
	   }
	   map.put(arr1[k1], count);
   }
   
   System.out.println("\nPRINTING DATA HAVING: "+(length+1));
   map.entrySet().forEach(Entry -> {
	   if(Entry.getValue()>1) {
		   System.out.println(Entry.getKey() + "-> Found " + Entry.getValue() + " times");
	   }
   });
   
   length ++;
   } 
  }	
}
