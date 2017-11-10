
/**
 * Code containing getters and setters for book class.
 *
 */
public class Book {
	private String code;
	private String title;
	private double price;

	public Book() {
		this("", "", 0.00);
	}

	/**
	 * @param bookCode
	 * @param bookTitle
	 * @param bookPrice
	 */
	public Book(String bookCode, String bookTitle, double bookPrice){
		code = bookCode;
		title = bookTitle;
		price = bookPrice;
	}

	/**
	 * @return
	 */
	public String getCode() { 
		return code; 
	}

	/**
	 * @return
	 */
	public String getTitle() {
		return title; 
	}

	/**
	 * @return
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @param bookCode
	 */
	public void setCode(String bookCode) {
		code = bookCode;
	}
	/**
	 * @param bookTitle
	 */
	public void setTitle(String bookTitle) {
		title = bookTitle;
	}
	/**
	 * @param bookPrice
	 */
	public void setPrice(double bookPrice){
		price = bookPrice;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		return "Code: " + code + "\n" +
				"Title: " + title + "\n" +
				"Price: " + price + "\n";
	}
}