package IndiaMart;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.Duration;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RunnerClass_1
{

	static	ChromeDriver driver;
	static Actions actions;
	static JavascriptExecutor js;
	static File file;
	static FileInputStream fis;
	static XSSFWorkbook workbook;
	static XSSFSheet sheet;
	static Row row;
	static StringBuilder stringBuilder = new StringBuilder() ;
	static LeadPrime_2 leadPrime;
	static WebDriverWait wait;
	static int flag =0;
	static File userCredentialsFile;
	static FileInputStream userFileInputStream;
	static Workbook userWorkbook;
	static Sheet userSheet;
	static Calendar userLoggedInTime;
	static FileOutputStream fos;
	static int lastRowNumber;
	static boolean flagToCheckUserLoginTime = false;
	static String loggedInTimeInSheet;
	static boolean flagToCheckLoginForFirstTimeOrRepeatedUser;
	static String userName;
	static String userAccountName;
	static String errormsg;
	static int loginErrorMessageFlag =0;
	
	RunnerClass_1()
	{
		/*String path = System.getProperty("user.dir");
		System.out.println(path); 
		System.setProperty(AppConfig.browserType,path+"\\chromedriver.exe");*/
		
		System.setProperty(AppConfig.browserType,AppConfig.browserPath);
		driver = new ChromeDriver();
		js = (JavascriptExecutor)driver;
		RunnerClass_1.driver.manage().window().maximize();
		actions = new Actions(driver);
		
		driver.manage().timeouts().implicitlyWait(100,TimeUnit.SECONDS);
		
		
	}
	public static void main(String[] args) throws Exception
	{
		RunnerClass_1 runnerClassObject = new RunnerClass_1();
		wait =new WebDriverWait(driver, Duration.ofSeconds(30)); 
		file= new File(AppConfig.filePath);
		fis = new FileInputStream(file);
		workbook  = new XSSFWorkbook(fis);
		sheet = workbook.getSheetAt(0);
		//IndiaMart scrapping
		if(AppConfig.LeadPrimeStatus=="No")
		{
		flagToCheckLoginForFirstTimeOrRepeatedUser = false;
		//check
		RunnerClass_1.indiaMartScrapping();
		}
		//LeadPrime Data dumping
		leadPrime = new LeadPrime_2(); 
		driver.navigate().to(AppConfig.leadPrimeURL);
		leadPrime.leadPrimeLogin();
		leadPrime.searchingExistingLeads();
		
	}
	
	public static boolean clickAnElement(WebElement element)
	{
		try
		{
			RunnerClass_1.wait.until(ExpectedConditions.elementToBeClickable(element));
			element.click();
			return true;
		}
	
		catch(Exception e) {return false;}
	}
	public static boolean sendDataToAnElement(WebElement element, String data)
	{
		try
		{
			RunnerClass_1.wait.until(ExpectedConditions.elementToBeClickable(element));
			element.sendKeys(data);
			return true;
		}
		
		catch(Exception e) {return false;}
	}
	
	static public void waitForElementLocated(By element)
	{
		try
		{
		wait.until(ExpectedConditions.elementToBeClickable(element));
		}
		catch(Exception e) {}
	}
	public static void indiaMartScrapping() throws Exception
	{
		RunnerClass_1.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		RunnerClass_1.driver.manage().timeouts().implicitlyWait(100,TimeUnit.SECONDS);
		IndiaMart.loadingURL(AppConfig.indiaMartURL);
		userCredentialsFile = new File(AppConfig.userCredentialsFilePath);
		userFileInputStream = new FileInputStream(userCredentialsFile);
		userWorkbook = new XSSFWorkbook(userFileInputStream);
		userSheet = userWorkbook.getSheetAt(0);
	    lastRowNumber = RunnerClass_1.sheet.getLastRowNum();
	    DataFormatter objDefaultFormat = new DataFormatter();
		FormulaEvaluator objFormulaEvaluator = new XSSFFormulaEvaluator((XSSFWorkbook) userWorkbook);
		for(int u=0;u<userSheet.getLastRowNum();u++)
		{
			//Get Users data from Excel
			System.out.println(userSheet.getLastRowNum());
			Row userRow = userSheet.getRow(u+1);
			Cell userStatus = userRow.getCell(5);
			try
			{
				if(userStatus.toString().equalsIgnoreCase("Active"))
				{
					Cell userNameCell = userRow.getCell(1);
		  			//String userName = userNameCell.toString();
		  			objFormulaEvaluator.evaluate(userNameCell); // This will evaluate the cell, And any type of cell will return string value
				    String userName = objDefaultFormat.formatCellValue(userNameCell,objFormulaEvaluator);
		  			System.out.println(userName);
		  			Cell userPasswordCell = userRow.getCell(2);
		  			objFormulaEvaluator.evaluate(userPasswordCell); // This will evaluate the cell, And any type of cell will return string value
				    String password = objDefaultFormat.formatCellValue(userPasswordCell,objFormulaEvaluator);
		  			System.out.println(password);
		  			//Srinija
		  			Cell userAccountNameCell = userRow.getCell(3);
		  			objFormulaEvaluator.evaluate(userAccountNameCell); // This will evaluate the cell, And any type of cell will return string value
		  			userAccountName  = objDefaultFormat.formatCellValue(userAccountNameCell,objFormulaEvaluator);
		  			System.out.println(userAccountName);
		  			//srinija
		  			//String password = userPasswordCell.toString();
		  			System.out.println(password);
		  			Cell loggedInTime_1 = userRow.getCell(4);
			    	objFormulaEvaluator.evaluate(loggedInTime_1); // This will evaluate the cell, And any type of cell will return string value
				    loggedInTimeInSheet = objDefaultFormat.formatCellValue(loggedInTime_1,objFormulaEvaluator);
				    IndiaMart.loginToIndiaMart(userName);
				    Thread.sleep(2000);
				    RunnerClass_1.wait =new WebDriverWait(driver, Duration.ofSeconds(3));
					RunnerClass_1.driver.manage().timeouts().implicitlyWait(3,TimeUnit.SECONDS);
					/*
				    if(u!=0)
				    {
				    	RunnerClass_1.clickAnElement(driver.findElement(Locators.submitWithAnotherUser));
				    	Thread.sleep(2000);
				    	try
						{
							RunnerClass_1.driver.findElement(Locators.closeMobileNumberPopup).click();
						}
						catch(NoSuchElementException e) {}
						catch(Exception e) {}
				    }
				    */
					RunnerClass_1.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
					RunnerClass_1.driver.manage().timeouts().implicitlyWait(100,TimeUnit.SECONDS);
				    IndiaMart.clickMessagesButton(password);
				    //check
				    boolean readMessagesStatus = IndiaMart.readMessages();
				    Thread.sleep(3000);
				    if(readMessagesStatus==false) continue;
				    userLoggedInTime = Calendar.getInstance();
		  			userFileInputStream.close();
		  			fos = new FileOutputStream(userCredentialsFile);
		  			Cell loggedInTime = userRow.createCell(4);
		  			loggedInTime.setCellType(CellType.STRING);
		  			loggedInTime.setCellValue(userLoggedInTime.getTime().toString());
		  			userWorkbook.write(fos);
		  			fos.close();
		  			if(userSheet.getLastRowNum()!=(u-1))
		  			{
		  			userCredentialsFile = new File(AppConfig.userCredentialsFilePath);
		  			userFileInputStream = new FileInputStream(userCredentialsFile);
		  			userWorkbook = new XSSFWorkbook(userFileInputStream);
		  			userSheet = userWorkbook.getSheetAt(0);
		  			IndiaMart.loginWithAnotherAccount();
		  			flagToCheckUserLoginTime = true;
		  			flagToCheckLoginForFirstTimeOrRepeatedUser = true;
		  			}
				}//If User is active or not check
			}
			catch(NullPointerException n)
			{
				
			}
		}
	}

}
