package no.larsereb.javaHTML;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class HTMLTag {
	private List<String> styleClasses;
	private String nodeName;
	private HTMLTag parent;
	private List<HTMLTag> children;
	private Map<String, String> attributes;
	private Map<String, String> styleRules;
	private boolean onlyText;
	private String text;

	/**
	 * Constructor.
	 *
	 * @param nodeName
	 */
	public HTMLTag(String nodeName) {
		this.nodeName = nodeName;
		styleClasses = new ArrayList<String>();
		attributes = new HashMap<String, String>();
		styleRules = new HashMap<String, String>();
		children = new ArrayList<HTMLTag>();
	}

	/**
	 * If the end result is going to be fed to a {@link Writer}, use
	 * {@link #writeOutput(Writer)} instead.
	 */
	@Override
	public String toString() {
		CharArrayWriter writer = new CharArrayWriter();
		try {
			writeOutput(writer);
			return writer.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// I dont know if this really make sense.
		return nodeName != null ? nodeName : text;
	}

	/**
	 * Writes the HTML directly to the Writer. Just pass the JspWriter into this
	 * method for efficient HTML output.
	 *
	 * @param writer
	 * @throws IOException
	 */
	public void writeOutput(Writer writer) throws IOException {
		if(onlyText && text != null) {
			writer.write(text);
			return;
		}
		writer.write("<" + nodeName);

		// Write classes into "class" attribute.
		boolean first = true;
		if(!styleClasses.isEmpty()) {
			writer.write(" class=\"");
			for(String c: styleClasses)
				if(first) {
					first = false;
					writer.write(c);
				} else
					writer.write(" " + c);
			writer.write("\"");
		}

		// output other properties
		for(Entry<String, String> e: attributes.entrySet())
			writer.write(" " + e.getKey() + "=\"" + e.getValue() + "\"");
		
		// Write style rules into "style" attribute
		if(!styleRules.isEmpty()) {
			writer.write(" style=\"");
			
			for(Entry<String, String> e: styleRules.entrySet())
				writer.write(e.getKey() + ":" + e.getValue() + ";");
			
			writer.write("\"");
		}

		// Output children
		if(!children.isEmpty()) {
			writer.write(">");
			
			for(HTMLTag child: children)
				child.writeOutput(writer);
;
		} else {
			writer.write(">");
		}
		writer.write("</" + nodeName + ">");
	}

	/**
	 * Get string representation of the end tag.
	 * @return
	 */
	public String getEndTag() {
		return "</" + nodeName + ">";
	}

	/**
	 *
	 * @param styleClass
	 * @return this object
	 */
	public HTMLTag addStyleClass(String styleClass) {
		if(styleClass != null && !styleClass.isEmpty())
			styleClasses.add(styleClass);
		return this;
	}

	/**
	 * Parses the value of a HTML "class"-attribute. Does not check if
	 * values are valid, that is your job.
	 * 
	 * @param styleClasses
	 * @return this object
	 */
	public HTMLTag addStyleClasses(String styleClasses) {
		if(styleClasses == null || styleClasses.isEmpty())
			return this;
		for(String styleClass: styleClasses.split(" "))
			if(!styleClass.isEmpty())
				addStyleClass(styleClass);
		return this;
	}

	/**
	 * 
	 * @param attribute
	 * @param value
	 * @return this object
	 */
	public HTMLTag setAttribute(String attribute, String value) {
		if(attribute != null && value != null)
			attributes.put(attribute, value);
		return this;
	}
	
	public HTMLTag setId(String id) {
		return setAttribute("id", id);
	}

	/**
	 * 
	 * @param attibute
	 * @param value
	 * @return this object
	 */
	public HTMLTag addStyleRule(String attibute, String value) {
		styleRules.put(attibute, value);
		return this;
	}

	/**
	 * Parses the value of a HTML "style"-attribute.
	 *
	 * @param styleRules
	 * @return this object
	 */
	public HTMLTag addStyleRules(String styleRules) {
		if(styleRules == null || styleRules.isEmpty())
			return this;
		for(String styleRule: styleRules.split(";"))
			if(!styleRule.isEmpty()) {
				String[] style = styleRule.split(":");
				addStyleRule(style[0].trim(), style[1].trim());
			}
		return this;
	}

	/**
	 * 
	 * @param nodeName
	 * @return this object
	 */
	public HTMLTag setNodeName(String nodeName) {
		this.nodeName = nodeName;
		return this;
	}
	
	/**
	 * 
	 * @param nodeName
	 * @return the new child
	 */
	public HTMLTag newChild(String nodeName) {
		HTMLTag child = new HTMLTag(nodeName);
		children.add(child);
		child.parent = this;
		return child;
	}

	/**
	 * Add child, but return THIS node.
	 * @param child
	 * @return
	 */
	public HTMLTag newChild(HTMLTag child) {
		children.add(child);
		child.parent = this;
		return this;
	}
	
	/**
	 * 
	 * @return parent object
	 */
	public HTMLTag getParent() {
		return parent;
	}

	/**
	 * 
	 * @return root, if node is root returns itself.
	 */
	public HTMLTag getRoot() {
		if(parent != null)
			return parent.getRoot();
		return this;
	}

	/**
	 * Behaves like a child-node. You can insert actual text, any random HTML,
	 * a {@link HTMLTag} object or anything else you can think of.
	 *
	 * @param text
	 * @return this object
	 */
	public HTMLTag addText(Object text) {
		HTMLTag t = this.newChild("text");
		t.setOnlyText(true);
		t.setText(text.toString());
		return this;
	}

	private void setOnlyText(boolean onlyText) {
		this.onlyText = onlyText;
	}
	
	private void setText(String text) {
		this.text = text;
	}

	public HTMLTag body() {
		return newChild("body");
	}

	public HTMLTag head() {
		return newChild("head");
	}
	
	/**
	 * Add title, only works if this is a head.
	 *
	 * @param title
	 * @return
	 */
	public HTMLTag setTitle(String title) {
		if(nodeName.toLowerCase().equals("head"))
			newChild("title").addText(title);
		return this;
	}
	
	public HTMLTag div() {
		return newChild("div");
	}
	
	public HTMLTag span() {
		return newChild("span");
	}

	public HTMLTag h1() {
		return newChild("h1");
	}

	public HTMLTag p() {
		return newChild("p");
	}

	public HTMLTag table() {
		return newChild("table");
	}

	public HTMLTag tbody() {
		return newChild("tbody");
	}
	
	public HTMLTag tr() {
		return newChild("tr");
	}
	
	public HTMLTag th() {
		return newChild("th");
	}
	
	public HTMLTag td() {
		return newChild("td");
	}

	public HTMLTag a() {
		return newChild("a");
	}

	/**
	 * Look how nice and simple it can be.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		HTMLTag tag = new HTMLTag("span");
		HTMLTag oldTag = tag;
		tag.addStyleRule("padding-top", "5px");
		tag.addStyleRule("padding-bottom", "3px");
		tag.addStyleRules("attribute: value; strange: not-strange");
		tag.addStyleClass("test");
		tag.addStyleClass("nice");
		tag.setAttribute("id", "thisIsId");
		System.out.println(tag);
		tag = tag.newChild("a");
		tag.setAttribute("href", "http://vg.no");
		tag.addStyleRule("font-weight", "bolder");
		tag.addText("Trykk her for å besøke vg.");
		System.out.println(oldTag);

		tag = new HTMLTag("html")
		  .head()
		    .setTitle("Simpelt").getParent()
		  .body()
		    .h1()
		      .addText("Se så lett det er!").getParent()
		    .p()
		      .addText("Dette er et eksempel for å vise hvor lett det er å lage en html-side i java.")
		      .addStyleRule("font-size", "11px").getParent()
		    .p()
		      .a()
		        .setAttribute("href", "http://vg.no")
		        .addText("Trykk her for å besøke vg.");
		System.out.println(tag.getRoot());
	}

	public HTMLTag h1(String string) {
		h1().addText(string);
		return this;
	}
}