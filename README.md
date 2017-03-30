# easy_log
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