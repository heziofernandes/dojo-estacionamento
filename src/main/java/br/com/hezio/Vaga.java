package br.com.hezio;

import java.util.Date;

public class Vaga {

	private final Carro carro;
	private final Date horaDeEntrada;

	public Vaga(Carro carro, Date horaDeEntrada) {
		this.carro = carro;
		this.horaDeEntrada = horaDeEntrada;
	}

	public boolean estaNessaVaga(Carro carro) {
		return this.carro.equals(carro);
	}

	public Date getHoraDeEntrada() {
		return this.horaDeEntrada;
	}

}
