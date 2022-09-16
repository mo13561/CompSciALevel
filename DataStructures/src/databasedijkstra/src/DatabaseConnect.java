import java.sql.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnect
{
    private static Connection conn = null;

    public static void main(String[] args) throws Exception {
        DatabaseConnect conn = new DatabaseConnect();
        //conn.createTable();
        //conn.insert();
        //conn.select();
        //conn.update();

        Scanner sc = new Scanner(System.in);
        int response;
        do {
            System.out.println("1) Customer Maintenance");
            System.out.println("2) Movie Maintenance");
            System.out.println("3) Download Maintenance");
            System.out.println("4) Rental Maintenance");
            System.out.println("5) Display Overdue Fees");
            System.out.println("6) Shortest path (from Johnny)");
            System.out.println("7) Quit");
            response = sc.nextInt();
            switch (response) {
                case 1:
                    customer();
                    break;
                case 2:
                    movie();
                    break;
                case 3:
                    download();
                    break;
                case 4:
                    rental();
                case 5:
                    boolean feesFetched = overdueFees();
                    System.out.println("Fees Fetched? " + feesFetched);
                    break;
                case 6:
                    findPath();
                default: break;
            }
        } while (response != 7);
        conn.close();
    }

    private static boolean findPath() throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("Name of customer to find path to: ");
        String name = sc.nextLine().trim();
        int destCustID = getCustomerID(name);
        boolean bSelect = false;
        Statement stmt;
        ResultSet rs;
        AdjList<Integer> adj = new AdjList<>(false);
        try
        {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT Connections.customer1, Connections.customer2, Connections.distance FROM Connections; ");
            while (rs.next())
            {
                int custID1 = rs.getInt("customer1");
                int custID2 = rs.getInt("customer2");
                int distance = rs.getInt("distance");
                adj.add(custID1,custID2,distance);
            }

            rs.close();
            stmt.close();
            bSelect = true;
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Dijkstra g = new Dijkstra(adj,1);
        System.out.println(g.Path(destCustID));
        System.out.println("Final distance from johnny to " + name + " : " + g.PathLength(destCustID));
        return bSelect;
    }

    private static boolean overdueFees() {
        boolean bSelect = false;
        Statement stmt;
        ResultSet rs;

        try
        {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT Movie.MovieID, Download.DownloadID, Download.DateOfDownload, (CASE WHEN ((julianday('now') - julianday(Download.DateOfDownload)) > 7) THEN (Movie.Price * Overdue.MoreThanOneWeek) "
                    + "WHEN ((julianday('now') - julianday(Download.DateOfDownload)) <= 7) THEN (Movie.Price * Overdue.OneWeekOrLess) END) AS overduefee, Download.CustomerID, Customer.CustomerName "
                    + "FROM Download, Movie, Overdue, Customer "
                    + "WHERE Download.CustomerID = Customer.CustomerID AND Overdue.Rating = Movie.Age AND Download.MovieID = Movie.MovieID AND Download.BoughtOrRented = 'rented' AND ((julianday('now') - julianday(Download.DateOfDownload)) > Overdue.Duration);");

            while (rs.next())
            {
                String customerName = rs.getString("customerName");

                System.out.println("NAME = " + customerName);
                System.out.println();
            }

            rs.close();
            stmt.close();
            bSelect = true;
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return bSelect;
    }

    private static void rental() {
        Scanner sc = new Scanner(System.in);
        int response;
        do {
            System.out.println("1) Add Rental");
            System.out.println("2) Modify Rental");
            System.out.println("3) Delete Rental");
            System.out.println("4) Main Menu");
            response = sc.nextInt();
            switch (response) {
                case 1:
                    boolean rentAdded = addRental();
                    System.out.println("Rental Added? " + rentAdded);
                    break;
                case 2:
                    //modifyCustomer();
                    break;
                case 3:
                    //deleteCustomer();
                    break;
                default: break;
            }
        } while (response != 4);

    }

    private static boolean addRental() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Name: ");
        String customerName = sc.nextLine();
        System.out.println("Movie: ");
        String movieName = sc.nextLine().trim();
        System.out.println("Rental Date (YYYY-MM-DD): ");
        String rentalDate = sc.nextLine().trim();
        System.out.println("Return Date (YYYY-MM-DD): ");
        String returnDate = sc.nextLine().trim();
        int movieID = -1;
        int customerID = -1;
        boolean bInsert = false;

        Statement stmtt;
        ResultSet rs;

        try
        {
            stmtt = conn.createStatement();
            rs = stmtt.executeQuery("SELECT (MovieID) FROM Movie WHERE Movie.Downloaded = '"+movieName+"';");
            movieID = rs.getInt("MovieID");
            rs.close();
            stmtt.close();
        } catch (Exception e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        Statement stmttt;
        ResultSet rs2;

        try
        {
            stmttt = conn.createStatement();
            rs2 = stmttt.executeQuery("SELECT (CustomerID) FROM Customer WHERE CustomerName = '"+customerName+"';");
            customerID = rs2.getInt("CustomerID");
            rs2.close();
            stmttt.close();
        } catch (Exception e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        Statement stmt;
        try
        {
            stmt = conn.createStatement();
            String sql = "INSERT INTO Download (DateOfDownload,CustomerID,MovieID,BoughtOrRented) "
                    + "VALUES (DATE('"+rentalDate+"'), "+customerID+", "+movieID+", 'rented' );";
            stmt.executeUpdate(sql);
            stmt.close();
            conn.commit();
            bInsert = true;

        } catch (Exception e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return bInsert;

    }

    private static void download() {
    }

    private static void movie() {
    }

    private static int getCustomerID(String name) {
        int customerID = -1;
        boolean bSelect = false;

        Statement stmtt;
        ResultSet rs;

        try
        {
            stmtt = conn.createStatement();
            rs = stmtt.executeQuery("SELECT (Customer.CustomerID) FROM Customer WHERE Customer.CustomerName = '"+name+"';");
            customerID = rs.getInt("CustomerID");
            rs.close();
            stmtt.close();
        } catch (Exception e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return customerID;
    }

    private static void customer() {
        Scanner sc = new Scanner(System.in);
        int response;
        do {
            System.out.println("1) Add Customer");
            System.out.println("2) Modify Customer");
            System.out.println("3) Delete Customer");
            System.out.println("4) Display Purchase History");
            System.out.println("5) Display Overdue Fees");
            System.out.println("6) Main Menu");
            response = sc.nextInt();
            switch (response) {
                case 1:
                    boolean custAdded = addCustomer();
                    System.out.println("Customer Added? " + custAdded);
                    break;
                case 2:
                    boolean custModified = modifyCustomer();
                    System.out.println("Customer Modified? " + custModified);
                    break;
                case 3:
                    boolean custDeleted = deleteCustomer();
                    System.out.println("Customer Deleted? " + custDeleted);
                    break;
                case 4:
                    //purchaseHistory();
                    break;
                case 5:
                    //customerOverdueFees();
                    break;
                default: break;
            }
        } while (response != 6);
    }

    private static boolean deleteCustomer() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Name: ");
        String name = sc.nextLine();
        boolean bInsert = false;
        Statement stmt;

        try
        {
            stmt = conn.createStatement();
            String sql = "DELETE FROM Customer WHERE CustomerName = '" + name + "';" ;
            stmt.executeUpdate(sql);
            stmt.close();
            conn.commit();
            bInsert = true;

        } catch (Exception e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return bInsert;
    }

    private static boolean modifyCustomer() {
        Scanner sc = new Scanner(System.in);
        boolean success = false;
        System.out.println("Name of customer to be modified: ");
        String name = sc.nextLine();
        int response;
        do {
            System.out.println("1) Update Phone");
            System.out.println("2) Update Address");
            response = sc.nextInt();
            switch (response) {
                case 1:  success = updateCustomer(true, name); break;
                case 2: success = updateCustomer(false, name); break;
                default: break;
            }
        } while (response != 3);
        return success;
    }
    private static boolean updateCustomer(Boolean phone, String name) {
        Scanner sc = new Scanner(System.in);
        System.out.println(phone ? "Phone: " : "Address: ");
        String data = sc.nextLine();
        boolean bInsert = false;
        Statement stmt;

        try
        {
            stmt = conn.createStatement();
            String sql;
            if (phone) {
                sql = "UPDATE Customer " +
                        "SET PhoneNumber = '" + data + "' " +
                        "WHERE CustomerName = '" + name + "';";
            } else {
                sql = "UPDATE Customer " +
                        "SET CustomerAddress = '" + data + "' " +
                        "WHERE CustomerName = '" + name + "';";
            }
            stmt.executeUpdate(sql);
            stmt.close();
            conn.commit();
            bInsert = true;

        } catch (Exception e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return bInsert;
    }

    private static boolean addCustomer() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Name: ");
        String name = sc.nextLine();
        System.out.println("Phone: ");
        String phone = sc.nextLine();
        System.out.println("Date of Birth (YYYY-MM-DD): ");
        String date = sc.nextLine().trim();
        System.out.println("Address: ");
        String address = sc.nextLine().trim();
        boolean bInsert = false;
        Statement stmt;

        try
        {
            stmt = conn.createStatement();
            String sql = "INSERT INTO Customer (CustomerName,PhoneNumber,DateofBirth,CustomerAddress) "
                    + "VALUES ('"+name+"', '"+phone+"', DATE('"+date+"'), '"+address+"' );";
            stmt.executeUpdate(sql);
            stmt.close();
            conn.commit();
            bInsert = true;

        } catch (Exception e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return bInsert;
    }

    public DatabaseConnect()
    {
        try
        {
            Class.forName("org.sqlite.JDBC");//Specify the SQLite Java driver
            conn = DriverManager.getConnection("jdbc:sqlite:FINALDBMOVIERENT (1).db");//Specify the database, since relative in the main project folder
            conn.setAutoCommit(false);// Important as you want control of when data is written
            System.out.println("Opened database successfully");
        } catch (Exception e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void close()
    {
        try
        {
            conn.close();
        }
        catch (SQLException ex)
        {
            Logger.getLogger(DatabaseConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean createTable()
    {
        Statement stmt = null;

        try
        {
            stmt = conn.createStatement();
            String sql = "CREATE TABLE COMPANY "
                    + "(ID INT PRIMARY KEY     NOT NULL,"
                    + " NAME           TEXT    NOT NULL, "
                    + " AGE            INT     NOT NULL, "
                    + " ADDRESS        CHAR(50), "
                    + " SALARY         REAL)";
            stmt.executeUpdate(sql);
            stmt.close();
            conn.commit();
        } catch (Exception e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return stmt != null;
    }

    public boolean insert()
    {
        boolean bInsert = false;
        Statement stmt;

        try
        {
            stmt = conn.createStatement();
            String sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) "
                    + "VALUES (1, 'Paul', 32, 'California', 20000.00 );";
            stmt.executeUpdate(sql);

            sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) "
                    + "VALUES (2, 'Allen', 25, 'Texas', 15000.00 );";
            stmt.executeUpdate(sql);

            sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) "
                    + "VALUES (3, 'Teddy', 23, 'Norway', 20000.00 );";
            stmt.executeUpdate(sql);

            sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) "
                    + "VALUES (4, 'Mark', 25, 'Richmond ', 65000.00 );";
            stmt.executeUpdate(sql);

            stmt.close();
            conn.commit();
            bInsert = true;

        } catch (Exception e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return bInsert;
    }

    public boolean select()
    {
        boolean bSelect = false;
        Statement stmt;
        ResultSet rs;

        try
        {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM COMPANY;");

            while (rs.next())
            {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String address = rs.getString("address");
                float salary = rs.getFloat("salary");

                System.out.println("ID = " + id);
                System.out.println("NAME = " + name);
                System.out.println("AGE = " + age);
                System.out.println("ADDRESS = " + address);
                System.out.println("SALARY = " + salary);
                System.out.println();
            }

            rs.close();
            stmt.close();
            bSelect = true;
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return bSelect;
    }

    public boolean update()
    {
        boolean bUpdate = false;
        Statement stmt;

        try
        {
            stmt = conn.createStatement();
            String sql = "UPDATE COMPANY set SALARY = 25000.00 where ID=1;";
            stmt.executeUpdate(sql);
            stmt.close();
            conn.commit();
            bUpdate = true;
        } catch (Exception e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return bUpdate;
    }
}