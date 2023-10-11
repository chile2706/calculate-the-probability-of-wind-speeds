import java.util.Scanner;
import java.lang.Math;
import java.io.*;
/**
* Calculate the Probability of Wind Speeds
* Using the Principle of Maximum Entropy and Linear Regression 
* to create a Cumulative Probability Distribution Function 
* that can be used to calculate the Probability of Wind Speeds.
*
* @author  le000422
* @version 1.0
* @since  April 30th
*/
public class Histogram{
	/**
   * A data structure to hold the data for each interval
   */
	private Bin[] histogram = new Bin[200];

	/**
   * A data structure to store data, which are values of wind speed, from the file
   */
	private double[] windValues = new double[9000];

	/**
   * A variable to store the count of valid wind values that the program reads in from file
   */
	private int numWindValues = 0;
	/**
   * A varibale to store the width of each interval
   */
	private double interval;
	/**
   * A varibale to store K computed from cdf() method
   */
	private double k;

	/**
   * This is the constructor of the class.
   * Initialize the bins in the histogram with the interval spacing
   * after obtaining the value for userDefinedInterval from the user
   * @param userDefinedInterval This is the paramter to History constructor
   */

	public Histogram(double userDefinedInterval){
		for (int i = 0; i < 200; i++){
			histogram[i] = new Bin(i*userDefinedInterval, 0, 0);
		}
		interval = userDefinedInterval;
	}

	/**
   * This method is used to get the file name and read the data in from the file 
   * and store them in an array called windValues
   * @param s This is the paramter to getData method
   * @exception IOException On input error
   * @see IOException
   */

	public void getData(Scanner s) throws IOException{
		String fileName;
		File file;
		String line;
		double windSpeed;
		int countLine = 0;
		while(true){
			System.out.println("Name of file: ");
			fileName = s.next();
			try{
				file = new File(fileName);
				Scanner scan = new Scanner(file);
				while(countLine < 7){
					line = scan.nextLine();
					countLine++;
				}
				while (scan.hasNextLine()){
					line = scan.nextLine();
					String [] col = line.split(",");
					if (!col[5].equals("NaN")){
						try{
							windSpeed = Double.parseDouble(col[5]);
							System.out.println("Value: " + windSpeed);
						}
						catch(Exception e){
							System.out.println(col[5] + " is not a floating point number!");
							continue;
						}
						windValues[numWindValues] = windSpeed;
						numWindValues++;
					}
					else{
						System.out.println(col[5] + " is not a floating point number!");
					}
				}
				scan.close();
				break;
			}
			catch(Exception e){
				System.out.println(fileName + " does not exist! Try again.");
				continue;
			}
		}
	}

	/**
   * This method is used to add each wind value to a correct bin.
   * wind values stored in double[] windValues are squared and added to its histogram bin.
   */

	public void addHistogram(){
		double sqWindValue;
		for(int i = 0; i < numWindValues; i++){
			sqWindValue = windValues[i]*windValues[i];
			for(int j = 0; j < 200; j++){
				if((sqWindValue >= histogram[j].interval) && (sqWindValue < histogram[j+1].interval)){
					histogram[j].count++;
					break;
				}
			}
		}	
	}

	/**
   * This method is used to normalize the counts within the bins to cumulative probabilities.
   */

	public void normalize(){
		double cumulative_P = 1;
		for (int j = 0; j < 200; j++){
			cumulative_P = cumulative_P - ((double) histogram[j].count / numWindValues);
			if (cumulative_P <0){
				break;
			}
			histogram[j].cumProbability = cumulative_P;
		}
	}

	/**
   * This method is used to compute 
   * the cumulative probability distribution function (K).
   */

	public void cdf(){
		double num = 0;
		double den = 0;
		for (int j = 0; j < 200; j++){
			if (histogram[j].cumProbability < 0.01){
				break;
			}
			num = num - Math.log(histogram[j].cumProbability);
			den = den + histogram[j+1].interval;
		}
		double k = num/den;
		this.k =k;
	}

	/**
   * This method is used to save K, interval, and the cumulative probability 
   * your program computed for each bin to a file called "cumProbability.txt".
   * @exception IOException On output error
   * @see IOException
   */

	public void writeFile() throws IOException{
		File fout = new File("cumProbability.txt");
		FileOutputStream fos = new FileOutputStream(fout);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

		bw.write("k " + k);
		bw.newLine();
		double intervalLine = interval;
		for (int j = 0; j < 200; j++){
			if (histogram[j].cumProbability < 0.01){
				break;
			}
			intervalLine = histogram[j].interval + interval;
			bw.write("" + intervalLine + " " + histogram[j].cumProbability);
			bw.newLine();
		}
		bw.close();
	}

	/**
   * This method is used to estimate the probability of wind speeds.
   * @param scan This is the paramter to analysis method
   */

	public void analysis(Scanner scan){
		String userInput;
		String windInput;
		System.out.println("Enter 'less', 'greaterEq', or 'q' to quit: ");
		userInput = scan.next();
		double prob;
		while (!userInput.equals("q")){
			double windSpeed = -1;
			if (userInput.equals("less") || userInput.equals("greaterEq")){
				while (windSpeed < 0){
					System.out.println("Enter wind speed: ");
					windInput = scan.next();
					try{
						windSpeed = Double.parseDouble(windInput);
					}
					catch(Exception e){
						System.out.println(windInput + " is not a valid number");
						continue;
					}
				}
				if (userInput.equals("less")){
					prob = 1.0 - Math.exp(-k*windSpeed*windSpeed);
					System.out.println("Probability wind speed < " + windSpeed + " is " + prob);
				}
				else{
					prob = Math.exp(-k*windSpeed*windSpeed);
					System.out.println("Probability wind speed >= " + windSpeed + " is " + prob);
				}
			}
			else{
				System.out.println(userInput + " is an invalid input");
			}

			System.out.println("Enter 'less', 'greaterEq', or 'q' to quite: ");
			userInput = scan.next();
		}
	}

	/**
   * This is the main method. First, it asks for user's input of 
   * interval and name of the file they want to analyze. Then it normalize 
   * the data, compute K and write them to a file called cumProbability.txt.
   * Finally, it runs the program to estimate the probability of wind speeds
   * that the user enters.
   * @param args Unused.
   * @exception IOException On input error.
   * @see IOException
   */

	public static void main(String[] args)throws IOException{
		Scanner scan = new Scanner(System.in);
		String userInput;
		double userDefinedInterval = 0;
		while(true){
			System.out.print("Interval of each bin: ");
			userInput = scan.next();
			try{
				userDefinedInterval = Double.parseDouble(userInput);
				if (userDefinedInterval < 50 || userDefinedInterval > 100){
					System.out.println("Invalid Input - " + userInput + " is not a number between 50 and 100!");
				}
				else{
					break;
				}
			}
			catch(Exception e){
				System.out.println("Invalid Input - " + userInput + " is not a number between 50 and 100!");
				continue;
			}
		}
		Histogram histogram = new Histogram(userDefinedInterval);
		histogram.getData(scan);
		histogram.addHistogram();
		histogram.normalize();
		histogram.cdf();
		histogram.writeFile();
		histogram.analysis(scan);
	}

}