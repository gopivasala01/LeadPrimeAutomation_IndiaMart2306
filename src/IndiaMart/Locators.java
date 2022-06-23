package IndiaMart;

import org.openqa.selenium.By;

public class Locators
{
	public static By userNameLogin = By.name("mobile");
	public static By loginButton = By.id("signInSubmitButton");
	public static By submitBUttonForLoginWIithAnotherUser = By.name("start");
	public static By messagesButton = By.id("message-center");
	public static By loginWithPassword = (By.xpath("//*[@value='Login with Password']"));
	public static By loginPassword = By.name("usr_pass");
	public static By loginButton2 = By.name("Submit2");
	public static By unreadCheckbox = By.xpath("//*[@class='container']/descendant::input[2]"); ////*[@class='container']/descendant::input[2]
	public static By contactCount = By.id("contact_count");
	public static By listOfUnreadMessages = By.xpath("//*[@id='scrollerdiv']/ul/li/div[2]");
	
	public static By sellerName = By.xpath("//*[@id='left-name']/div[1]/p[1]");
	public static By nameAndLocation = By.xpath("//*[@class=' mslft dflx ']/div[2]/div[1]/p[1]");
	public static By location = By.xpath("//*[@class=' mslft dflx ']/div[2]/div[1]/p[1]/span");
	public static By sellerMobileNumber = By.xpath("//*[@id='ins']/p");
	public static By userProfileButton = By.xpath("//*[@id='lshead']/a");
	public static By signOutButton = By.linkText("Sign Out");
	public static By signOutButton2 = By.xpath("//*[@class='sign-hover-dropdn Hd_dbn logd']/a[last()]");
	public static By signInAsaDifferentUser = By.linkText("Sign In as Different User");
	public static By signInAsaDifferentUser2 = By.xpath("//a[text()='Sign In as Different User']");
	public static By submitWithAnotherUser = By.name("start");
	public static By loginErrorMessage = By.id("err_msg");
	public static By modelPopUp25Years = By.id("closeModal_25Anniversary");
	public static By closeMobileNumberPopup = By.id("close_s");
	public static By unreadMessagesList = By.xpath("//*[@class='msg_unrd_cnt']");
	public static By allMessagesList = By.xpath("//*[@id='scrollerdiv']/ul/li");
	public static By alternateSellerMobileNumber = By.xpath("//*[@id='left-name']/p");
	
	public static By firstMessageTime = By.xpath("//*[@class='user-message-send ']/div[2]");
	public static By messageTimeReceived = By.xpath("//*[@class='message-send ']/div[2]");
	public static By messageTimeReceived2 = By.xpath("//*[@class='reminder-on-thread'][last()]");
	
	//public static By productInfo = By.xpath("//*[@class='inner_msg_div']/h2"); //*[@class='message-send ']/descendant::h2
	
	public static By productInfo = By.xpath("//*[@class='inner_msg_div']/h2[last()]");
	public static By alternativeProductInfo = By.xpath("//*[@class='message-send ']/div[1]");
	//public static By alternativeProductInfo2 = By.xpath("//*[@class='reminder-text-head'][last()]//span");
	public static By alternativeProductInfo2 = By.xpath("//*[@class='reminder-text-head'][last()]");
	
	public static By loadingSpinnerInMessagesScreen = By.xpath("//*[@class='preloader']/div");
	public static By loginPopUpCloseButton = By.xpath("//*[@id='popupContact_s']/div/a");
	public static By timeWhenTimeAndDateNotAvailableInMessage = By.xpath("//*[@class='lst-msg-date unread']");
	public static By beAwareOfUserElement = By.id("fraud_div");
	public static By beAwareOfUserElement2 = By.cssSelector("#fraud_div > svg");

	//Lead Prime
	public static By leadprimecompanyID = By.id("txt_companycode");
	public static By leadprimeuserName = By.name("txt_UserName");
	public static By leadprimepassword = By.name("txt_Password");
	public static By leadprimeloginButton = By.name("btn_Login");
	public static By searchTextbox = By.name("ctl00$txtPropertySearch");
	public static By searchButton = By.name("ctl00$imgbtn_search");
	public static By teamLeadsCount = By.id("ContentPlaceHolder1_lnkTeamLeadTab");
	public static By leadsLink = By.linkText("Leads");
	public static By createLeadLink = By.xpath("//*[@class='sidemenu']/li[4]/ul/li[1]/a");
	public static By leadFirstName = By.name("ctl00$ContentPlaceHolder1$Leaddetails$txt_FirstName");
	public static By leadMobileNumber = By.name("ctl00$ContentPlaceHolder1$Leaddetails$txt_Mobile1");
	public static By leadEmail = By.name("ctl00$ContentPlaceHolder1$Leaddetails$txt_EmailID");
	public static By assignedToDropdown = By.name("ctl00$ContentPlaceHolder1$Leaddetails$ddl_AssignTo");
	public static By assignedToDropdownList = By.xpath("//*[@name='ctl00$ContentPlaceHolder1$Leaddetails$ddl_AssignTo']/option");
	public static By leadCity = By.name("ctl00$ContentPlaceHolder1$Leaddetails$txt_City");
	public static By leadState = By.name("ctl00$ContentPlaceHolder1$Leaddetails$txt_State");
	public static By leadDetailsTab = By.xpath("//*[@id='ContentPlaceHolder1_Horizontabs']/ul/li[1]");
	public static By requirementsTab = By.xpath("//*[@id='ContentPlaceHolder1_Horizontabs']/ul/li[2]");
	public static By productCategory = By.xpath("//*[@id='ContentPlaceHolder1_RequirementsH_pnlRqmtCheckboxlist1']/table/tbody/tr/td/label");
	public static By productCategoryCheckbox = By.xpath("//*[@id='ContentPlaceHolder1_RequirementsH_pnlRqmtCheckboxlist1']/table/tbody/tr/td/input");
	public static By productDefaultCategory_others = By.xpath("//*[@id='ContentPlaceHolder1_RequirementsH_chklstRqmt1']/tbody/tr[12]/td[2]/input");
	public static By productBrand = By.name("ctl00$ContentPlaceHolder1$RequirementsH$txtlstRqmt2");
	public static By customFieldAttributesTab = By.xpath("//*[@id='ContentPlaceHolder1_Horizontabs']/ul/li[4]");
	public static By leadSource = By.xpath("(//*[@class='custom_lead_attr_area'])[8]/div/table/tbody/tr[11]/td[1]/input");
	//public static By leadSaveButton = By.xpath("(//*[@class='cread_lead_table'])[6]/descendant::a");
	public static By leadSaveButton = By.linkText("Save");
	public static By existingLeadsCount = By.linkText("View Details"); ////*[@class='leadname']/descendant::a
	public static By viewDetailsButton = By.linkText("View Details");
	public static By date = By.id("ContentPlaceHolder1_lblTLLastCntd");
	public static By editLeadLink = By.xpath("(//*[@class='level1 static'])[1]/li[1]/a"); ////*[@id='ContentPlaceHolder1_MTLLeadMenu']/ul/li[1]
	public static By scrollBarInCustomAttributesTab = By.xpath("(//*[@class='slimScrollDiv'])[2]/div[2]");
}
