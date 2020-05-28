import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException; // Import the IOException class to handle errors
import java.io.FileWriter; // Import the FileWriter class
import java.time.LocalDate; // Import the LocalDateTime class
import java.time.format.DateTimeFormatter; // Import the DateTimeFormatter class

public class ProjectPlanner{
	
	public static void main(String[]args) {
  	static int projNumber = 0; // this static variable will keep track of project number
  	static Scanner userInput = new Scanner(System.in); // Scanner object to read user inputs throughout the program
  	
       ArrayList<Project> projectList = new ArrayList<Project>(); // create a List array of projects
       System.out.print("=====================================================================================\n");
       System.out.print("==================== Structural engineering firm \" Poised\" ========================\n");
       System.out.println("===================================================================================\n");
       while (true)
       {
           System.out.print("\n\n==============================Main menu======================================\n");
           System.out.print("\nPlease choose an option from the menu bellow\n");
           System.out.print("\n1 - Create new Project\n" + "2 - Update existing projects\n"
                   + "3 - Get projects from the database\n" + "4 - Finalize existing projects\n"
                   + "5 - View pending projects\n" + "6 - View overdue projects \n"
                   + "7 - Find project by number or name\n" + "8 - Exit program\n");
           String option = userInput.nextLine(); // read input entered by user
           switch (option)
           {
           case "1":
               projectList.add(new Project()); // Add project to list of projects and send it to createNew() function
               createNew(projectList);
               break;
           case "2":
               updateProject(projectList);
               break;
           case "3":
               System.out.print("=======================EXISTING PROJECTS==============================\n");
               getExistingProjects(projectList);
               break;
           case "4":
               finalizeMenu(projectList);
               break;
           case "5":
               pendingProject(projectList);
               break;
           case "6":
               overDueProject(projectList);
               break;
           case "7":
               // find project by number or name
               findProject(projectList);
               break;
           case "8":
               if (projectList.isEmpty())
               {
                   /* there's nothing to do */
               }
               else
               {
                   callSaveToFile(projectList);
               }
               System.out.print("Exiting program... bye! \n");
               System.exit(0);
           default:
               System.out.print("Invalid selection. Try again\n");
               break;
           }
       }
   }
   static LocalDate getDateFormat(String strDate)
   {
       LocalDate dateObj = null;
       DateTimeFormatter myFormatObj = null;
       myFormatObj = DateTimeFormatter.ofPattern("dd MMMM yyyy"); // format how date object will be
       dateObj = LocalDate.parse(strDate, myFormatObj); // convert string entered by user to LocalDate object
       return dateObj; // return object to calling function
   }
   static void insertPersonInfo(Project projObj, String whoIs)
   {
       String name = "";
       String email = "";
       String phone = "";
       String add = "";
       System.out.print("Enter name: ");
       name = userInput.nextLine();
       while (true)
       {
           try
           {
               System.out.print("Enter email: ");
               email = userInput.nextLine();
               String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."
                       + "[a-zA-Z0-9_+&*-]+)*@"
                       + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
                       + "A-Z]{2,7}$";
               if (email.matches(emailRegex))
               { // if email is valid
                   break;
               }
               else
               { // email is not valid
                   throw new Exception();
               }
           }
           catch (Exception e)
           {
               System.out.println("*Invalid email*");
               continue;
           }
       }
       while (true)
       {
           try
           {
               System.out.print("Enter Telephone: ");
               phone = userInput.nextLine();
               if (phone.matches("[0/+27]?[0-9]+") && (phone.length() >= 9 && phone.length() <= 12))
               {
                   break;
               }
               else
               { // invalid number
                   throw new Exception();
               }
           }
           catch (Exception e)
           {
               System.out.println("*Invalid phone number*");
               continue;
           }
       }
       System.out.print("Enter address:\n");
       add = userInput.nextLine();
       if (whoIs == "customer")
       {
           projObj.setCustomer(name, phone, email, add, whoIs);
       }
       else if (whoIs == "architect")
       {
           projObj.setArchitect(name, phone, email, add, whoIs);
       }
       else if (whoIs == "contractor")
       {
           projObj.setContractor(name, phone, email, add, whoIs);
       }
   }
   static void createNew(ArrayList<Project> projectList)
   {
       int currIndex;
       for (currIndex = 0; currIndex < projectList.size(); currIndex++)
       {
           // currIndex is the index
           // projectList.get(currIndex) is the project we want
       }
       currIndex = currIndex - 1; // fix out of bound index(when currSize is out of loop above it has 1 more than
                                   // currently available subscripts)
       System.out.print("\n=======Create New Project======= \n\n");
       // ===== ask for project information=========
       System.out.print("Enter project name: \n");
       String projName = userInput.nextLine(); // read user input
       projectList.get(currIndex).setName(projName); // save input to project
       System.out.println("The number of this project is " + (++projNumber));
       projectList.get(currIndex).setNumber(projNumber); // save project number to project and post increment
       System.out.print("Enter project address:\n");
       String projAdd = userInput.nextLine(); // read user input
       projectList.get(currIndex).setAddress(projAdd); // save input to project
       System.out.print("Enter project erf number:\n");
       String erf = userInput.nextLine(); // read user input
       projectList.get(currIndex).setErfNumber(erf); // save input to project
       System.out.print("Enter project type(eg:house, apartment etc...):\n");
       String buildType = userInput.nextLine();
       projectList.get(currIndex).setType(buildType);
       while (true)
       {
           try
           {
               System.out.print("What is the amount being charged for this project ?:\n");
               String projPrice = userInput.nextLine();
               projectList.get(currIndex).setFee(Double.parseDouble(projPrice));
               break;
           }
           catch (Exception e)
           {
               System.out.println("*make sure you enter a monetary value*");
               continue;
           }
       }
       enterProjectDueDate(projectList, currIndex);
       System.out.print("________________________________________________\n");
       System.out.print("Enter Customer\'s details\n\n");
       insertPersonInfo(projectList.get(currIndex), "customer"); // save customer information to project
       projName = projName.replaceAll(" ", ""); // remove any white space from the if statement bellow to ignore white spaces
       if (projName.isEmpty())
       {
           String str = projectList.get(currIndex).getCustomer().getName(); // get name of customer
           String[] arrOfStr = str.split(" ", 0); // split user name in order to get surname(last name)
           String newName = projectList.get(currIndex).getType() + " " + arrOfStr[arrOfStr.length - 1];
           projectList.get(currIndex).setName(newName);
       }
       System.out.print("________________________________________________\n");
       System.out.print("Enter Architect\'s details\n\n");
       insertPersonInfo(projectList.get(currIndex), "architect"); // save architect information to project
       System.out.print("________________________________________________\n");
       System.out.print("Enter Contractor\'s details\n\n");
       insertPersonInfo(projectList.get(currIndex), "contractor"); // save contractor information to project
       System.out.print("****Project details have been successufully captured!****\n");
   }
   public static void enterProjectDueDate(ArrayList<Project> projectList, int currIndex)
   {
       while (true)
       {
           try
           {
               System.out.print("Enter the project due date(format: day month year; eg: 09 November 2020):\n");
               String dateStr = userInput.nextLine(); // read user input
               projectList.get(currIndex).setDeadline(getDateFormat(dateStr));
               break; // if it gets here all is well. Come out of the loop
           }
           catch (Exception e)
           {
               System.out.print(
                       "*Make sure date format is correct \nFormat: day month year \nMake use of two digits for the day eg: 01\n"
                               + "and first letter of month must be captalized eg: January*\n\n");
               continue;
           }
       }
   }
   static void updateProject(ArrayList<Project> projectList)
   {
       System.out.print("=========================Update existing Project=================================\n\n");
       while (true)
       {
           System.out.print("Note that there are currently " + projNumber + " project(s) registered.\n"
                   + "Project numbers start from 1\n");
           if (projNumber == 0)
           { // there are 0 projects do not allow user to continue
               return;
           }
           System.out.print("\nEnter the project number you want to update: ");
           int index = 0;
           try
           {
               String i = userInput.nextLine();
               index = Integer.parseInt(i); // convert user input to integer
           }
           catch (Exception e)
           {
               System.out.print("**wrong input format!**");
               return;
           }
           index = index - 1; // subscript start at 0
           if (index >= 0 && index < projNumber && projectList.isEmpty() == false && projectList.get(index) != null)
           {
               System.out.print("\nChoose an option from the menu bellow\n");
               System.out.print("\n1 - Change project due date\n" + "2 - Change total amount of the fee paid to date \n"
                               + "3 - Update Contractor\'s contact details\n" + "4 - Go back to main menu\n");
               String opt = userInput.nextLine();
               switch (opt)
               {
               case "1":
                   System.out.print("\n=========== Change project due date===========\n\n");
                   enterProjectDueDate(projectList, index);
                   System.out.print("***Due date updated successfully!***\n\n");
                   return; // go back to main menu
               case "2":
                   System.out.print("\n==========Change total amount of the fee paid to date ===========\n\n");
                   while (true)
                   {
                       try
                       {
                           System.out.print("Enter total amount of the fee paid to date for project " + (index + 1) + " :");
                           String amountStr = userInput.nextLine();
                           double amountPaid = Double.parseDouble(amountStr);
                           projectList.get(index).setAmountPaid(amountPaid); // save amount paid to date to project
                           System.out.print("***total amount of the fee paid to date updated successfully!***\n\n");
                           break;
                       }
                       catch (Exception e)
                       {
                           System.out.println("*make sure you enter a monetary value*");
                           continue;
                       }
                   }
                   return; // go back to main menu;
               case "3":
                   System.out.print("\n=============Update Contractor\'s contact details======================\n\n");
                   System.out.print("Contractor\'s details for project " + (index + 1) + ":\n");
                   insertPersonInfo(projectList.get(index), "contractor");
                   System.out.print("***Contractor\'s details updated successfully!***\n\n");
                   return;
               case "4":
                   return; // go back to main menu
               default:
                   System.out.print("Invalid menu Selection\n\n");
                   break;
               }
           }
           else
           {
               System.out.print("You have entered and an invalid project number!\n");
               return;
           }
       }
   }
   static void pendingProject(ArrayList<Project> projectList)
   {
       System.out.print("====================Pending Projects=========================== \n\n");
       if (projectList.isEmpty())
       {
           System.out.print("No projects available\n");
           System.out.print("OR make sure you have retrieved projects from the database (Option 3)");
       }
       else
       {
           for (Project obj : projectList)
           {
               if (!obj.isFinalized())
               {
                   displayExistingProjects(obj);
               }
           }
       }
       goBacktoMenu();
   }
   static void overDueProject(ArrayList<Project> projectList)
   {
       System.out.print("====================Overdue projects ========================= \n\n");
       if (projectList.isEmpty())
       {
           System.out.print("No projects available\n");
           System.out.print("Or make sure you get projects from the database (Option 3)");
       }
       else
       {
           LocalDate dateNow = LocalDate.now(); // current date is the date the project was created format(yyy-mm-dd)
           for (Project obj : projectList)
           {
               if (!obj.isFinalized() && dateNow.isAfter(obj.getDeadline()))
               {
                   displayExistingProjects(obj);
               }
           }
       }
   }
   static void findProject(ArrayList<Project> projectList)
   {
       System.out.print("====================Find projects========================= \n\n");
       System.out.print("Enter project number or name to find it:\n");
       String input = userInput.nextLine();
       boolean isNumber = false;
       for (int i = 0; i < input.length(); i++)
       {
           if (!Character.isDigit(input.charAt(i)))
           {
               isNumber = false;
               break;
           }
           else
           {
               isNumber = true;
           }
       }
       boolean foundSomething = false; // This flag will notify user when something went wrong
       try
       {
           for (Project obj : projectList)
           {
               if ((isNumber && (obj.getNumber() == Integer.parseInt(input)))|| (!isNumber && obj.getName().equals(input)))
               {
                   displayExistingProjects(obj);
                   foundSomething = true;
               }
           }
       }
       catch (Exception e)
       {
           e.printStackTrace();
       }
       if (foundSomething)
       {
           // pass
       }
       else
       {
           System.out.print("Search result is empty!\n");
       }
       goBacktoMenu(); // go back to main menu
   }
   static void finalizeMenu(ArrayList<Project> projectList)
   {
       System.out.print("========================Finalize project==========================\n\n");
       System.out.print("Note that there are currently " + projNumber + " project(s) registered.\n"
               + "Project numbers start from 1\n");
       if (projNumber == 0)
       { // there are 0 projects do not allow user to continue
           return;
       }
       System.out.print("\nEnter the project number you want to finalize: ");
       int index = 0;
       try
       {
           String i = userInput.nextLine();
           index = Integer.parseInt(i); // convert user input to integer
       }
       catch (Exception e)
       {
           System.out.print("**wrong input format!**");
           return;
       }
       index = index - 1; // subscript start at 0
       if (index >= 0 && index < projNumber && projectList.isEmpty() == false && projectList.get(index) != null)
       {
           System.out.print("\n\n"); // newlines
           projectList.get(index).finalizeProject(projectList.get(index));
       }
       else
       {
           System.out.print("Make sure you enter a valid project number!\n");
       }
   }
   static void getExistingProjects(ArrayList<Project> projectList)
   {
       ArrayList<String> list = new ArrayList<String>(); // list will store projects from txt file
       File inputFile = null;
       try
       {
           inputFile = new File("existing_projects.txt"); // specify file
           Scanner fileReader = new Scanner(inputFile); // Scanner object
           int count = 1;
           while (fileReader.hasNext())
           {
               String line = fileReader.nextLine();
               String[] str = line.split(", ", 0); // line read from file split at comma(", ") and stored to str array
               for (String i : str)
                   list.add(i);
               if (count % 4 == 0)
               {
                   Project proj = new Project(); // create project object that will be stored in projectList
                   proj.setName(list.get(0));
                   boolean finalized = Boolean.parseBoolean(list.get(1));
                   proj.setIsFinalized(finalized);
                   proj.setType(list.get(2));
                   proj.setAddress(list.get(3));
                   proj.setErfNumber(list.get(4));
                   proj.setFee(Double.parseDouble(list.get(5)));
                   proj.setAmountPaid(Double.parseDouble(list.get(6)));
                   LocalDate dateObj = null;
                   DateTimeFormatter myFormatObj = null;
                   myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // format how date object will be
                   dateObj = LocalDate.parse(list.get(7), myFormatObj);
                   proj.setDate(dateObj); // project date assigned
                   dateObj = LocalDate.parse(list.get(8), myFormatObj);
                   proj.setDeadline(dateObj); // project deadline
                   proj.setCustomer(list.get(9), list.get(10), list.get(11), list.get(12), "customer");
                   proj.setArchitect(list.get(13), list.get(14), list.get(15), list.get(16), "architect");
                   proj.setContractor(list.get(17), list.get(18), list.get(19), list.get(20), "contractor");
                   projectList.add(proj); // add project to the list of projects
                   proj.setNumber(++projNumber); // increment number of projects
                   list.clear(); // list is cleared for next new project
               }
               count++;
           }
           for(Project obj: projectList)
           {
               displayExistingProjects(obj); // display projects to the user
           }
           fileReader.close();
       }
       catch (FileNotFoundException e)
       {
           System.out.print("File not found");
       }
       catch (Exception e)
       {
           System.out.println("Error in " + inputFile.getName());
           System.out.println(
                   "make sure the values in the file are comma separated plus a white space such as -> (\", \")");
       }
       goBacktoMenu();
   }
   static void goBacktoMenu()
   {
       System.out.print("\n*** Press enter to go back to main menu ***\n");
       try
       {
           System.in.read();
       }
       catch (Exception e)
       {
       }
   }
   public static void displayExistingProjects(Project proj)
   {
       String content = " "; // this variable will store all information pertaining to the project
       content = "**************Project number: " + proj.getNumber() + " ************************\n\n";
       content += "Is project finalized ?: " + proj.isFinalized() + "\n";
       content += "Project name: " + proj.getName() + "\n";
       content += "Type of building: " + proj.getType() + "\n";
       content += "The physical address for the project: " + proj.getAddress() + "\n";
       content += "ERF number: " + proj.getErfNumber() + "\n";
       content += "The total fee charged for the project: " + proj.getFee() + "\n";
       content += "The total amount paid to date: " + proj.getAmountPaid() + "\n";
       DateTimeFormatter myFormatObj;
       myFormatObj = DateTimeFormatter.ofPattern("dd MMMM yyyy");
       String formattedDate = proj.getDate().format(myFormatObj);
       content += "Project date assigned: " + formattedDate + "\n";
       formattedDate = proj.getDeadline().format(myFormatObj);
       content += "Project deadline: " + formattedDate + "\n";
       content += "\n";
       content += "Customer\'s details:\n";
       content += "Name: " + proj.getCustomer().getName() + "\n";
       content += "Email: " + proj.getCustomer().getEmail() + "\n";
       content += "Telephone: " + proj.getCustomer().getPhone() + "\n";
       content += "address: " + proj.getCustomer().getAddress() + "\n";
       content += "\n";
       content += "Architect\'s details:\n";
       content += "Name: " + proj.getArchitect().getName() + "\n";
       content += "Email: " + proj.getArchitect().getEmail() + "\n";
       content += "Telephone: " + proj.getArchitect().getPhone() + "\n";
       content += "address: " + proj.getArchitect().getAddress() + "\n";
       content += "\n";
       content += "Contractor\'s details:\n";
       content += "Name: " + proj.getContractor().getName() + "\n";
       content += "Email: " + proj.getContractor().getEmail() + "\n";
       content += "Telephone: " + proj.getContractor().getPhone() + "\n";
       content += "address: " + proj.getContractor().getAddress() + "\n\n";
       System.out.print(content); // display projects
   }
   public static void callSaveToFile(ArrayList<Project> projectList)
   {
       File inputFile = null;
       try
       {
           inputFile = new File("existing_projects.txt"); // specify file
           if (inputFile.createNewFile())
           {
               saveToFile(projectList);
           }
           else
           {
               saveToFile(projectList);
           }
       }
       catch (IOException e)
       {
           System.out.println("An error occurred.");
           e.printStackTrace();
       }
       System.out.print("Changes to projects saved to " + inputFile.getName());
       System.exit(0); // terminate program
   }
   static void saveToFile(ArrayList<Project> projectList) throws IOException
   {
       FileWriter fileWriter = new FileWriter("existing_projects.txt");
       String content = "";
       for (Project obj : projectList)
       {
           content = obj.getName() + ", ";
           content += obj.isFinalized() + ", ";
           content += obj.getType() + ", ";
           content += obj.getAddress() + ", ";
           content += obj.getErfNumber() + ", ";
           content += obj.getFee() + ", ";
           content += obj.getAmountPaid() + ", ";
           content += obj.getDate() + ", ";
           content += obj.getDeadline() + "\n";
           content += obj.getCustomer().getName() + ", ";
           content += obj.getCustomer().getEmail() + ", ";
           content += obj.getCustomer().getPhone() + ", ";
           content += obj.getCustomer().getAddress() + "\n";
           content += obj.getArchitect().getName() + ", ";
           content += obj.getArchitect().getEmail() + ", ";
           content += obj.getArchitect().getPhone() + ", ";
           content += obj.getArchitect().getAddress() + "\n";
           content += obj.getContractor().getName() + ", ";
           content += obj.getContractor().getEmail() + ", ";
           content += obj.getContractor().getPhone() + ", ";
           content += obj.getContractor().getAddress() + "\n";
           fileWriter.write(content);
       }
       fileWriter.close();
   }
}
===================================================================================
Project.java
import java.time.*;                            // import the time class
import java.time.format.DateTimeFormatter;       // import DateTimeFormatter
import java.io.File;                           // Import the File class
import java.io.FileWriter;                 // Import the FileWriter class
import java.io.IOException; // Import the IOException class to handle errors
public class Project
{
   public Project()
   {
       project_number = 0;
       project_name = "";
       bulding_type = "";
       address = "";
       erf_number = "";
       project_fee = 0.0;
       amount_paid = 0.0;
       project_date_assigned = LocalDate.now();
       project_deadline = null;
       isFinalized = false;
       customer = null;
       architect = null;
       contractor = null;
       System.out.println();
   }
   public Project(int pNum, String pName, String bType, String add, String erf, double pFee, double aPaid ,LocalDate pDealine)
   {
       setNumber(pNum);
       setName(pName);
       setType(bType);
       setAddress(add);
       setErfNumber(erf);
       setFee(pFee);
       setAmountPaid(aPaid);
       setDeadline(pDealine);
       isFinalized = false; // project when created is not finalized      
       project_date_assigned = LocalDate.now(); // current date is the date the project was created format(yyy-mm-dd)
   }
   public void finalizeProject(Project projObj)
   {
       String invoice = "";
       if(projObj.getAmountPaid() < projObj.getFee())
       {
           invoice = "Name: " + projObj.getCustomer().getName() + "\n";
           invoice += "Email: " + projObj.getCustomer().getEmail() + "\n";
           invoice += "Telephone: " + projObj.getCustomer().getPhone() + "\n";
           invoice += "address: " + projObj.getCustomer().getAddress() + "\n";
           invoice += "Due amount: " + (projObj.getFee() - projObj.getAmountPaid()) + "\n";
           System.out.println("_________________Customer\'s invoice_____________________\n");
           System.out.println(invoice);
           System.out.println("_________________________________________________________");
       }
       projObj.setIsFinalized(true); // mark project as finalized
       LocalDateTime finalDate = LocalDateTime.now();
       DateTimeFormatter myDateFormat = DateTimeFormatter.ofPattern("E, MMM dd yyyy HH:mm:ss");
       String formattedDate = finalDate.format(myDateFormat); // store date finalized in a string variable
       String content = " "; // this variable will store all information pertaining to the project
       content = "Project number: " + projObj.getNumber() + "\n";
       content += "Is project finalized ?: " + projObj.isFinalized()+ "\n";
       content += "Project name: " + projObj.getName() + "\n";
       content += "Type of building: " + projObj.getType() + "\n";
       content += "The physical address for the project: " + projObj.getAddress() + "\n";
       content += "ERF number: " + projObj.getErfNumber() + "\n";
       content += "The total fee charged for the project: " + projObj.getFee() + "\n";
       content += "The total amount paid to date: " + projObj.getAmountPaid() + "\n";
       content += "Project date assigned: " + projObj.getDate() + "\n";
       content += "Project deadline: " + projObj.getDeadline() + "\n";
       content += "Project date finalized: " + formattedDate + "\n";
       content += "\n\n";
       content += "Customer\'s details:\n";
       content += "Name: " + projObj.getCustomer().getName() + "\n";
       content += "Email: " + projObj.getCustomer().getEmail() + "\n";
       content += "Telephone: " + projObj.getCustomer().getPhone() + "\n";
       content += "address: " + projObj.getCustomer().getAddress() + "\n";
       content += "\n\n";
       content += "Architect\'s details:\n";
       content += "Name: " + projObj.getArchitect().getName() + "\n";
       content += "Email: " + projObj.getArchitect().getEmail() + "\n";
       content += "Telephone: " + projObj.getArchitect().getPhone() + "\n";
       content += "address: " + projObj.getArchitect().getAddress() + "\n";
       content += "\n\n";
       content += "Contractor\'s details:\n";
       content += "Name: " + projObj.getContractor().getName() + "\n";
       content += "Email: " + projObj.getContractor().getEmail() + "\n";
       content += "Telephone: " + projObj.getContractor().getPhone() + "\n";
       content += "address: " + projObj.getContractor().getAddress();
       try
       {
           File oFile = new File("Completed project" + projObj.getNumber() + ".txt"); // specify file
           if (oFile.createNewFile())
           { // if file was successfully created
                 FileWriter myWriter = new FileWriter(oFile); // fileWriter object
                 myWriter.write(content); // write information about the project to file
                 myWriter.close(); // close file after finish writing  
                 System.out.println("***Project information saved to: " + oFile.getName() + "***");      
              }
              else
              {
                  String newFile = "Completed project" + projObj.getNumber() + ".txt";
                  File oFile2 = new File(newFile); // specify file
                  FileWriter myWriter = new FileWriter(oFile2);
                  myWriter.write(content); // write information about the project to file
                  myWriter.close(); // close file after finish writing
                  // let user know of output
                  System.out.println("***Project information saved to: " + oFile2.getName() + "***");
              }
            }
           catch (IOException e)
           {
               System.out.println("**An error occurred**");
                e.printStackTrace();
            }  
   }
   public void setCustomer(String name, String phone, String email, String add, String r)
   {
       customer = new Persons(name, phone, email, add, r); // create customer object and assign data
   }
   public void setArchitect(String name, String phone, String email, String add, String r)
   {
       architect = new Persona(name, phone, email, add, r); // create architect object and assign data
   }
   public void setContractor(String name, String phone, String email, String add, String r)
   {  
       contractor = new Persons(name, phone, email, add, r); // create contractor object and assign data
   }
   public void setName(String n)
   {
       project_name = n;
   }
   public void setNumber(int n)
   {
       project_number = n;
   }
   public void setType(String t)
   {
       bulding_type = t;
   }
   public void setAddress(String a)
   {
       address = a;
   }
   public void setErfNumber(String n)
   {
       erf_number = n;
   }
   public void setFee(double f)
   {
       project_fee = f;
   }
   public void setAmountPaid(double a)
   {
       amount_paid = a;
   }
   public void setDeadline(LocalDate d)
   {
       project_deadline = d;
   }
   public void setDate(LocalDate d)
   {
       project_date_assigned = d;
   }
   public void setIsFinalized(boolean s)
   {
       isFinalized = s;
   }
   public String getName()
   {
       return project_name;
   }
   public int getNumber()
   {
       return project_number;
   }
   public String getType()
   {
       return bulding_type;
   }
   public String getAddress()
   {
       return address;
   }
   public String getErfNumber()
   {
       return erf_number;
   }
   public double getFee()
   {
       return project_fee;
   }
   public double getAmountPaid()
   {
       return amount_paid;
   }
   public LocalDate getDeadline()
   {
       return project_deadline;
   }
   public LocalDate getDate()
   {
       return project_date_assigned;
   }
   public boolean isFinalized()
   {
       return isFinalized;
   }
   public Persons getCustomer()
   {
       return customer;
   }
   public Persons getArchitect()
   {
       return architect;
   }
   public Persons getContractor()
   {
       return contractor;
   }
   private int project_number;
   private String project_name;
   private String bulding_type;
   private   String address;
   private   String erf_number;
   private   double project_fee;
   private   double amount_paid;
   private LocalDate project_date_assigned;
   private   LocalDate project_deadline;
   //private   LocalDate dateFinalized;
   private boolean isFinalized;
   private Persons customer;
   private Persons architect;
   private Persons contractor;
  
}