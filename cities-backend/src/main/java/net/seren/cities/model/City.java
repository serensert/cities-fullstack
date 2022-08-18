package net.seren.cities.model;

import javax.persistence.*;

@Entity
@Table(name = "cities")
public class City {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "name")
	private String name;

	@Column(name = "version")
	private long version;

	@Column(name = "extension")
	private String extension;

	public City() {

	}

	public City(String name, long version) {
		this.name = name;
		this.version = version;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	@Override
	public String toString() {
		return "City [id=" + id + ", name=" + name + ", version=" + version + ", extension=" + extension + "]";
	}

}
