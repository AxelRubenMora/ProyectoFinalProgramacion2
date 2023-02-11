package doll;

public class Contact {
	private String name;
	private String mail;
	private int phoneNumber;
	
	public Contact(String name,int phoneNumber,String mail) {
		this.name=name;
		this.mail=mail;
		this.phoneNumber=phoneNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public int getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
}
