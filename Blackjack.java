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
    //Login Process
    player.login();
    //
  }
}
