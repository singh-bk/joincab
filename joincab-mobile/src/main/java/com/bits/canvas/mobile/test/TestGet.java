package com.bits.canvas.mobile.test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TestGet {

	public static void main(String[] args) throws Exception{
		URL url = new URL("http://localhost:8080/thehytt/detail/hop?hopId=dada501b-2b93-42cb-ae5d-7b7916deed86&postId=86143103-eef5-42c9-9203-60dc095b713a");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		try {

			conn.setDoInput(true);
			// Start the query
			InputStream in = new BufferedInputStream(conn.getInputStream());
			String str = readStream(in);
			System.out.println(str);
			byte[] bytes = str.getBytes();
			for(int i=0;i<bytes.length;i++)
			System.out.print(bytes[i]);
		} finally {
			conn.disconnect();
		}
	
	}
	
	private static String readStream(InputStream stream){
		StringBuilder resp = new StringBuilder();

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
			String line = "";
			while((line = reader.readLine()) != null){
				resp.append(line);
			}
		}catch(Exception ex){
		}
		return resp.toString();
	}
}
