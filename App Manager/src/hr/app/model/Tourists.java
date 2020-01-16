package hr.app.model;

public class Tourists {
	
	private int id;
	private String name;
	private String country;
	private String city;
	private int numberOfAdults;
	private int numberOfChildren;
	private int numberOfPersons;
	private String email;
	private String phoneNumber;
	private boolean pets;
	private String touristsNote;
	
	
	
	public Tourists(int id, String name, String country,
			String city, int numberOfAdults, int numberOfChildren,
			int numberOfPersons, String email, String phoneNumber,
			boolean pets, String touristsNote) {
		this.id = id;
		this.name = name;
		this.country = country;
		this.city = city;
		this.numberOfAdults = numberOfAdults;
		this.numberOfChildren = numberOfChildren;
		this.numberOfPersons = numberOfPersons;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.pets = pets;
		this.touristsNote = touristsNote;
	}

	public Tourists(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public int getId() {
		return id;
	}

	public String getCountry() {
		return country;
	}

	public String getCity() {
		return city;
	}

	public int getNumberOfAdults() {
		return numberOfAdults;
	}

	public int getNumberOfChildren() {
		return numberOfChildren;
	}

	public int getNumberOfPersons() {
		return numberOfPersons;
	}

	public String getEmail() {
		return email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public boolean isPets() {
		return pets;
	}

	public String getTouristsNote() {
		return touristsNote;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setNumberOfAdults(int numberOfAdults) {
		this.numberOfAdults = numberOfAdults;
	}

	public void setNumberOfChildren(int numberOfChildren) {
		this.numberOfChildren = numberOfChildren;
	}

	public void setNumberOfPersons(int numberOfPersons) {
		this.numberOfPersons = numberOfPersons;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setPets(boolean pets) {
		this.pets = pets;
	}

	public void setTouristsNote(String touristsNote) {
		this.touristsNote = touristsNote;
	}

	@Override
	public String toString() {
		return "Tourists [name=" + name + "]";
	}
	
	
	
	

}
