package no.larsereb.test.javaHTML;

import no.larsereb.javaHTML.HTMLTag;

import org.junit.Test;

import junit.framework.Assert;

public class HTMLTagTest {
	@Test
	public void test() {
		HTMLTag tag = new HTMLTag("html");
		Assert.assertEquals("<html></html>", tag.toString());
		tag.addClass("someClass");
		Assert.assertEquals("<html class=\"someClass\"></html>", tag.toString());
		tag = new HTMLTag("html").addStyleRule("padding", "5px");
		Assert.assertEquals("<html style=\"padding:5px;\"></html>", tag.toString());
		tag = new HTMLTag("html").setAttribute("manifest", "manifest.txt");
		Assert.assertEquals("<html manifest=\"manifest.txt\"></html>", tag.toString());
		
		tag.addStyleRule("margin-bottom", "5px")
		   .addClass("someClass")
		   .setAttribute("data-rol", "test")
		   .setAttribute("data-comment", "just testing that chaining works.");
	}
	
	@Test
	public void createCompleteSite() {
		
	}
}
