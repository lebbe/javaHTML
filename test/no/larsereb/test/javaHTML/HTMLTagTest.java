package no.larsereb.test.javaHTML;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import no.larsereb.javaHTML.HTMLTag;

import org.junit.Test;

import junit.framework.Assert;

public class HTMLTagTest {
	@Test
	public void test() {
		HTMLTag tag = new HTMLTag("html");
		Assert.assertEquals("<html></html>", tag.toString());
		tag.addStyleClass("someClass");
		Assert.assertEquals("<html class=\"someClass\"></html>", tag.toString());
		tag = new HTMLTag("html").addStyleRule("padding", "5px");
		Assert.assertEquals("<html style=\"padding: 5px;\"></html>", tag.toString());
		tag = new HTMLTag("html").setAttribute("manifest", "manifest.txt");
		Assert.assertEquals("<html manifest=\"manifest.txt\"></html>", tag.toString());

		tag.addStyleRule("margin-bottom", "5px")
		   .addStyleClass("someClass")
		   .setAttribute("data-rol", "test")
		   .setAttribute("data-comment", "just testing that chaining works.");
	}

	@Test
	public void testAnchor() {
		HTMLTag tag = new HTMLTag("div")
			.addStyleClass("button")
			.span()
			  .setAttribute("class", "iconDelete")
			  .getParent()
			.span()
			  .addStyleClass("text")
			  .setAsAnchor().getRoot();

		try {
			OutputStream stream = new ByteArrayOutputStream();
			OutputStreamWriter out = new OutputStreamWriter(stream);
			HTMLTag.writeStartTagOutput(tag, out);
			out.close();
			Assert.assertEquals("<div class=\"button\"><span class=\"iconDelete\"></span><span class=\"text\">", stream.toString());
			stream = new ByteArrayOutputStream();
			out = new OutputStreamWriter(stream);
			HTMLTag.writeEndTagOutput(tag, out);
			out.close();
			Assert.assertEquals("out = new OutputStreamWriter(stream);", stream.toString());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
