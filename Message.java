import java.io.Serializable;

public class Message implements Serializable{

	private String author;
	private String text;

  public Message(String author, String text) {
		this.author = author;
		this.text = text;
	}

  public String getAuthor() {
		return author;
	}

  public String getText() {
		return text;
	}

  @Override
	public String toString() {
		return "Autor: " + author + "   Texto: " + text;
	}

}