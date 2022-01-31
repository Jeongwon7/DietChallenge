package dietchallenge;

public class DietpenaltyVO {
	private int pbno;
	private String pname;
	   
	   public int getPbno() {
	      return pbno;
	   }
	   public void setPbno(int pbno) {
	      this.pbno = pbno;
	   }
	   public String getPname() {
	      return pname;
	   }
	   public void setPname(String pname) {
	      this.pname = pname;
	   }
	   @Override
	public String toString() {
		return pname;
	}

}
