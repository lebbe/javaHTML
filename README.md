javaHTML
========

Another HTML Markup Builder written in Java

I wanted to make an easy way to create HTML-strings in java, being a
developer working with jsp and java servlets. Feel free to fork, copy,
or do whatever you want with this code. For good or bad.

Example:

<pre>
public String makeButton(String iconClass, String text) {
  HTMLTag output = new HTMLTag("div");

  output.addStyleRule("margin", "5px").span()
    .span().addClass("icon").addClass(iconClass).getParent()
    .span().addText(text);
    
  return output.root().toString(); // root() fetches the root node
}
</pre>

This could also be:

<pre>
public String makeButton(String iconClass, String text) {
  return new HTMLTag("div").
    addStyleRule("margin", "5px")
    .span()
      .span()
        .addClass("icon")
        .addClass(iconClass)
        .getParent()
      .span()
        .addText(text).
      root().toString;
}
</pre>


<b>Using javaHTML to create custom tags in jsp</b>

javaHTML is optimized for writing output for custom jsp tags:
You can send the JSPWriter object in to the HTMLTag element,
and this object is recursively sent throughout the nodes. This
means fewer costly string-concatenation at runtime.

<pre>
public class CustomTag extends BodyTagSupport {
  public int doStartTag() throws JspException {
		return EVAL_BODY_BUFFERED;
	}
	
	public int doEndTag() throws JspException {
		HTMLTag mainTag;
		
		// Do your stuff here
		....
		
		try {
			mainTag.writeOutput(pageContext.getOut());
		} catch (IOException e) {
			throw new ApplicationException(e);
		}
		return EVAL_PAGE;
	}
	
	public int doAfterBody() {
		return SKIP_BODY;
  }
}
</pre>
