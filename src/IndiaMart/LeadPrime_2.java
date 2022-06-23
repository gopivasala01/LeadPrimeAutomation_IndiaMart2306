package IndiaMart;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LeadPrime_2 
{
	
	static FileInputStream  areaLocationsInHYDSheet;
	static XSSFWorkbook areaLocationsworkbook ;
	static String mobileNumber=null;
    static String city="";
    static String state="";
    static String firstName ="";
    static String email = "";
    static String productName ="";
    static int checkOutOfStationInLocation=0;
    static ArrayList<String> allProductsAvailableInLeadPrime = new ArrayList<String>();
    static int productTypeIndexValue;
    static String currentuser = "";
	public void leadPrimeLogin() throws Exception
	   {
		RunnerClass_1.driver.manage().window().maximize();   
		//Thread.sleep(2000);
		RunnerClass_1.waitForElementLocated(Locators.leadprimecompanyID);
		RunnerClass_1.driver.findElement(Locators.leadprimecompanyID).sendKeys(AppConfig.leadPrimeCompanyID);
		RunnerClass_1.waitForElementLocated(Locators.leadprimeuserName);
		RunnerClass_1.driver.findElement(Locators.leadprimeuserName).sendKeys(AppConfig.leadPrimeUserNAme);
		RunnerClass_1.waitForElementLocated(Locators.leadprimepassword);
		RunnerClass_1.driver.findElement(Locators.leadprimepassword).sendKeys(AppConfig.leadPrimePassword);
		RunnerClass_1.waitForElementLocated(Locators.leadprimeloginButton);
		RunnerClass_1.driver.findElement(Locators.leadprimeloginButton).click();
		Thread.sleep(5000);
		areaLocationsInHYDSheet = new FileInputStream(AppConfig.areaLocationsSheet);
	   }
	
	public static void searchingExistingLeads() throws IOException, Exception
	{
		   
		   FileInputStream fis = new FileInputStream(AppConfig.filePath);
		   areaLocationsworkbook = new XSSFWorkbook(areaLocationsInHYDSheet);
		   XSSFWorkbook workbook = new XSSFWorkbook(fis);
		   DataFormatter objDefaultFormat = new DataFormatter();
		   FormulaEvaluator objFormulaEvaluator = new XSSFFormulaEvaluator((XSSFWorkbook) workbook);
		   XSSFSheet sheet =workbook.getSheetAt(0);
		   System.out.println(sheet.getLastRowNum());
		   //get all product categories available in the Lead Prime, then match the user product type with categories list and select it
		   LeadPrime_2.getAllExistingProductCategories();
		   System.out.println("Product size = "+allProductsAvailableInLeadPrime.size());
		   int m=0;
		   for(int i=1;i<=sheet.getLastRowNum();i++) 
		   {
			   checkOutOfStationInLocation=0;
			   mobileNumber=null;
			     city="";
			    state="";
			   Row row = sheet.getRow(i);
			   Cell status = row.getCell(6);
				   System.out.println(status.toString());
				   if(status.toString().contains("Pending"))
				   {
					   System.out.println(i);
					   //mobile number
					   Cell mobileNumberCell = row.getCell(2);
					   objFormulaEvaluator.evaluate(mobileNumberCell); // This will evaluate the cell, And any type of cell will return string value
					   mobileNumber = objDefaultFormat.formatCellValue(mobileNumberCell,objFormulaEvaluator);
					   System.out.println(mobileNumber.toString());
					   if(mobileNumber==null||mobileNumber=="") 
					   {
						   Row row1= sheet.getRow(i);
							 Cell cell1 = row1.createCell(6);
							 cell1.setCellValue("No Number");
						   continue;
					   }
					   //city and state
					   Cell cityAndState = row.getCell(3);
					   Cell productTypeToTestIfTheMessageHasLocation = row.getCell(5);
					   int wordsCountInMessage = productTypeToTestIfTheMessageHasLocation.toString().split(" ").length;
					   LeadPrime_2.getCityAndState(cityAndState.toString(),wordsCountInMessage, productTypeToTestIfTheMessageHasLocation.toString());
					   if(checkOutOfStationInLocation==2)
					   {
						   System.out.println("In Station");
						   //LeadPrime.assignedUsers(AppConfig.usersInLeadPrime[m]);
					   }
					   else 
						   {
						   System.out.println("Out of Station");
						  // LeadPrime.assignedUsers(AppConfig.assignedUserForOutOfHYD);
						   }
					   //first name
						 try
						 {
						 Cell firstName1 = row.getCell(1);
						 firstName = firstName1.toString();
						 Pattern pt = Pattern.compile("[^a-zA-Z ]");
					        Matcher match= pt.matcher(firstName);
					        while(match.find())
					        {
					            String s= match.group();
					            firstName=firstName.replaceAll("\\"+s, "");
					        }
						 System.out.println(firstName.toString());
						 }
						 catch(Exception e)
						 {
						 firstName = "NA";
						 System.out.println(firstName.toString());
						 }
						 
					    // Email
						 email = mobileNumber+"@buildersmart.in";
						 System.out.println(email);			 
						 //Product Type
						 Cell productType = row.getCell(5);
						 int productIndexValue = LeadPrime_2.getProductType(productType.toString());
						 System.out.println("Product Name and Index = "+allProductsAvailableInLeadPrime.get(productTypeIndexValue)+" "+productTypeIndexValue);
						 
						 //Enter the mobile number in search box and search for the lead
						 RunnerClass_1.waitForElementLocated(Locators.searchTextbox);
						 RunnerClass_1.driver.findElement(Locators.searchTextbox).clear();
						 RunnerClass_1.driver.findElement(Locators.searchTextbox).sendKeys(mobileNumber.toString());  //mobileNumber.toString()
						 RunnerClass_1.waitForElementLocated(Locators.searchButton);
						 RunnerClass_1.driver.findElement(Locators.searchButton).click();
						 //Thread.sleep(2000);
						 RunnerClass_1.wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.teamLeadsCount));
						 String teamLeadsCount = RunnerClass_1.driver.findElement(Locators.teamLeadsCount).getText();
						 int teamLeadCountInNumber = LeadPrime_2.getNumberFromAString(teamLeadsCount);
						 System.out.println("Number from team lead count "+teamLeadCountInNumber);
						 
						 //if team lead count is Zero, create a new lead
						 if(teamLeadCountInNumber>0)
						 {
						   LeadPrime_2.selectingExistingLead();	 
						 }
						 else 
						 {
							 RunnerClass_1.waitForElementLocated(Locators.leadsLink);
							 RunnerClass_1.driver.findElement(Locators.leadsLink).click();
							 //Thread.sleep(1000);
							 RunnerClass_1.waitForElementLocated(Locators.createLeadLink);
							 RunnerClass_1.driver.findElement(Locators.createLeadLink).click();
						 }
						 if(teamLeadCountInNumber==0)
						 {
							 RunnerClass_1.waitForElementLocated(Locators.leadFirstName);
							 RunnerClass_1.driver.findElement(Locators.leadFirstName).sendKeys(firstName.toString());
							 RunnerClass_1.waitForElementLocated(Locators.leadMobileNumber);
							 RunnerClass_1.driver.findElement(Locators.leadMobileNumber).sendKeys(mobileNumber.toString());
							 RunnerClass_1.waitForElementLocated(Locators.leadEmail);
							 RunnerClass_1.driver.findElement(Locators.leadEmail).sendKeys(email);
						 }
							 RunnerClass_1.waitForElementLocated(Locators.leadCity);
							 RunnerClass_1.driver.findElement(Locators.leadCity).sendKeys(city);
							 RunnerClass_1.waitForElementLocated(Locators.leadState);
							 RunnerClass_1.driver.findElement(Locators.leadState).sendKeys(state);
							 if(checkOutOfStationInLocation==2)
							   {
								   //System.out.println("In Station");
								 //Srinija
								 	currentuser = AppConfig.usersInLeadPrime[m];
								   LeadPrime.assignedUsers(AppConfig.usersInLeadPrime[m]);
							   }
							   else 
							   {
								   //System.out.println("Out of Station");
								   currentuser = AppConfig.assignedUserForOutOfHYD;
								  LeadPrime.assignedUsers(AppConfig.assignedUserForOutOfHYD);
							   }
							 RunnerClass_1.waitForElementLocated(Locators.requirementsTab);
							 RunnerClass_1.driver.findElement(Locators.requirementsTab).click();
							 if(teamLeadCountInNumber>0)
							 {
								 LeadPrime_2.disableAlreadySelectedProductTypeForExistingLead();
							 }
							 Thread.sleep(3000);
							 RunnerClass_1.wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.productCategory));
							 List<WebElement> products = RunnerClass_1.driver.findElements(Locators.productCategory);
							 if(productIndexValue==0)
							 {
								 RunnerClass_1.waitForElementLocated(Locators.productDefaultCategory_others);
								 RunnerClass_1.driver.findElement(Locators.productDefaultCategory_others).click();
							 }
							 else
							 {
							 products.get(productTypeIndexValue).click();
							 }
							 RunnerClass_1.waitForElementLocated(Locators.productBrand);
							 if(teamLeadCountInNumber==0)
							     RunnerClass_1.driver.findElement(Locators.productBrand).sendKeys(productType.toString());
							 else
								 RunnerClass_1.driver.findElement(Locators.productBrand).sendKeys("\n\n"+productType.toString());
							 Thread.sleep(2000);
							 if(teamLeadCountInNumber==0)
							 {
							 RunnerClass_1.waitForElementLocated(Locators.customFieldAttributesTab);
							 RunnerClass_1.driver.findElement(Locators.customFieldAttributesTab).click();
							 Thread.sleep(2000);
							 
							 //RunnerClass_1.js.executeScript("document.getElementByXpath('(//*[@class='slimScrollDiv'])[2]/div[2]').scrollTop += 250", "");
							 
							 RunnerClass_1.actions.moveToElement(RunnerClass_1.driver.findElement(By.xpath("(//*[@class='custom_lead_attributes'])[7]"))).build().perform();
							 Thread.sleep(1000);
							 RunnerClass_1.actions.dragAndDropBy(RunnerClass_1.driver.findElement(Locators.scrollBarInCustomAttributesTab), 0, 100).build().perform();;
							// RunnerClass_1.js.executeScript("arguments[0].scrollIntoView(true)", RunnerClass_1.driver.findElement(Locators.customFieldAttributesTab));
							 List<WebElement> leadSourceList = RunnerClass_1.driver.findElements(By.xpath("(//*[@class='custom_lead_attr_area'])[8]/div/table/tbody/tr/td/label"));
							 for(int l=0;l<leadSourceList.size();l++)
							 {
								 String leadSourceName = leadSourceList.get(l).getText();
								 if(leadSourceName.contains("IndiaMart"))
								 {
									 leadSourceList.get(l).click();
									 break;
								 }
							 }
							 }
							 Thread.sleep(3000);
							 //srinija changed
							 //RunnerClass_1.driver.findElement(Locators.leadSaveButton).click();
							 RunnerClass_1.wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.leadSaveButton));
							 RunnerClass_1.driver.findElement(Locators.leadSaveButton).click();
							 //Row row1= sheet.getRow(i);
							 Cell cell1 = row.createCell(6);
							 cell1.setCellValue("Completed");
							 
							 //Srinija start
							  DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");  
							  LocalDateTime now = LocalDateTime.now();  
							  System.out.println(dtf.format(now));
							  
							  Cell LeadAssignedDate = row.createCell(7);
							  LeadAssignedDate.setCellValue(dtf.format(now));
							 
							  Cell leadName = row.createCell(8);
							  leadName.setCellValue(currentuser);
							  
								 //srinija end

							 if(m==AppConfig.usersInLeadPrime.length-1)
								 m=0;
							 else 
							     m++;
						 RunnerClass_1.driver.navigate().refresh();
					   //break;
						 FileOutputStream outputStream = new FileOutputStream(AppConfig.filePath);
						   workbook.write(outputStream);
				   }
		   }
		  
	}
	public static boolean getCityAndState(String cityAndState, int wordsCountInMessage , String productTypeToTestIfTheMessageHasLocation) throws IOException
	{
		boolean tempFlag =false;
		
		   DataFormatter objDefaultFormat2 = new DataFormatter();
		   FormulaEvaluator objFormulaEvaluator2 = new XSSFFormulaEvaluator((XSSFWorkbook) areaLocationsworkbook);
		   XSSFSheet areaLocationssheet =areaLocationsworkbook.getSheetAt(0);
		   try
		   {
		   if(cityAndState.toString().contains("No Location"))
			 {
				 city = "";
				 state = "";
				 checkOutOfStationInLocation =2;
				 return true;
			 }
		  
		   city = cityAndState.toString().split(",")[0].trim();
		   state = cityAndState.toString().split(",")[1].trim();
		   if(city.contains("Hyderabad")&&state.contains("Telangana"))
		   {
			   checkOutOfStationInLocation=2;
			   return false;
		   }
		   }
		   catch(Exception e)
		   {
			   city = "";
			   state = "";
			   checkOutOfStationInLocation =2;
			   return true;
		   }
		 
		   //if(cityAndState.toString().contains(("Hyderabad"))||cityAndState.toString().contains("Secunderabad")||cityAndState.toString().contains("|"))
			// {
			   
			   int lastRowNumber = areaLocationssheet.getLastRowNum();
			   for(int i=0;i<lastRowNumber;i++)
			   {
				   Row row = areaLocationssheet.getRow(i);
				   Cell cell = row.getCell(0);
				   if(cityAndState.toString().contains(cell.toString()))
                   {
					   checkOutOfStationInLocation =2;
					  
					   return false;
                   }
				   
			   }
			   
			   for(int m1=0;m1<wordsCountInMessage;m1++)
			   {
				   String word1 = productTypeToTestIfTheMessageHasLocation.toString().split(" ")[m1].trim();
				   for(int m2=0;m2<areaLocationssheet.getLastRowNum();m2++)
				   {
					   Row areaLocationRow = areaLocationssheet.getRow(m2);
					   Cell areaLocationCell = areaLocationRow.getCell(0);
					   String area = areaLocationCell.toString();
					   if(area.contains(word1))
					   {
						   tempFlag = true;
						   checkOutOfStationInLocation =2;
						   break;
					   }
				   }
				   if(tempFlag==true) return false;
			   }
			// }
		   //else checkOutOfStationInLocation =1;
		   
		   return false;
		   
	}
	
	public static int getProductType(String productType)
	{
		int flag=0;
		if(productType.trim().split(" ").length>1)
		{
		   int productTypeWordCount = productType.split(" ").length;
		   for(int i=0;i<productTypeWordCount;i++)
		   {
			   String productNameInTheArrayFromIndiaMart = productType.split(" ")[i].trim();
			   if(productNameInTheArrayFromIndiaMart.equalsIgnoreCase("I")||productNameInTheArrayFromIndiaMart.equalsIgnoreCase("am")||productNameInTheArrayFromIndiaMart.equalsIgnoreCase("Interested")||productNameInTheArrayFromIndiaMart.equalsIgnoreCase("to")||productNameInTheArrayFromIndiaMart.equalsIgnoreCase("is")||productNameInTheArrayFromIndiaMart.equalsIgnoreCase("on")||productNameInTheArrayFromIndiaMart.equalsIgnoreCase("for")||productNameInTheArrayFromIndiaMart.equalsIgnoreCase("in"))
			   {
				   continue;
			   }
			   for(int j=0;j<allProductsAvailableInLeadPrime.size();j++)
			   {
				   
				   String productNameFromTheListOfLeadPrime = allProductsAvailableInLeadPrime.get(j);
				   if(allProductsAvailableInLeadPrime.get(j).toLowerCase().contains(productType.split(" ")[i].trim().toLowerCase()))
				   {
					   productTypeIndexValue = j;
					   System.out.println("Index of the product category multiple= "+productTypeIndexValue);
					   flag=1;
					   return 1;
				   }
			   }
			   if(flag==1) break;
		   }
		}
		else 
		{
			for(int i=0;i<allProductsAvailableInLeadPrime.size();i++)
			{
				if(productType.contains(allProductsAvailableInLeadPrime.get(i)))
				{
					productTypeIndexValue = i;
					System.out.println("Index of the product category = "+productTypeIndexValue);
					return 1;
				}
			}
		}
	    
		return 0;
	}
	
	public static void getAllExistingProductCategories()
	{
		 RunnerClass_1.waitForElementLocated(Locators.leadsLink);
		 RunnerClass_1.driver.findElement(Locators.leadsLink).click();
		 //Thread.sleep(1000);
		 RunnerClass_1.waitForElementLocated(Locators.createLeadLink);
		 RunnerClass_1.driver.findElement(Locators.createLeadLink).click();
		 RunnerClass_1.waitForElementLocated(Locators.requirementsTab);
		 RunnerClass_1.driver.findElement(Locators.requirementsTab).click();
		 RunnerClass_1.wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.productCategory));
	     List<WebElement> products = RunnerClass_1.driver.findElements(Locators.productCategory);
	     
	     for(int i=0;i<products.size();i++ )
	     {
	    	 String productName = products.get(i).getText();
	    	 allProductsAvailableInLeadPrime.add(productName);
	     }
	}
	public static void disableAlreadySelectedProductTypeForExistingLead() throws Exception
	{
		Thread.sleep(3000);
		RunnerClass_1.wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.productCategoryCheckbox));
	    List<WebElement> products = RunnerClass_1.driver.findElements(Locators.productCategoryCheckbox);
	    for(int i=0;i<products.size();i++)
	    {
	    	String pName = products.get(i).getText();
	    	if(products.get(i).isSelected())
	    	{
	    		products.get(i).click();
	    	}
	    }
	}
	public static int getNumberFromAString(String teamLeadCount)
	{
		Pattern pattern = Pattern.compile("[^0-9]");
		String numberOnly = pattern.matcher(teamLeadCount).replaceAll("");
		int teamLeadCountInInteger = Integer.parseInt(numberOnly);
		return teamLeadCountInInteger;
	}
	public static void selectingExistingLead() throws Exception
	{
		RunnerClass_1.waitForElementLocated(Locators.teamLeadsCount);
		   RunnerClass_1.driver.findElement(Locators.teamLeadsCount).click();
		   
		   //RunnerClass_1.wait.until(ExpectedConditions.visibilityOfAllElements(RunnerClass_1.driver.findElements(Locators.existingLeadsCount)));
		   Thread.sleep(3000);
		   List<WebElement> totalExistingLeads = RunnerClass_1.driver.findElements(Locators.existingLeadsCount);
		   System.out.println(totalExistingLeads.size());
		   List<String> dates = new ArrayList<String>();
		   int j;
		   if(totalExistingLeads.size()>1)
		   {
			   totalExistingLeads.get(0).click();
			   totalExistingLeads = RunnerClass_1.driver.findElements(Locators.existingLeadsCount);
		       //Thread.sleep(3000);
			   for(j=0;j<totalExistingLeads.size();j++)
			   {
				   //Thread.sleep(2000);
				   RunnerClass_1.wait.until(ExpectedConditions.elementToBeClickable(totalExistingLeads.get(j)));
				   totalExistingLeads.get(j).click();
				   //Thread.sleep(2000);
				   RunnerClass_1.wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(Locators.date));
				   String lastContactedDate = RunnerClass_1.driver.findElement(Locators.date).getText();
			       dates.add(lastContactedDate.toString());
			       Date d1 = new Date(lastContactedDate.toString());	
			       totalExistingLeads = RunnerClass_1.driver.findElements(Locators.existingLeadsCount);
			       
			   }
			   Date maxDate = LeadPrime.getMaxDate(dates);
			   String maxDate2 = maxDate.toString().replace(" 00:00:00 IST ", "");
			   int dateFlag = 0;
			   for(j=0;j<totalExistingLeads.size();j++)
			   {
				   
				   String lastContactedDate = RunnerClass_1.driver.findElement(Locators.date).getText();
				   if(lastContactedDate.contains(maxDate2));
				   {
				   dateFlag = j+1;
				   break;
				   }
			   }
			   
			   Thread.sleep(2000);
			   totalExistingLeads = RunnerClass_1.driver.findElements(Locators.existingLeadsCount);
			   RunnerClass_1.driver.findElement(By.xpath("//*[@class='leadname']/descendant::a["+dateFlag+"]")).click();;
			   //Thread.sleep(2000);
			   RunnerClass_1.waitForElementLocated(Locators.editLeadLink);
			   RunnerClass_1.driver.findElement(Locators.editLeadLink).click();
	        }
		   else 
		   {
		     Thread.sleep(2000);
		     RunnerClass_1.waitForElementLocated(Locators.viewDetailsButton);
		     RunnerClass_1.driver.findElement(Locators.viewDetailsButton).click();
		     RunnerClass_1.waitForElementLocated(Locators.editLeadLink);
		     RunnerClass_1.driver.findElement(Locators.editLeadLink).click();
		     Thread.sleep(2000);
	       }

        }
}
