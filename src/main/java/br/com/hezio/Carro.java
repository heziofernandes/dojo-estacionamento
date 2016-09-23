package br.com.hezio;

public class Carro {

	private final String placa;

	public Carro(String placa) {
		this.placa = placa;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.placa == null) ? 0 : this.placa.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Carro other = (Carro) obj;
		if (this.placa == null) {
			if (other.placa != null) {
				return false;
			}
		} else if (!this.placa.equals(other.placa)) {
			return false;
		}
		return true;
	}

	public String getPlaca() {
		return this.placa;
	}

}
