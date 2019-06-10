package com.pkg.utilities;

import java.util.Scanner;

public class ServiceBUSCheck{

//prime number 
	
	public static void main(String []args){
		fibonacciseries();
	Scanner sc=new Scanner(System.in);
	System.out.println("Enter the number");
	int i=sc.nextInt();
	System.out.println("your number is: "+i);
	 int flag=0;
	 
	 if(i==0|i==1){
		 System.out.println("Not a prime number");
	 }
	 
	 else{
		 for(int n=2;n<=i/2;n++){
			 if(i%n==0){
				 System.out.println("Not a prime number");
				 flag=1;
				 break;
			 }
			 
		 }
		 
		 if(flag==0){
			 System.out.println("No is prime");
		 }
	 }
	
	}
	
	
	public static void fibonacciseries(){
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the numbers which are in fibonacci series");
		int k=sc.nextInt();
		int a=0;
		int b=1;
		for(int i=0;i<k;i++){
			System.out.println(a);			
			int c=a+b;
			a=b;
			b=c;
			
				
			
			
			
		}
		
		
		
		
	}
	


}
