/**
* Data Structure to hold the range of values for each interval.
* Each Bin will store the interval (that is width or range) of an element
* of the histogram, the count of wind speeds that belong in that Bin’s interval 
* in the histogram, and, will eventually store the processed cumulative 
* probability for each interval (in each Bin) in the histogram.
*
* @author le000422
* @version 1.0
* @since  April 30th
*/
public class Bin{
	/**
   * A variable to store the interval of the Bin
   */
	public double interval;
	/**
   * A variable to store the count of wind speeds that belong 
   * in that Bin's interval
   */
	public int count;
	/**
   * A varibale to store the cumulative probability
   */
	public double cumProbability;
	/**
   * This is the constructor of the class.
   * @param interval This is the first paramter to the constructor
   * @param count This is the second paramter to the constructor
   * @param cumProbability This is the third paramter to the constructor
   */
	public Bin(double interval, int count, double cumProbability){
		this.interval = interval;
		this.count = count;
		this.cumProbability = cumProbability;
	}
}