class ProjectDetails {
  
	int projectNum;   
   String projectName;
   String typeOfBuilding;
   String physicalAddress;
   int erfNum;
   int totalFee;
   int totalPaidalready;
   String deadline;
  
   public void setTotalpaidalready(int totalpaidalready) {
		totalPaidlready = totalPaidalready;
   }
   public void setDeadline(String deadline){
		deadline = deadline;
	}
   void print() {
	   System.out.println("Project Number: " + projectNum);
	   System.out.println("Project Name: " + projectName);
	   System.out.println("Type of building: " + typeOfBuilding);
	   System.out.println("ERF Number: " + erfNum);
	   System.out.println("Physical Address: " + physicalAddress);
	   System.out.println("Total fee charged for project: " + totalFee);
	   System.out.println("Total amount already paid: " + totalPaidalready);
	   System.out.println("Deadline of the project: " + deadline);
   }
}	