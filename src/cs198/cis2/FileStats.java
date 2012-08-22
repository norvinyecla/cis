package cs198.cis2;

public class FileStats {
	  private long id;
	  private String type;
	  private String conf;
	  public long getId() {
	    return id;
	  }

	  public void setId(long id) {
	    this.id = id;
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
	    return id+".jpg "+type+" "+conf;
	  }
	} 