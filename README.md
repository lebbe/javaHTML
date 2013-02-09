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
