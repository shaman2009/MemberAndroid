package com.dandelion.memberandroid.httpapi;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.squareup.okhttp.OkHttpClient;

public class OkHttp {
	public static OkHttpClient client = new OkHttpClient();
	void run() throws IOException {
		String result = get(new URL("https://raw.github.com/square/okhttp/master/README.md"));
		System.out.println(result);
	}
	public static String get(URL url) throws IOException {
		HttpURLConnection connection = client.open(url);
		InputStream in = null;
		try {
			// Read the response.
			in = connection.getInputStream();
			byte[] response = readFully(in);
			return new String(response, "UTF-8");
		} finally {
			if (in != null)
				in.close();
		}
	}
	public static String post(URL url, byte[] body) throws IOException {
		HttpURLConnection connection = client.open(url);
		OutputStream out = null;
		InputStream in = null;
		try {
			// Write the request.
			connection.setRequestMethod("POST");
			out = connection.getOutputStream();
			out.write(body);
			out.close();

			// Read the response.
			if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new IOException("Unexpected HTTP response: "
						+ connection.getResponseCode() + " "
						+ connection.getResponseMessage());
			}
			in = connection.getInputStream();
			return readFirstLine(in);
		} finally {
			// Clean up.
			if (out != null)
				out.close();
			if (in != null)
				in.close();
		}
	}
	public static String put(URL url, byte[] body) throws IOException {
		HttpURLConnection connection = client.open(url);
		OutputStream out = null;
		InputStream in = null;
		try {
			// Write the request.
			connection.setRequestMethod("PUT");
			out = connection.getOutputStream();
			out.write(body);
			out.close();

			// Read the response.
			if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new IOException("Unexpected HTTP response: "
						+ connection.getResponseCode() + " "
						+ connection.getResponseMessage());
			}
			in = connection.getInputStream();
			return readFirstLine(in);
		} finally {
			// Clean up.
			if (out != null)
				out.close();
			if (in != null)
				in.close();
		}
	}
	public static String readFirstLine(InputStream in) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
		return reader.readLine();
	}
	public static byte[] readFully(InputStream in) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		for (int count; (count = in.read(buffer)) != -1;) {
			out.write(buffer, 0, count);
		}
		return out.toByteArray();
	}

	public static void main(String[] args) throws IOException {
		new OkHttp().run();
	}
}