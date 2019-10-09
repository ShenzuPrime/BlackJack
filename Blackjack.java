import java.util.*;
import java.sql.*;
class Player{
  //data fields
  String name;
  String username;
  String password;
  int[] hand = new int[11];
  int balance;
  //constructor for creating the player
  Player(){
  }
  void setName(String a){
    this.name = a;
  }
  void setUsername(String a){
    this.username = a;
  }
  void setPassword(String a){
    this.password = a;
  }
  void setBalance(int a){
    this.balance = a;
  }
  void clearHand(){
    int[] newHand = new int[11];
    this.hand = newHand;
  }
  void login(){
    try{
      Class.forName("com.mysql.jdbc.Driver");
      String query1 = "Select balance From testing.player where username = "+username+" and password = "+password;
      String query2 = "Select name From testing.player where username = "+username+" and password = "+password;
      Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testing","tester","test123");
      Statement stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery(query1);
      balance = rs.getInt(1);
      rs = stmt.executeQuery(query2);
      name = rs.getString(1);
      con.close();
      System.out.println("Welcome "+name+". Your current balance is: "+ balance);
    }catch(Exception e){
      System.out.println(e);
    }
  }
  void logout(){
    try{
      Class.forName("com.mysql.jdbc.Driver");
      String query = "";
      Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testing","tester","test123");
      Statement stmt = con.createStatement();
      stmt.executeQuery(query);
      con.close();
      System.out.println("Balance has been updated to: "+balance);
    }catch(Exception e){
      System.out.println(e);
    }

  }
}
public class Blackjack{
  String[] deck = new String[]{"A","2","3","4","5","6","7","8","9","10","J","Q","K"};
/*
  int draw(String[] a){
    int b = (int)((Math.random()*13)+1);
    System.out.println("You have drawn a "+a[b]);

  } */
  public static void main(String[] args){
    //creating player and scanner for keyboard
    Player player = new Player();
    Scanner keyboard = new Scanner(System.in);

    //Prompting user to give information to log in
    System.out.println("Please input your username:");
    player.setUsername(keyboard.nextLine());
    System.out.println("Please input your password:");
    player.setPassword(keyboard.nextLine());

    player.login();
    if(player.balance>0) System.out.prinln("Time to begin the game");

    boolean playing = true;

    //game logic
    while(player.balance > 0 && playing == true){
      System.out.println("Would you like to begin (Enter Z if you would like to Stop):");
      if(keyboard.nextLine() == 'z' || keyboard.nextLine() == 'Z') break;
    }
    if(player.balance > 0){
      System.out.prinln("Thank you for playing, your balance will be saved, please play again.");
      player.logout();
    }
  }
}
