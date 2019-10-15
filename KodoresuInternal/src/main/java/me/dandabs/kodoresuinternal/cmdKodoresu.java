package me.dandabs.kodoresuinternal;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Connection;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class cmdKodoresu implements CommandExecutor {

    // DataBase vars.
    final String username = "kodoresu"; // Enter in your db username
    final String password = "j9fxUaNv82WJmf63"; // Enter your password for the db
    final String url = "jdbc:mysql://116.203.95.196:3306/kodoresu"; // Enter URL w/db name

    // Connection vars
    static Connection connection; // This is the variable we will use to connect to database

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        try { // We use a try catch to avoid errors, hopefully we don't get any.
            Class.forName("com.mysql.jdbc.Driver"); // this accesses Driver in jdbc.
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("jdbc driver unavailable!");
            return false;
        }
        try { // Another try catch to get any SQL errors (for example connections errors)
            connection = DriverManager.getConnection(url, username, password);
            // with the method getConnection() from DriverManager, we're trying to set
            // the connection's url, username, password to the variables we made earlier and
            // trying to get a connection at the same time. JDBC allows us to do this.
        } catch (SQLException e) { // catching errors)
            e.printStackTrace(); // prints out SQLException errors to the console (if any)
        }

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length == 1) {

                String sql = "SELECT * FROM tblAuthenticationTokens WHERE authid='" + args[0] + "' AND playerid='" + player.getName()
                        + "' AND activated='0'";

                try {

                    PreparedStatement stmt = connection.prepareStatement(sql);
                    ResultSet results = stmt.executeQuery();

                    if (!results.next()) {
                        System.out.println("Failed");

                        player.sendMessage(ChatColor.RED + "Error! " + ChatColor.YELLOW
                                + "That authentication token does not exist.");
                        return false;

                    } else {

                        // create the java mysql update preparedstatement
                        String query = "update tblAuthenticationTokens set activated = ? where authid = ? AND playerid= ?";
                        PreparedStatement preparedStmt = connection.prepareStatement(query);
                        preparedStmt.setString(1, "1");
                        preparedStmt.setString(2, args[0]);
                        preparedStmt.setString(3, player.getName());

                        // execute the java preparedstatement
                        preparedStmt.executeUpdate();

                        player.sendMessage(ChatColor.GREEN + "Success! " + ChatColor.YELLOW
                        + "You can now log into the Kodoresu mobile app.");

                        return true;

                    }

                } catch (SQLException e) {

                    // TODO Auto-generated catch block
                    e.printStackTrace();

                }

                return true;

            } else {

                player.sendMessage(
                        ChatColor.RED + "Error! " + ChatColor.YELLOW + "Please type /kodoresu <id from app>.");
                return false;

            }

        } else
            return false;

    }

}