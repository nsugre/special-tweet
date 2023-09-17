package scripts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TwitterAutomation {

	static WebDriver wd;
	static WebDriverWait wait;
	static WebElement uname, nxt, pwd, lgnBtn, postTxtArea, postBtn, account_menu, logoutLink, confirmLogout;

	public static void main(String[] args) throws IOException {
		
		String user   = args[0];
		String pass   = args[1];
		String fileN  = args[2];
		
		// Read text file and return list of string
		if (fileN == null)	fileN = "text.txt";
		List<String> posts = readFile(fileN);

		ChromeOptions co = new ChromeOptions();
		//co.addArguments("--headless");
		
		wd = new ChromeDriver(co);
		wait = new WebDriverWait(wd, Duration.ofSeconds(10));
		
		wd.manage().window().maximize();

		wd.get("https://twitter.com/i/flow/login");

		// Locate username field and enter value in it.
		uname = waitForPresence("//input[@name='text']");
		uname.sendKeys(user);

		// Locate Next button and click on it.
		nxt = waitForPresence("//span[text()='Next']");
		nxt.click();

		// Locate password field and enter value in it.
		pwd = waitForPresence("//input[@name='password']");
		pwd.sendKeys(pass);

		// Locate login button and click on it.
		lgnBtn = waitForClickability("//span[text()='Log in']");
		lgnBtn.click();

		// loop over the posts
		for (String post : posts) {
			// Locate Post text area and enter text into it
			postTxtArea = waitForPresence("//label[@data-testid='tweetTextarea_0_label']//span");
			postTxtArea.sendKeys(post);
			
			// Locate post button and click on it
			postBtn = waitForClickability("//div[@role='button']//span[text()='Post']");
			postBtn.click();
			
			Random random = new Random();
			int randomWait = random.nextInt(11)+10;
			pauseForXSec(randomWait);
		}
		
		//logout from the application
		//locate profile
		account_menu = waitForPresence("//div[@aria-label='Account menu']");
		account_menu.click();
		
		//logout
		logoutLink = waitForPresence("//a[@href='/logout']");
		logoutLink.click();
		
		//confirm logout
		confirmLogout = waitForClickability("//span[text()='Log out']");
		confirmLogout.click();
		
		wd.close();

	}

	private static WebElement waitForPresence(String xpath) {
		return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
	}

	private static WebElement waitForClickability(String xpath) {
		return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
	}

	private static List<String> readFile(String filename) throws IOException {
		List<String> lines = new ArrayList<>();
		BufferedReader bufferedReader = null;
		FileReader fileReader = null;
		File file = null;
		try {
			String path = System.getProperty("user.dir") + "\\data\\" + filename;
			file = new File(path);
			fileReader = new FileReader(file, StandardCharsets.UTF_8);
			bufferedReader = new BufferedReader(fileReader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				if (line.length() > 5) { lines.add(line); }
			}
		} catch (IOException e) {
			System.err.println("Error reading the file: " + e.getMessage());
		} finally {
			bufferedReader.close();
		}
		return lines;
	}
	
	private static void pauseForXSec(int x) {
		try {
			Thread.sleep(x * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
