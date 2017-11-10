import java.sql.*;

public class BookDB {
	private static Connection connection;
	private static Statement scrollStatement;
	private static ResultSet books;

	
	/**
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * code for database connection.
	 */
	public static void loadDB() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/book_database","root","password");
		if (connection != null) {
			System.out.println("Connected to the database test");
		}
	}

	/**
	 * @throws SQLException
	 * code to open database connection.
	 */
	public static void open() throws SQLException {
		scrollStatement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		String query = "SELECT BookCode, BookTitle, BookPrice "
				+ "FROM BookDB ORDER BY BookCode ASC";
		books = scrollStatement.executeQuery(query);
	}
	/**
	 * code to close database connection.
	 */
	public static void close() {
		try {
			books.close();
			scrollStatement.close();
		}
		catch(SQLException sqle) {
			System.err.println(sqle.getMessage());
		}
	}

	/**
	 * @return
	 * @throws SQLException
	 * Code to display the first record.
	 */
	public static Book moveFirst() throws SQLException {
		books.first();
		Book firstBook = new Book(books.getString("BookCode"),
				books.getString("BookTitle"),
				books.getDouble("BookPrice"));
		return firstBook;
	}

	/**
	 * @return
	 * @throws SQLException
	 * Code to display the previous record.
	 */
	public static Book movePrevious() throws SQLException {
		if (books.isFirst() == false)
			books.previous();
		else
			books.first();
		Book previousBook = new Book(books.getString(1),
				books.getString(2),
				books.getDouble(3));
		return previousBook;

	}
	
	
	/**
	 * @return
	 * @throws SQLException
	 * Code to display the next record.
	 */
	public static Book moveNext() throws SQLException {
		if (books.isLast() == false)
			books.next();
		else
			books.last();
		Book nextBook = new Book(books.getString(1),
				books.getString(2),
				books.getDouble(3));
		return nextBook;

	}

	/**
	 * @return
	 * @throws SQLException
	 * Code to display the last record.
	 */
	public static Book moveLast() throws SQLException {
		books.last();
		Book lastBook = new Book(books.getString(1),
				books.getString(2),
				books.getDouble(3));
		return lastBook;
	}
	
	
	/**
	 * @param book
	 * @throws SQLException
	 * Code to add the book record in the database.
	 */
	public static void addRecord(Book book) throws SQLException {
		String query = "INSERT INTO BookDB (BookCode, BookTitle, BookPrice) " +
				"VALUES ('" + book.getCode() + "', " +
				"'" + book.getTitle() + "', " +
				"'" + book.getPrice() + "')";
		Statement statement = connection.createStatement();
		statement.executeUpdate(query);
		statement.close();
		close();
		open();
	}

	
	/**
	 * @param book
	 * @throws SQLException
	 * code to update and save the data in database.
	 */
	public static void saveDB(Book book) throws SQLException {
		String query = "UPDATE BookDB SET " +
				"BookCode = '" + book.getCode() + "', " +
				"BookTitle = '" + book.getTitle() + "', " +
				"BookPrice = '" + book.getPrice() + "' " +
				"WHERE BookCode = '" + book.getCode() + "'";
		Statement statement = connection.createStatement();
		statement.executeUpdate(query);
		statement.close();
	}

	
	/**
	 * @param bookCode
	 * @throws SQLException
	 * Code to delete record from database.
	 */
	public static void deleteRecord(String bookCode) throws SQLException {
		String query = "DELETE FROM BookDB " +
				"WHERE BookCode = '" + bookCode + "'";
		Statement statement = connection.createStatement();
		statement.executeUpdate(query);
		statement.close();
		close();
		open();
	}

	
	/**
	 * @param bookCode
	 * @throws SQLException
	 * code to search the book details on the basis of bookCode.
	 */
	public static void findOnCode(String bookCode) throws SQLException {
		String query = "SELECT * FROM BookDB WHERE BookCode = '" + bookCode + "'";
		Statement statement = connection.createStatement();
		books = statement.executeQuery(query);
		statement.close();
		close();
		open();
	}
}