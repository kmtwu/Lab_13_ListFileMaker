import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Scanner;
import java.nio.*;
import java.io.*;
import javax.swing.JFileChooser;
import static java.nio.file.StandardOpenOption.CREATE;

public class ListEditor {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ArrayList<String> myArrList = new ArrayList<>();
        ArrayList<String> newList = new ArrayList<>();
        JFileChooser chooser = new JFileChooser();
        String action;
        boolean YorN = false;
        String addValue;
        String insertValue;
        int insertPlace;
        int delPlace;
        String menuResponse;
        int moveInitial;
        int moveResult;
        boolean needsToBeSaved = false;
        String recordedLine;
        File chosenFile;
        String ListName;
        boolean clearOrNot = false;
        boolean saveB4Leave = false;

        System.out.println("Hello! Welcome to myListMaker! You can make changes to your list now!");
        ListName = SafeInput.getNonZeroLenString(in, "What is the list's name?") + ".txt";
        do {
            printList(myArrList);
            System.out.println();
            menuResponse = getMenuResponse(in);
            if (menuResponse.equals("A") || menuResponse.equals("a")) {
                System.out.println("Enter the string you would like to add.");
                addValue = in.next();
                in.nextLine();
                addItem(myArrList, addValue);
                needsToBeSaved = true;
            }
            else if (menuResponse.equals("D") || menuResponse.equals("d")) {
                for (int i = 0; i < myArrList.size(); i++) {
                    System.out.print("(" + (i + 1) + ". " + myArrList.get(i) + ") ");
                }
                delPlace = SafeInput.getRangedInt(in, "Enter the place of the item you would like to remove.", 1, myArrList.size());
                myArrList = delItem(myArrList, (delPlace - 1));
                needsToBeSaved = true;
            }
            else if (menuResponse.equals("I") || menuResponse.equals("i")) {
                insertPlace = SafeInput.getRangedInt(in, "Enter the place of where you want to add the new item", 1, myArrList.size());
                System.out.println("Enter what value you want to add.");
                insertValue = in.next();
                in.nextLine();
                insertItem(myArrList, insertValue, insertPlace);
                needsToBeSaved = true;
            }
            else if (menuResponse.equals("V") || menuResponse.equals("v")) {
                printList(myArrList);
            }
            else if (menuResponse.equals("Q") || menuResponse.equals("q")) {
                if (needsToBeSaved) {
                    saveB4Leave = SafeInput.getYNConfirm(in, "Would you like to save before leaving?");
                    if (saveB4Leave) {
                        File directoryFile = new File(System.getProperty("user.dir"));
                        Path file = Paths.get(directoryFile.getPath() + ("\\src\\" + ListName + ".txt"));

                        try {
                            OutputStream out = new BufferedOutputStream(Files.newOutputStream(file, CREATE));
                            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));

                            for (String arrListPart : myArrList) {
                                writer.write(arrListPart, 0, arrListPart.length());
                                writer.newLine();
                            }
                            writer.close();
                            System.out.println("List saved!");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        needsToBeSaved = false;
                    }
                    else {
                        System.out.println("Progress deleted.");
                        needsToBeSaved = false;
                    }
                }
                if (SafeInput.getYNConfirm(in, "Are you sure you'd like to quit?")) {
                    YorN = true;
                    System.out.println("Bye!");
                } else {
                    System.out.println("Oops!");
                }
            }
            else if (menuResponse.equals("M") || menuResponse.equals("m")) {
                moveInitial = SafeInput.getRangedInt(in, "Enter the place of the item you want to move.", 1, myArrList.size());
                moveResult = SafeInput.getRangedInt(in, "Enter where you would like to insert the moved value.", 1, myArrList.size());
                moveItem(myArrList, moveInitial, moveResult);
                needsToBeSaved = true;
            }
            else if (menuResponse.equals("S") || menuResponse.equals("s")) {

                File directoryFile = new File(System.getProperty("user.dir"));
                Path file = Paths.get(directoryFile.getPath() + ("\\src\\" + ListName));

                try {
                    OutputStream out = new BufferedOutputStream(Files.newOutputStream(file, CREATE));
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));

                    for (String arrListPart : myArrList) {
                        writer.write(arrListPart, 0, arrListPart.length());
                        writer.newLine();
                    }
                    writer.close();
                    System.out.println("List saved!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                needsToBeSaved = false;
            }
            else if (menuResponse.equals("O") || menuResponse.equals("o")) {
                if (needsToBeSaved) {
                    saveB4Leave = SafeInput.getYNConfirm(in, "Would you like to save before leaving?");
                    if (saveB4Leave) {
                        File directoryFile = new File(System.getProperty("user.dir"));
                        Path file = Paths.get(directoryFile.getPath() + ("\\src\\" + ListName));

                        try {
                            OutputStream out = new BufferedOutputStream(Files.newOutputStream(file, CREATE));
                            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));

                            for (String arrListPart : myArrList) {
                                writer.write(arrListPart, 0, arrListPart.length());
                                writer.newLine();
                            }
                            writer.close();
                            System.out.println("List saved!");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        needsToBeSaved = false;
                    }
                    else {
                        System.out.println("Progress deleted.");
                        needsToBeSaved = false;
                    }
                }
                try {

                    File directoryFile2 = new File(System.getProperty("user.dir"));
                    chooser.setCurrentDirectory(directoryFile2);

                    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                        chosenFile = chooser.getSelectedFile();
                        Path file = chosenFile.toPath();
                        ListName = chosenFile.getName();
                        InputStream in2 = new BufferedInputStream(Files.newInputStream(file, StandardOpenOption.CREATE));
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in2));
                        myArrList = clearList();
                        while (reader.ready()) {
                            recordedLine = reader.readLine();
                            myArrList.add(recordedLine);
                        }
                        reader.close();
                    }
                } catch (FileNotFoundException e) {
                    System.out.println("File not found!!!");
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if (menuResponse.equals("C") || menuResponse.equals("c")) {
                clearOrNot = SafeInput.getYNConfirm(in, "Are you sure to clear this list?");
                if (clearOrNot) {
                    myArrList = clearList();
                    File directoryFile = new File(System.getProperty("user.dir"));
                    Path file = Paths.get(directoryFile.getPath() + ("\\src\\" + ListName));

                    try {
                        OutputStream out = new BufferedOutputStream(Files.newOutputStream(file, CREATE));
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));

                        for (String arrListPart : myArrList) {
                            writer.write("", 0, arrListPart.length());
                            writer.newLine();
                        }
                        writer.close();
                        System.out.println("List cleared!");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    needsToBeSaved = false;
                } else {
                  System.out.println("Never mind.");
                }
            }
        } while (!YorN);
    }

    private static String getMenuResponse(Scanner pipe){
        System.out.println("Enter A/a add an item, D/d to delete an item, I/i to insert an item, V/v to view the list, enter Q/q to quit, M/m to move an item, O/o to open a list file from disk, S/s to save the current list to disk, and C/c to clear all the elements from the current list.");
        String input;
        boolean cont = true;
        do {
            input = pipe.next();
            if(input.equals("A") || input.equals("a") || input.equals("D") || input.equals("d") || input.equals("I") || input.equals("i") || input.equals("V") || input.equals("v") || input.equals("Q") || input.equals("q") || input.equals("M") || input.equals("m") || input.equals("O") || input.equals("o") || input.equals("S") || input.equals("s") || input.equals("C") || input.equals("c")) {
                cont = false;
            }
            else {
                System.out.println("Invalid input!");
                System.out.println("Enter A/a add an item, D/d to delete an item, I/i to insert an item, V/v to view the list, enter Q/q to quit, M/m to move an item, O/o to open a list file from disk, S to save the current list to disk, and C/c to clear all the elements from the current list.");
                pipe.nextLine();
            }
        } while(cont);
        return input;
    }

    private static ArrayList<String> addItem (ArrayList<String> myList, String appendItem) {
        myList.add(appendItem);
        return myList;
    }

    private static ArrayList<String> delItem (ArrayList<String> myList, int delPlace) {
        ArrayList<String> newList = new ArrayList<>();
        for (int i = 0; i < myList.size(); i++) {
            if (!(i == delPlace)){
                newList.add(myList.get(i));
            }
        }
        return newList;
    }

    private static ArrayList<String> insertItem (ArrayList<String> myList, String newItem, int newPlace) {
        myList.add(newPlace - 1, newItem);
        return myList;
    }

    private static void printList (ArrayList<String> myList) {
        for (int i = 0; i < myList.size(); i++) {
            System.out.print(myList.get(i) + " ");
        }
        System.out.println();
    }

    private static ArrayList<String> moveItem (ArrayList<String> myList, int origin, int end) {
        String addItem = myList.get(origin);
        ArrayList<String> newList = new ArrayList<>();
        for (int i = 0; i < myList.size(); i++) {
            if (!(i == origin)){
                newList.add(myList.get(i));
            }
        }
        newList.add(end - 1, addItem);
        return newList;
    }

    private static ArrayList<String> clearList () {
        ArrayList<String> newList = new ArrayList<>();
        return newList;
    }
}

