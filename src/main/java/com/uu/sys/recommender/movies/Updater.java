package com.uu.sys.recommender.movies;

import java.util.ArrayList;

import org.apache.mahout.math.DenseMatrix;
import org.apache.mahout.math.Matrix;

import com.uu.sys.recommender.doa.StoreDao;

public class Updater 
{
	private Long userID;
	private ArrayList<Long> itemIDs;
	private int currentItemIndex;
	public double MAX_RATING = 5;
	
	/*
	 * Constructors
	 */
	
	public Updater(){}
	
	public Updater(long uId, ArrayList<Long> items, int index)
	{
		this.userID = uId;
		this.itemIDs = items;
		this.currentItemIndex = index;
	}
	
	/*
	 * Get feedback, update user side-information
	 * currItem keeps track of index of the current item in the sorted item list
	 */
	public Matrix updateVariables(Matrix W, Matrix fVector, Matrix pVector, Matrix uVector,double r)
	{	
		Matrix tempVector = new DenseMatrix(itemIDs.size(),1);
		double rating = r;
		double gamma = Math.pow(10, -1.4); //Set gamma value
		/*
		 * Update matrix W, u and database
		 */
		tempVector = fVector.minus(pVector); //feedback vector(e) - probability vector(p)
		tempVector = tempVector.times(gamma); //gamma*(e-p)
		W = W.plus(tempVector.times(uVector.transpose())); //W+gamma*(e-p)*transpose(u)
		
		StoreDao.storeUserInfo(userID,itemIDs.get(currentItemIndex),rating);
		StoreDao.storeMatrixW(W); // Store Matrix W in database before execution ends		
		//System.out.println("\nMatrix W\n========\n"+W);		
		//System.out.println("End...");
		
		return W;//Return updated W
	}
}

