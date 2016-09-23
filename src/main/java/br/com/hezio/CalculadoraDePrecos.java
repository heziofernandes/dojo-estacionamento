package br.com.hezio;

public class CalculadoraDePrecos {

	private final int TEMPO_MINIMO_PARA_COBRAR = 3;
	private final int TEMPO_DA_DIARIA = 6;

	public int calculaValorDoEstacionamento(int numeroDeHorasQuePermaneceu) {
		if (numeroDeHorasQuePermaneceu <= TEMPO_MINIMO_PARA_COBRAR) {
			return PrecosDoEstacionamento.ATE_3_HORAS.getValor();
		}
		if (numeroDeHorasQuePermaneceu >= TEMPO_DA_DIARIA) {
			return PrecosDoEstacionamento.DIARIA.getValor();
		}
		return ((numeroDeHorasQuePermaneceu - TEMPO_MINIMO_PARA_COBRAR) * PrecosDoEstacionamento.HORA_ADICIONAL.getValor())
				+ PrecosDoEstacionamento.ATE_3_HORAS.getValor();
	}

}
