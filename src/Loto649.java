import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;

public class Loto649 {
	
	private Scanner sc = new Scanner(System.in);
	private File f = new File("649.txt"); //Here we set the first text file name
	private File g = new File("540.txt"); //Here we set the second text file name
	
	public Loto649(){
		
	}
	
	public void readFromFile(File f) { //The method with which we read from a file
		
		try {
		
			Process p = Runtime.getRuntime().exec("tail -" + 6 + " " + f.getPath());
			BufferedReader bf = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String s;
			
			while((s = bf.readLine()) != null)
				System.out.print(s);
			
		}catch(FileNotFoundException e) {
			e.getMessage();
		}catch(IOException e) {
			e.getMessage();
		}
	}
	
	public void writeInFile(int[] array, int howMany) {//The method with which we write in a file
		
		File file = g;
		if(howMany == 6)
			file = f;
		
		try {
			
			PrintStream ps = new PrintStream(new FileOutputStream(file,true));
			for(int i:array)
				ps.print(i+" ");
			
			ps.println(", ");
			
			ps.close();
			
		}catch(IOException e) {
			e.getMessage();
		}
		
	}
	
	public void showResults() { //A method with which we show the results from the 2 text files
		System.out.println("6/49, last results: ");
		readFromFile(f);
		System.out.println("\n5/40, last results: ");
		readFromFile(g);
	}
	
	public static int[] genNumb(int nr, int min, int max) { //A method in which we generate 5 or 6 numbers between min and max
		
		Random rand = new Random();
		int[] numbers=new int[nr];
		int currentNumber;
		
		for(int i=0;i<numbers.length;i++) {
			currentNumber=rand.nextInt(max)+min;
			if(contains(currentNumber,i,numbers)) {
				i--;
			}
			else {
				numbers[i]=currentNumber;
			}
			
		}
		
		return numbers;
		
	}
	
	public static boolean contains(int nr, int poz, int[] currentArray) {//A method in which we verify if a number is in a array. We use this to detect if in the generated array(for example) are duplicates, to correct them.
		
		for(int i=0;i<poz;i++)
			if(currentArray[i]==nr)return true;
		
		return false;
		
	}
	
	public int CommonNumbersCount(int[] numbers, int[] numbers1) { //A method in which we count how many numbers from our array are in the generated array 
		
		int contor = 0;
		
		for(int i=0;i<numbers.length;i++) {
			
			if(contains(numbers[i], numbers.length,numbers1)) {
				contor++;
			}
			
		}
		
		return contor;
		
	}
	
	public void ChooseOption() { //A method to choose which game to play or a 3rd option, with which we show the results
		
		int option = sc.nextInt();
		
		switch(option) {
			case 1: play(6,49);
				break;
				
			case 2: play(5,40);
				break;
				
			case 3: showResults();
				break;
				
			default:
				return;
		}
		
		System.out.print("Choose 1 to go back to menu or anything else to exit! \n");
		int option2 = sc.nextInt();
		
		if(option2 == 1) {
			showMenu();
			ChooseOption();
		}
		else System.exit(1);
	}
	
	public void showMenu() { // A simple method to show a menu
		System.out.print("Choose an option: \n");
		System.out.print("1-Loto 6/49\n");
		System.out.print("2-Loto 5/40\n");
		System.out.print("3-Show last loto 6/49 and loto 5/40 results\n");
	}
	
	public void play(int howMany, int From) { //In this method we introduce 5 or 6 numbers, we generate a random array and compare it with our numbers
		
		int[] array = new int[howMany];
		System.out.println("Choose "+howMany+" numbers: \n");
		for(int i=0;i<howMany;i++) {
			System.out.print(i+": ");
			array[i] = sc.nextInt();
			if(contains(array[i],i,array)) {
				System.out.print("You already have that number. Please choose a different number.\n");
				i--;
			}
			if(array[i]<1 || array[i]>From) {
				System.out.print("The number you chose is not between 1 and "+ From+". Please choose another number.\n");
				i--;
			}
		}
		
		System.out.println();
		
		int[] numbGenerated = genNumb(howMany, 0, From);
		for(int i=0;i<howMany;i++)
			System.out.print(numbGenerated[i]+" ");
		
		writeInFile(numbGenerated, howMany);
		
		System.out.println();
		System.out.println("Same numbers: ");
		System.out.print(CommonNumbersCount(array,numbGenerated)+"\n");
		
	}
	
	public static void main(String[] args) { //The main method
		
		Loto649 l = new Loto649();
		
		l.showMenu();
		l.ChooseOption();
		
	}
	

}
