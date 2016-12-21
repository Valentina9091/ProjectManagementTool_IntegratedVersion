package fi.hut.soberit.agilefant.web;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import org.apache.poi.hpsf.HPSFException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

import fi.hut.soberit.agilefant.config.DBConnectivity;

@Component("advanceReportAction")
@Scope("prototype")
/**
 * 
 * @author Valentina Palghadmal
 * @since August 2016
 *
 */
public class AdvanceReportAction extends ActionSupport {
	private String myRadio;
	private InputStream inputStream;
	private String startDate;
	private String endDate;
	private String productName;
	private String projectName;
	
	
	public String initialize() {
		return Action.SUCCESS;
	}

	public String getSearchReports() throws HPSFException, SQLException {
		
		//Testing
		System.out.println("Product Name: "+getProductName());
		System.out.println("project name: "+getProjectName());
		System.out.println("start date: "+ getStartDate() +"\t End date: "+getEndDate());
		
		//TODO: filters for product and project name
		String productName=getProductName();
		String projectName=getProjectName();
		
		ArrayList financeList = getFinanceReport(getStartDate(),getEndDate(),getProductName(),getProjectName());
		exportToExcel((ArrayList)financeList.get(0), (ArrayList)financeList.get(1));	
		return Action.SUCCESS;

	}
	
	public String getAdvanceReports() throws HPSFException, SQLException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		//System.out.println(dateFormat.format(date)); 
		
		String radioSelected = getMyRadio();
		if (radioSelected.equals("weekly")) {
			String currentDate=dateFormat.format(date);
			Calendar cal = Calendar.getInstance();
			//Weekly
	        cal.add(Calendar.DATE, -7);
	        Date todate1 = cal.getTime();    
	        String pastDate = dateFormat.format(todate1);
			ArrayList financeList = getFinanceReport(pastDate.trim(),currentDate.trim(),null,null);
			exportToExcel((ArrayList)financeList.get(0), (ArrayList)financeList.get(1));	
			return Action.SUCCESS;
		} else if (radioSelected.equals("monthly")) {
			String currentDate=dateFormat.format(date);
			Calendar cal = Calendar.getInstance();
			//Monthly
	        cal.add(Calendar.MONTH, -1); // TODO: Monthly
	        Date todate1 = cal.getTime();    
	        String pastDate = dateFormat.format(todate1);
			ArrayList financeList = getFinanceReport(pastDate.trim(),currentDate.trim(),null,null);
			exportToExcel((ArrayList)financeList.get(0), (ArrayList)financeList.get(1));	
			return Action.SUCCESS;
		} else {
			String currentDate=dateFormat.format(date);
			Calendar cal = Calendar.getInstance();
			//To get Quaterly date
	        cal.add(Calendar.MONTH, -3);
	        Date todate1 = cal.getTime();    
	        String pastDate = dateFormat.format(todate1);
			ArrayList financeList = getFinanceReport(pastDate,currentDate,null,null);
			exportToExcel((ArrayList)financeList.get(0), (ArrayList)financeList.get(1));	
			return Action.SUCCESS;
		}

	}
	
	public void exportToExcel(ArrayList headers, ArrayList data) throws HPSFException {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("Finance Report");
		int rowIdx = 0;
		short cellIdx = 0;

		// Header
		HSSFRow hssfHeader = sheet.createRow(rowIdx);
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		
		for (Iterator cells = headers.iterator(); cells.hasNext();) {
			HSSFCell hssfCell = hssfHeader.createCell(cellIdx++);
			hssfCell.setCellStyle(cellStyle);
			hssfCell.setCellValue((String) cells.next());
			
		}
		// Data
		rowIdx = 1;
		for (Iterator rows = data.iterator(); rows.hasNext();) {
			ArrayList row = (ArrayList) rows.next();
			HSSFRow hssfRow = sheet.createRow(rowIdx++);
			cellIdx = 0;
			for (Iterator cells = row.iterator(); cells.hasNext();) {
				HSSFCell hssfCell = hssfRow.createCell(cellIdx++);
				hssfCell.setCellValue((String) cells.next());
			}
		}
		
		try {
			ByteArrayOutputStream outs = new ByteArrayOutputStream();
			wb.write(outs);
			setInputStream(new ByteArrayInputStream(outs.toByteArray()));
			outs.close();
		} catch (IOException e) {
			throw new HPSFException(e.getMessage());
		}
	}
	
	public ArrayList getFinanceReport(String start,String end,String productName,String projectName){
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		conn = DBConnectivity.dataBaseConnect();
		ArrayList hd = new ArrayList();
		ArrayList data = new ArrayList();
		//StringBuffer sql=new StringBuffer();
		try {
			stmt = conn.createStatement();
			if (productName != null && projectName != null) {
				if (!productName.contains("select")) {
					productName = " where pj.name = '" + productName + "' ";
					if (!projectName.contains("select"))
						projectName = " and p.name = '" + projectName + "' ";
					else {
						projectName = "";
					}
				} else {
					productName = "";
					if (!projectName.contains("select"))
						projectName = " where p.name = '" + projectName + "' ";
					else {
						projectName = "";
					}
				}
				
				
				
			}
			else if (productName != null) {
				if (!productName.contains("select"))
					productName = " where pj.name = '" + productName + "' "; 
				else {
					productName = "";
				}
			}
			else if(projectName!=null){
				if (!projectName.contains("select"))
					projectName=" where p.name = '"+projectName+"' "; 
				else {
					projectName = "";
				}
				
			}
			else{
				productName = "";
				projectName = "";
			}
			System.out.println("Before Date");
			System.out.println(start+"\t"+end);
			if(start!=null && end!=null && !start.contains(" ") && !end.contains(" ")){
				start= " where hr.date >= '"+start+"' ";
				end= " and hr.date <= '"+end+"' ";		
			}
			
			else if(start!=null && !start.contains(" ")){
				start= " where hr.date >= '"+start+"' "; 
			}
			else if(end!=null && !end.contains(" ")){
				end= " where hr.date <= '"+end+"' ";
			}
			else{
				start="";
				end="";
			}
			System.out.println("After Date");

			String sqlQuery="SELECT pj.name AS product_name,p.name AS project_name, p.startDate, p.endDate, p.cost FROM agilefant.products pj LEFT JOIN (SELECT it.cost, pr.name, pr.product_id, pr.startDate, pr.endDate FROM agilefant.projects pr LEFT JOIN (SELECT SUM(st.Cost) AS cost, i.project_id FROM agilefant.iterations i RIGHT JOIN (SELECT      (h.hours_spent * u.cost) AS Cost, h.story_id, h.iteration_id FROM agilefant.users u RIGHT JOIN (SELECT  SUM(hr.minutesSpent / 60) AS hours_spent, t.story_id,  hr.user_id, s.iteration_id FROM agilefant.hourentries hr RIGHT JOIN agilefant.tasks t ON t.id = hr.task_id RIGHT JOIN agilefant.stories s ON s.id = t.story_id " +start +end +" GROUP BY hr.user_id , s.id) h ON h.user_id = u.id) st ON i.id = st.iteration_id GROUP BY i.id) it ON it.project_id = pr.id GROUP BY pr.id) p ON p.product_id = pj.id"+productName+projectName+" ;";
			//TODO: Date function
			//String sql="SELECT a3.cost AS Cost, a3.name AS project, pd.name, a3.startDate, a3.endDate FROM (SELECT a1.cost, pr.id, pr.name, pr.product_id, pr.startDate, pr.endDate FROM     (SELECT b.cost, i.project_id FROM (SELECT a.cost, s.iteration_id FROM (SELECT SUM(h.hours_spent * u.cost) AS cost, h.story_id FROM (SELECT hr.user_id,             hr.story_id, SUM(hr.minutesSpent / 60) AS hours_spent FROM agilefant.hourentries hr GROUP BY hr.user_id , hr.story_id) h LEFT JOIN agilefant.users u ON h.user_id = u.id GROUP BY h.story_id) a RIGHT JOIN agilefant.stories s ON s.id = a.story_id GROUP BY s.iteration_id) b RIGHT JOIN agilefant.iterations i ON i.id = b.iteration_id WHERE i.startDate >= '"+start+"' AND i.endDate <= '"+end+"' GROUP BY i.project_id) a1 RIGHT JOIN agilefant.projects pr ON a1.project_id = pr.id) a3 RIGHT JOIN agilefant.products pd ON a3.product_id = pd.id;";
			//Return all the financial data
			/*if(startDate.isEmpty() && endDate.isEmpty())
				sql="SELECT a3.cost AS Cost, a3.name AS project, pd.name, a3.startDate, a3.endDate FROM (SELECT a1.cost, pr.id, pr.name, pr.product_id, pr.startDate, pr.endDate FROM     (SELECT b.cost, i.project_id FROM (SELECT a.cost, s.iteration_id FROM (SELECT SUM(h.hours_spent * u.cost) AS cost, h.story_id FROM (SELECT hr.user_id,             hr.story_id, SUM(hr.minutesSpent / 60) AS hours_spent FROM agilefant.hourentries hr GROUP BY hr.user_id , hr.story_id) h LEFT JOIN agilefant.users u ON h.user_id = u.id GROUP BY h.story_id) a RIGHT JOIN agilefant.stories s ON s.id = a.story_id GROUP BY s.iteration_id) b RIGHT JOIN agilefant.iterations i ON i.id = b.iteration_id GROUP BY i.project_id) a1 RIGHT JOIN agilefant.projects pr ON a1.project_id = pr.id) a3 RIGHT JOIN agilefant.products pd ON a3.product_id = pd.id;";
			else if(startDate.isEmpty())
				sql="SELECT a3.cost AS Cost, a3.name AS project, pd.name, a3.startDate, a3.endDate FROM (SELECT a1.cost, pr.id, pr.name, pr.product_id, pr.startDate, pr.endDate FROM     (SELECT b.cost, i.project_id FROM (SELECT a.cost, s.iteration_id FROM (SELECT SUM(h.hours_spent * u.cost) AS cost, h.story_id FROM (SELECT hr.user_id,             hr.story_id, SUM(hr.minutesSpent / 60) AS hours_spent FROM agilefant.hourentries hr GROUP BY hr.user_id , hr.story_id) h LEFT JOIN agilefant.users u ON h.user_id = u.id GROUP BY h.story_id) a RIGHT JOIN agilefant.stories s ON s.id = a.story_id GROUP BY s.iteration_id) b RIGHT JOIN agilefant.iterations i ON i.id = b.iteration_id GROUP BY i.project_id) a1 RIGHT JOIN agilefant.projects pr ON a1.project_id = pr.id) a3 RIGHT JOIN agilefant.products pd ON a3.product_id = pd.id;";
			else if(endDate.isEmpty())
				sql="SELECT a3.cost AS Cost, a3.name AS project, pd.name, a3.startDate, a3.endDate FROM (SELECT a1.cost, pr.id, pr.name, pr.product_id, pr.startDate, pr.endDate FROM     (SELECT b.cost, i.project_id FROM (SELECT a.cost, s.iteration_id FROM (SELECT SUM(h.hours_spent * u.cost) AS cost, h.story_id FROM (SELECT hr.user_id,             hr.story_id, SUM(hr.minutesSpent / 60) AS hours_spent FROM agilefant.hourentries hr GROUP BY hr.user_id , hr.story_id) h LEFT JOIN agilefant.users u ON h.user_id = u.id GROUP BY h.story_id) a RIGHT JOIN agilefant.stories s ON s.id = a.story_id GROUP BY s.iteration_id) b RIGHT JOIN agilefant.iterations i ON i.id = b.iteration_id GROUP BY i.project_id) a1 RIGHT JOIN agilefant.projects pr ON a1.project_id = pr.id) a3 RIGHT JOIN agilefant.products pd ON a3.product_id = pd.id;";
			else
				sql="SELECT a3.cost AS Cost, a3.name AS project, pd.name, a3.startDate, a3.endDate FROM (SELECT a1.cost, pr.id, pr.name, pr.product_id, pr.startDate, pr.endDate FROM     (SELECT b.cost, i.project_id FROM (SELECT a.cost, s.iteration_id FROM (SELECT SUM(h.hours_spent * u.cost) AS cost, h.story_id FROM (SELECT hr.user_id,             hr.story_id, SUM(hr.minutesSpent / 60) AS hours_spent FROM agilefant.hourentries hr GROUP BY hr.user_id , hr.story_id) h LEFT JOIN agilefant.users u ON h.user_id = u.id GROUP BY h.story_id) a RIGHT JOIN agilefant.stories s ON s.id = a.story_id GROUP BY s.iteration_id) b RIGHT JOIN agilefant.iterations i ON i.id = b.iteration_id GROUP BY i.project_id) a1 RIGHT JOIN agilefant.projects pr ON a1.project_id = pr.id) a3 RIGHT JOIN agilefant.products pd ON a3.product_id = pd.id;";
			*/
			//sql.append("SELECT pj.name AS product_name,p.name AS project_name, p.startDate, p.endDate, p.cost FROM agilefant.products pj LEFT JOIN (SELECT it.cost, pr.name, pr.product_id, pr.startDate, pr.endDate FROM agilefant.projects pr LEFT JOIN (SELECT SUM(st.Cost) AS cost, i.project_id FROM agilefant.iterations i RIGHT JOIN (SELECT      (h.hours_spent * u.cost) AS Cost, h.story_id, h.iteration_id FROM agilefant.users u RIGHT JOIN (SELECT  SUM(hr.minutesSpent / 60) AS hours_spent, t.story_id,       hr.user_id, s.iteration_id FROM agilefant.hourentries hr RIGHT JOIN agilefant.tasks t ON t.id = hr.task_id RIGHT JOIN agilefant.stories s ON s.id = t.story_id     GROUP BY hr.user_id , s.id) h ON h.user_id = u.id) st ON i.id = st.iteration_id GROUP BY i.id) it ON it.project_id = pr.id GROUP BY pr.id) p ON p.product_id = pj.id;");
			//sql.append("SELECT a3.cost AS Cost, a3.name AS project, pd.name, a3.startDate, a3.endDate FROM (SELECT a1.cost, pr.id, pr.name, pr.product_id, pr.startDate, pr.endDate FROM     (SELECT b.cost, i.project_id FROM (SELECT a.cost, s.iteration_id FROM (SELECT SUM(h.hours_spent * u.cost) AS cost, h.story_id FROM (SELECT hr.user_id,             hr.story_id, SUM(hr.minutesSpent / 60) AS hours_spent FROM agilefant.hourentries hr GROUP BY hr.user_id , hr.story_id) h LEFT JOIN agilefant.users u ON h.user_id = u.id GROUP BY h.story_id) a RIGHT JOIN agilefant.stories s ON s.id = a.story_id GROUP BY s.iteration_id) b RIGHT JOIN agilefant.iterations i ON i.id = b.iteration_id GROUP BY i.project_id) a1 RIGHT JOIN agilefant.projects pr ON a1.project_id = pr.id) a3 RIGHT JOIN agilefant.products pd ON a3.product_id = pd.id;");
			System.out.println("Query :: "+sqlQuery);
			rs = stmt.executeQuery(sqlQuery);
			while(rs.next()){
				ArrayList cells = new ArrayList();
				cells.add(rs.getString("product_name"));
				cells.add(rs.getString("project_name"));
				cells.add(rs.getString("startDate"));
				cells.add(rs.getString("endDate"));
				cells.add(rs.getString("cost"));
				data.add(cells);
			}
			ArrayList headers = new ArrayList();
			headers.add("Product");
			headers.add("Poject");
			headers.add("Start Date");
			headers.add("End Date");
			headers.add("Cost (USD)");
			//exportToExcel(headers, data);
			hd.add(headers);
			hd.add(data);
			System.out.println("Executed getFinanceReport...");
			return hd;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return hd;
	}
	public String getMyRadio() {
        return myRadio;     
    }
    public void setMyRadio(String myRadio) {
        this.myRadio = myRadio;
    }
    
    public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

}