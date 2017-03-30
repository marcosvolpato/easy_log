/**
 * 
 */
package easyLog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Calendar;

/**
 * 
 * <h1>Print log messages in files easily</h1>
 * <p>O EasyLog offer a easy interface to print log messages in disc</p>
 * <p>Whether to debug applications or to track program performance.</p>
 * 
 * <h2>Settings</h2>
 * <p><strong>The available settings methods</strong></p>
 * <ul>
 * <li><code>setHeader(String);</code> -> to set the header message</li>
 * <li><code>setUseTraceData(boolean);</code> -> to set if must show the line number and class name in the message</li>
 * <li><code>setIsPrintOnTop(boolean);</code> -> to set if the message should be printed at the top of file</li>
 * <li><code>setHeaderLayout(boolean);</code> -> if the header should be displayed</li>
 * <li><code>setLineLimit(int);</code> -> to set the maximum number of line that file should store</li>
 * </ul>
 * <br>
 * <br>
 * <h2>Helpers</h2>
 * <p><strong>in case useTraceData is false and user wants to customize the message, he has a few helpers to get the data from stack trace</strong></p>
 * <ul>
 * <li><code>EasyLog.logData();</code> -> return the line number and class name</li>
 * <li><code>EasyLog.getClassName();</code> -> return the class name</li>
 * <li><code>EasyLog.getLineNum();</code> -> return the line number</li>
 * </ul>
 * <br>
 * <br>
 * <h2>Usage</h2>
 * 
 * <p><h3>EasyLog settings:</h3></p>
 * 
 * <p><code>EasyLog lp = new EasyLog("C:/your/path/to/file.txt");</code></p>
 * <p><code>lp.setHeaderLayout(false);</code></p>
 * <p><code>lp.setIsPrintOnTop(true);</code></p>
 * 
 * <br>
 * 
 * <p><h3>Basic usage EasyLog:</h3></p>
 * 
 * <p><code>lp.printLog("Line debug test");</code></p>
 * 
 * <p><strong>this will generate this line in file.txt e. g.:</strong></p>
 * 
 * <p><code>Thu Mar 30 11:18:18 BRT 2017 normal : Line debug test - at easyLog.Main.main(Main.java:13)</code></p>
 * 
 * <br>
 * 
 * <p><code>lp.printLog("Line debug test", false);</code></p>
 * <p><strong>this will generate the same line in file.txt but the second parameter says 
 * that append is false so this delete all data existent before:</strong></p>
 * 
 * <br>
 * 
 * <p><code>lp.printLog("Line debug test", EasyLog.LogType.error);</code></p>
 * <p><strong>this will generate this line in file.txt e. g.:</strong></p>
 * <p><code>Thu Mar 30 11:18:18 BRT 2017 error  : Line debug test - at easyLog.Main.main(Main.java:13)</code></p>
 *
 * <p><strong>notice that the type message was changed from normal to error. The available types are:</strong></p>
 * <ul>
 * <li>EasyLog.LogType.normal for default messages</li>
 * <li>EasyLog.LogType.debug for debugging messages</li>
 * <li>EasyLog.LogType.warning for warning messages</li>
 * <li>EasyLog.LogType.error for error messages</li>
 * </ul>
 * 
 * <br>
 * 
 * <p><code>lp.printLog("Line debug test", EasyLog.LogType.error, false);</code></p>
 * <p><strong>this will generate the same line in file.txt but the third parameter says 
 * that append is false so this delete all data existent before:</strong></p>
 * 
 * 
 * 
 * 
 * <br>
 * <br>
 * <br>
 * <br>
 * 
 * @author Marcos V. Volpato
 * @version 1.0.0
 * @since   2017-03-29
 *
 */
public final class EasyLog {
	private File file;
	private String header;
	private int lineLimit;
	private int currentLinePosition;
	private boolean isHeaderLayout;
	private boolean isHeaderPrinted;
	private boolean isPrintOnTop;
	private boolean useTraceData;
	private Calendar calendar;
	
	public EasyLog(String path){
		file = new File (path);
		calendar = Calendar.getInstance();
		header =  "======================= ";
		header += calendar.getTime()+" ";
		header += "=======================";
		lineLimit = 3000;
		currentLinePosition = 0;
		isHeaderLayout = true;
		isHeaderPrinted = false;
		isPrintOnTop = false;
		useTraceData = true;
	}
	
	
	
	/**
	 * This is the basic print method and assume the LogType as normal and append as true
	 * @param str the message that must be printed
	 * @since 1.0.0
	 */
	public void printLog(String str){
	    this.printPrepare(strTypped(str, LogType.normal), true);
	}
	
	/**
	 * This is a print log message method
	 * @param str the message that must be printed
	 * @param logType the int that indicates the type of the log message
	 * @since 1.0.0
	 */
	public void printLog(String str, int logType){
	    this.printPrepare(strTypped(str, logType), true);
	}
	
	/**
	 * This is a print log message method
	 * @param str the message that must be printed
	 * @param append the boolean that says if the old data in file must be deleted
	 */
	public void printLog(String str, boolean append){
	    this.printPrepare(strTypped(str, LogType.normal), append);
	}
	
	/**
	 * This is a print log message method
	 * @param str the message that must be printed
	 * @param logType the int that indicates the type of the log message
	 * @param append the boolean that says if the old data in file must be deleted
	 */
	public void printLog(String str, int logType, boolean append){
		this.printPrepare(strTypped(str, logType), append);
	}
	
	
	
	/**
	 * to access e. g. EasyLog.LogType.error
	 * @since 1.0.0
	 */
	public interface LogType{
		int normal = 1;
		int debug = 2;
		int warning = 3;
		int error = 4;
	}
	
	/**
	 * Setter for the header message displayed if isHeaderLayout is equals to true.
	 * Default: "======================= weekday month day hours BRT year ======================="
	 * @param header the new header message.
	 * @since 1.0.0
	 */
	public void setHeader(String header) {
		this.header = header;
	}
	
	/**
	 * Setter that change the maximum line limit to the file
	 * Default: 3000
	 * @param lineLimit
	 * @since 1.0.0
	 */
	public void setLineLimit(int lineLimit) {
		this.lineLimit = lineLimit;
	}
	
	/**
	 * Setter that says if the print layout should use the header line message
	 * Default: true
	 * @param isHeaderLayout
	 * @since 1.0.0
	 */
	public void setHeaderLayout(boolean isHeaderLayout) {
		this.isHeaderLayout = isHeaderLayout;
	}
	
	/**
	 * Setter that indicates if the application must print in the top of the file
	 * Default: false
	 * @param isPrintOnTop
	 * @since 1.0.0
	 */
	public void setIsPrintOnTop(boolean isPrintOnTop) {
		this.isPrintOnTop = isPrintOnTop;
	}
	public void setUseTraceData(boolean useTraceData) {
		this.useTraceData = useTraceData;
	}
	
	/**
	 * This is used only to intermediate the real print methods checking the variables in conditionals flows
	 * @param str The String to be printed as message.
	 * @param append A boolean that indicate if the remaining data must be preserved or deleted
	 * @since 1.0.0
	 */
	private void printPrepare(String str, boolean append){
		if(isHeaderLayout){
			if(!isHeaderPrinted){
				if(!isPrintOnTop){
					// é para imprimir normal
					print(header, append);
					isHeaderPrinted = true;
					print(str, true);
				}else{
					//é para imprimir no topo
					printOnTop(header, currentLinePosition);
					currentLinePosition++;
					isHeaderPrinted = true;
					printOnTop(str, currentLinePosition);
					currentLinePosition++;
				}
				
			}else{
				if(append){
					if(!isPrintOnTop){
						print(str, append);
					}else{
						//é para imprimir no topo
						printOnTop(str, currentLinePosition);
						currentLinePosition++;
						
					}
				}else{
					if(!isPrintOnTop){
						print(header, append);
						isHeaderPrinted = true;
						print(str, true);
					}else{
						//é para imprimir no topo
						print(header, append);
						isHeaderPrinted = true;
						print(str, true);
						
					}
				}
			}
		}else{
			if(!isPrintOnTop){
				print(calendar.getTime()+" "+str, append);
			}else{
				//é para imprimir no topo
				if(append)
					printOnTop(calendar.getTime()+" "+str, 0);
				else
					print(calendar.getTime()+" "+str, append);
			}
		}
		checkStackedLineLimit();
	}
	
	/**
	 * This is the standard method to print log in the file. It print a new message in the end of the file 
	 * and should be cheaper in computer resources
	 * @param str The String to be printed in of the file.
	 * @param append A boolean that indicate if the remaining data must be preserved or deleted
	 * @since 1.0.0
	 */
	private void print(String str, boolean append){
		PrintWriter printWriter;
		try {
			if(useTraceData)
				str = str+lineOut(5);
			file.createNewFile();
			printWriter = new PrintWriter (new FileOutputStream(file, append));
			printWriter.println (str);
			printWriter.close ();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	/**
	 * This is used to print the message in the top of the file and being able to preserve the data that already is there.
	 * Obs.: Use only if append is true.
	 * Obs.2: The use of this method should be more expensive than standard print method.
	 * @param str The String to be printed in the top of the file.
	 * @param position a int that indicates the line that the message may be inserted.
	 * @since 1.0.0
	 */
	private void printOnTop(String str, int position){
		try {
			FileInputStream fis = new FileInputStream(file);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			String result = "";
			String line = "";
			String newLineChar = System.getProperty("line.separator");
			if(isHeaderLayout ){
				if(isHeaderPrinted){
					if(useTraceData)
						str = str+lineOut(5);
				}
			}else{
				if(useTraceData)
					str = str+lineOut(5);
			}
			int counter = 0;
			
			while( (line = br.readLine()) != null){
				if(counter == position){
					result = result + str + newLineChar + line + newLineChar;
				}else{
					result = result + line + newLineChar; 
				}
				counter++;
			}
			br.close();
	
//			result = str + result;
	
			file.delete();
			FileOutputStream fos;
		
			fos = new FileOutputStream(file);
			
			fos.write(result.getBytes());
			fos.flush();
			fos.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * This run the file informed in constructor method to count all the lines existing there
	 * @return int that represent the total of lines in the file
	 * @since 1.0.0
	 */
	private int getTotalLines(){
		int counter = 0;
		try {
			FileInputStream fis = new FileInputStream(file);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			String line = "";
			
			while( (line = br.readLine()) != null){
				counter++;
			}
//			System.out.println(counter);
			
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return counter;
		
		
	}
	/**
	 * This method check if the total of lines in file is bigger than the lineLimit and delete the over lines
	 * @since 1.0.0
	 */
	private void checkStackedLineLimit(){
		int totalLines = getTotalLines();
		if(totalLines > lineLimit){
			try {
				FileInputStream fis = new FileInputStream(file);
				BufferedReader br = new BufferedReader(new InputStreamReader(fis));
				String result = "";
				String line = "";
				String newLineChar = System.getProperty("line.separator");
				int counter = 0;
				if(isPrintOnTop){
					while( (line = br.readLine()) != null){
						if(counter < lineLimit)
							result = result + line + newLineChar;
						else
							break;
						counter++;
					}
				}else{
					int diferenca = totalLines - lineLimit;
					while( (line = br.readLine()) != null){
						if(counter >= diferenca)
							result = result + line + newLineChar;
						counter++;
					}
				}
				br.close();
				file.delete();
				FileOutputStream fos;
			
				fos = new FileOutputStream(file);
				
				fos.write(result.getBytes());
				fos.flush();
				fos.close();
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * This is used to decorate the message with the type of message informed in the printLog method 
	 * @param str the String to be printed
	 * @param logType The int that represents the type of message
	 * @return String decorated with the type of message
	 * @since 1.0.0
	 */
	private String strTypped(String str, int logType){
		switch (logType) {
	        case LogType.normal:
	            return "normal : "+str;
	        case LogType.debug:
	            return "debug  : "+str;
	        case LogType.warning:
	            return "warning: "+str;
	        case LogType.error:
	            return "error  : "+str;
	    }
		return str;
	}
	
	/**
	 * This is used when the user wants to customize the own additional message data.
	 * @return String containing the class and the line number of the place this method is called.
	 * @since 1.0.0
	 */
	public static String logData(){
		return " -> "+Thread.currentThread().getStackTrace()[2].getClassName()+" : "+Thread.currentThread().getStackTrace()[2].getLineNumber();
	}
	
	/**
	 * This is used when the user wants to customize the own additional message data.
	 * @return int that represents the line number of the place this method is called
	 * @since 1.0.0
	 */
	public static int getLineNum() {
	    return Thread.currentThread().getStackTrace()[2].getLineNumber();
	}
	
	/**
	 * This is used when the user wants to customize the own additional message data.
	 * @return String containing the class of the place this method is called.
	 * @since 1.0.0
	 */
	public static String getClassName() {
	    return Thread.currentThread().getStackTrace()[2].getClassName();
	}
	
	/**
	 * This is used to generate the details data from the place that user id calling the printLog.
	 * @param level is a int that says which level in the stack trace you want to access.
	 * @return a String that contain the data details of level informed in the level parameter.
	 * @since 1.0.0
	 */
	private static String lineOut(int level) {
//	    int level = 3;
	    StackTraceElement[] traces;
	    traces = Thread.currentThread().getStackTrace();
	    return (" - at "  + traces[level] + " " );
	}
	
}
