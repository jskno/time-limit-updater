package eu.euipo.tools.em.config.filenet;

import filenet.vw.api.VWException;
import filenet.vw.api.VWSession;
import org.apache.log4j.Logger;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@ConfigurationProperties(prefix = "filenet")
public class P8Connection {

	/** The log. */
	private static Logger log = Logger.getLogger(P8Connection.class);
	
	private String userName;
	private String password;
	private String connectionPoint;
	private String ceURI;
	private String stanza;

	private VWSession vwSession;

	public P8Connection() {}

	@PostConstruct
	public void initialize() throws VWException {
		log.debug(userName + " on " + ceURI);
		CEConnect ceConnect = new CEConnect(ceURI, stanza);
		ceConnect.logon(userName, password);
		vwSession = new VWSession();
		vwSession.setBootstrapCEURI(ceURI);
		vwSession.logon(userName, password, connectionPoint);
	}

	public VWSession getPESession() {
		return vwSession;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConnectionPoint() {
		return connectionPoint;
	}

	public void setConnectionPoint(String connectionPoint) {
		this.connectionPoint = connectionPoint;
	}

	public String getCeURI() {
		return ceURI;
	}

	public void setCeURI(String ceURI) {
		this.ceURI = ceURI;
	}

	public String getStanza() {
		return stanza;
	}

	public void setStanza(String stanza) {
		this.stanza = stanza;
	}

	public void logoff() {
		try {
			if(vwSession.isLoggedOn()){
				vwSession.logoff();
			}
		} catch (VWException e) {
			log.error(e);
		}
	}




}
