package bookstore.constant;

public class SqlConstant {

    public static final String CREATE_BOOKSHELF = "INSERT INTO Bookshelf value (?,?,?,?,?)";
    public static final String UPDATE_BOOKSHELF = "UPDATE Bookshelf SET count = ?, price = ?, arrival_date = ? WHERE id = ?";
    public static final String READ_BOOKSHELF = "SELECT * FROM Bookshelf WHERE id = ?";
    public static final String READ_ALL_BOOKSHELVES = "SELECT * FROM Bookshelf";
    public static final String CREATE_BOOK = "INSERT INTO Book value (?,?,?,?)";
    public static final String READ_BOOK = "SELECT * FROM Book WHERE id = ?";
    public static final String CREATE_ORDER = "INSERT INTO BookOrder value (?,?,?,?,?,?,?,?)";
    public static final String UPDATE_ORDER = "UPDATE BookOrder SET completion_date = ?, price = ?, status = ? WHERE id = ?";
    public static final String READ_ORDER = "SELECT * FROM BookOrder WHERE id = ?";
    public static final String READ_ALL_ORDER = "SELECT * FROM BookOrder";
    public static final String CREATE_REQUEST = "INSERT INTO BookRequest value (?,?,?,?)";
    public static final String UPDATE_REQUEST = "UPDATE BookRequest SET count = ?, status = ? WHERE id = ?";
    public static final String READ_REQUEST = "SELECT * FROM BookRequest WHERE id = ?";
    public static final String READ_ALL_REQUEST = "SELECT * FROM BookRequest";
    public static final String READ_REQUEST_BY_ORDER = "SELECT request_id FROM Order_Request WHERE order_id = ?";
    public static final String CREATE_REQUEST_BY_ORDER = "INSERT INTO Order_Request value (?,?)";
    public static final String READ_BOOK_BY_ORDER = "SELECT book_id FROM Order_Book WHERE order_id = ?";
    public static final String CREATE_BOOK_BY_ORDER = "INSERT INTO Order_Book value (?,?)";

    private SqlConstant() {
    }
}
