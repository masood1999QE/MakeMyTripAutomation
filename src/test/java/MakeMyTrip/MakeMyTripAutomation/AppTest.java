package MakeMyTrip.MakeMyTripAutomation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.common.io.Files;

/**
 * Unit test for simple App.
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
    		
    		WebDriver driver =new ChromeDriver();
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
    		    	
    		List<WebElement> StartDatecalendarList=new ArrayList<>();
    		
    		By startDateElementLocator=By.cssSelector("div[class*='searchToCity']+div[class*='dates']");
    		WebElement startDateElement=driver.findElement(startDateElementLocator);
    		wait.until(ExpectedConditions.elementToBeClickable(startDateElement));
    		System.out.println("Enabled :"+ startDateElement.isEnabled());
    		Thread.sleep(5000);
    		startDateElement.click();
    		
    		Actions act=new Actions(driver);
    		act.scrollByAmount(0, 100).build().perform();
    		
    		By startDateLocator=By.cssSelector("div[class='DayPicker-Month']:nth-child(1) div[class*='DayPicker-Day']");
    		StartDatecalendarList=driver.findElements(startDateLocator);
    		System.out.println("Start Date Selection");
    		for(WebElement ele:StartDatecalendarList)
    		{
    			//System.out.println(ele.getAttribute("class"));
    			String className=ele.getAttribute("class");
    			if(!(className.contains("DayPicker-Day--outside") || className.contains("DayPicker-Day--disabled")))
    			{
    				WebElement eleText=ele.findElement(By.cssSelector("p:nth-child(1)"));
    				System.out.println("Day:"+eleText.getText());
    				if(eleText.getText().contains(DepatureDate))
    				{
    					eleText.click();
    					break;
    				}
    				
    			}
    			
    		}
    		System.out.println("Return Date Selection");
    		By returnDateCalendarBtnLocator=By.cssSelector("div[class*='reDates']");
    		WebElement returnDateCalendarBtn=driver.findElement(returnDateCalendarBtnLocator);
    		returnDateCalendarBtn.click();
    		
    		Thread.sleep(5000);
    		
    		List<WebElement> ReturnDatecalendarList=new ArrayList<>();
    		By ReturnDateLocator=By.cssSelector("div[class='DayPicker-Month']:nth-child(2) div[class*='DayPicker-Day']");
    		ReturnDatecalendarList=driver.findElements(ReturnDateLocator);
    		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(ReturnDateLocator));
    		
    		for(WebElement ele:ReturnDatecalendarList)
    		{
    			
    			String className=ele.getAttribute("class");
    			if(!(className.contains("DayPicker-Day--outside") || className.contains("DayPicker-Day--disabled")))
    			{
    				WebElement eleText=ele.findElement(By.cssSelector("p:nth-child(1)"));
    				System.out.println("Day:"+eleText.getText());
    				if(eleText.getText().contains(ArrivalDate))
    				{
    					eleText.click();
    					break;
    				}
    				
    			}
    			
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
    		
    		takeScreenshot(driver);
    		
    		driver.close();
    		
    		writeDataInTestDataExcelFile(rowIndex);
    		
    		
    	
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

