package no.larsereb.javaHTML;

import java.io.CharArrayWriter;
import java.io.IOException;

public class HTMLDocument {
	private String doctype;
	private HTMLTag html;
	
	public HTMLDocument() {
		doctype = "<!DOCTYPE html>";
		html = new HTMLTag("html");
	}
	
	public HTMLTag getHTML() {
		return html;
	}
	
	public static HTMLDocument startDocument() {
		return new HTMLDocument();
	}
	
	@Override
	public String toString() {
		CharArrayWriter writer = new CharArrayWriter();
		
		writer.append(doctype);
		try {
			html.writeOutput(writer);
		} catch (IOException e) {
		}
		return writer.toString();
	}
}
