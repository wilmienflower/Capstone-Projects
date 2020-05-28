public class Persons
{
   public Persons()
   {
       name = "";
       telephone = "";
       email = "";
       address = "";
       role_in_project = "";
   }
   public Persons(String name, String phone, String email, String address, String role)
   {
       // call setter functions
       setName(name);
       setPhone(phone);
       setEmail(email);
       setAddress(address);
       setRole(role);  
   }
   public void setName(String n)
   {
       name = n;
   }
   public void setPhone(String n)
   {
       telephone = n;
   }
   public void setEmail(String e)
   {
       email = e;
   }
   public void setAddress(String a)
   {
       address = a;
   }
   public void setRole(String r)
   {
       role_in_project = r;  
   }
   public String getName()
   {
       return name;
   }
   public String getPhone()
   {
       return telephone;
   }
   public String getEmail()
   {
       return email;
   }
   public String getAddress()
   {
       return address;
   }
   public String getRole()
   {
       return role_in_project;
   }
   private String name;
   private String telephone;
   private String email;
   private String address;
   private String role_in_project; // who is the person? customer, contractor etc      
}