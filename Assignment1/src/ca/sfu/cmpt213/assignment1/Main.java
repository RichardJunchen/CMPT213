package ca.sfu.cmpt213.assignment1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    /**
     * @ uses Print the menu for choosing options
     */
    public static void printMenu() {

        System.out.println(" ******************** ");
        System.out.println(" ---> Main Menu <--- ");
        System.out.println(" ******************** ");
        System.out.println(" 1. List all Tokimons \n" + " 2. Add a new Tokimon \n" + " 3. Remove a Tokimon \n"
                + " 4. Change  Tokimon strength \n" + " 5. DEBUG : Dump objects (toString) \n" + " 6. Exit ");
    }

    /**
     * @ uses Asking the options
     * @ return the options for main menu-> integer from  1 to 6
     * @ notice Please enter a integer
     */
    public static int displayMainMenu() {
        printMenu();
        Scanner in = new Scanner(System.in);
        System.out.println(" Please enter the number from 1 ~ 6. ");
        int choice = in.nextInt();
        while (choice < 1 || choice > 6) {                                 // check if the range 1to 6
            System.out.println(choice + " is a WRONG input !!! " +
                    "Please enter the number from 1 ~ 6. ");               // ask it again
            printMenu();
            choice = in.nextInt();
        }
        return choice;
    }

    /**
     * @ uses option one-> showing all details of Tokimons
     * @ par List of Tokimons
     */
    public static void displayAllTokis(List<Tokimons> temp) {
        int size = temp.size();
        System.out.println(" In the database, Tokimons are : ");
        if (temp.isEmpty()) {
            System.out.println(" --> There are nothing at all ! <--");
        }else{
            for (int i = 0; i < size; i++) {                                 // using loop to show each one
                System.out.println(i + 1 + "." + " Name : " + temp.get(i).getName() +
                        "   Ability : " + temp.get(i).getType() +
                        "   Height : " + temp.get(i).getHeight() + " m " +
                        "   Weight : " + temp.get(i).getWeight() + " m " +
                        "   The number of strength : " + temp.get(i).getStrength());
            }
        }
    }

    /**
     * @ uses option two-> add new Tokimons
     * @ par List if Tokimons
     * @ enter For the height and weight only accept positive number
     */
    public static void addNewToki(List<Tokimons> temp) {
        Tokimons temp_toki = new Tokimons();                     // create a local temp Toki for adding
        Scanner in = new Scanner(System.in);
        System.out.println(" Enter the new Tokimon 's name : ");
        temp_toki.setName(in.nextLine());
        System.out.println(" Enter the new Tokimon 's ability : ");
        temp_toki.setType(in.nextLine());
        System.out.println(" Enter the new Tokimon 's height : ");
        double temp_height = in.nextDouble();
        while (temp_height < 0) {                                //check if it is a positive (0 is acceptable)
            System.out.println(" Sorry we only accept the positive number.");
            System.out.println(" Please enter it again ");
            temp_height = in.nextDouble();
        }
        temp_toki.setHeight(temp_height);
        System.out.println(" Enter the new Tokimon 's weight : ");
        double temp_weight = in.nextDouble();
        while (temp_weight < 0) {
            System.out.println(" Sorry we only accept the positive number.");
            System.out.println(" Please enter it again ");
            temp_weight = in.nextDouble();
        }
        temp_toki.setWeight(temp_weight);
        temp_toki.setStrength(0);                              //  set strength default to 0

        temp.add(temp_toki);                                   // add it to Tokimons of List
    }

    /**
     * @ par  List of Tokimons
     * @ uses option three -> delete a Tokimon from List
     */
    public static void deleteToki(List<Tokimons> temp) {
        System.out.println(" You're in the deleting  system  which one you want to delete ? ");
        if (temp.isEmpty()) {
            System.out.println(" It is EMPTY, please add a new Tokimon first");
            togo(temp);
        } else {
            System.out.println(" ------> All Tokimons  <------");
            displayAllTokis(temp);
            System.out.println("Please choose from 1 " + " to " + temp.size() + ".  Also enter 0 to cancel action !!! ");
            Scanner in = new Scanner(System.in);
            int remove_index = in.nextInt();

            while (remove_index != 0 && (remove_index < 1 || remove_index > temp.size())) {   // check conditions
                System.out.println(" WRONG INPUT! Please choose from 1 " + " to " + temp.size());
                remove_index = in.nextInt();
            }
            if (remove_index != 0) {
                temp.remove(remove_index - 1);
                System.out.println(" Removed " + remove_index + "th " + " element ");
            }
        }
    }

    /**
     * @ par List of Tokimons
     * @ uses option four -> change the strength of Tokimon
     * @ notice when you choose the Tokimon please enter from (1 ~ n) enter 0 is cancel
     * @ notice enter a integer for strength
     */
    public static void alterToki(List<Tokimons> temp) {
        System.out.println(" You're in the editing system  which one you want to change ? ");
        if (temp.isEmpty()) {
            System.out.println(" It is EMPTY, please add a new Tokimon first");
            togo(temp);
        } else {
            System.out.println(" ------> All Tokimons  <------");
            displayAllTokis(temp);
            System.out.println("Please choose from 1 " + " to " + temp.size() + ".  Also enter 0 to cancel action !!! ");
            Scanner in = new Scanner(System.in);
            int change_index = in.nextInt();

            while (change_index != 0 && (change_index < 1 || change_index > temp.size())) {    // check the conditions
                System.out.println(" WRONG INPUT! Please choose from 1 " + " to " + temp.size());
                change_index = in.nextInt();
            }
            if (change_index != 0) {                                              //  if enter 0 then cancel
                System.out.println(" You want to change " + temp.get(change_index - 1).getName() + "'s strength ");
                System.out.println(" By how much ? Please enter below ! ");
                Scanner sc = new Scanner(System.in);
                String number = sc.nextLine();
                while (number.contains("."))                                       // if enter a double then ask again
                {
                    System.out.println(" Please enter a integer ! ");
                    number = sc.nextLine();
                }
                int temp_strength = Integer.parseInt(number);                             // change to integer
                while (temp.get(change_index - 1).getStrength() + temp_strength < 0) {    // check if it is not negative
                    System.out.println(" The number " + "[ " + temp_strength + " ] " + "that you enter is invalid. ");
                    System.out.println(" You only can enter a negative number not less than " +
                            "-" + temp.get(change_index - 1).getStrength());
                    number = sc.nextLine();
                    while (number.contains("."))                                        // check the double again
                    {
                        System.out.println(" Please enter a integer ! ");
                        number = sc.nextLine();
                    }
                    temp_strength = Integer.parseInt(number);
                }
                int final_ans = temp_strength + temp.get(change_index - 1).getStrength();
                temp.get(change_index - 1).setStrength(final_ans);
                System.out.println(temp.get(change_index - 1).getName() + " now has strength " + final_ans);
            }
        }
    }

    /**
     * @ par List of Tokimons
     * @ uses option five -> use toString to show details
     */
    public static void dumpToki(List<Tokimons> temp){
        if (temp.isEmpty()){
            System.out.println(" [ There is empty ! ] ");
        }
        else {
            for (int i = 0; i < temp.size(); i ++){               // using loop to call toString()
                System.out.println( (i+1) + ".  " + temp.get(i));
            }
        }
    }
    /**
     * @ uses exit the program
     */
    public static void exit(){
        System.out.println(" Thank you for using our APP ! ");
        System.out.println(" Goodbye!! Have a wonderful day ~ ");
    }

    /**
     * @ par List of Tokimons
     * @ uses check the user enter and decided to choose the different options
     * @ notice when finish an option, go back to main menu
     */
    public static void togo(List<Tokimons> temp ){
        int choice = displayMainMenu();

        if (choice == 1){
            displayAllTokis(temp);
            togo(temp);
        }
        if (choice == 2){
            addNewToki(temp);
            togo(temp);
        }
        if (choice == 3){
            deleteToki(temp);
            togo(temp);
        }
        if (choice == 4){
            alterToki(temp);
            togo(temp);
        }
        if (choice == 5){
            dumpToki(temp);
            togo(temp);
        }
        if (choice == 6){
            exit();
        }

    }
    /**
     * @ uses create a new List for storing data and welcome slogan
     */
    public static void main(String[] args) {
        List<Tokimons> database = new ArrayList<>();                 // create the new ArrayList
        System.out.println( " " );
        System.out.println(" ********Tokimon Tracker by Junchen Li   ID : 301385486 ********");
        System.out.println(" Hello!! Welcome to the Searching Tokimons System ! ");
        System.out.println(" What can I do for you today ? ");
        togo(database);                                             // go to decided menu
    }
}