package dietchallenge;

public class DietmemberVO {
	private int bno;
	private String name;
	private String gender;
	private int age;
	private double height;
	private double weight;
	private double rcmdcalories;
	private double totvariation;
	private double pweight;		
		
	public double getPweight() {
		return pweight;
	}
	public void setPweight(double pweight) {
		this.pweight = pweight;
	}
	public double getTotvariation() {
			return totvariation;
	}
	public void setTotvariation(double totvariation) {
			this.totvariation = totvariation;
	}
	   
	public int getBno() {
		return bno;
	}
	public void setBno(int bno) {
		this.bno = bno;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public double getRcmdcalories() {
		return rcmdcalories;
	}
	public void setRcmdcalories(double rcmdcalories) {
		this.rcmdcalories = rcmdcalories;
	}
	   
	
	

}
