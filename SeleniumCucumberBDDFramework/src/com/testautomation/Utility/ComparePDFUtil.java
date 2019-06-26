package com.testautomation.Utility;

import java.io.IOException;

import de.redsix.pdfcompare.PdfComparator;

public class ComparePDFUtil {	
	
	@SuppressWarnings("rawtypes")
	public boolean isValidated(String expected, String actual) {

		boolean isEquals = false;
		try {
			isEquals = new PdfComparator(expected, actual).compare().writeTo("diffOutput.pdf");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!isEquals) {
			System.out.println("Differences found!");
		}
		System.out.println("isEquals - "+ isEquals);
		return isEquals;
	}
	
	public static void main(String[] args) {
		
		String expected  = "C:\\Users\\RanadipLodh\\Downloads\\Backlog Status Report_31052019123156.pdf";
		String actual  	 = "C:\\Users\\RanadipLodh\\Downloads\\Backlog Status Report_31052019105541.pdf";
		
		new ComparePDFUtil().isValidated(expected, actual);
	}
	
	
}
