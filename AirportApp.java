//
//      Name:           Beauchamp, Joshua
//      Project:        5
//      Due:            12/08/23
//      Course:         cs-2400-02-f23
//
//      Description:
//                  The project is a program that reads in airports' respective IATA codes and their information, such as name and location, 
//                  into an hashed dictionary where the IATAs are the key and the information is the values. There is a directed graph that
//                  stores the airports IATA codes as vertices and the distance between them all as the edges. The main menu prompts the user
//                  for one of four command, which are retrieving airport information using IATA codes, displaying all the commands and their
//                  actions, finding the cheapest path between two airports, and then exiting the program entirely. The cheapest path is the
//                  main focus as it utilizes the most recent ADT, the graph, to accomplish tasks. Its methods incorporate past ADTs, such as
//                  the stack, a priority queue that uses minheap, iterators, and a hashed dictionary.
//

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class AirportApp {
    public static void main(String[] args) throws Exception {
        Scanner scnr = new Scanner(System.in);

        BufferedReader airportsFile = new BufferedReader(new FileReader("airports.csv"));
        BufferedReader distancesFile = new BufferedReader(new FileReader("distances.csv"));

        DictionaryInterface<String, String> airportDictionary = new HashedDictionary<>();
        GraphInterface<String> digraph = new Digraph<>();

        String info;
        String[] airports;
        String[] distancesInfo;
        String fileLine;
        

        //Reads airports file into the airport dictionary
        try{
            fileLine = null;
                while((fileLine = airportsFile.readLine()) != null){
                    airports = fileLine.split(",");
                    airportDictionary.add(airports[0], airports[1]);
                    digraph.addVertex(airports[0]);
                }
            airportsFile.close();
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        
        //Read the vertices and edges into directed graph
        try{
            fileLine = null;

            while((fileLine = distancesFile.readLine()) != null){
                distancesInfo = fileLine.split(",");
                Vertex<String> vertex1 = new Vertex<String>(distancesInfo[1]);
                Vertex<String> vertex2 = new Vertex<String>(distancesInfo[0]);
                vertex1.setPredecessor(vertex2);
                vertex1.setCost(Double.parseDouble(distancesInfo[2]));
                vertex1.connect(vertex2, vertex1.getCost());
                digraph.addEdge(distancesInfo[0], distancesInfo[1], Double.parseDouble(distancesInfo[2]));
            }
            distancesFile.close();
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }

        System.out.println("Airports v0.1 by J. Beauchamp");

        String input;
        do{
            System.out.print("Command? ");
            input = scnr.next();
            input = input.toUpperCase();
            switch (input){
                case "H": 
                    System.out.println("Q Query the airport information by entering the airport code.");
                    System.out.println("D Find the minimum distance between two airports.");
                    System.out.println("H Display this message.");
                    System.out.println("E Exit");
                break;

                case "Q" :
                    System.out.print("Airport code? ");
                    String userInput = scnr.next().toUpperCase();
                    info = airportDictionary.getValue(userInput);
                    if(info != null)
                        System.out.println(info);
                    else
                        System.out.println("Airport code unknown.");

                break;

                case "D":
                    try{
                        System.out.print("Airport codes from to ? ");
                        String start = scnr.next().toUpperCase();
                        String end = scnr.next().toUpperCase();
                        StackInterface<String> cheapestPath = new LinkedStack<>();
                        double connect = digraph.getCheapestPath(start, end, cheapestPath);
                        if(connect == 0.0)
                            System.out.println("Airports not connected");
                        else{
                            System.out.println("The minimum distance between " + airportDictionary.getValue(start) + " and " + airportDictionary.getValue(end) + " is " + connect + " through the path: ");
                            while(!cheapestPath.isEmpty()){
                                String code = cheapestPath.pop();
                                System.out.println(code + " - " + airportDictionary.getValue(code));
                            }
                        }
                    }
                    catch(NullPointerException e){
                        System.out.println("Airport code unknown");
                    }
                break;

                case "E":
                    System.exit(0);

                default:
                    System.out.println("Invalid command.");


            }
        } while(!input.equals("E"));
        scnr.close();
    }
}
