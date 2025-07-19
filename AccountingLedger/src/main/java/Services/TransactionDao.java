package Services;

import Models.Transaction;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDao {

	private final DataSource dataSource;

	public TransactionDao(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	//Gets all transactions that are in accountingledger_v2 db
	public List<Transaction> getAll() {
		//Create list to put our results into
		List<Transaction> transactionList = new ArrayList<>();
		String query = "SELECT * FROM transactions;";

		//try with resources to create a connection with SQL db
		try(Connection connection = dataSource.getConnection()) {
			//Prepare the query/statement
			PreparedStatement statement = connection.prepareStatement(query);

			//Get the results
			ResultSet results = statement.executeQuery();
			//For all results/rows returned from db, map each to a Transaction object, then add it to the list.
			while(results.next()) {
				transactionList.add(mapRow(results));
			}

		} catch(SQLException e) {
			throw new RuntimeException(e);
		}

		return transactionList;
	}


	//Maps a row to a Transaction object from the database results. Returns the Transaction object
	private Transaction mapRow(ResultSet results) throws SQLException {
		int transactionId = results.getInt("transaction_id");
		Date date = results.getDate("date");
		Time time = results.getTime("time");
		String description = results.getString("description");
		String vendor = results.getString("vendor");
		double amount = results.getDouble("amount");

		return new Transaction(transactionId, date, time, description, vendor, amount);
	}


}
