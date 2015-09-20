package com.bits.canvas.mobile.test;

import java.util.StringTokenizer;

public class Test3 {

	public static void main(String[] args){
		String str = "singh.bk@gmail.com|12.121212|77.121216|HSR LAyout|src123Place|12.121212|77.121216|HSR LAyout|dest123Place|2|2|2|0|0|0|1";
		StringTokenizer tokens = new StringTokenizer(str, "|");
		
		System.out.println(tokens.nextToken());
	}
}
