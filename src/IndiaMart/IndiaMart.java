package IndiaMart;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.Keys;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class IndiaMart
{
	static int lastLoggedInDay;
	static int lastLoggedInTimeInHours;
	static int lastLoggedInTimeinMinutes;
	static int dayInMessage;
	static int timeInHoursInMessage_2;
	static int timeInMinutesInMessage_2;
	//add
	static int timeInSecondsInMessage_2;
	static String amOrPmInMessage;
	static int flagToCheckAfternoon;
	static int flagToCheckAfternoonInMinutesVariance;
	//add
	static int flagToCheckAfternoonInSecondsVariance;
	static int monthInLoggedInTime;
	static int monthInMessage;
	static int timeInMinutesInLastLoggedInTime;
	static int messagesTime;
	static int lastRowNumberInLeadsSheet;

	//public static int lastRowNumber = RunnerClass_1.sheet.getLastRowNum();
	public static boolean loadingURL(String URL)
	{
		RunnerClass_1.driver.get(URL);
		RunnerClass_1.driver.manage().window().maximize();
		return true;
		
	}
	public static boolean loginToIndiaMart(String userName) throws Exception
	{
		RunnerClass_1.wait = new WebDriverWait(RunnerClass_1.driver, Duration.ofSeconds(3));
		RunnerClass_1.driver.manage().timeouts().implicitlyWait(3,TimeUnit.SECONDS);
		Thread.sleep(4000);
		RunnerClass_1.sendDataToAnElement(RunnerClass_1.driver.findElement(Locators.userNameLogin), userName);
		//if(RunnerClass_1.flagToCheckUserLoginTime==false)
		if(RunnerClass_1.flagToCheckLoginForFirstTimeOrRepeatedUser==true)
		RunnerClass_1.clickAnElement(RunnerClass_1.driver.findElement(Locators.submitBUttonForLoginWIithAnotherUser));
		else
		RunnerClass_1.clickAnElement(RunnerClass_1.driver.findElement(Locators.loginButton));
		try
		{
			if(RunnerClass_1.driver.findElement(By.id("form_otp1")).isDisplayed())
			{
				RunnerClass_1.actions.sendKeys(Keys.ESCAPE).build().perform();
				RunnerClass_1.driver.navigate().refresh();
			}
		}
		catch (Exception e2)
		{
			
		}
		return true;
	}
	public static boolean clickMessagesButton(String password) throws Exception
	{
		RunnerClass_1.wait = new WebDriverWait(RunnerClass_1.driver, Duration.ofSeconds(2));
		RunnerClass_1.driver.manage().timeouts().implicitlyWait(2,TimeUnit.SECONDS);
		RunnerClass_1.loginErrorMessageFlag =0;
		Thread.sleep(2000);
		RunnerClass_1.clickAnElement(RunnerClass_1.driver.findElement(Locators.messagesButton));
		Thread.sleep(2000);
		RunnerClass_1.clickAnElement(RunnerClass_1.driver.findElement(Locators.loginWithPassword));
		RunnerClass_1.sendDataToAnElement(RunnerClass_1.driver.findElement(Locators.loginPassword), password);
		Thread.sleep(1000);
		RunnerClass_1.clickAnElement(RunnerClass_1.driver.findElement(Locators.loginButton2));
		Thread.sleep(4000);
		RunnerClass_1.wait = new WebDriverWait(RunnerClass_1.driver, Duration.ofSeconds(2));
		RunnerClass_1.driver.manage().timeouts().implicitlyWait(2,TimeUnit.SECONDS);
		//srinija start
		try
    	{
    		if(RunnerClass_1.driver.findElement(Locators.loginErrorMessage).isDisplayed())
    		{
    			RunnerClass_1.actions.sendKeys(Keys.ESCAPE).build().perform();
    			//RunnerClass_1.driver.navigate().refresh();
			    RunnerClass_1.loginErrorMessageFlag =1;
    			IndiaMart.loginWithAnotherAccount();
    		}
    	}
    	catch(Exception e2)
    	{
    		
    	}
		//srinija end
		try
		{
		RunnerClass_1.clickAnElement(RunnerClass_1.driver.findElement(Locators.modelPopUp25Years));
		}
		catch(Exception e) {}
		RunnerClass_1.wait = new WebDriverWait(RunnerClass_1.driver, Duration.ofSeconds(30));
		RunnerClass_1.driver.manage().timeouts().implicitlyWait(50,TimeUnit.SECONDS);
		return true;
	}
	public static boolean readMessages() throws Exception
	{
		/*
		try 
		{
		RunnerClass_1.wait.until(ExpectedConditions.invisibilityOfElementLocated(Locators.loadingSpinnerInMessagesScreen));
		}
		catch(Exception e) 
		{
			RunnerClass_1.driver.navigate().refresh();
		}*/
		RunnerClass_1.wait = new WebDriverWait(RunnerClass_1.driver, Duration.ofSeconds(2));
		RunnerClass_1.driver.manage().timeouts().implicitlyWait(2,TimeUnit.SECONDS);
		Thread.sleep(8000);
		lastRowNumberInLeadsSheet = RunnerClass_1.sheet.getLastRowNum();
		System.out.println("Last row number ="+lastRowNumberInLeadsSheet);
		List<WebElement> unreadMessages ;
		try
		{
		unreadMessages = RunnerClass_1.driver.findElements(Locators.allMessagesList);
		System.out.println(unreadMessages.size());
		}
		catch(Exception e)
		{
			return false;
		}
		if(unreadMessages.size()==1) return true;
		for(int i=1;i<=unreadMessages.size();i++)
		{
			flagToCheckAfternoon=0;
			flagToCheckAfternoonInMinutesVariance =0;
			amOrPmInMessage=null;
			timeInHoursInMessage_2=0;
			timeInMinutesInMessage_2=0;
			//
			timeInSecondsInMessage_2 = 0;
			flagToCheckAfternoonInSecondsVariance =0;
			//check
			//Srinija
			try
			{
				if(RunnerClass_1.driver.findElement(Locators.beAwareOfUserElement).isDisplayed())
				{
					RunnerClass_1.clickAnElement(RunnerClass_1.driver.findElement(Locators.beAwareOfUserElement2));
				}

			}
			catch(Exception e2)
			{
				
			}
			

			//Srinija
			RunnerClass_1.actions.moveToElement(unreadMessages.get(i)).build().perform();
			unreadMessages = RunnerClass_1.driver.findElements(Locators.allMessagesList);
			RunnerClass_1.clickAnElement(unreadMessages.get(i-1));
			Thread.sleep(4000);
			Calendar cal;
			try
			{
				Thread.sleep(1000);
				List<WebElement> messagesTime = RunnerClass_1.driver.findElements(By.xpath("//*[@class='user-message-send ']/div[2]"));
				System.out.println("All messages count ="+messagesTime.size());
				String messageTimeAndDate = RunnerClass_1.driver.findElement(By.xpath("(//*[@class='user-message-send ']/div[2])["+messagesTime.size()+"]")).getText();
			//String messageTimeAndDate = RunnerClass_1.driver.findElement(Locators.firstMessageTime).getText();
				System.out.println(messageTimeAndDate);
				cal  = IndiaMart.validatingMessageTime(messageTimeAndDate);
			}
			catch(Exception e)
			{
				try
				{
					Thread.sleep(2000);
					List<WebElement> messagesTime = RunnerClass_1.driver.findElements(By.xpath("//*[@class='message-send ']/div[2]"));
					System.out.println("All messages count ="+messagesTime.size());
					String messageTimeAndDate = RunnerClass_1.driver.findElement(By.xpath("(//*[@class='message-send ']/div[2])["+messagesTime.size()+"]")).getText();
					System.out.println(messageTimeAndDate);
					cal  = IndiaMart.validatingMessageTime(messageTimeAndDate);
				}
				catch(Exception e2)
				{
					Thread.sleep(4000);
					List<WebElement> messagesTime = RunnerClass_1.driver.findElements(By.xpath("//*[@class='rem-thread-time']"));
					System.out.println("All messages count ="+messagesTime.size());
					String messageTimeAndDate = RunnerClass_1.driver.findElement(By.xpath("(//*[@class='rem-thread-time'])["+messagesTime.size()+"]")).getText();
					System.out.println(messageTimeAndDate);
					cal  = IndiaMart.validatingMessageTime(messageTimeAndDate);
				}
			}
			
				//commented by srinija
			/*try
			{
			Thread.sleep(2000);
			String messageTimeAndDate = RunnerClass_1.driver.findElement(Locators.messageTimeReceived).getText();
			System.out.println(messageTimeAndDate);
			cal  = IndiaMart.validatingMessageTime(messageTimeAndDate);
			}
			catch(Exception e2)
			{
				Thread.sleep(2000);
				String messageTimeAndDate = RunnerClass_1.driver.findElement(Locators.messageTimeReceived2).getText();
				System.out.println(messageTimeAndDate);
				cal  = IndiaMart.validatingMessageTime(messageTimeAndDate);
			}*/
		
		
				
			//	finally
				//{
			//		continue;
			//	}
			
			RunnerClass_1.wait = new WebDriverWait(RunnerClass_1.driver, Duration.ofSeconds(30));
			RunnerClass_1.driver.manage().timeouts().implicitlyWait(100,TimeUnit.SECONDS);
			//Calendar cal = DatesComparator.gettingDate(date);
			Calendar currentDate = IndiaMart.convertingLastLoggedInDate(RunnerClass_1.loggedInTimeInSheet);
			
			//currentDate.setTime(RunnerClass_1.loggedInTimeInSheet);
			//Calendar currentDate = IndiaMart.validatingMessageTime(RunnerClass_1.loggedInTimeInSheet);
			System.out.println("Last logged in Time "+currentDate.getTime());
			System.out.println(cal.compareTo(currentDate));
			
			/*if(cal.compareTo(currentDate)<0)
			{
				System.out.println("UI date is latest");
				IndiaMart.gettingLeadAndProductDetails(i, unreadMessages.get(i-1));
				
			}
			else */
			
			System.out.println("Last Logged In Month ="+monthInLoggedInTime);
			System.out.println("Month in message ="+monthInMessage);
			if(monthInMessage>monthInLoggedInTime)
			{
				System.out.println("UI date is latest");
				IndiaMart.gettingLeadAndProductDetails(i,unreadMessages.get(i-1));
			}
			else 
			{
				if(monthInMessage==monthInLoggedInTime)
                {
			       if(dayInMessage>lastLoggedInDay)
			       {
				     System.out.println("UI date is latest");
				     IndiaMart.gettingLeadAndProductDetails(i, unreadMessages.get(i-1));
			       }
			       else 
			      {
			    	 if(dayInMessage==lastLoggedInDay)
				     {
					    if(flagToCheckAfternoon>0&&amOrPmInMessage.contains("pm"))
					    {
					       if(timeInHoursInMessage_2>flagToCheckAfternoon)	
					       {
						   System.out.println("UI date is latest");
							IndiaMart.gettingLeadAndProductDetails(i, unreadMessages.get(i-1));
					       }
					       else 
					       {
					    	   if(timeInHoursInMessage_2==flagToCheckAfternoon)
					    	   {
					    		   if(timeInMinutesInMessage_2>timeInMinutesInLastLoggedInTime)
					    		   {
					    			   System.out.println("UI date is latest");
										IndiaMart.gettingLeadAndProductDetails(i, unreadMessages.get(i-1));
					    		   }
					    		   else return true;
					    	   }
					    	   else 
					    	   {
					    		   if(timeInHoursInMessage_2==12&&flagToCheckAfternoon==1000)
						    	   {
						    		   if(timeInMinutesInMessage_2>=timeInMinutesInLastLoggedInTime)
						    		   {
						    			   System.out.println("UI date is latest");
											IndiaMart.gettingLeadAndProductDetails(i, unreadMessages.get(i-1));
						    		   }
						    		  else return true;
						    	   }
					    		   
					    	   }
					    	  // else return true;
					        }
					    }
					    else
					    {
					       if(timeInHoursInMessage_2==flagToCheckAfternoon)
					       {
						   if(timeInMinutesInMessage_2>flagToCheckAfternoonInMinutesVariance)
						   {
							   System.out.println("UI date is latest");
							   IndiaMart.gettingLeadAndProductDetails(i,unreadMessages.get(i-1));
							   
						   }
						   
					       }
					       else return true;
					     //seconds
							if(timeInSecondsInMessage_2==flagToCheckAfternoon)
							{
								if(timeInSecondsInMessage_2>flagToCheckAfternoonInSecondsVariance)
								{
									   System.out.println("UI date is latest");
									   IndiaMart.gettingLeadAndProductDetails(i,unreadMessages.get(i-1));
									   
								   }
							}
					    }
					  }
					 else return true;
				  }
                }
				else return true;
               
			}
			//else return true;
				
				//return true;
	   
		}
		return true;

	}
	public static Calendar validatingMessageTime(String date)
	{

		String monthIntegerValue=null;
		String[] splittingTime = date.split(",")[0].trim().split(" ");
		String month = splittingTime[0].trim();
		System.out.println(splittingTime[0].trim());
		switch(splittingTime[0].trim())
		{
		case "Jan":
			  monthIntegerValue ="1";
			  break;
		case "Feb":
			  monthIntegerValue ="2";
			  break;
		case "Mar":
			  monthIntegerValue ="3";
			  break;
		case "Apr":
			  monthIntegerValue ="4";
			  break;
		case "May":
			  monthIntegerValue ="5";
			  break;
		case "Jun":
			  monthIntegerValue ="6";
			  break;
		case "Jul":
			  monthIntegerValue ="7";
			  break;
		case "Aug":
			  monthIntegerValue ="8";
			  break;
		case "Sep":
			  monthIntegerValue ="9";
			  break;
		case "Oct":
			  monthIntegerValue ="10";
			  break;
		case "Nov":
			  monthIntegerValue ="11";
			  break;
		case "Dec":
			  monthIntegerValue ="12";
			  break;
		default:
			Date d = new Date();
			int tempDate = d.getMonth()+1;
			 monthIntegerValue= String.valueOf(tempDate);
			 
				
			
		}
		System.out.println(monthIntegerValue);
		String dateInMessage = date.split(",")[0].trim().split(" ")[1].trim();
		dayInMessage = Integer.parseInt(dateInMessage);
		System.out.println("Date from message "+dateInMessage);
		String amOrPMInMessage = date.split(",")[1].trim().split(":")[1].trim().replaceAll("([0-9]+)","").trim();
		System.out.println("AM or PM "+amOrPMInMessage);
		amOrPmInMessage = amOrPMInMessage;
		String timeInHoursInMessage = date.split(",")[1].trim().split(":")[0].trim();
		System.out.println("Time hours in Message is "+timeInHoursInMessage);
		timeInHoursInMessage_2 = Integer.parseInt(timeInHoursInMessage);
		String timeInMinutesInMessage = date.split(",")[1].trim().split(":")[1].trim().replaceAll("([a-z]+)","").trim();
		System.out.println("Time Minutes in Message is "+timeInMinutesInMessage);
		timeInMinutesInMessage_2  = Integer.parseInt(timeInMinutesInMessage);
		//String combinedDate = "2021".concat("/"+monthIntegerValue).concat("/"+dateInMessage).concat(" "+timeInHoursInMessage).concat(":"+timeInMinutesInMessage).concat(" "+amOrPMInMessage);
		String combinedDate = month.concat(" "+dateInMessage).concat(" "+timeInHoursInMessage).concat(":"+timeInMinutesInMessage).concat(":"+"00").concat(" IST ").concat("2021");
		System.out.println("Combined date ="+combinedDate);
		Calendar c = Calendar.getInstance();
		monthInMessage = Integer.parseInt(monthIntegerValue);
		c.set(2021,Integer.parseInt(monthIntegerValue)-1, Integer.parseInt(dateInMessage), Integer.parseInt(timeInHoursInMessage), Integer.parseInt(timeInMinutesInMessage));
		System.out.println(c.getTime());
    	return c;
	}
	public static Calendar convertingLastLoggedInDate(String lastLoggedInTime)
	{//Sat Apr 23 13:20:13 IST 2021
		String[] splittingDate = lastLoggedInTime.split(" ");
		String monthIntegerValue = splittingDate[1];
		int day = Integer.parseInt(splittingDate[2].trim());
		lastLoggedInDay = day;
		String[] splitTime = splittingDate[3].split(":");
		int timeInHours = Integer.parseInt(splitTime[0].trim());
		lastLoggedInTimeInHours = timeInHours;
		if(timeInHours>12)
		{
			flagToCheckAfternoon = timeInHours-12;
		}
		if(timeInHours==12)
		{
			flagToCheckAfternoon = 1000;
		}
		System.out.println("Variation hours = "+flagToCheckAfternoon);
		int timeInMinutes = Integer.parseInt(splitTime[1].trim());
		timeInMinutesInLastLoggedInTime = timeInMinutes;
		if(timeInHours==12)
		{
			flagToCheckAfternoonInMinutesVariance = timeInMinutes;
		}
		lastLoggedInTimeinMinutes = timeInMinutes;
		int timeInSeconds = Integer.parseInt(splitTime[2].trim());
		int year = Integer.parseInt(splittingDate[5].trim());
		switch(monthIntegerValue.trim())
		{
		case "Jan":
			  monthIntegerValue ="1";
			  break;
		case "Feb":
			  monthIntegerValue ="2";
			  break;
		case "Mar":
			  monthIntegerValue ="3";
			  break;
		case "Apr":
			  monthIntegerValue ="4";
			  break;
		case "May":
			  monthIntegerValue ="5";
			  break;
		case "Jun":
			  monthIntegerValue ="6";
			  break;
		case "Jul":
			  monthIntegerValue ="7";
			  break;
		case "Aug":
			  monthIntegerValue ="8";
			  break;
		case "Sep":
			  monthIntegerValue ="9";
			  break;
		case "Oct":
			  monthIntegerValue ="10";
			  break;
		case "Nov":
			  monthIntegerValue ="11";
			  break;
		case "Dec":
			  monthIntegerValue ="12";
			  break;
		default:
			Date d = new Date();
			int tempDate = d.getMonth()+1;
			 monthIntegerValue= String.valueOf(tempDate);
		}
		monthInLoggedInTime = Integer.parseInt(monthIntegerValue);
		Calendar c = Calendar.getInstance();
		c.set(year,Integer.parseInt(monthIntegerValue)-1 , day, timeInHours, timeInMinutes, timeInSeconds);
		return c;
	}
	public static void gettingLeadAndProductDetails(int i, WebElement element) throws IOException, Exception
	{
		RunnerClass_1.flag=0;
		RunnerClass_1.wait = new WebDriverWait(RunnerClass_1.driver, Duration.ofSeconds(3));
		RunnerClass_1.driver.manage().timeouts().implicitlyWait(3,TimeUnit.SECONDS);
		Thread.sleep(4000);
		//Row row = RunnerClass_1.sheet.getRow(lastRowNumber+i+1);
		Row row = RunnerClass_1.sheet.createRow(i+RunnerClass_1.lastRowNumber);
		Cell cell =row.createCell(0);
		//cell.setCellType(cell.CELL_TYPE_NUMERIC);
		cell.setCellValue(i+RunnerClass_1.lastRowNumber+1);
		//RunnerClass.waitForElementLocated(Locators.productInfo);
		String productInfo;
		try
		{
		 productInfo = RunnerClass_1.driver.findElement(Locators.productInfo).getText();
		 System.out.println(productInfo);
		}
		catch(Exception e)
		{
			try
			{
				productInfo = RunnerClass_1.driver.findElement(Locators.alternativeProductInfo).getText();
				System.out.println("productInfo");
			}
			//Srinija messages
			catch(Exception e2)
			{
				productInfo = RunnerClass_1.driver.findElement(Locators.alternativeProductInfo2).getText();
				 System.out.println("productInfo");
			}
			finally
			{
				productInfo = "N/A";
			}
		}
		
		
		Cell productBrand =row.createCell(5);
		 try 
		  {
			 productBrand.setCellValue(productInfo);
		  }
		 catch(Exception e)
		 {
			 productBrand.setCellValue(productInfo);
		 }
		 
		 //Seller Name
		 String sellerName = IndiaMart.getActualTextofAnElement(RunnerClass_1.driver.findElement(Locators.sellerName));
		 RunnerClass_1.stringBuilder = IndiaMart.getNumber(sellerName);
		 if(RunnerClass_1.stringBuilder.length()==10)
         {
        	 System.out.println("No Name");
        	 System.out.println(RunnerClass_1.stringBuilder.toString());
        	 RunnerClass_1.flag =1;
        	 Cell sellerName2 = row.createCell(2);
        	 //sellerName2.setCellType(sellerName2.CELL_TYPE_STRING);
        	 sellerName2.setCellValue(RunnerClass_1.stringBuilder.toString());
         }
		 RunnerClass_1.stringBuilder.delete(0, RunnerClass_1.stringBuilder.length());
			//seller location
			if(RunnerClass_1.flag==0)
			{
				Cell sellerName2 = row.createCell(1);
				//sellerName2.setCellType(sellerName2.CELL_TYPE_STRING);
				System.out.println(sellerName.replaceAll("|", "").trim());
				sellerName2.setCellValue(sellerName.replaceAll("|", "").trim());
							
			}
			else
			{
				Cell sellerName2 = row.createCell(1);
				sellerName2.setCellValue("Buyer");
			}
				Cell user = row.createCell(4);
				user.setCellValue(RunnerClass_1.userAccountName);
				System.out.println("India mart user logged in is:");
				System.out.println(user);
	
			
			//seller location
			try
			{
				//RunnerClass.waitForElementLocated(Locators.nameAndLocation);
				if(RunnerClass_1.driver.findElements(Locators.nameAndLocation).size()>0)
				{
				String sellerLocation = IndiaMart.getActualTextofAnElement(RunnerClass_1.driver.findElement(Locators.location));
				Cell sellerLocation2 = row.createCell(3);
				//sellerLocation2.setCellType(sellerLocation2.CELL_TYPE_STRING);
				sellerLocation2.setCellValue(sellerLocation.replaceAll("|", "").trim());
				System.out.println(sellerLocation.replaceAll("|", "").trim());
				}
				else
				{
					Cell sellerLocation2 = row.createCell(3);
					//sellerLocation2.setCellType(sellerLocation2.CELL_TYPE_STRING);
					sellerLocation2.setCellValue("No Location");
					System.out.println("No Location");
				}
			}
			catch(Exception e)
			{
					Cell sellerLocation2 = row.createCell(3);
					//sellerLocation2.setCellType(sellerLocation2.CELL_TYPE_STRING);
					sellerLocation2.setCellValue("No Location");	
	                System.out.println("No Location");	
	                RunnerClass_1.js.executeScript("arguments[0].scrollIntoView();",element);
			}
			String sellerMobileNumber ="";
			try
			{
			sellerMobileNumber = IndiaMart.getActualTextofAnElement(RunnerClass_1.driver.findElement(Locators.sellerMobileNumber));
			}
			catch(NoSuchElementException e)
		    {
				if(RunnerClass_1.driver.findElements(By.xpath("//*[@id='left-name']/p")).size()==1)
				{
		        sellerMobileNumber = IndiaMart.getActualTextofAnElement(RunnerClass_1.driver.findElement(Locators.alternateSellerMobileNumber));
				}
				else if(RunnerClass_1.driver.findElements(By.xpath("//*[@id='left-name']/p")).size()==2)
				    {
					sellerMobileNumber = IndiaMart.getActualTextofAnElement(RunnerClass_1.driver.findElement(By.xpath("//*[@id='left-name']/p[2]")));
				    }
			}
			catch(Exception e)
			{
				sellerMobileNumber = IndiaMart.getActualTextofAnElement(RunnerClass_1.driver.findElement(Locators.alternateSellerMobileNumber));
			}
			
			//System.out.println(sMobileNumber);
			RunnerClass_1.stringBuilder = IndiaMart.getNumber(sellerMobileNumber);
			Cell mobileNumber = row.createCell(2);
			//mobileNumber.setCellType(mobileNumber.CELL_TYPE_STRING);
			mobileNumber.setCellValue(RunnerClass_1.stringBuilder.toString().replaceAll("|", "").trim());
			System.out.println(RunnerClass_1.stringBuilder.toString().replaceAll("|", "").trim());
			
			//Migrated Status
			RunnerClass_1.stringBuilder.delete(0, RunnerClass_1.stringBuilder.length());
			
			System.out.println("-----------------------------------");
			//Product Type
			/*commented by srinija*/
			 //Cell productType =row.createCell(4);
			 //productType.setCellType(productType.CELL_TYPE_STRING);
			 //productType.setCellValue(AppConfig.productType);
		
			Cell migrationStatus =row.createCell(6);
			//migrationStatus.setCellType(migrationStatus.CELL_TYPE_STRING);
			migrationStatus.setCellValue("Pending");
			//actions.moveToElement(driver.findElement(By.xpath("//*[@class='cs_position']/div")), 0,200);
			RunnerClass_1.fis.close();
			FileOutputStream outputStream = new FileOutputStream(RunnerClass_1.file);
			RunnerClass_1.workbook.write(outputStream);
			RunnerClass_1.wait = new WebDriverWait(RunnerClass_1.driver, Duration.ofSeconds(2));
			RunnerClass_1.driver.manage().timeouts().implicitlyWait(1,TimeUnit.SECONDS);
			lastRowNumberInLeadsSheet = RunnerClass_1.sheet.getLastRowNum();
	}
	public static  String getActualTextofAnElement(WebElement element)
	{
		String sName = element.getText();
			
		for (WebElement child : element.findElements(By.xpath("./*"))) 
		{
			sName = sName.replaceFirst(child.getText(), "");
	    }
		return sName;
	}
	public static StringBuilder getNumber(String actualText)
	{
		char[] chars = actualText.toCharArray();
	      for(char c : chars)
	      {
	         if(Character.isDigit(c))
	         {
	            RunnerClass_1.stringBuilder.append(c);
	         }
	        
	      }
		return RunnerClass_1.stringBuilder;
	}
	public static void loginWithAnotherAccount() throws Exception
	{
		RunnerClass_1.wait = new WebDriverWait(RunnerClass_1.driver, Duration.ofSeconds(2));
		RunnerClass_1.driver.manage().timeouts().implicitlyWait(1,TimeUnit.SECONDS);
		if(RunnerClass_1.loginErrorMessageFlag == 0)
		{
			RunnerClass_1.clickAnElement(RunnerClass_1.driver.findElement(Locators.userProfileButton));
			Thread.sleep(1000);
			try
			{
				RunnerClass_1.clickAnElement(RunnerClass_1.driver.findElement(Locators.signOutButton));
			}
			catch(Exception e2)
			{
				RunnerClass_1.clickAnElement(RunnerClass_1.driver.findElement(Locators.signOutButton2));
			}
			Thread.sleep(2000);
			RunnerClass_1.clickAnElement(RunnerClass_1.driver.findElement(Locators.userProfileButton));
			RunnerClass_1.clickAnElement(RunnerClass_1.driver.findElement(Locators.signInAsaDifferentUser));
		}
		else
		{
			RunnerClass_1.clickAnElement(RunnerClass_1.driver.findElement(Locators.userProfileButton));
			RunnerClass_1.clickAnElement(RunnerClass_1.driver.findElement(Locators.signInAsaDifferentUser2));
		}		
	}
}
		
