/***
 * @author Jeff Nkandu
 * Quick sort algorithm adapted from : http://theoryapp.com/quick-sort/ 
 */

package com.uu.sys.recommender.movies;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.math.Matrix;
import org.apache.mahout.math.DenseMatrix;

//import com.google.gson.JsonObject;

public class Recommender
{
	private ArrayList<Long>  itemIDs = new ArrayList<Long>(); //Current data set
	private ArrayList<Long> sortedItemIDs = new ArrayList<Long>(); //Sorted current data set
	
	public Matrix pVector; //probability Vector, m rows 1 column
	public Matrix rVector; //recommendation Vector
	public Matrix uVector; //user side information Vector
	public Matrix W;       //Abstract matrix to score probability vector
	
	/*
	 *  Constructor
	 */
	public Recommender(Matrix p,Matrix u, Matrix W_matrix, ArrayList<Long> items)
	{
		pVector = new DenseMatrix(items.size(),1);
		rVector = new DenseMatrix(items.size(),1);
		uVector = new DenseMatrix(items.size(),1);
		W = new DenseMatrix(items.size(),items.size());
		
		this.itemIDs = items;
		this.W.assign(W_matrix);
		this.uVector.assign(u);
		this.pVector.assign(p);
		this.rVector.assign(0.0); //Set all elements to 0
	}
	
	public ArrayList<Long> getRecommendations() throws TasteException,IOException
	{
		int numOfItems = itemIDs.size();

		//System.out.println("\nU Vector:\n"+uVector);

		/*
		 * Compute vector p
		 */ 
		Matrix tempVector = new DenseMatrix(numOfItems,1);
		tempVector = W.times(uVector); //W*u
		
		for(int i=0;i<numOfItems;i++)
		{
			pVector.set(i,0,Math.exp(tempVector.get(i, 0))); //exponent(W*u)
		}
	
		pVector = pVector.divide(pVector.zSum()); //(exponent(W*u))/sumAll(exponent(W*u))
		
		/*
		 * Prepare recommendations 
		 * (Assign all values in vector p to vector r and sort vector r)
		 */
		rVector.assign(pVector);
		this.sortedItemIDs.addAll(insertionSortrVector(rVector, itemIDs)); //sort items		

/*		System.out.println("\nSorted Recommendations\n=======================");
		System.out.println("No.\tOrig. Index\tProbability\t\t\tMovie Title");
*/
		/*
		 * Display sorted item list
		 */
/*		for (int i=0;i<sortedItemIDs.size();i++)
		{
			Movie movie = new Movie();
			long movie_id = sortedItemIDs.get(i);  
			JsonObject movieJsonObj = movie.movieInfo(movie_id); //Get movie info from database
			
			int index = itemIDs.indexOf(sortedItemIDs.get(i));
			System.out.println((i+1)+"\t"+index+"\t\t"+rVector.get(i, 0)+"\t\t"+movieJsonObj.get("title"));
		}*/
		
		return sortedItemIDs;
		
	}

	
	public Matrix getpVector()
	{
		return pVector;
	}
	
	/*
	 * For sorting the recommendation vector
	 * Passively sorts the items ID list, using indices from rVector
	 */
	private static ArrayList<Long> insertionSortrVector(Matrix rV, ArrayList<Long> items)
	{
		ArrayList<Long> list = new ArrayList<Long>();
		list.addAll(items);
		
        for (int i = 1; i < list.size(); i++) //rV vector is same size as number of items
        {
            double nextRecVecElement = rV.get(i, 0);
            long nextItem = list.get(i);
            
            // find the insertion location while moving all larger element up
            int j = i;
            while (j > 0 && rV.get(j-1, 0) < nextRecVecElement) 
            {
            	rV.set(j, 0,rV.get(j-1, 0));
            	list.set(j, list.get(j-1));
                j--;
            }
            // insert the element
            rV.set(j, 0,nextRecVecElement);
            list.set(j, nextItem);
        }
        
        //System.out.println("\n"+rV+"\n"+"Vector Sum:"+rV.zSum());
        
        return list;
	}
	
	/*
	private static void quickSortrVector(Matrix rV, ArrayList<Long> items, int first, int last)
	{
	    if (first >= last)
	        return;
	 
	    int pivot = partition(rV, items, first, last);
	    quickSortrVector(rV, items, first, pivot - 1); // sort the left part
	    quickSortrVector(rV, items, pivot + 1, last); // sort the right part
	}
	
	private static void swap(Matrix rV, ArrayList<Long> items, int a, int b){
	    double t1=rV.get(a, 0);
	    rV.set(a, 0,rV.get(b, 0));
	    rV.set(b, 0,t1);
	    
	    long t2=items.get(a);
	    items.set(a, items.get(b));
	    items.set(b, t2);
	}
	
	private static int partition(Matrix rV, ArrayList<Long> items, int first, int last)
	{
	    double mid1 = rV.get(first, 0);
	    int left = first, right = last;
	 
	    while (left < right)
	    {
	        // Find an element bigger than the pivot from the left
	        while (rV.get(left,0) >= mid1 && left < right)
	            left++;
	        // Find an element smaller than the pivot from the right
	        while (rV.get(right,0) < mid1)
	            right--;
	        // Swap the two elements found
	        if (left < right)
	            swap(rV, items, left, right);
	    }
	 
	    // move the pivot element to the middle
	    swap (rV, items, first, right);
	    return right;
	}*/

	
} // End class

