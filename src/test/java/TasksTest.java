import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TasksTest {

	public WebDriver acessarAplicacao() {
		System.setProperty("webdriver.chrome.driver",
				"/Users/renan.lopes/Workspace/tools/seleniumDrivers/chromedriver");

		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.navigate().to("http://localhost:8001/tasks");

		return driver;
	}

	@Test
	public void deveSalvarTarefaComSucesso() {
		WebDriver driver = acessarAplicacao();

		try {
			driver.findElement(By.id("addTodo")).click();
			driver.findElement(By.id("task")).sendKeys("Decrição");
			driver.findElement(By.id("dueDate")).sendKeys("01/01/2025");
			driver.findElement(By.id("saveButton")).click();

			String message = driver.findElement(By.id("message")).getText();

			Assert.assertEquals("Success!", message);
		} finally {
			driver.quit();
		}
	}
	
	@Test
	public void naoDeveSalvarTarefaSemDescricao() {
		WebDriver driver = acessarAplicacao();
		
		try {
			driver.findElement(By.id("addTodo")).click();
			driver.findElement(By.id("dueDate")).sendKeys("01/01/2025");
//			driver.findElement(By.id("task")).sendKeys("Decrição");
			driver.findElement(By.id("saveButton")).click();
			
			String message = driver.findElement(By.id("message")).getText();
			
			Assert.assertEquals("Fill the task description", message);
		} finally {
			driver.quit();
		}
	}
	

	@Test
	public void naoDeveSalvarTarefaSemData() {
		WebDriver driver = acessarAplicacao();
		
		try {
			driver.findElement(By.id("addTodo")).click();
//			driver.findElement(By.id("dueDate")).sendKeys("01/01/2025");
			driver.findElement(By.id("task")).sendKeys("Decrição");
			driver.findElement(By.id("saveButton")).click();
			
			String message = driver.findElement(By.id("message")).getText();
			
			Assert.assertEquals("Fill the due date", message);
		} finally {
			driver.quit();
		}
	}
	
	@Test
	public void naoDeveSalvarTarefaComDataPassada() {
		WebDriver driver = acessarAplicacao();
		
		try {
			driver.findElement(By.id("addTodo")).click();
			driver.findElement(By.id("dueDate")).sendKeys("01/01/2010");
			driver.findElement(By.id("task")).sendKeys("Decrição");
			driver.findElement(By.id("saveButton")).click();
			
			String message = driver.findElement(By.id("message")).getText();
			
			Assert.assertEquals("Due date must not be in past", message);
		} finally {
			driver.quit();
		}
	}
}
