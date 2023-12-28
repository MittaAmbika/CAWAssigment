import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import io.github.bonigarcia.wdm.WebDriverManager;

public class HomePage {
	WebDriver driver;
	@BeforeClass
	void setup() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get("https://testpages.herokuapp.com/styled/tag/dynamic-table.html");
		//driver.manage().timeouts().implicitlyWait(10,TimeUnit.MILLISECONDS);

	}
	@Test
	void verifypagetitle() {
		String title=driver.getTitle();
		Assert.assertEquals(title, "Table HTML Tag - JavaScript Created","Title is mismatched");
		
	}
		
	@Test 
	void clickontabledata() {
		driver.findElement(By.xpath("//*[contains(text(), \"Table Data\")]")).click();
	}
	@Test(dataProvider="dp")
	void jsondata( String data)
	{
		WebElement inputTextBox = driver.findElement(By.id("//textarea[@id='jsondata']"));
		inputTextBox.sendKeys(data);
	}
	
	
	@DataProvider(name="dp")
	public String[] readJson() throws IOException, ParseException{
		JSONParser jsonParser=new JSONParser();
		FileReader reader=new FileReader(".\\jsonfiles\\testdata.json");
		 Object obj=jsonParser.parse(reader);
	JSONObject userloginJsonobj=(JSONObject)obj;
	JSONArray userloginarray=(JSONArray)userloginJsonobj.get("Dynamic Table");
	String arr[]=new String[userloginJsonobj.size()];
	for(int i=0;i<userloginarray.size();i++) {
		JSONObject users=(JSONObject)userloginarray.get(i);
	String name=(String)users.get("name");
	String age=(String)users.get("age");
	arr[i]=name+","+age;
	}
	return arr;
	}
	
	
}





