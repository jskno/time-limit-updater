package eu.euipo.tools.em.config.filenet;

import com.filenet.api.core.Connection;
import com.filenet.api.core.Factory;
import com.filenet.api.util.UserContext;

import javax.security.auth.Subject;

/**
 * The Class CEConnect.
 */
public class CEConnect {

	//Connection Parameters
	/** The stanza. */
	private String stanza 	= null;
	
	/** The conn. */
	private Connection conn = null;
	
	/** The uc. */
	private UserContext uc 	= null;
	
	/** The ce uri. */
	private String ceUri	= null;
	
	
	/**
	 * Connects To URI With Default Stanza.
	 *
	 * @param ceUri the ce uri
	 */
	public CEConnect(String ceUri){
	    this.stanza = "FileNetP8WSI";
	    this.ceUri  = ceUri;
	    connect();
	}
	
	/**
	 * Connects To URI With Given Stanza.
	 *
	 * @param ceUrl the ce url
	 * @param stanza the stanza
	 */
	public CEConnect(String ceUrl, String stanza){
	    this.stanza = stanza;
	    this.ceUri  = ceUrl;
	    connect();
	}
	
	/**
	 * Connect.
	 */
	private void connect(){
		conn = Factory.Connection.getConnection(this.ceUri);
		uc 	 = UserContext.get();
	}
	
	/**
	 * Logon.
	 *
	 * @param userName the user name
	 * @param password the password
	 */
	public void logon(String userName, String password){
		Subject subject = UserContext.createSubject(conn, userName, password, this.stanza);
		uc.pushSubject(subject);
	}
	
	/**
	 * Gets the cE uri.
	 *
	 * @return the cE uri
	 */
	public String getCEUri(){
		return this.ceUri;
	}
	
	/**
	 * Gets the connection.
	 *
	 * @return the connection
	 */
	public Connection getConnection(){
		return conn;
	}
	
	/**
	 * Pop subject.
	 */
	public void popSubject(){
		uc.popSubject();
	}
}
