package no.larsereb.javaHTML;

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
		StringBuffer b = new StringBuffer();
		b.append(doctype);
		html.toString(b);
		return b.toString();
	}
}
