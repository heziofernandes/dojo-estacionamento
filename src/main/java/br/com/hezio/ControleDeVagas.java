package br.com.hezio;

import java.util.Date;

public class ControleDeVagas {
	private Vaga[] vagas;

	public ControleDeVagas(int totalDeVagas) {
		vagas = new Vaga[totalDeVagas];
	}

	public int getVagasDisponiveis() {
		int vagasDisponiveis = 0;
		for (Vaga vaga : vagas) {
			if (vaga == null) {
				vagasDisponiveis++;
			}
		}
		return vagasDisponiveis;
	}

	public boolean existe(Carro carro) {
		for (Vaga vaga : vagas) {
			if (vaga != null && vaga.estaNessaVaga(carro)) {
				return true;
			}
		}
		return false;
	}

	public void adicionaCarro(Carro carro, Date horaDeEntrada) throws EstacionamentoLotadoException {
		vagas[buscarVagaDisponivel()] = new Vaga(carro, horaDeEntrada);
	}

	private int buscarVagaDisponivel() throws EstacionamentoLotadoException {
		for (int numeroDaVaga = 0; numeroDaVaga < vagas.length; numeroDaVaga++) {
			if (vagas[numeroDaVaga] == null) {
				return numeroDaVaga;
			}
		}
		throw new EstacionamentoLotadoException();
	}

	public void removeCarro(Carro carro) throws CarroFantasmaException {
		int vagaDoCarro = buscarVagaDoCarro(carro);
		vagas[vagaDoCarro] = null;
	}

	public boolean carroExisteEPodeSair(Carro carro, Date horaDeSaida) {
		for (Vaga vaga : vagas) {
			if (vaga != null && vaga.estaNessaVaga(carro)) {
				return vaga.getHoraDeEntrada().before(horaDeSaida);
			}
		}
		return false;
	}

	public Vaga buscarCarro(Carro carro) throws CarroFantasmaException {
		int vagaDoCarro = buscarVagaDoCarro(carro);
		return vagas[vagaDoCarro];
	}

	private int buscarVagaDoCarro(Carro carro) throws CarroFantasmaException {
		for (int index = 0; index < vagas.length; index++) {
			Vaga vaga = vagas[index];
			if (vaga != null && vaga.estaNessaVaga(carro)) {
				return index;
			}
		}
		throw new CarroFantasmaException();
	}
}
