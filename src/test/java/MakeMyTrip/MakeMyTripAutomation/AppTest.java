package MakeMyTrip.MakeMyTripAutomation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.google.common.io.Files;

/**
 * Unit test for simple App.
 * hii
 */
public class AppTest {

    /**
     * Rigorous Test :-)
     * @throws IOException 
     * @throws InterruptedException 
     */
    @Test(dataProvider="testData")
    public void performSearch(String FromcityName,String TocityName,String DepatureDate,String ArrivalDate,String AdultsRange,String ChildrenRange,String InfantRange,String TravelClass,int rowIndex) throws Exception {

    		System.out.println("FromCityName:"+FromcityName);
    		System.out.println("TocityName:"+ TocityName);
    		System.out.println("DepatureDate:"+DepatureDate);
    		System.out.println("ArrivalDate:"+ArrivalDate);
    		System.out.println("AdultsRange:"+AdultsRange);
    		System.out.println("ChildrenRange:"+ChildrenRange);
    		System.out.println("InfantRange:"+InfantRange);
    		System.out.println("TravelClass:"+TravelClass);
    		
    		System.out.println("End of Data Sets");

    		
    		EdgeOptions browserOptions = new EdgeOptions();
    		browserOptions.setPlatformName("Windows 11");
    		browserOptions.setBrowserVersion("131");
    		HashMap<String, Object> ltOptions = new HashMap<String, Object>();
    		ltOptions.put("username", "ayishanaznee");
    		ltOptions.put("accessKey", "4Xnbo8fQHaD5ujtkRrijjTNesnyL0fYgbAppuln5dEkMiy4FZM");
    		ltOptions.put("visual", true);
    		ltOptions.put("video", true);
    		ltOptions.put("network", true);
    		ltOptions.put("build", "MakeMyTrip");
    		ltOptions.put("project", "MakeMyTripAutomation");
  
    		ltOptions.put("console", "true");
    		ltOptions.put("w3c", true);
    		browserOptions.setCapability("LT:Options", ltOptions);
    		
    		//4Xnbo8fQHaD5ujtkRrijjTNesnyL0fYgbAppuln5dEkMiy4FZM
    		//WebDriver driver =new RemoteWebDriver(new URL("https://ayishanaznee:4Xnbo8fQHaD5ujtkRrijjTNesnyL0fYgbAppuln5dEkMiy4FZM@hub.lambdatest.com/wd/hub"),browserOptions);
    		
    		ChromeOptions browserChromeOptions=new ChromeOptions();
    		 // Create a map for Chrome preferences
            Map<String, Object> prefs = new HashMap<>();
            prefs.put("profile.default_content_setting_values.notifications", 2);  // Disable notifications
            browserChromeOptions.setExperimentalOption("prefs", prefs);
    		
    		browserChromeOptions.addArguments("--disable-infobars");
    		browserChromeOptions.addArguments("--disable-extensions");
    		browserChromeOptions.addArguments("--disable-automation");
    		browserChromeOptions.addArguments("--disable-blink-features=AutomationControlled"); 
    		
    		WebDriver driver =new ChromeDriver(browserChromeOptions);
    		driver.manage().window().maximize();
    		driver.get("https://www.makemytrip.com/");
    		
    		//Assert.assertTrue(driver.getTitle().contains("MakeMyTrip"));
    		
    		WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(15));
    		By acceptCookieLocator=By.cssSelector("button[class*='cookiesModal__acceptCookiesBtn']");
    		try {
    		wait.until(ExpectedConditions.visibilityOfElementLocated(acceptCookieLocator));
    		WebElement acceptCookieBtn=driver.findElement(acceptCookieLocator);
    			if(acceptCookieBtn.isEnabled())
    			{
    				acceptCookieBtn.click();
    			}
    		}
    		catch(Exception e) {
    			System.out.println("Accept Cookie is missing");
    		}
    		
    		driver.navigate().refresh();
    		System.out.println("Refresh Done Successfully");
    		System.out.println("Before Thread Sleep");
    		//Thread.sleep(2000);
    		//div[@data-cy='outsideModal']//section/span[@data-cy='closeModal']
    		By closeModal=By.cssSelector("div[data-cy='outsideModal'] section span[data-cy='closeModal']");
    		
    		
    		try {
    			wait.until(ExpectedConditions.elementToBeClickable(closeModal));
    			WebElement closeModalEle=driver.findElement(closeModal);
    			
    			closeModalEle.click();
    			
    		}
    		catch(Exception e)
    		{
    			System.out.println("close modal is missing from the page");
    		}
    		By oneWayTripRadioBtn=By.cssSelector("li[data-cy*='oneWayTrip']");
    		wait.until(ExpectedConditions.elementToBeClickable(oneWayTripRadioBtn));
    		driver.findElement(oneWayTripRadioBtn).click();
    		By fromCity=By.cssSelector("div[class*='fsw_inner returnPersuasion'] label[for='fromCity'] input");
    		WebElement fromCityNameElement=driver.findElement(fromCity);
    		String fromCityName=fromCityNameElement.getAttribute("value");
    		By fromCityAutosuggestionsList=By.cssSelector("ul[role='listbox'][class*='autosuggest'] li *[class*='spaceBetween appendRight10']");
    		
    		List<WebElement> autoSuggestionLists=new ArrayList<>();
    		By inputFromCityName=By.cssSelector("input[placeholder='From']");
    		

    		Wait<WebDriver> Fluentwait = new FluentWait<WebDriver>(driver)
    			    .withTimeout(Duration.ofSeconds(15))
    			    .pollingEvery(Duration.ofSeconds(2))
    			    .ignoring(NoSuchElementException.class);

    		if(fromCityName.contains(FromcityName))
    		{
    			System.out.print("From City is "+ FromcityName);
    			
    		}
    		else
    		{
    			fromCityNameElement.click();
    			wait.until(ExpectedConditions.visibilityOfElementLocated(inputFromCityName));
    			WebElement inputFromCityNameElement=driver.findElement(inputFromCityName);
    			inputFromCityNameElement.sendKeys(FromcityName);
    	
    			//Re-find the element after changes in the DOM
    			Thread.sleep(2000);
    			autoSuggestionLists=driver.findElements(fromCityAutosuggestionsList);
    			System.out.println("Before Size:"+autoSuggestionLists.size());
    						
    			for(WebElement ele :autoSuggestionLists)
    			{
    				System.out.println("City Text Name:"+ele.getText());
    				if(ele.getText().contains(FromcityName))
    				{
    					ele.click();
    					break;
    				}
    				
    			}	
    		}
    		
    		By toCity=By.cssSelector("input[data-cy='toCity']");
    		WebElement toCityElement=driver.findElement(toCity);
    		toCityElement.click();
    		
    		By toCityInput=By.cssSelector("input[placeholder='To']");
    		WebElement toCityInputElement=driver.findElement(toCityInput);
    		toCityInputElement.sendKeys(TocityName);
    		
    		//to suggestion
    		System.out.println("To City Suggestion");
    		By ToCityAuto=By.cssSelector("div[role='listbox'] ul[role='listbox'][class*='autosuggest'] li *[class*='spaceBetween appendRight10']");
    		List<WebElement> autoSuggestionLists1=driver.findElements(ToCityAuto);
    		Thread.sleep(5000);
    		autoSuggestionLists=driver.findElements(fromCityAutosuggestionsList);
    		for(WebElement ele :autoSuggestionLists)
    		{
    			System.out.println("City Text Name:"+ele.getText());
    			if(ele.getText().contains(TocityName))
    			{
    				ele.click();
    				break;
    			}
    			
    		}	
    		
    		By startDateElementLocator=By.cssSelector("div[class*='searchToCity']+div[class*='dates']");
    		WebElement startDateElement=driver.findElement(startDateElementLocator);
    		wait.until(ExpectedConditions.elementToBeClickable(startDateElement));
    		System.out.println("Enabled :"+ startDateElement.isEnabled());
    		Thread.sleep(5000);
    		startDateElement.click();
    		
    		Actions act=new Actions(driver);
    		act.scrollByAmount(0, 100).build().perform();
    		
    		int index=selectReturnDateMonth(driver,wait,DepatureDate);
    		if(index==-1)
    		{
    			System.out.println("Unable to find the Depature given month in the calendar");
    			takeScreenshot(driver);
    			driver.close();
    			return;
    		}
    		By startDateLocator=By.cssSelector("div[class='DayPicker-Month']:nth-child("+index+") div[class*='DayPicker-Day']");
    		boolean isDayDateSelected=selectDayDate(driver,wait,startDateLocator,DepatureDate);
    		System.out.println("Depature Day Date Selection");
    		if(!isDayDateSelected)
    		{
    			System.out.println("Unable to find the given Depature day date in the calendar");
    			takeScreenshot(driver);
    			driver.close();
    			return;
    			
    		}
    		
    		System.out.println("Return Date Selection");
    		By returnDateCalendarBtnLocator=By.cssSelector("div[class*='reDates']");
    		WebElement returnDateCalendarBtn=driver.findElement(returnDateCalendarBtnLocator);
    		returnDateCalendarBtn.click();
    		
    		Thread.sleep(5000);
    		index=selectReturnDateMonth(driver,wait,ArrivalDate);
    		if(index==-1)
    		{
    			System.out.println("Unable to find the given Arrival month in the calendar");
    			takeScreenshot(driver);
    			driver.close();
    			return;
    		}
    		
    		By ReturnDateLocator=By.cssSelector("div[class='DayPicker-Month']:nth-child("+index+") div[class*='DayPicker-Day']");
    		isDayDateSelected=selectDayDate(driver,wait,ReturnDateLocator,ArrivalDate);
    		System.out.println("Arrival Day Date Selection");
    		if(!isDayDateSelected)
    		{
    			System.out.println("Unable to find the given Arrival day date in the calendar");
    			takeScreenshot(driver);
    			driver.close();
    			return;
    			
    		}
    		
    		
    		
    		By flightTravellerLocator = By.cssSelector("div[data-cy='flightTraveller']");
    		WebElement flightTravellerElement=driver.findElement(flightTravellerLocator);
    		flightTravellerElement.click();
    		
    		By adultRangeLocator =By.cssSelector("p[data-cy='adultRange']+ul[class*='guestCounter'] li");
    		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(adultRangeLocator));
    		List<WebElement> adultRangeLists=driver.findElements(adultRangeLocator);
    		System.out.println("Flight traveller Selection:"+adultRangeLists.size());
    		for(WebElement ele:adultRangeLists)
    		{
    			System.out.println("Range:"+ele.getText());
    			if(ele.getText().contains(AdultsRange))
    			{
    				ele.click();
    				break;
    			}
    		}
    		
    		By childrenSeatLocator=By.cssSelector("p[data-cy=\"childrenRange\"]+ul>li");
    		List<WebElement> childrenSeats=driver.findElements(childrenSeatLocator);
    		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(childrenSeatLocator));
    		for(WebElement ele:childrenSeats)
    		{
    			if(ele.getText().contains(ChildrenRange))
    			{
    				ele.click();
    				break;
    			}
    		}
    		
    		By infantSeatLocator=By.cssSelector("p[data-cy='infantRange']+ul>li");
    		List<WebElement> infantSeats=driver.findElements(infantSeatLocator);
    		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(infantSeatLocator));
    		for(WebElement ele:infantSeats)
    		{
    			if(ele.getText().contains(InfantRange))
    			{
    				ele.click();
    				break;
    			}
    		}
    		
    		By travelClassLocator=By.cssSelector("p[data-cy='chooseTravelClass']+ul>li[data-cy*='travelClass']");
    		List<WebElement> travelClasses=driver.findElements(travelClassLocator);
    		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(travelClassLocator));
    		for(WebElement ele:travelClasses)
    		{
    			System.out.println("ele.getText():"+ele.getText());
    			System.out.println("TravelClassTravelClass:"+TravelClass);
    			System.out.println("ele.getText().equalsIgnoreCase(TravelClass)"+ele.getText().equalsIgnoreCase(TravelClass));
    			if(ele.getText().equalsIgnoreCase(TravelClass)||(ele.getText().contains("new") && ele.getText().contains(TravelClass)))
    			{
    				ele.click();
    				break;
    			}
    		}
    		
    		By travelClassApplyLocator=By.cssSelector("button[data-cy=\"travellerApplyBtn\"]");
    		wait.until(ExpectedConditions.elementToBeClickable(travelClassApplyLocator));
    		WebElement travelClassApplyElement=driver.findElement(travelClassApplyLocator);
    		travelClassApplyElement.click();
    		
    		By roundTripRadioLocator=By.cssSelector("li[data-cy='roundTrip']");
    		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(roundTripRadioLocator));
    		WebElement roundTripRadioLocatorEle=driver.findElement(roundTripRadioLocator);
    		
    		if(roundTripRadioLocatorEle.isSelected() ||roundTripRadioLocatorEle.getAttribute("class").contains("selected"))
    		{
    			System.out.println("Radio Button Is Selected");
    		}
    		
    		By searchBtnLocator=By.xpath("//a[contains(text(),'Search')]");
    		WebElement searchBtnLocatorEle=driver.findElement(searchBtnLocator);
    		searchBtnLocatorEle.click();
    		
    		takeScreenshot(driver);

    		By popUpLocator=By.xpath("//p[contains(text(),'Now Lock Prices & Pay Later!')]");
    		wait.until(ExpectedConditions.visibilityOfElementLocated(popUpLocator));
    		WebElement popUpBtnEle=driver.findElement(popUpLocator);
    		Assert.assertTrue(popUpBtnEle.getText().contains("Now Lock Prices & Pay Later!"));
    		
    		By closePopUpLocator=By.cssSelector("div[class='commonOverlay '] span[class*='overlayCrossIcon']");
    		WebElement closePopUpBtnEle=driver.findElement(closePopUpLocator);
    		closePopUpBtnEle.click();
    		
    	//	driver.close();
    		
    		By arrowUpLocator=By.xpath("//span[contains(@class,'arrowUp')]");
    		
    		WebElement arrowUpWebEle=wait.until(ExpectedConditions.elementToBeClickable(arrowUpLocator));
    		arrowUpWebEle.click();
    		
    		
    		By  lists = By.cssSelector("div[class*='splitviewSticky'] div[class*='appendRight']");
    		
    		List<WebElement> listsWebEle = new ArrayList<>();
    		listsWebEle	=driver.findElements(lists); 
    		int listSize= listsWebEle.size();
    		System.out.println("Lists Size:"+listSize);
    		ArrayList<String> arr1=new ArrayList<String>(); 
    		for(WebElement ele:listsWebEle)
    		{
    			System.out.println("WebElement getText:"+ele.getText());
    			arr1.add(ele.getText());
    		}
    		List <String> prices=new ArrayList<String>();
    		for(String ls:arr1)
    		{
    			String str[]= ls.split("\n");
    			System.out.println("Before Format");
        		for(String st: str) {
        			System.out.println("After Formatting:"+st);
        			if(st.contains("₹"))
        			{
        				prices.add(st.split(" ")[1]);
        				
        			}
        			
        		}
    		}
    		List<Integer> pricesInt=new ArrayList<Integer>();
    		for(String price:prices)
    		{
    			System.out.println("Ticket price:"+price);
    			int num=Integer.parseInt(price.split(",")[0]+price.split(",")[1]); 
    			pricesInt.add(num);
    			System.out.println("Price converted into num:"+num);
    		}
    		
    		System.out.println("Price 1+ price 2:"+Integer.valueOf(pricesInt.get(0)+pricesInt.get(1)));
    		Assert.assertTrue(Integer.valueOf(pricesInt.get(0)+pricesInt.get(1)).equals(pricesInt.get(2)));
    		
    		By bookNowLocator=By.xpath("//div[contains(@class,'stickyFlightDtl')]//button[contains(text(),'Book')]");
    		WebElement bookNowele=wait.until(ExpectedConditions.elementToBeClickable(bookNowLocator));
    		bookNowele.click();
    		
    		By optionsWindow=By.cssSelector("ul[class*='ffTabList'] li[class*='active']");
    		WebElement optionsWindowEle=wait.until(ExpectedConditions.visibilityOfElementLocated(optionsWindow));
    		Assert.assertTrue(optionsWindowEle.getText().equalsIgnoreCase("Onward"));
    		
    		By windowBtnLocator=By.xpath("//div[@class='makeFlex']//button[contains(@class,'buttonPrimary')]");
    		WebElement windowBtnEle=wait.until(ExpectedConditions.elementToBeClickable(windowBtnLocator));
    		System.out.println("Window Bottom Locator Text:"+windowBtnEle.getText());
    		Assert.assertTrue(windowBtnEle.getText().equalsIgnoreCase("continue"));
    		
    		By optionsWindow2=By.cssSelector("ul[class*='ffTabList'] li:not(.active)");
    		WebElement optionsWindow2BtnEle=wait.until(ExpectedConditions.elementToBeClickable(optionsWindow2));
    		optionsWindow2BtnEle.click();
    		 
    		 
    		By windowBtnLocator2=By.xpath("//div[@class='makeFlex']//button[contains(@class,'buttonPrimary')]");
    		WebElement windowBtnEle2=wait.until(ExpectedConditions.elementToBeClickable(windowBtnLocator));
    		System.out.println("Window Bottom Locator Text:"+windowBtnEle2.getText());
    		Assert.assertTrue(windowBtnEle2.getText().equalsIgnoreCase("book now"));
    		windowBtnEle2.click();
    		
    		String orginalWindowHandle=driver.getWindowHandle();
    		Set<String> handles=driver.getWindowHandles();
    		for(String handle:handles)
    		{
    			if(!handle.equals(orginalWindowHandle))
    			{
    				driver.switchTo().window(handle);
    				
    				String currentURL=driver.getCurrentUrl();
    	    		System.out.println("currentURL:"+ currentURL);
    	    		SoftAssert assert1=new SoftAssert();
    	    		wait.until(ExpectedConditions.urlContains("reviewDetails"));
    	    		assert1.assertTrue(currentURL.contains("reviewDetails"));
    	    		
    	    		By reviewPageHeaderLocator=By.xpath("//h2[@data-test='component-title' and contains(text(),'Complete your booking')]");
    	    		WebElement reviewPageHeaderEle=wait.until(ExpectedConditions.visibilityOfElementLocated(reviewPageHeaderLocator));
    	    		System.out.println("reviewPageHeaderEle Text:"+ reviewPageHeaderEle.getText());
    	    		assert1.assertTrue(reviewPageHeaderEle.getText().contains("Complete your booking"));
    	    		assert1.assertAll();
    			}
    		}
    		
    		
    		//driver.switchTo().window(orginalWindowHandle);
    		
    		
    		
    		//I need to check this piece of code 
    		writeDataInTestDataExcelFile(rowIndex);
    		
    		
    		
    		System.out.println("Test Executed Successfully");
    	
    }
    
    public int selectReturnDateMonth(WebDriver driver,WebDriverWait wait,String inputMonth)
    {
    	By calendarMonthCaption=By.cssSelector("div[class='DayPicker-Month'] div[class*='DayPicker-Caption']");
    	wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(calendarMonthCaption));
    	List<WebElement> months=new ArrayList<>();
    	months=driver.findElements(calendarMonthCaption);
    	System.out.println("List Size:"+months.size());
    	
    	int index=-1;
    	while(index==-1)
    	{
	    	for(int i=0;i<months.size();i++)
	    	{
	    		System.out.println("Return Month:"+months.get(i).getText());
	    		System.out.println("Arrrival  Day:"+inputMonth.split("[ /-]")[0]);
	    		System.out.println("Arrrival  Month:"+inputMonth.split("[ /-]")[1]);
	    		System.out.println("Arrrival  Year:"+inputMonth.split("[ /-]")[2]);
	    		
	    		System.out.println("compare:"+ months.get(i).getText().contains(inputMonth.split("[ /-]")[1]));
	    		if(months.get(i).getText().contains(inputMonth.split("[ /-]")[1])  && months.get(i).getText().contains(inputMonth.split("[ /-]")[2]))
	    		{
	    			System.out.println("Matched Month"+ months.get(i).getText());
	    			index=i+1;
	    			break;
	    		}
	    	}
    	
	    	if(index==-1)
	    	{
		    	By nextBtnLocator=By.cssSelector("span[role='button'][aria-label='Next Month']");
		    	try {
				    	wait.until(ExpectedConditions.elementToBeClickable(nextBtnLocator));
				    	WebElement nextBtn=driver.findElement(nextBtnLocator);
				    	nextBtn.click();  	
		    		}
		    	catch(Exception e)
		    	{
		    		
		    		System.out.println("Next Button doesn't exists");
		    		break;
		    	}
	    	}
    	}
    	
    	return index;
    }
    
    public boolean selectDayDate(WebDriver driver,WebDriverWait wait,By dateElementLocator,String inputDate) {
  
    	System.out.println("Input Day Date Split inside "+inputDate.split("[ /-]")[0]);
    	List<WebElement> ReturnDatecalendarList=new ArrayList<>();
		ReturnDatecalendarList=driver.findElements(dateElementLocator);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(dateElementLocator));
		boolean isClicked=false;
		for(WebElement ele:ReturnDatecalendarList)
		{
			
			String className=ele.getAttribute("class");
			if(!(className.contains("DayPicker-Day--outside") || className.contains("DayPicker-Day--disabled")))
			{
				WebElement eleText=ele.findElement(By.cssSelector("p:nth-child(1)"));
				System.out.println("Day:"+eleText.getText());
				if(eleText.getText().equalsIgnoreCase(inputDate.split("[ /-]")[0]))
				{
					eleText.click();
					isClicked=true;
					System.out.println("Day is Clicked:"+isClicked);
					break;
				}
				
			}
			
		}
		System.out.println("outer loop Day is Clicked:"+isClicked);
		return isClicked;
    }
    
    @DataProvider(name="testData")
    public Object readExcelTestData() throws Exception{
    
    	XSSFWorkbook workBook;
    	XSSFSheet sheet;
    	
    	DataFormatter formatter=new DataFormatter();
    	
    	String excelPath;
    	excelPath=System.getProperty("user.dir")+File.separator+"resources"+File.separator+"TestData"+File.separator+"MakeMyTripData.xlsx";
    	FileInputStream fis=new FileInputStream(new File(excelPath));
    	
    	workBook=new XSSFWorkbook(fis);
    	sheet=workBook.getSheet("Sheet1");
    	int rowCount=sheet.getPhysicalNumberOfRows();
    	System.out.println("Row Count:"+rowCount);
    	int colCount=sheet.getRow(0).getLastCellNum();
    	Object[][] testData=new Object[rowCount-1][colCount];
    	System.out.println("Row Count:"+rowCount+" Column Count:"+colCount);
    	System.out.println("Row Count:"+sheet.getLastRowNum()+" Column Count:"+sheet.getRow(sheet.getLastRowNum()).getLastCellNum());
    	for(int i=1,row=0;i<rowCount;i++,row++)
    	{
    		for(int j=0;j<colCount-1;j++)
    		{
    			//System.out.println("Data at i:"+i+" j:"+j+" is "+formatter.formatCellValue(sheet.getRow(i).getCell(j)));
    			testData[row][j]=formatter.formatCellValue(sheet.getRow(i).getCell(j));
    			//System.out.println("Matrix Row Count:"+row);
    		}
    		testData[row][colCount-1]=i;
    	}
    	
    	workBook.close();
    	return testData;
    }
    
    public void writeDataInTestDataExcelFile(int rowIndex) throws Exception {
    	
    	XSSFWorkbook workBook;
    	XSSFSheet sheet;
    	
    	String excelPath;
    	excelPath=System.getProperty("user.dir")+File.separator+"resources"+File.separator+"TestData"+File.separator+"MakeMyTripData.xlsx";
    	FileInputStream fis=new FileInputStream(new File(excelPath));
    	workBook=new XSSFWorkbook(fis);
    	sheet=workBook.getSheetAt(0);
		sheet.getRow(rowIndex).createCell(8).setCellValue("Pass");
		
		FileOutputStream fos=new FileOutputStream(excelPath);
		workBook.write(fos);
	
		fos.flush();
		workBook.close();
    	
    	return;
    }
    
    public void takeScreenshot(WebDriver driver) throws Exception
    {
    	TakesScreenshot screenShot=(TakesScreenshot) driver;
    	File screenShotFileSrc= screenShot.getScreenshotAs(OutputType.FILE);
    	
    	String destinationPath=System.getProperty("user.dir")+File.separator+"results"+File.separator+getTimeStamp()+".png";
    	System.out.println("destinationPath:"+destinationPath);
    	Files.copy(screenShotFileSrc, new File(destinationPath));
    	
    }
    
    @Test
    public String getTimeStamp() {
    	
    	Timestamp timestamp=new Timestamp(System.currentTimeMillis());
    	System.out.println("TimeStamp:"+timestamp);
    	
    	String timeStampString=timestamp.toString();
    	System.out.println("TimeStampString:"+timeStampString);
    	String arr[]=timeStampString.split("\\.");
    	
    	String arr1[]=arr[0].split(":");
    	String result=arr1[0]+"-"+arr1[1]+"-"+arr1[2];
    	System.out.println("result"+result);
    	return result;
    }
    
}

