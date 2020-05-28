import math
#Find out which option the user would like.
choice = (input("Choose either 'investment' or 'bond' from the menu below to proceed." 
               "\n investment - to calculate the amount of interest you'll earn on interest"    #\n option to print underneath each other.
               "\n bond - to calculate the amount you'll have to pay on a home loan: \n").lower())  #.lower() to keep all options from user in one format (lowercase)

#If user chose investment; find out which option of investment they'd like to follow.
if choice == "investment":
    interest = (input("Would you like Simple or Compound interest? ").lower())

#Simple Investment option.
if choice == "investment" and interest == "simple":
    money_deposit = float(input("Please enter the amount of money you'd like to deposit: "))
    rate = (float(input("Please enter the percentage of interest (%): ")) / 100)  #divide user's input with 100 to get correct rate value.
    years = int(input("How many years do you plan on investing?: "))
    A = money_deposit*(1+((rate)*years))    #formula to calculate Simple Investment earnings!
    print("Your earnings will be: R " + str(A))

#Compound Investment Option.
elif choice == "investment" and interest == "compound":
    money_deposit = float(input("Please enter the amount of money you'd like to deposit: "))
    rate = (float(input("Please enter the percentage of interest (%): ")) / 100)    #divide user's input with 100 to get correct rate value.
    years = (int(input("How many years do you plan on investing?: ")))
    A = money_deposit * math.pow((1+rate),years)    #formula to calculate Compound Investment earnings!
    print("Your earnings will be: R" + str(A))

#If the user chose Bond the following will appear.
elif choice == "bond":
    house_value = float(input("What is the present value of your house? "))
    rate = (((float(input("Please enter the percentage of interest (%): ")) /100) /12))  #divide by 12 for monthly rate value.
    months = int(input("How many months will it take you to repay the bond? "))
    repayment = house_value * ((rate*(1 + rate)**months) / (((1 + rate)**months)-1))    #formula to calculate monthly payment of loan.
    print("Your monthly payment will be R" + str(repayment))

#If user did not type a valid response or left it blank, the following message will be printed out:
else:
    print("You didn't enter anything. Please enter your choice from the menu to proceed.")
  
