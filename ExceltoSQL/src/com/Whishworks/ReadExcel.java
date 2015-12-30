package com.Whishworks;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcel {

	public static String db_Name ;
	public static String table_Name;
	public static String username;
	public static String Passsword;
	public static void main(String[] args) throws IOException {
		
		String result=null;
		String excelFilePath = fileChooser();
		FileInputStream inputStream = new FileInputStream(new File(
				excelFilePath));

		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet firstSheet = workbook.getSheetAt(0);
		
		// Number of valid cells in the sheet
		int column_size =0;
		
		// to get number of cells 
		Iterator<Row> iterator1 = firstSheet.iterator();
		while (iterator1.hasNext()) {
			Row nextRow = iterator1.next();
			int temp =nextRow.getPhysicalNumberOfCells();
			if (temp>column_size){
				column_size=temp;
			}
		}
		Iterator<Row> iterator = firstSheet.iterator();
		
		String[] columns = new String[column_size] ;
		while (iterator.hasNext()) {
			Row nextRow = iterator.next();
			nextRow.getPhysicalNumberOfCells();
			Iterator<Cell> cellIterator = nextRow.cellIterator();
			int i =0;
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				//columns[i]=cell.getStringCellValue();
				//i++;
				
				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_STRING:
					columns[i] = cell.getStringCellValue();
					i++;
					break;
				case Cell.CELL_TYPE_BOOLEAN:
					boolean bool = cell.getBooleanCellValue();
					columns[i]= Boolean.toString(bool);
					i++;
					break;
				case Cell.CELL_TYPE_NUMERIC:
				double temp = cell.getNumericCellValue();
					columns[i]=String.valueOf(temp);
					i++;			
					break;
				default:  
					columns[i]=null;
					i++;			
					break;	
				}
				
			}
			
			// call the method to insert the data into DB
				result = WritetoDB.putValues(columns,db_Name,table_Name,username,Passsword);
				
				for (int j = 0; j < columns.length; j++) {
					columns[j]=null;
				}	
		}
		workbook.close();
		
		if (result=="Successfull"){
			JOptionPane.showMessageDialog(null, "Table Successfully updated !!");    
		}else {
			JOptionPane.showMessageDialog(null, result);  
		}
		inputStream.close();
	}
	
	// Choose the file to be inserted
	public static String fileChooser(){
		String filePath = null;
		JFileChooser chooser = new JFileChooser();
		 int returnValue = chooser.showOpenDialog( null ) ;
		 File file = null;
		 if( returnValue == JFileChooser.APPROVE_OPTION ) {
		        file = chooser.getSelectedFile() ;
		 }
		 if(file != null)
		 {
		      filePath = file.getPath();
		 } 
		  db_Name = JOptionPane.showInputDialog("DB Name:");
		  table_Name = JOptionPane.showInputDialog("Table Name:");
		  username = JOptionPane.showInputDialog("Username:");
		  Passsword = JOptionPane.showInputDialog("Password:");
		
		return filePath;
		
	}
}