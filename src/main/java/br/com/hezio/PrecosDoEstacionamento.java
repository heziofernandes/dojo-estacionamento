package br.com.hezio;

public enum PrecosDoEstacionamento {
	DIARIA(20),
	HORA_ADICIONAL(1),
	ATE_3_HORAS(4);

	private int valor;

	private PrecosDoEstacionamento(int valor) {
		this.valor = valor;
	}

	int getValor() {
		return this.valor;
	}
}
