package cs198.cis2;

public class FileStats {
	  private long id;
	  private String userid;
	  private String filename;
	  private String type;
	  private String conf;
	  
	  public long getId() {
	    return id;
	  }

	  public void setId(long id) {
	    this.id = id;
	  }
	  
	  public String getUserId() {
		    return userid;
	  }

	  public void setUserId(String userid) {
	    this.userid = userid;
	  }

	  public String getFileName() {
	    return filename;
	  }

	  public void setFileName(String f) {
	    this.filename = f;
	  }
	  
	  public String getType() {
	    return type;
	  }

	  public void setType(String type) {
	    this.type = type;
	  }
	  
	  public String getConf() {
	    return conf;
	  }

	  public void setConf(String conf) {
	    this.conf = conf;
	  }

	  // Will be used by the ArrayAdapter in the ListView
	  @Override
	  public String toString() {
	    return userid+" "+id+".jpg "+type+" "+conf;
	  }
	} 