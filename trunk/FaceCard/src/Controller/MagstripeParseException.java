package Controller;

/**
 * Exception thrown during parsing of magstripe data string.
 *
 *@author <a href="http://www.acmetech.com/">Wayne K. Walrath, Acme Technologies</a>
 *
 * <br /><br />
 * <font size="-1">Copyright 2005 &copy; Acme Technologies. All rights reserved. Permission granted to
 * incorporate these classes into non-commercial (shareware applications ARE allowed)
 * derivative works. You may not post these classes on the internet or otherwise distribute
 * them EXCEPT as part of a derivative work which includes the Acme Technologies copyright
 * and the address of our website (www.acmetech.com).
 * <br /><br />For an easy flat-fee redistribution license for commercial use, please contact
 * Acme Technologies through one of the means listed 
 * at <a href="http://www.acmetech.com/">www.acmetech.com</a>.
 * </font>
 */
public class MagstripeParseException extends Exception
{

	/**
	 * Default constructor.
	 */
	public MagstripeParseException(String error){
		super(error);
	}


}
