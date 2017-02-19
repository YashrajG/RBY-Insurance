import java.util.*;
import java.io.*;
class InsuranceManager
{
	public static void main(String args[]) throws IOException
	{
		int ch2,choice,inc;
		Client c=new Client();
		Scanner sc=new Scanner(System.in);
		System.out.println("\t::WELCOME TO RBY INSURANCE Co. Ltd.::");
		System.out.println();
		do
		{
			System.out.println("Choose an option number:");
			System.out.println("1.Client Sign Up");
			System.out.println("2.Client Login");

			choice=sc.nextInt();

			switch (choice)
			{
			case 1:	c.formFill(true);
			System.out.println("Account created. Please login to continue:-");
			case 2:	c.formFill(false);
			break;
			default:System.out.println("Invalid input");
			}
		}while((choice!=1 && choice!=2 ) || c.name==null);
		System.out.println(c.name);
		do
		{
			System.out.println("Choose an option number:");
			System.out.println("1.Pay premiums");
			System.out.println("2.New Vehicle Insurance Policy");
			System.out.println("3.New Mediclaim Policy");
			System.out.println("4.Claim");
			System.out.println("5.Exit");
			choice=sc.nextInt();

			switch(choice)
			{
			case 1: if(c.medPolicy.number!=null)
			{
				System.out.println("1.Mediclaim");
				inc=2;
			}
			else inc=1;
			for(int i=0;i<c.vehicleIPolicies.size();i++)
			{
				System.out.println((i+inc)+"."+ ((VInsurePolicy)c.vehicleIPolicies.elementAt(i)).carbrand + " " + ((VInsurePolicy)c.vehicleIPolicies.elementAt(i)).carname);
			}
			ch2=sc.nextInt();
			if(ch2==1 && inc==2)
			{
				c.medPolicy.payPremium();
			}
			else if(ch2>=1 && ch2<c.vehicleIPolicies.size()+2)
			{
				((VInsurePolicy)c.vehicleIPolicies.elementAt(ch2-inc)).payPremium();
			}
			else
			{
				System.out.println("Wrong choice.");
			}
			break;

			case 2: c.addVehInsPolicy();
			break;

			case 3: c.addMedPolicy();
			break;

			case 4: if(c.medPolicy.number!=null)
			{
				System.out.println("1.Mediclaim ");
				inc=2;
			}
			else inc=1;
			for(int i=0;i<c.vehicleIPolicies.size();i++)
			{
				System.out.println((i+inc)+"."+ ((VInsurePolicy)c.vehicleIPolicies.elementAt(i)).carbrand + " " + ((VInsurePolicy)c.vehicleIPolicies.elementAt(i)).carname);
			}
			ch2=sc.nextInt();
			if(ch2==1 && inc==2)
			{
				c.medPolicy.claim();
			}
			else if(ch2>=1 && ch2<c.vehicleIPolicies.size()+2)
			{
				((VInsurePolicy)c.vehicleIPolicies.elementAt(ch2-inc)).claim();
			}
			else
			{
				System.out.println("Wrong choice.");
			}
			break;
			case 5:break;
			default:System.out.println("Incorrect choice. Please choose again:");			
			}
		}while(choice!=5);		
	}
}

class Client
{
	String name,gender,address,number,username,password,details[];
	int age,totalVPolicies;
	long income;
	File vFile;
	static Vector fContent;
	PrintWriter f;
	Mediclaim medPolicy;
	Vector vehicleIPolicies;
	Scanner sc;
	Client()throws FileNotFoundException
	{
		sc=new Scanner(System.in);
		vFile=new File("Client.txt");
		Scanner scanFile=new Scanner(vFile);
		//Storing in file
		fContent=new Vector();
		while(scanFile.hasNextLine())
		{
			//Added to vector;
			fContent.addElement(scanFile.nextLine());
		}
		vehicleIPolicies=new Vector();
		details=new String[9];		
	}
	public void formFill(boolean isNew)throws IOException
	{	
		if (isNew)
		{
			System.out.println("\t\tKYC Form");

			System.out.println("Enter name:");
			name=sc.nextLine();

			System.out.println("Enter Gender:");
			gender=sc.next();

			System.out.println("Enter Age:");
			age=sc.nextInt();

			System.out.println("Enter Address:");
			address=sc.nextLine();
			address=sc.nextLine();

			System.out.println("Enter Phone Number:");
			number=sc.nextLine();

			System.out.println("Enter Income:");
			income=sc.nextLong();

			totalVPolicies=0;

			boolean flag;
			do{
				System.out.println("Enter a username:");
				username=sc.next();
				flag=false;
				for(int i=0;i<fContent.size();i++)
				{
					if(fContent.elementAt(i).toString().startsWith(username))
					{	
						System.out.println("Username already exist.");
						System.out.println("Please enter some other username");
						flag=true;
					}
				}
			}while(flag);

			System.out.println("Enter a password:");
			password=sc.next();	

			f= new PrintWriter(new BufferedWriter(new FileWriter("Client.txt",false)));
			String[] details=new String[9];
			details[0]=username;
			details[1]=password;
			details[2]=name;
			details[3]=gender;
			details[4]=Integer.toString(age);
			details[5]=address;
			details[6]=number;
			details[7]=Long.toString(income);
			details[8]=Integer.toString(totalVPolicies);
			for(int vIndex=0;vIndex<fContent.size();vIndex++)
				f.println(fContent.elementAt(vIndex));
			String newLine="";
			int j;
			for(j=0;j<details.length-1;j++)
				newLine=newLine+details[j]+",";
			newLine=newLine+details[j];	
			fContent.addElement(newLine);
			int i=0;
			while(i<details.length-1)
				f.print(details[i++]+",");
			f.println(details[i]);
			f.close();
		}
		else
		{
			boolean isExisting=false;
			String repeat="n";
			do
			{
				System.out.println("Enter your user Name:");
				username=sc.next();
				System.out.println("Enter your Password:");
				String enteredPassword=sc.next();
				for(int i=0;i<fContent.size();i++)
				{	
					isExisting=false;
					int j=0;
					while(fContent.elementAt(i).toString().charAt(j)!=',')	
					{
						j++;
					}
					if(username.equals(fContent.elementAt(i).toString().substring(0,j)))
					{
						isExisting=true;
						String line=fContent.elementAt(i).toString();
						int mark=-1,detIndex=0;	//to help with extraction of data
						for(j=0;j<line.length();j++)
						{
							if(line.charAt(j)==',')
							{
								details[detIndex]=line.substring(mark+1,j);
								detIndex++;
								mark=j;					
							}
						}
						details[detIndex]=line.substring(mark+1);
						//We got details. now assigning them.
						username=details[0];
						password=details[1];
						name=details[2];
						gender=details[3];
						age=Integer.parseInt(details[4]);
						address=details[5];
						number=details[6];
						income=Long.parseLong(details[7]);
						totalVPolicies=Integer.parseInt(details[8]);
						repeat="n";
						if(!enteredPassword.equals(password))
						{
							System.out.println("Incorrect password. Please enter valid password");
							repeat="y";							
						}
						break;
					}
				}
				if(!isExisting)
				{
					System.out.println("Username does not exist.");
					System.out.println("Do you want to try again?");
					repeat=sc.next();
				}
			}while(repeat.contains("y")||repeat.contains("Y"));
			for(int i=1;i<=totalVPolicies;i++)
			{
				vehicleIPolicies.addElement(new VInsurePolicy(username,false,i));
			}
			medPolicy=new Mediclaim(name,false);
		}
	}
	void addMedPolicy()
	{
		try{
			medPolicy=new Mediclaim(username,true);
		}catch(IOException e){}
	}
	void addVehInsPolicy()throws IOException
	{
			vehicleIPolicies.addElement(new VInsurePolicy(username,true,0));	
			totalVPolicies++;
	}
}
interface InsurancePolicy
{
	public void payPremium();
	public void claim() throws IOException;
}
class Mediclaim implements InsurancePolicy
{
	PrintWriter f;
	String name,gender,occupation,address,number,medDetails[];
	int age;
	double income;
	long medInsAmount,medClaimAmt,medPremium,balance;
	Scanner scUser,scFile;
	File mFile;
	static Vector mContent;

	//Constructor
	Mediclaim(String customerUsername,boolean creation)throws IOException
	{
		try
		{
			name=customerUsername;
			scUser=new Scanner(System.in);
			mFile=new File("Mediclaim.txt");

			scFile=new Scanner(mFile);

			medDetails=new String[10];
			//Storing in file
			mContent=new Vector();
			while(scFile.hasNextLine())
			{
				//System.out.println("Added to vector");
				mContent.addElement(scFile.nextLine());
			}
			//scanFile.close();
			if(creation)
			{
				medForm();
			}
			else
			{
				medLoadData(customerUsername);
			}
		}
		catch(FileNotFoundException e){}
	}

	public void medForm() throws IOException
	{
		int choice;

		//Accepting Details

		System.out.println("Enter the full name of person Insured");
		String name=scUser.nextLine();

		System.out.print("Enter your age:");
		age=scUser.nextInt();

		System.out.print("Enter your gender:");
		gender=scUser.next();

		System.out.println("Enter your address:");
		address=scUser.nextLine();
		address=scUser.nextLine();

		System.out.println("Enter your mobile number:");
		number=scUser.nextLine();

		System.out.println("Enter your Occupation:");
		occupation=scUser.nextLine();

		System.out.println("Enter your monthly income:");
		income=scUser.nextLong();

		System.out.println("Choose your Sum Insured:");

		if(income>=15000 && income<=50000)
		{
			System.out.println("1. Rs.3,00,000");
		}
		else if(income>50000 && income<=100000)
		{
			System.out.println("1. Rs.3,00,000");
			System.out.println("2. Rs.5,00,000");
		}
		else if(income>100000)
		{
			System.out.println("1. Rs.3,00,000");
			System.out.println("2. Rs.5,00,000");
			System.out.println("3. Rs.10,00,000");
		}
		else
		{
			System.out.println("You are not elligible for Mediclaim Policy");
		}

		do
		{
			choice=scUser.nextInt();
			switch(choice)
			{
			case 1: medInsAmount=300000;
			break;

			case 2: medInsAmount=500000;
			break;

			case 3: medInsAmount=1000000;
			break;

			default :System.out.println("Wrong choice!");

			}
		} while(choice!=1 && choice!=2 && choice!=3);

		if(medInsAmount==300000)
			medPremium=5000;
		else  if(medInsAmount==500000)
			medPremium=8000;
		else if(medInsAmount==1000000)
			medPremium=12000;

		balance=medInsAmount;

		System.out.println("Your policy is successfully registered.");
		System.out.println();
		System.out.println("Your yearly premium is Rs."+medPremium); 

		for(int mIndex=0;mIndex<mContent.size();mIndex++)
			if(mContent.elementAt(mIndex).toString().startsWith(name))
			{
				mContent.removeElementAt(mIndex);
				mIndex--;
			}
		try{
			f=new PrintWriter(new BufferedWriter(new FileWriter("Mediclaim.txt",false)));
			medDetails[0]=name;
			medDetails[1]=Integer.toString(age);
			medDetails[2]=gender;
			medDetails[3]=address;
			medDetails[4]=number;
			medDetails[5]=occupation;
			medDetails[6]=Double.toString(income);
			medDetails[7]=Long.toString(medInsAmount);
			medDetails[8]=Long.toString(medPremium);
			medDetails[9]=Long.toString(balance);
			for(int vIndex=0;vIndex<mContent.size();vIndex++)
				f.println(mContent.elementAt(vIndex));
			String newLine="";
			int j;
			for(j=0;j<medDetails.length-1;j++)
				newLine=newLine+medDetails[j]+",";
			newLine=newLine+medDetails[j];	
			mContent.addElement(newLine);
			int i=0;
			while(i<medDetails.length-1)
				f.print(medDetails[i++]+",");
			f.println(medDetails[i]);
			f.close();
		}	
		catch(FileNotFoundException e){}

	}

	public void medLoadData(String customerUsername)
	{
		for(int i=0;i<mContent.size();i++)
		{
			if(mContent.elementAt(i).toString().startsWith(customerUsername))
			{
				String line;
				line=mContent.elementAt(i).toString();
				int mark=-1,detIndex=0;
				for(int j=0;j<line.length();j++)
				{
					if(line.charAt(j)==',')
					{
						medDetails[detIndex]=line.substring(mark+1,j);
						detIndex++;
						mark=j;					
					}
				}
				medDetails[detIndex]=line.substring(mark+1);
				//We got details. now assigning them.
				name=medDetails[0];
				age=Integer.parseInt(medDetails[1]);
				gender=medDetails[2];
				address=medDetails[3];
				number=medDetails[4];
				occupation=medDetails[5];
				income=Double.parseDouble(medDetails[6]);
				medInsAmount=Long.parseLong(medDetails[7]);
				medClaimAmt=Long.parseLong(medDetails[8]);
				balance=Long.parseLong(medDetails[9]);
			}
		}
	}

	public void payPremium()
	{
		System.out.println("Are you sure you want to pay the premium?");
		String choice=scUser.nextLine();
		if(choice.contains("yes")||choice.contains("YES")||choice.contains("Yes")||choice.equalsIgnoreCase("y"))
		{
			System.out.println("Thank You! Your mediclaim premium of Rs."+medPremium+" is paid");

		}
		else
		{
			System.out.println("Premium Payment Terminated");	
		}	
	}

	public void claim()throws IOException
	{
		System.out.println("Enter your hospital expenses");
		this.medLoadData(name);
		medClaimAmt=scUser.nextLong();
		System.out.println("Balance="+balance);
		System.out.println("Claim Amount="+medClaimAmt);
		if ((medClaimAmt<=balance)&&(balance>0)&&(medClaimAmt>=1000))
		{
			balance=balance-medClaimAmt;

			System.out.println("Amount of Rs "+medClaimAmt+" is credited to your account");
		}
		else if(medClaimAmt<1000)
		{

			System.out.println("Minimum claim amount should be Rs 1000");
		}
		else
		{

			System.out.println("Sorry! Your claim amount is exceeding your balance");
			System.out.println("Do you want the balance of Rs "+balance+" to be credited to your account");
			String choice=scUser.nextLine();

			if((choice.equalsIgnoreCase("yes")==true)||(choice.equalsIgnoreCase("y")==true)||(choice.equalsIgnoreCase("\n")==true))
			{
				System.out.println("Amount of Rs "+balance+" is credited into your account");
				balance=0;
			}
		}
		for(int i=0;i<mContent.size();i++)
		{
			String line;
			line=mContent.elementAt(i).toString();
			int mark=-1,detIndex=0;
			for(int j=0;j<line.length();j++)
			{
				if(line.charAt(j)==',')
				{
					medDetails[detIndex]=line.substring(mark+1,j);
					detIndex++;
					mark=j;					
				}
			}
			medDetails[detIndex]=line.substring(mark+1);
			//We got name of one record
			if(medDetails[0].equals(name))
			{	
				for(int k=line.length()-1;k>=0;k--)
				{
					if(line.charAt(k)==',')
					{
						String newLine=line.substring(0,k)+","+Long.toString(balance);
						mContent.insertElementAt(newLine,i);
						mContent.removeElementAt(i+1);
						break;					
					}	
				}
				break;
			}

		}
		f=new PrintWriter(new BufferedWriter(new FileWriter("Mediclaim.txt",false)));
		for(int vIndex=0;vIndex<mContent.size();vIndex++)
			f.println(mContent.elementAt(vIndex));	
		f.close();


	}
}
class VInsurePolicy implements InsurancePolicy 
{
	PrintWriter f;
	String owner,carbrand,carname,numberPlate,details[];
	int age;
	long insAmount,totRepairs,claimedAmt,carValue,premium;
	Scanner scanUser,scanFile;
	File vFile;
	static Vector fContent;

	//Constructor
	VInsurePolicy(String customerUsername,boolean creation,int policyNumber)throws IOException
	{
		owner=customerUsername;
		scanUser=new Scanner(System.in);
		vFile=new File("Vehicle.txt");

		scanFile=new Scanner(vFile);

		details=new String[8];
		//Storing in file
		fContent=new Vector();
		while(scanFile.hasNextLine())
		{
			//System.out.println("Added to vector");
			fContent.addElement(scanFile.nextLine());
		}
		//scanFile.close();
		if(creation)
		{
			formFill();
		}
		else
		{
			loadData(customerUsername,policyNumber);
		}
	}

	void formFill()throws IOException
	{
		//Accepting details
		System.out.println("Enter your car name");
		String car=scanUser.nextLine();
		car.trim();
		if(car.contains(" "))
		{
			carbrand=car.substring(0,car.indexOf(" "));
			carname=car.substring(car.indexOf(" ")+1);
		}
		else
		{
			carname=car;
			System.out.print("Enter car brand:");
			carbrand=scanUser.nextLine();
		}

		System.out.print("Enter your car's complete numberplate:");
		boolean isPresent=false;
		do
		{
			numberPlate=scanUser.nextLine();
			isPresent=false;
			String line;
			for(int i=0;i<fContent.size();i++)
			{	
				line=fContent.elementAt(i).toString();
				int mark=-1;
				String numPlate="";
				for(int j=0;j<line.length();j++)
				{
					if(line.charAt(j)==',')
					{
						if(mark!=-1)
						{
							numPlate=line.substring(mark+1,j);
							break;
						}
						else
							mark=j;
					}
				}
				if(numPlate.equalsIgnoreCase(numberPlate))
				{
					isPresent=true;
					System.out.println("Car with numberplate "+numPlate+"is already insured.");
					System.out.println("Please enter another numberplate:");
				}
			}
		}while(isPresent);	

		System.out.print("Enter the purchase cost of your car:");
		carValue=scanUser.nextLong();

		System.out.println("How old is your car in years?");
		age=scanUser.nextInt();

		System.out.println("Your policy is successfully registered.");

		insAmount=carValue;
		premium=(insAmount/100000)*3000;
		for(int a=1;a<=age;a++)
			insAmount*=(float)9/10;
		totRepairs=(6*insAmount)/10;
		claimedAmt=0;

		System.out.println("Your yearly premium is Rs."+premium);


		f=new PrintWriter(new BufferedWriter(new FileWriter("Vehicle.txt",false)));
		String[] details=new String[8];
		details[0]=owner;
		details[1]=numberPlate;
		details[2]=carname;
		details[3]=carbrand;
		details[4]=Integer.toString(age);
		details[5]=Long.toString(insAmount);
		details[6]=Long.toString(totRepairs);
		details[7]=Long.toString(claimedAmt);
		for(int vIndex=0;vIndex<fContent.size();vIndex++)
			f.println(fContent.elementAt(vIndex));
		String newLine="";
		int j;
		for(j=0;j<details.length-1;j++)
			newLine=newLine+details[j]+",";
		newLine=newLine+details[j];	
		fContent.addElement(newLine);
		int i=0;
		while(i<details.length-1)
			f.print(details[i++]+",");
		f.println(details[i]);
		f.close();


	}
	void loadData(String customerUsername,int policyNumber)
	{
		int ctr=0;
		for(int i=0;i<fContent.size();i++)
		{
			if(fContent.elementAt(i).toString().startsWith(customerUsername))
			{
				ctr++;
				if(ctr==policyNumber)
				{
					String line;
					line=fContent.elementAt(i).toString();
					int mark=-1,detIndex=0;
					for(int j=0;j<line.length();j++)
					{
						if(line.charAt(j)==',')
						{
							details[detIndex]=line.substring(mark+1,j);
							detIndex++;
							mark=j;					
						}
					}
					details[detIndex]=line.substring(mark+1);
					//We got details. now assigning them.
					owner=details[0];
					numberPlate=details[1];
					carname=details[2];
					carbrand=details[3];
					age=Integer.parseInt(details[4]);
					insAmount=Long.parseLong(details[5]);
					totRepairs=Long.parseLong(details[6]);
					claimedAmt=Long.parseLong(details[7]);
				}
			}
		}
		premium=(insAmount/100000)*3000;
	}
	public void claim()throws IOException
	{
		boolean isReparable=false;
		String parts[]={"tyre","engine","key","light","hood","bonnet","windshield","glass","mirror"};

		System.out.println("In fewest possible words, explain the physical damage happened to your car:");
		String problem=scanUser.nextLine();
		String problem2=scanUser.nextLine();
		problem.toLowerCase();
		problem2.toLowerCase();
		for(String piece:parts)
			if(problem.contains(piece)||problem2.contains(piece))
			{
				isReparable=true;
				break;
			}

		if(isReparable==true)
		{
			this.claimRepairs();
		}
		else
		{
			System.out.println("Is your car reparable?");
			String isRepair=scanUser.nextLine();
			if(isRepair.contains("y")||isRepair.contains("Y"))
				this.claimRepairs();
			else 
				this.claimAll();

		}
	}
	private void claimRepairs()throws IOException
	{
		System.out.println("Our experts conclude that the problem is repairable.");
		System.out.print("Enter the name of the authorised service center at which your car is being repaired:");
		String serCent=scanUser.nextLine();
		System.out.println("Enter the cost of repairs at "+serCent);
		long cost=scanUser.nextLong();
		if(claimedAmt+cost < totRepairs)
		{
			System.out.println("A transaction of Rs."+cost+" has been initiated towards "+serCent+".");
			claimedAmt+=cost;
			System.out.println("An amount of Rs."+(totRepairs-claimedAmt)+" is claimable for damage repair costs.");
		}
		else if(claimedAmt+cost > totRepairs)
		{
			System.out.println("Your total claimed amount exceeds Rs."+totRepairs);
			System.out.println("Would you like to initialise a transaction of only Rs."+(totRepairs-claimedAmt)+" only?");
			String shdTrans=scanUser.nextLine();
			shdTrans=scanUser.nextLine();
			if(shdTrans.contains("y") || shdTrans.contains("Y"))
			{
				System.out.println("A transaction of Rs."+(totRepairs-claimedAmt)+" has been initiated towards "+serCent+".");
				claimedAmt=totRepairs;
			}
			else System.out.println("Transaction aborted.");
		}
		else
		{
			System.out.println("A transaction of Rs."+cost+" has been initiated towards "+serCent+".");
			claimedAmt+=cost;
			System.out.println("You cannot claim any more money");
		}


		//for(int vIndex=0;vIndex<fContent.size();vIndex++)
		//	System.out.println(fContent.elementAt(vIndex));	

		//scanFile.close();
		f=new PrintWriter(new BufferedWriter(new FileWriter("Vehicle.txt",false)));
		for(int i=0;i<fContent.size();i++)
		{
			String line;
			line=fContent.elementAt(i).toString();
			int mark=-1;
			String numPlate="";
			for(int j=0;j<line.length();j++)
			{
				if(line.charAt(j)==',')
				{
					if(mark!=-1)
					{
						numPlate=line.substring(mark+1,j);
						break;
					}
					else
						mark=j;

				}
			}
			//We got numberplate of one record
			if(numPlate.equals(numberPlate))
			{	
				for(int k=line.length()-1;k>=0;k--)
				{
					if(line.charAt(k)==',')
					{
						String newLine=line.substring(0,k)+","+Long.toString(claimedAmt);
						fContent.removeElementAt(i);
						fContent.insertElementAt(newLine,i);
						break;					
					}	
				}
			}

		}
		for(int vIndex=0;vIndex<fContent.size();vIndex++)
			f.println(fContent.elementAt(vIndex));	
		f.close();

	}
	private void claimAll()throws IOException
	{
		System.out.println("We are sorry to know that the damage dealt by your car is not repairable");
		for(int i=0;i<fContent.size();i++)
		{
			String line;
			line=fContent.elementAt(i).toString();
			int mark=-1,detIndex=0;
			for(int j=0;j<line.length();j++)
			{
				if(line.charAt(j)==',')
				{
					details[detIndex]=line.substring(mark+1,j);
					detIndex++;
					mark=j;					
				}
			}
			details[detIndex]=line.substring(mark+1);
			//We got number plate of one record
			if(details[1].equals(numberPlate))
			{	
				System.out.println("A transaction of Rs."+(insAmount-claimedAmt)+" has been initiated towards your registered account.");
				fContent.removeElementAt(i);
				break;
			}

		}
		f=new PrintWriter(new BufferedWriter(new FileWriter("Vehicle.txt",false)));
		for(int vIndex=0;vIndex<fContent.size();vIndex++)
			f.println(fContent.elementAt(vIndex));	
		f.close();
	}
	public void payPremium()
	{
		System.out.println("Are you sure you want to pay the premium?");
		String choice=scanUser.nextLine();
		if(choice.contains("yes")||choice.contains("YES")||choice.contains("Yes")||choice.equalsIgnoreCase("y"))
		{
			System.out.println("Thank You! Your Vehicle Insurance premium of Rs."+premium+" is paid");

		}
		else
		{
			System.out.println("Premium Payment Terminated");	
		}
	}
}
