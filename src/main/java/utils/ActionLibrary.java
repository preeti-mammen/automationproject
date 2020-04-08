package utils;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.LogStatus;

import org.apache.commons.collections4.list.TreeList;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

// TODO: Auto-generated Javadoc
/**
 * The Class ActionLibrary.
 * 
 * @author Preeti Mammen
 */
public class ActionLibrary {

	/** The act. */
	private ActionToFetchPropertyValue act = new ActionToFetchPropertyValue();

	/**
	 * Wait for page load complete.
	 */
	public void waitForPageLoadComplete() {
		try {
			new WebDriverWait(Constants.getDriver(), 60).until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver wdriver) {
					return ((JavascriptExecutor) Constants.getDriver()).executeScript("return document.readyState")
							.equals("complete");
				}
			});
		} catch (Exception e) {
			SetUpTest.test.log(LogStatus.ERROR, "Exception Occured: " + e.getMessage());
		}
	}

	/**
	 * Wait for visibility of all elements located by.
	 *
	 * @param locator
	 *            the locator
	 */
	public void waitForVisibilityOfAllElementsLocatedBy(By locator) {
		try {
			new WebDriverWait(Constants.getDriver(), 60)
					.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
		} catch (Exception e) {
			SetUpTest.test.log(LogStatus.ERROR, "Exception Occured: " + e.getMessage());
		}
	}

	/**
	 * Wait for visibility of all elements located by.
	 *
	 * @param element
	 *            the element
	 */
	public void waitForVisibilityOfWebElement(WebElement element) {
		try {
			new WebDriverWait(Constants.getDriver(), 60).until(ExpectedConditions.visibilityOf(element));
		} catch (Exception e) {
			SetUpTest.test.log(LogStatus.ERROR, "Exception Occured: " + e.getMessage());
		}
	}

	/**
	 * Method to scroll down a page.
	 */
	public void scrollPage() {
		try {
			JavascriptExecutor jse = (JavascriptExecutor) Constants.getDriver();
			jse.executeScript("javascript:window.scrollBy(0,350)", "");
		} catch (Exception e) {
			SetUpTest.test.log(LogStatus.ERROR, "Exception Occured: " + e.getMessage());
		}
	}

	/**
	 * Method to assert element present on a page.
	 *
	 * @param element
	 *            the element
	 * @return true, if successful
	 */
	public boolean assertElementPresent(WebElement element) {
		try {
			new WebDriverWait(Constants.getDriver(), 60).until(ExpectedConditions.visibilityOf(element));
			element.isDisplayed();
			SetUpTest.test.log(LogStatus.PASS, "Element is Found: " + element.isDisplayed());
			return true;
		} catch (Exception e) {
			SetUpTest.test.log(LogStatus.ERROR, "Exception Occured: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Wrapper Method to mouseover on an element and click the element.
	 *
	 * @param element
	 *            the element
	 */
	public void mouseOverElementAndClick(WebElement element) {
		Actions actiondo = new Actions(Constants.getDriver());
		try {
			new WebDriverWait(Constants.getDriver(), 60).until(ExpectedConditions.visibilityOf(element));
			actiondo.moveToElement(element).click().build().perform();
		} catch (Exception e) {
			try {
				actiondo.moveToElement(element).click().build().perform();
			} catch (Exception e1) {
				SetUpTest.test.log(LogStatus.ERROR, "Exception Occured: " + e1.getMessage());
			}
		}
	}

	/**
	 * Method to mouseover on a header within another header.
	 *
	 * @param parentElement
	 *            the parent element
	 * @param childElement
	 *            the child element
	 */
	public void mouseOverElementWithinElement(WebElement parentElement, WebElement childElement) {
		Actions actiondo = new Actions(Constants.getDriver());

		try {
			new WebDriverWait(Constants.getDriver(), 60).until(ExpectedConditions.visibilityOf(parentElement));
			actiondo.moveToElement(parentElement).build().perform();
			new WebDriverWait(Constants.getDriver(), 60).until(ExpectedConditions.visibilityOf(childElement));
			actiondo.moveToElement(childElement).click().build().perform();
		} catch (Exception e) {
			try {
				actiondo.moveToElement(parentElement).moveToElement(childElement).click().build().perform();
			} catch (Exception e1) {
				SetUpTest.test.log(LogStatus.ERROR, "Exception Occured: " + e1.getMessage());
			}
		}
	}

	/**
	 * Method to select values for dropdown fields.
	 *
	 * @param element
	 *            the element
	 * @param fieldValueKey
	 *            the field value key
	 * @param sheetName
	 *            the sheet name
	 */
	public void selectValueForDropdown(WebElement element, String fieldValueKey, String sheetName) {
		try {
			String title = null, name = null;
			name = getFieldValue(fieldValueKey, sheetName);
			SetUpTest.test.log(LogStatus.INFO, "Field Value: " + fieldValueKey + " Value: " + name);
			title = name;
			selectValue(element, title);
		} catch (Exception e) {
			SetUpTest.test.log(LogStatus.ERROR, "Exception Occured: " + e.getMessage());
		}
	}

	/**
	 * Select value.
	 *
	 * @param element
	 *            the element
	 * @param value
	 *            the value
	 */
	public void selectValue(WebElement element, String value) {
		Select selection;
		try {
			new WebDriverWait(Constants.getDriver(), 60).until(ExpectedConditions.visibilityOf(element));
			selection = new Select(element);
			selection.selectByVisibleText(value);
		} catch (Exception e) {
			try {
				selection = new Select(element);
				selection.selectByVisibleText(value);
			} catch (Exception e1) {
				SetUpTest.test.log(LogStatus.ERROR, "Exception Occured: " + e1.getMessage());
			}
		}
	}

	/**
	 * Method to get text from dropdown fields.
	 *
	 * @param element
	 *            the element
	 * @return the value from dropdown field
	 */
	public String getValueFromDropdownField(WebElement element) {
		String value = null;
		Select selection;
		try {
			new WebDriverWait(Constants.getDriver(), 60).until(ExpectedConditions.visibilityOf(element));
			selection = new Select(element);
			value = selection.getFirstSelectedOption().getText().trim();
		} catch (Exception e) {
			try {
				selection = new Select(element);
				value = selection.getFirstSelectedOption().getText().trim();
			} catch (Exception e1) {
				SetUpTest.test.log(LogStatus.ERROR, "Exception Occured: " + e1.getMessage());
			}
		}
		return value;
	}

	/**
	 * Method to enter values in text boxes from excel sheet.
	 *
	 * @param element
	 *            the element
	 * @param fieldValueKey
	 *            the field value key
	 * @param sheetName
	 *            the sheet name
	 * @return the string
	 */
	public String enterTextBoxValue(WebElement element, String fieldValueKey, String sheetName) {
		String title = null, name = null;
		try {
			element.clear();
			if (fieldValueKey.equals("CustomerName")) {
				title = getFieldValue(fieldValueKey, sheetName);
				name = title + "_" + RandomStringUtils.randomAlphabetic(7);
			} else if (fieldValueKey.equals("PID")) {
				name = RandomStringUtils.randomNumeric(6);
			} else if (fieldValueKey.equals("DataRetentionPeriod")) {
				title = getFieldValue(fieldValueKey, sheetName);
				name = title.substring(0, title.length() - 2);
				System.out.println("Retention ***** " + name);
			} else {
				name = getFieldValue(fieldValueKey, sheetName);
			}
			SetUpTest.test.log(LogStatus.INFO, "Field Value: " + fieldValueKey + " Value: " + name);
			title = name;
			enterText(element, title);
		} catch (Exception e) {
			SetUpTest.test.log(LogStatus.ERROR, "Exception Occured: " + e.getMessage());
		}
		return title;
	}

	/**
	 * Method to enter values in text boxes from properties file.
	 *
	 * @param config
	 *            the config
	 * @param key
	 *            the key
	 * @param element
	 *            the element
	 */
	public void enterTextBoxValue(String config, String key, WebElement element) {
		String title = null, name = null;
		try {
			element.clear();
			name = act.action(key, config);
			SetUpTest.test.log(LogStatus.INFO, "Field Value: " + key + " Value: " + name);
			title = name;
			enterText(element, title);
		} catch (Exception e) {
			SetUpTest.test.log(LogStatus.ERROR, "Exception Occured: " + e.getMessage());
		}
	}

	/**
	 * Enter Values in Textboxes.
	 *
	 * @param element
	 *            the element
	 * @param value
	 *            the value
	 */
	public void enterText(WebElement element, String value) {
		try {
			new WebDriverWait(Constants.getDriver(), 60).until(ExpectedConditions.visibilityOf(element));
			element.clear();
			element.sendKeys(value);
		} catch (Exception e) {
			SetUpTest.test.log(LogStatus.ERROR, "Exception Occured: " + e.getMessage());
		}

	}

	/**
	 * Method to get values from Text boxes.
	 *
	 * @param element
	 *            the element
	 * @return the text box value
	 */
	public String getTextBoxValue(WebElement element) {
		String value = null;
		try {
			new WebDriverWait(Constants.getDriver(), 60).until(ExpectedConditions.visibilityOf(element));
			value = element.getText().trim();
		} catch (Exception e) {
			try {
				value = element.getAttribute("value").trim();
			} catch (Exception e1) {
				SetUpTest.test.log(LogStatus.ERROR, "Exception Occured: " + e1.getMessage());
			}
		}
		return value;
	}

	/**
	 * Method to get values from excel sheet.
	 *
	 * @param fieldValueKey
	 *            the field value key
	 * @param sheetName
	 *            the sheet name
	 * @return the field value
	 */
	public String getFieldValue(String fieldValueKey, String sheetName) {
		String filePath = null;
		filePath = "src/main/resources/properties/FieldValues.xlsx";
		Map<String, String> map = new LinkedHashMap<String, String>();
		FileInputStream ExcelFile;
		try {
			ExcelFile = new FileInputStream(filePath);
			XSSFWorkbook ExcelWorkBook = new XSSFWorkbook(ExcelFile);
			XSSFSheet ExcelWSheet = ExcelWorkBook.getSheet(sheetName);
			int firstRowNum = ExcelWSheet.getFirstRowNum();
			int lastRowNum = ExcelWSheet.getLastRowNum();
			for (int i = firstRowNum + 1; i < lastRowNum; i++) {
				int j = 0;
				map.put(ExcelWSheet.getRow(i).getCell(j).getStringCellValue(),
						ExcelWSheet.getRow(i).getCell(++j).getStringCellValue());
			}
			ExcelWorkBook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map.get(fieldValueKey);
	}

	/**
	 * Wrapper method to click on any WebElement.
	 *
	 * @param element
	 *            the element
	 */
	public void clickElement(WebElement element) {
		try {
			new WebDriverWait(Constants.getDriver(), 60).until(ExpectedConditions.elementToBeClickable(element));
			element.click();
		} catch (Exception e) {
			try {
				element.click();
			} catch (Exception e1) {
				SetUpTest.test.log(LogStatus.ERROR, "Exception Occured: " + e1.getMessage());
			}
		}
	}

	/**
	 * Extract Numeric Part from any String.
	 *
	 * @param str
	 *            the str
	 * @return the linked list
	 */
	public LinkedList<String> extractNumFromString(String str) {
		LinkedList<String> list = new LinkedList<String>();
		char[] chars = new char[str.length()];
		int i = 0;
		for (int j = 0; j < str.length(); j++) {
			char c = str.charAt(j);
			if (Character.isDigit(c)) {
				chars[i++] = c;
				if (j != (chars.length) - 1) {
					continue;
				}
			}
			if (chars[0] == '\0') {
				continue;
			}
			String num = new String(chars).trim();
			list.add(num);
			// Re-index the values
			chars = new char[str.length()];
			i = 0;
		}
		SetUpTest.test.log(LogStatus.INFO, "List of numbers from String: " + list);
		return list;
	}

	/**
	 * Waiting Generic Method.
	 *
	 * @param timeInMilliseconds
	 *            the time in milliseconds
	 */
	public void wait_now(int timeInMilliseconds) {
		try {
			Thread.sleep(timeInMilliseconds);
		} catch (Exception e) {
			SetUpTest.test.log(LogStatus.ERROR, "Exception Occured: " + e.getMessage());
		}
	}

	/**
	 * Sets the default zoom.
	 */
	public void setDefaultZoom() {
		try {
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_0);
			robot.keyRelease(KeyEvent.VK_0);
			robot.keyRelease(KeyEvent.VK_CONTROL);
		} catch (AWTException e) {
			SetUpTest.test.log(LogStatus.ERROR, "Exception Occured: " + e.getMessage());
		}
	}

	/**
	 * Zoom in browser.
	 */
	public static void zoomInBrowser() {
		for (int i = 0; i < 2; i++) {
			try {
				Robot robot = new Robot();
				robot.keyPress(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_ADD);
				robot.keyRelease(KeyEvent.VK_SUBTRACT);
				robot.keyRelease(KeyEvent.VK_ADD);
			} catch (AWTException e) {
				SetUpTest.test.log(LogStatus.ERROR, "Exception Occured: " + e.getMessage());
			}
		}
	}

	/**
	 * Zoom out browser.
	 */
	public static void zoomOutBrowser() {
		for (int i = 0; i < 2; i++) {
			try {
				Robot robot = new Robot();
				robot.keyPress(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_SUBTRACT);
				robot.keyRelease(KeyEvent.VK_SUBTRACT);
				robot.keyRelease(KeyEvent.VK_CONTROL);
			} catch (AWTException e) {
				SetUpTest.test.log(LogStatus.ERROR, "Exception Occured: " + e.getMessage());
			}
		}
	}

	/**
	 * Checks if is empty download directory.
	 *
	 * @return true, if is empty download directory
	 */
	public boolean isEmptyDownloadDirectory() {
		boolean flag = false;
		File file = new File(Constants.reportDownloadPath);
		if (file.isDirectory()) {
			if (file.list().length > 0) {
				SetUpTest.test.log(LogStatus.INFO, "Download Directory is not Empty");
				flag = false;
			} else {
				SetUpTest.test.log(LogStatus.INFO, "Download Directory is empty");
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * Clean download directory contents.
	 *
	 * @return true, if successful
	 */
	public boolean cleanDownloadDirectoryContents() {
		boolean flag = false;
		File dir = new File(Constants.reportDownloadPath);
		if (dir.isDirectory()) {
			for (File file : dir.listFiles()) {
				if (!file.isDirectory()) {
					file.delete();
					SetUpTest.test.log(LogStatus.INFO, "Download Directory is not Empty");
					flag = false;
				}
			}
		}
		return flag;
	}

	/**
	 * Checks if is file present in download directory.
	 *
	 * @param fileName
	 *            the file name
	 * @return true, if is file present in download directory
	 */
	public boolean isFilePresentInDownloadDirectory(String fileName) {
		boolean flag = false;
		File file = new File(Constants.reportDownloadPath);
		if (isEmptyDownloadDirectory()) {
			SetUpTest.test.log(LogStatus.INFO, "Empty Directory! No File Found!");
			flag = false;
		} else {
			if (file.list().length > 0) {
				String[] ll = file.list();
				for (int i = 0; i < ll.length; i++) {
					if (ll[i].contains(fileName)) {
						SetUpTest.test.log(LogStatus.INFO, "There is a file with Filename :" + fileName);
						flag = true;
					}
				}
			}
		}
		return flag;
	}

	/**
	 * Scroll until text visible.
	 *
	 * @param str
	 *            the str
	 */
	public void scrollUntilTextVisible(String str) {
		((JavascriptExecutor) Constants.getDriver()).executeScript("arguments[0].scrollIntoView(true);", str);
		wait_now(1000);
	}

	/**
	 * Scroll until Element Visible.
	 *
	 * @param ele
	 *            the ele
	 */
	public void scrollUntilElementVisible(WebElement ele) {
		((JavascriptExecutor) Constants.getDriver()).executeScript("arguments[0].scrollIntoView(true);", ele);
		wait_now(1000);
	}

	/**
	 * Scroll using down arrow.
	 */
	public void scrollUsingDownArrow() {
		try {
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_DOWN);
			robot.keyPress(KeyEvent.KEY_RELEASED);
		} catch (Exception e) {
			SetUpTest.test.log(LogStatus.ERROR, "Exception Occured: " + e.getMessage());
		}
	}

	/**
	 * Assert value present.
	 *
	 * @param elements
	 *            the elements
	 * @param projectName
	 *            the project name
	 * @return true, if successful
	 */
	public boolean assertValuePresent(List<WebElement> elements, String projectName) {
		boolean flag = false;
		List<WebElement> ele = elements;
		Iterator<WebElement> itr = ele.iterator();
		while (itr.hasNext()) {
			WebElement ele1 = itr.next();
			String text = ele1.getText().trim();
			System.out.println("Received Text Value: " + text);
			if (text.equals(projectName)) {
				flag = true;
				break;
			} else {
				continue;
			}
		}
		return flag;
	}

	/**
	 * Compare excels.
	 *
	 * @param originalFile            the original file
	 * @param newFile            the new file
	 * @return true, if successful
	 */
	@SuppressWarnings({ "resource", "unchecked", "rawtypes" })
	public static boolean compareExcels(String originalFile, String newFile) {

		boolean flag = false;

		String origFile = null, comparingFile = null;
		origFile = "src/main/resources/compareReports/" + originalFile;
		comparingFile = "src/main/resources/compareReports/" + newFile;

		FileInputStream ExcelFileOrig = null, ExcelFileNew = null;
		try {
			ExcelFileOrig = new FileInputStream(origFile);
			ExcelFileNew = new FileInputStream(comparingFile);
			XSSFWorkbook ExcelWorkBookOrig = new XSSFWorkbook(ExcelFileOrig);
			XSSFWorkbook ExcelWorkBookNew = new XSSFWorkbook(ExcelFileNew);

			List arr1 = new TreeList();
			List arr2 = new TreeList();

			int noOfSheetsExcelOrig = ExcelWorkBookOrig.getNumberOfSheets();
			int noOfSheetsExcelNew = ExcelWorkBookNew.getNumberOfSheets();

			SetUpTest.test.log(LogStatus.INFO, "Total Sheets in Original Excel: " + noOfSheetsExcelOrig);
			SetUpTest.test.log(LogStatus.INFO, "Total Sheets in Comparing Excel: " + noOfSheetsExcelNew);

			for (int i = 0; i < noOfSheetsExcelOrig; i++) {
				XSSFSheet ExcelWSheetOrig = ExcelWorkBookOrig.getSheetAt(i);
				Iterator<Row> rows = ExcelWSheetOrig.rowIterator();
				while (rows.hasNext()) {
					Row row = rows.next();
					Iterator<Cell> cells = row.cellIterator();
					while (cells.hasNext()) {
						Cell cell = cells.next();

						switch (cell.getCellTypeEnum()) {
						case NUMERIC:
							arr1.add(cell.getNumericCellValue());
							break;
						case STRING:
							arr1.add(cell.getStringCellValue());
							break;
						case BOOLEAN:
							arr1.add(cell.getBooleanCellValue());
							break;
						default:
							break;
						}
					}
				}
			}
			// SetUpTest.test.log(LogStatus.INFO, "Array 1 - Original Excel Entries: " +
			// arr1);

			for (int i = 0; i < noOfSheetsExcelNew; i++) {
				XSSFSheet ExcelWSheetNew = ExcelWorkBookNew.getSheetAt(i);
				Iterator<Row> rows = ExcelWSheetNew.rowIterator();
				while (rows.hasNext()) {
					Row row = rows.next();
					Iterator<Cell> cells = row.cellIterator();
					while (cells.hasNext()) {
						Cell cell = cells.next();

						switch (cell.getCellTypeEnum()) {
						case NUMERIC:
							arr2.add(cell.getNumericCellValue());
							break;
						case STRING:
							arr2.add(cell.getStringCellValue());
							break;
						case BOOLEAN:
							arr2.add(cell.getBooleanCellValue());
							break;
						default:
							break;
						}
					}
				}
			}

			// SetUpTest.test.log(LogStatus.INFO, "Array 2 - New Excel Entries : " + arr2);

			if (arr1.equals(arr2)) {
				flag = true;
			} else {
				flag = false;
			}

			SetUpTest.test.log(LogStatus.INFO, "IS ORIGINAL AND NEW EXCEL SAME?? " + flag);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * Compare excel sheets.
	 *
	 * @param originalFile
	 *            the original file
	 * @param newFile
	 *            the new file
	 */
	@SuppressWarnings("resource")
	public static void compareExcelSheets(String originalFile, String newFile) {
		try {
			// get input excel files
			FileInputStream excellFile1 = new FileInputStream(
					new File("src/main/resources/compareReports/" + originalFile));
			FileInputStream excellFile2 = new FileInputStream(new File("src/main/resources/compareReports/" + newFile));

			// Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook1 = new XSSFWorkbook(excellFile1);
			XSSFWorkbook workbook2 = new XSSFWorkbook(excellFile2);

			int noOfSheetsExcelOrig = workbook1.getNumberOfSheets();
			int noOfSheetsExcelNew = workbook2.getNumberOfSheets();

			SetUpTest.test.log(LogStatus.INFO, "Total Sheets in Original Excel: " + noOfSheetsExcelOrig);
			SetUpTest.test.log(LogStatus.INFO, "Total Sheets in Comparing Excel: " + noOfSheetsExcelNew);

			for (int i = 0; i < noOfSheetsExcelOrig; i++) {
				XSSFSheet sheet1 = workbook1.getSheetAt(i);
				XSSFSheet sheet2 = workbook2.getSheetAt(i);

				// Compare sheets
				if (compareTwoSheets(sheet1, sheet2)) {
					SetUpTest.test.log(LogStatus.PASS,
							"\n\nThe two excel sheets are Equal for Sheet: " + workbook1.getSheetName(i));
				} else {
					SetUpTest.test.log(LogStatus.FAIL,
							"\n\nThe two excel sheets are Not Equal for Sheet: " + workbook1.getSheetName(i));
				}
			}

			// close files
			excellFile1.close();
			excellFile2.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Compare two sheets.
	 *
	 * @param sheet1
	 *            the sheet 1
	 * @param sheet2
	 *            the sheet 2
	 * @return true, if successful
	 */
	// Compare Two Sheets
	public static boolean compareTwoSheets(XSSFSheet sheet1, XSSFSheet sheet2) {
		int firstRow1 = sheet1.getFirstRowNum();
		int lastRow1 = sheet1.getLastRowNum();
		boolean equalSheets = true;
		SetUpTest.test.log(LogStatus.INFO, "Total rows in Sheet1: " + sheet1.getPhysicalNumberOfRows());
		for (int i = firstRow1; i <= lastRow1; i++) {

			SetUpTest.test.log(LogStatus.INFO, "\n\nComparing Row " + i);

			XSSFRow row1 = sheet1.getRow(i);
			XSSFRow row2 = sheet2.getRow(i);
			if (!compareTwoRows(row1, row2)) {
				equalSheets = false;
				SetUpTest.test.log(LogStatus.ERROR, "Row " + i + " of sheet " + sheet1.getSheetName() + " - Not Equal");
				break;
			} else {
				equalSheets = true;
			}
		}
		return equalSheets;
	}

	/**
	 * Compare two rows.
	 *
	 * @param row1
	 *            the row 1
	 * @param row2
	 *            the row 2
	 * @return true, if successful
	 */
	// Compare Two Rows
	public static boolean compareTwoRows(XSSFRow row1, XSSFRow row2) {
		if ((row1 == null) && (row2 == null)) {
			return true;
		} else if ((row1 == null) || (row2 == null)) {
			return false;
		}

		int firstCell1 = row1.getFirstCellNum();
		int lastCell1 = row1.getLastCellNum();
		boolean equalRows = true;
		SetUpTest.test.log(LogStatus.INFO, "Total columns in Row: " + row1.getPhysicalNumberOfCells());

		// Compare all cells in a row
		for (int i = firstCell1; i <= lastCell1; i++) {
			XSSFCell cell1 = row1.getCell(i);
			XSSFCell cell2 = row2.getCell(i);
			if (!compareTwoCells(cell1, cell2)) {
				equalRows = false;
				SetUpTest.test.log(LogStatus.ERROR, "      Cell " + i + " of row " + row1.getRowNum() + " - Not Equal");
				break;
			} else {
				equalRows = true;
			}
		}
		return equalRows;
	}

	/**
	 * Compare two cells.
	 *
	 * @param cell1
	 *            the cell 1
	 * @param cell2
	 *            the cell 2
	 * @return true, if successful
	 */
	// Compare Two Cells
	public static boolean compareTwoCells(XSSFCell cell1, XSSFCell cell2) {
		if ((cell1 == null) && (cell2 == null)) {
			return true;
		} else if ((cell1 == null) || (cell2 == null)) {
			return false;
		}

		boolean equalCells = false;
		CellType type1 = cell1.getCellTypeEnum();
		CellType type2 = cell2.getCellTypeEnum();
		if (type1 == type2) {
			if (cell1.getCellStyle().equals(cell2.getCellStyle())) {
				// Compare cells based on its type
				switch (cell1.getCellTypeEnum()) {
				case FORMULA:
					if (cell1.getCellFormula().equals(cell2.getCellFormula())) {
						equalCells = true;
					}
					break;
				case NUMERIC:
					if (cell1.getNumericCellValue() == cell2.getNumericCellValue()) {
						equalCells = true;
					}
					break;
				case STRING:
					if (cell1.getStringCellValue().equals(cell2.getStringCellValue())) {
						equalCells = true;
					}
					break;
				case BLANK:
					if (cell2.getCellTypeEnum() == CellType.BLANK) {
						equalCells = true;
					}
					break;
				case BOOLEAN:
					if (cell1.getBooleanCellValue() == cell2.getBooleanCellValue()) {
						equalCells = true;
					}
					break;
				case ERROR:
					if (cell1.getErrorCellValue() == cell2.getErrorCellValue()) {
						equalCells = true;
					}
					break;
				default:
					if (cell1.getStringCellValue().equals(cell2.getStringCellValue())) {
						equalCells = true;
					}
					break;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
		return equalCells;
	}
}
