package no.larsereb.test.javaHTML;

import no.larsereb.javaHTML.HTMLDocument;
import no.larsereb.javaHTML.HTMLTag;

import org.junit.Test;

import junit.framework.Assert;

public class HTMLDocumentTest {
	@Test
	public void test() {
		HTMLDocument d = HTMLDocument.startDocument();
		d.getHTML()
			.setAttribute("manifest", "manifest.txt")
			.newChild("head")
			  .newChild("title")
			    .addText("This is the title").getParent()
			  .getParent()
			.body()
			  .h1("This is a header")
			  .p().addText("This is a paragraph with a ").a()
			    .setAttribute("href", "http://reddit.com/")
			    .addText("link").getParent()
			  .addText("!").getParent();
		
		System.out.println(d);
	}
}
