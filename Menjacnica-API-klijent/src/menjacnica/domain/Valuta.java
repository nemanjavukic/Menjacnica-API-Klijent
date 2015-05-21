package menjacnica.domain;

public class Valuta {
private String naziv;
private double kurs;

public int hashCode() {
	final int prime = 31;
	int result = 1;
	long temp;
	temp = Double.doubleToLongBits(kurs);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	result = prime * result + ((naziv == null) ? 0 : naziv.hashCode());
	return result;
}

public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	Valuta other = (Valuta) obj;
	if (Double.doubleToLongBits(kurs) != Double.doubleToLongBits(other.kurs))
		return false;
	if (naziv == null) {
		if (other.naziv != null)
			return false;
	} else if (!naziv.equals(other.naziv))
		return false;
	return true;
}
public String getNaziv() {
	return naziv;
}
public void setNaziv(String naziv) {
	this.naziv = naziv;
}
public double getKurs() {
	return kurs;
}
public void setKurs(double kurs) {
	this.kurs = kurs;
}

}
