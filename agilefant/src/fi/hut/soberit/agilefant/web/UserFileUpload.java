package fi.hut.soberit.agilefant.web;



import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;




import java.io.File;

import com.opensymphony.xwork2.ActionSupport;

public class UserFileUpload extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private File fileUpload;
	private String fileUploadContentType;
	private String fileUploadFileName;
	private String fullpath;
	private static String type;
	public String getFileUploadContentType() {
		return fileUploadContentType;
	}

	public void setFileUploadContentType(String fileUploadContentType) {
		this.fileUploadContentType = fileUploadContentType;
	}

	public String getFileUploadFileName() {
		return fileUploadFileName;
	}

	public void setFileUploadFileName(String fileUploadFileName) {
		this.fileUploadFileName = fileUploadFileName;
	}

	public File getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(File fileUpload) {
		this.fileUpload = fileUpload;
	}
	
	
	
	
	
	public String getFullpath() {
		return fullpath;
	}

	public void setFullpath(String fullpath) {
		this.fullpath = fullpath;
	}
	
	
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
	
	

	public String execute() throws Exception{
		System.out.println("file    "+fileUploadFileName);
		
		String newfile= fileUpload.toString();
		Vector dataHolder=read(newfile);
		System.out.println("hellooooo");
		saveToDatabase(dataHolder);
		
		
		

		return SUCCESS;

	}

	
	
	
	public static Vector read(String fileName)    {
        Vector cellVectorHolder = new Vector();
        try{
        	
        	
        	
        	System.out.println("hellooooohiiiiiiiiiiiii");
            FileInputStream myInput = new FileInputStream(fileName);
            //POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);
            XSSFWorkbook myWorkBook = new XSSFWorkbook(myInput);
            XSSFSheet mySheet = myWorkBook.getSheetAt(0);
            Iterator rowIter = mySheet.rowIterator();
            
            System.out.println(rowIter);
            
            while(rowIter.hasNext()){
                XSSFRow myRow = (XSSFRow) rowIter.next();
                
                Iterator cellIter = myRow.cellIterator();
                //Vector cellStoreVector=new Vector();
                List list = new ArrayList();
                while(cellIter.hasNext()){
                    XSSFCell myCell = (XSSFCell) cellIter.next();
                    list.add(myCell);
                }
                cellVectorHolder.addElement(list);
                
                
                System.out.println(list);
            }
        }catch (Exception e){e.printStackTrace(); }
        return cellVectorHolder;
    }
	
	
	
	
	 private static void saveToDatabase(Vector dataHolder) {
	        String id="";
	        String timestamp="";
	        String userId="";
	        String userName="";
	        
	 
	        String Bytes="";
	        System.out.println(dataHolder);
	        
	        
	        
	        if(type.equals("1")){
	        	
	        	
	        	for(Iterator iterator = dataHolder.iterator();iterator.hasNext();) {
		            List list = (List) iterator.next();
		            id = list.get(0).toString();
		            timestamp = list.get(1).toString();
		            userId = list.get(2).toString();
		            userName = list.get(3).toString();
		            
		           

		            try {
		                Class.forName("com.mysql.jdbc.Driver").newInstance();
		                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/agilefant", "root", "agilefant");
		                System.out.println("connection made...");
		                
		                PreparedStatement stmt=null;
		                Statement stmt1 = con.createStatement();
		                ResultSet rs = stmt1.executeQuery("SELECT id FROM agilefant_revisions WHERE id = '" + id + "'");
		                
		                
		              
		                System.out.println(type);
		                
		                stmt=con.prepareStatement("INSERT INTO agilefant_revisions(id, timestamp, userId, userName) VALUES(?,?,?,?)");
		                
		                stmt.setString(1, id);
		                stmt.setString(2, timestamp);
		                stmt.setString(3, userId);
		                stmt.setString(4, userName);
		                
		               
		                
		                
		                stmt.executeUpdate();

		                System.out.println("Data is inserted");
		                stmt.close();
		                con.close();
		                
		            } catch (ClassNotFoundException e) {
		                e.printStackTrace();
		            } catch (SQLException e) {
		                e.printStackTrace();
		            } catch (InstantiationException e) {
		                e.printStackTrace();
		            } catch (IllegalAccessException e) {
		                e.printStackTrace();
		            }
		        }
           	 
             
           	
           }
	        else {
	        	String t_id="";
		        String description="";
		        String name="";
		       
	        	
	        	for(Iterator iterator = dataHolder.iterator();iterator.hasNext();) {
		            List list = (List) iterator.next();
		            t_id = list.get(0).toString();
		            description = list.get(1).toString();
		            name = list.get(2).toString();
		           
		            
		           

		            try {
		                Class.forName("com.mysql.jdbc.Driver").newInstance();
		                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/agilefant", "root", "agilefant");
		                System.out.println("connection made...");
		                
		                PreparedStatement stmt=null;
		                System.out.println(type);
		                
		                stmt=con.prepareStatement("INSERT INTO teams(id, description, name) VALUES(?,?,?)");
		                
		                stmt.setString(1, t_id);
		                stmt.setString(2, description);
		                stmt.setString(3, name);
		                
		               
		                
		                
		                stmt.executeUpdate();

		                System.out.println("Data is inserted");
		                stmt.close();
		                con.close();
		            } catch (ClassNotFoundException e) {
		                e.printStackTrace();
		            } catch (SQLException e) {
		                e.printStackTrace();
		            } catch (InstantiationException e) {
		                e.printStackTrace();
		            } catch (IllegalAccessException e) {
		                e.printStackTrace();
		            }
		        }
	        	
	        	
             	
             	
             }

	        



	        }
	
	
	
	
		
	private static void alert(String string) {
		// TODO Auto-generated method stub
		
	}

	public String display() {
		return NONE;
	}



	

    
}