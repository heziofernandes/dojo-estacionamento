package br.com.hezio;

import java.util.Calendar;
import java.util.Date;

public class Estacionamento {

	private ControleDeVagas controleDeVagas;

	private static final int HORA_DE_FECHAMENTO = 22;
	private static final int HORA_DE_ABERTURA = 8;
	private static final int TOTAL_DE_VAGAS = 500;

	private final Periodo periodoDeFuncionamento;
	private final CalculadoraDePrecos tabelaDePrecos;

	public Estacionamento() {
		controleDeVagas = new ControleDeVagas(TOTAL_DE_VAGAS);
		Date horaDeAbertura = configuraHora(HORA_DE_ABERTURA);
		Date horaDeFechamento = configuraHora(HORA_DE_FECHAMENTO);
		periodoDeFuncionamento = new Periodo(horaDeAbertura, horaDeFechamento);
		tabelaDePrecos = new CalculadoraDePrecos();
	}

	private Date configuraHora(int hora) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, hora);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	public int getVagasDisponiveis() {
		return controleDeVagas.getVagasDisponiveis();
	}

	public void entrouUmCarro(Carro carro) throws CarroDuplicadoException, EstacionamentoLotadoException {
		if (controleDeVagas.existe(carro)) {
			throw new CarroDuplicadoException();
		}
		controleDeVagas.adicionaCarro(carro, new Date());
	}

	public void entrouUmCarro(Carro carro, Date hora) throws EstacionamentoFechadoException, EstacionamentoLotadoException {
		if (!periodoDeFuncionamento.isHoraDentroDoPeriodo(hora)) {
			throw new EstacionamentoFechadoException();
		}
		if (controleDeVagas.existe(carro)) {
			throw new IllegalStateException();
		}
		controleDeVagas.adicionaCarro(carro, hora);
	}

	public int saiuUmCarro(Carro carro, Date horaDeSaida) throws EstacionamentoFechadoException, CarroFantasmaException {
		Vaga vaga = controleDeVagas.buscarCarro(carro);

		if (!periodoDeFuncionamento.isHoraDentroDoPeriodo(horaDeSaida)) {
			throw new EstacionamentoFechadoException();
		}

		Date horaDeEntrada = vaga.getHoraDeEntrada();
		Periodo periodo;
		try {
			periodo = new Periodo(horaDeEntrada, horaDeSaida);
		} catch (IllegalStateException e) {
			throw new CarroFantasmaException();
		}
		controleDeVagas.removeCarro(carro);
		return periodo.calculaDiferencaEmHoras();
	}

	public int carroEstaSaindo(Carro carro, Date horaDeSaida) throws EstacionamentoFechadoException, CarroFantasmaException {
		return tabelaDePrecos.calculaValorDoEstacionamento(saiuUmCarro(carro, horaDeSaida));
	}
}
