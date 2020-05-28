import datetime
user_info = open("user.txt","r+")
task_info = open("tasks.txt","a+")
users = []
passwords = []
current_date = datetime.datetime.now() 

t_or_f = True # boolean to make things easier 
for line in user_info:
    users.append(line.split(", ")[0])
    passwords.append(line.split(", ")[1].rstrip("\r\n"))


def reg_user(username):
    register_u = True
    while register_u == True:
        if username == "admin":
            new_user = input("Please enter a username: ")
            if new_user in users:
                print("Username taken")
            else:
                new_password = input("Please enter a password: ")
                check_password = input("Please re-enter password: ")
                if new_password != check_password:
                    print("Password does not match!")
                else:
                    register_u = False
                    user_info.write("\n" + new_user + ", " + new_password)
            
            
            user_info.close()

        else: # if they are regular users they will be told they cannot register users.
            print("You need to be an admin to register users.")
def add_task(add_task):
    # addTask = True
    # while addTask == True:
    user_task = input("Enter username of user responsible for the task: ")
    task_title = input("Enter the title of your task: ")
    description = input("Enter a short description of the task: ")
    due_date = input("Enter the due date for the task: ")

    task_info.write("\n" + user_task + ", " + task_title + ", " + description + ", " + str(current_date.strftime("%B %d, %Y")) + ", " + due_date + ", " + "No")

            
    task_info.close()

def view_all():
    viewTask = True
    while viewTask == True:
        task = open("tasks.txt", "r")
        for line in task:
            fields = line.split(", ")
            task_user = fields[0]
            task_title = fields[1]
            task_description = fields[2]
            assigned_date = fields[3]
            due_date = fields[4]
            completed = fields [5]

            print("User: " + task_user + "\n" + "Title: " + task_title + "\n" + "Description:" + task_description + "\n" + "Assigned date: " + assigned_date + "\n" + "Due date: " + due_date + "\n" + "Completed?:" + completed)
        break
    task.close()

def view_mine():
    viewMine = True
    count = 0
    allTasks = []
    userTasks = []
    while viewMine == True:
        task = open("tasks.txt", "r")
        for line in task:
            fields = line.split(", ")
            task_user = fields[0]
            allTasks.append(fields)
            if task_user == username:
                userTasks.append(fields)
                task_title = fields[1]
                task_description = fields[2]
                assigned_date = fields[3]
                due_date = fields[4]
                completed = fields [5]
                count += 1
                
                print(str(count) + "User: " + task_user + "\n" + "Title: " + task_title + "\n" + "Description:" + task_description + "\n" + "Assigned date: " + assigned_date + "\n" + "Due date: " + due_date + "\n" + "Completed?:" + completed)
        task.close()

        userChoice = int(input("Choose a task to edit or -1 to return to the main menu"))
        if userChoice == -1:
            break
        selected = userTasks[userChoice - 1]
        print("user: " + selected[0]+
              "\nTitle: " + selected[1]+
              "\nDescription: " + selected[2]+
              "\nAssigned date: " + selected[3]+
              "\nDue date: " + selected[4]+
              "\nCompleted: " + selected[5])
        userEdit = input("Would you like to \n1. Edit the task. \n2. Mark as completed")
        if userEdit == "1":
            if selected[5] == "Yes":
                print("Task is already completed")
                break
            editSelect = input("Do you want to edit the \n 1. User \n 2. Due Date")
            if editSelect == "1":
                editUser = input("Enter the new user responsible: ")
                userTasks[userChoice - 1][0] = editUser
                # selected[0] = editUser
                
            elif editSelect == "2":
                editdate = input("Enter the new due date: ")
                userTasks[userChoice - 1][4] = editdate

        elif userEdit == "2":
            yesno = input("Enter Yes to mark as completed: ")
            userTasks[userChoice - 1][5] = yesno + "\n"

        tasks = open("tasks.txt", "r+")
        tasks.truncate(0)
        for task in allTasks:
            tasks.write(", ".join(task))
        tasks.close()
                
            
            
def main_menu(): # my main menu options
    runMenu = True # setting the condition for the menu to run on an infinite loop
    while runMenu == True: # the while loop that has the menu run on an infinite loop till the user enters 'e' to exit 
        if username == "admin": # the admin gets an extra feature in their menu 

            user_input = input("Please select one of the following: \n"+
                               "r - register user \n"+
                               "a - add task \n"+
                               "va - view all tasks \n"+
                               "vm - view my tasks \n"+
                               "s - statistics \n"+
                               "e - exit \n")


        else: # other users get a normal menu
            user_input = input("Please select one of the following: \n"+
                               "r - register user \n"+
                               "a - add task \n"+
                               "va - view all tasks \n"+
                               "vm - view my tasks \n"+
                               "e - exit \n")

         # only admins can register users    
        if user_input == "r":
            reg_user(username)

        # this allows any user to add a task
        elif user_input == "a":
            add_task(add_task)

        # this allows any user to see all the tasks in readable order
        elif user_input == "va":
            view_all()
            
        
        # this allows users to only see tasks specifically for them
        elif user_input == "vm":
            view_mine()

        elif user_input == "e":
            quit()
            
        # this is the extra feature on the admin's code 
        elif user_input == "s":
            tasks = 0
            users = 0
            for line in open("tasks.txt", "r").readlines(  ): tasks += 1
            for line in open("user.txt", "r").readlines(  ): users += 1
            print("Tasks: " + str(tasks) + "\n" + "Users: " + str(users))


        else:
            print("Goodbye.")
            quit()
                

# loop that runs login until username/password is correct 
while t_or_f == True:
    username = input("Username: ")
    password = input("Password: ")
    for i in range(len(users)):
        if users[i] == username:
            if passwords[i] == password:
                print("Welcome")
                main_menu()
                t_or_false = False # breaks the infinite loop

    if t_or_f == True:
        print("incorrect username:")
