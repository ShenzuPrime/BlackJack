import java.util.*;
import java.sql.*;
class Player{
  //data fields
  String name;
  String username;
  String password;
  int[] hand = new int[11];
  int balance;
  //constructor
  Player(){
  }
  //methdos
  void setName(String a){
    name = a;
  }
  void setUsername(String a){
    username = a;
  }
  void setPassword(String a){
    password = a;
  }
  void setBalance(int a){
    balance = a;
  }
  void bet(int a){
    balance = balance - a;
  }
  void winning(int a){
    balance = balance+(a*2);
  }
  int handTotal(){
    int total=0;
    for(int a=0; a<hand.length; a++){
      total = total + hand[a];
    }
    return total;
  }
  void addToHand(int a){
    boolean methodCheck = true;
    int b = 0;
    while(methodCheck == true){
      if( hand[b] == 0){
        hand[b] = a;
        break;
      }
      b++;
    }
  }
  void clearHand(){
    int[] newHand = new int[11];
    hand = newHand;
  }
  void login(){
    try{
      Class.forName("com.mysql.cj.jdbc.Driver");
      String query1 = "Select balance From testing.player where username = '"+username+"' and password = '"+password+"';";
      String query2 = "Select name From testing.player where username = '"+username+"' and password = '"+password+"';";
      Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testing","tester","test123");
      Statement stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery(query1);
      if(rs.next()){
        balance = rs.getInt(1);
      }
      rs = stmt.executeQuery(query2);
      if(rs.next()){
        name = rs.getString(1);
      }
      con.close();
      System.out.println("Welcome "+name+". Your current balance is: "+balance);
    }catch(Exception e){
      System.out.println(e);
    }
  }
  void logout(){
    try{
      Class.forName("com.mysql.cj.jdbc.Driver");
      String query = "update testing.player set balance = "+balance+" where username = '"+username+"';";
      Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testing","tester","test123");
      Statement stmt = con.createStatement();
      stmt.executeUpdate(query);
      con.close();
      System.out.println("Balance has been updated to: "+balance);
    }catch(Exception e){
      System.out.println(e);
    }

  }
}
public class Blackjack{
  static int draw(char[] a,Player person){
    Scanner keyboard1 = new Scanner(System.in);
    int b = (int)((Math.random()*13)+1);
    System.out.println("A"+a[b]+" was just drawn");
    if(Character.isDigit(a[b])) return Character.getNumericValue(a[b]);
    if(Character.isLetter(a[b])){
      switch(a[b]){
        case 'A':
          if(person.handTotal()>10) return 1;
          if(person.handTotal()<11) return 11;
        default:
          return 10;
      }
    }
    else return 0;
  }
  public static void main(String[] args){
    char[] deck = new char[]{'A','2','3','4','5','6','7','8','9','T','J','Q','K'};
    int gamble = 0;
    //creating player and scanner for keyboard
    Player player = new Player();
    Player dealer = new Player();
    Scanner keyboard = new Scanner(System.in);

    //Prompting user to give information to log in
    System.out.println("Please input your username:");
    player.setUsername(keyboard.nextLine());
    System.out.println("Please input your password:");
    player.setPassword(keyboard.nextLine());

    player.login();

    if(player.balance>0){
      System.out.println("Time to begin the game");
    }
    boolean playing = true;
    String check;
    String hitCheck;
    //game logic
    while(player.balance > 0 && playing == true){
      System.out.println("Would you like to begin (Enter Z if you would like to Stop):");
      check = keyboard.nextLine();
      if(check.equals("z") || check.equals("Z")){
        playing = false;
      }

      System.out.println("Current balance is "+player.balance+" how much do you want to bet?:");
      while(gamble == 0){
        gamble = keyboard.nextInt();
      }
      player.bet(gamble);

      System.out.println("The dealer is about to draw");
      dealer.addToHand(draw(deck,dealer));
      dealer.addToHand(draw(deck,dealer));
      System.out.println("The Dealer's hand totals to "+dealer.handTotal());

      player.addToHand(draw(deck,player));
      player.addToHand(draw(deck,player));
      System.out.println("Your hand totals to "+player.handTotal());

      //players turn
      System.out.println("Would you like to hit? Y for yes, N for no");
      hitCheck = "y";
      while(hitCheck.equals("Y") || hitCheck.equals("y")){
        if(player.handTotal() > 21) break;
        hitCheck = keyboard.nextLine();
        player.addToHand(draw(deck,player));
        System.out.println("Your hand totals to "+player.handTotal());
      }

      //dealer tries to win
      while(dealer.handTotal() < player.handTotal() && dealer.handTotal() < 21){
        if(player.handTotal() > 21) break;
        dealer.addToHand((draw(deck,player)));
        System.out.println("The dealer's hand totals to "+dealer.handTotal());
      }

      //results
      if(player.handTotal()>21 || (dealer.handTotal() > player.handTotal() && dealer.handTotal()<22)){
        System.out.println("You have lost this hand. Sorry!");
        System.out.println("Current balance after loss: "+player.balance);
      }
      if(dealer.handTotal()>21 || (player.handTotal() > dealer.handTotal() && player.handTotal()<22)){
        System.out.println("You have won this hand. Congrats!");
        System.out.println("You have won "+gamble*2+" chips.");
        player.winning(gamble);
        System.out.println("Current balance after winning: "+player.balance);
      }
      if(dealer.handTotal() == player.handTotal()){
        System.out.println("It is a draw.");
        System.out.println("You have not won nor lost money.");
        player.winning((int)(gamble/2));
      }
    }
    //game end
    if(player.balance < 0) System.out.println("You have run out of balance, please add more");
    System.out.println("Thank you for playing, your balance will be saved, please play again.");
    player.logout();
  }
}
