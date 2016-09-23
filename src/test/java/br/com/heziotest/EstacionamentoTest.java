package br.com.heziotest;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import br.com.hezio.Carro;
import br.com.hezio.CarroDuplicadoException;
import br.com.hezio.CarroFantasmaException;
import br.com.hezio.Estacionamento;
import br.com.hezio.EstacionamentoFechadoException;
import br.com.hezio.EstacionamentoLotadoException;

public class EstacionamentoTest {

	Estacionamento estacionamento;

	@Before
	public void criarEstacionamento() {
		estacionamento = new Estacionamento();
	}

	@Test
	public void deveTer500VagasDisponiveis() {
		assertEquals(500, estacionamento.getVagasDisponiveis());
	}

	@Test
	public void entrouUmCarro() throws CarroDuplicadoException, EstacionamentoLotadoException {
		estacionamento.entrouUmCarro(gol());
		assertEquals(499, estacionamento.getVagasDisponiveis());
	}

	@Test
	public void saiuUmCarro() throws EstacionamentoLotadoException, EstacionamentoFechadoException, CarroFantasmaException {
		estacionamento.entrouUmCarro(gol(), as(10));
		estacionamento.saiuUmCarro(gol(), as(12));
		assertEquals(500, estacionamento.getVagasDisponiveis());
	}

	@Test(expected = CarroDuplicadoException.class)
	public void naoPodeEntrarCarroDuplicado() throws CarroDuplicadoException, EstacionamentoLotadoException {
		estacionamento.entrouUmCarro(gol());
		estacionamento.entrouUmCarro(gol());

	}

	@Test(expected = EstacionamentoLotadoException.class)
	public void estacionamentoLotado() throws CarroDuplicadoException, EstacionamentoLotadoException {
		for (int i = 0; i < 500; i++) {
			estacionamento.entrouUmCarro(new Carro("A" + i));
		}
		estacionamento.entrouUmCarro(gol());
	}

	@Test(expected = CarroFantasmaException.class)
	public void naoPodeSairCarroQueNaoExiste() throws CarroDuplicadoException, EstacionamentoLotadoException, EstacionamentoFechadoException,
			CarroFantasmaException {
		estacionamento.entrouUmCarro(palio());
		estacionamento.saiuUmCarro(gol(), as(14));
	}

	@Test(expected = EstacionamentoFechadoException.class)
	public void tentouEntrarUmCarroForadoHorario() throws EstacionamentoFechadoException, EstacionamentoLotadoException {
		estacionamento.entrouUmCarro(gol(), as(6));
	}

	private Date as(int horario) {
		Calendar agora = Calendar.getInstance();
		agora.set(Calendar.HOUR_OF_DAY, horario);
		return agora.getTime();
	}

	@Test(expected = EstacionamentoFechadoException.class)
	public void tentouSairUmCarroForaDoHorario() throws EstacionamentoFechadoException, CarroFantasmaException, EstacionamentoLotadoException {
		Calendar hora = Calendar.getInstance();
		hora.set(Calendar.HOUR_OF_DAY, 20);
		Carro gol = gol();
		estacionamento.entrouUmCarro(gol, hora.getTime());

		hora.set(Calendar.HOUR_OF_DAY, 23);
		estacionamento.saiuUmCarro(gol, hora.getTime());

	}

	@Test(expected = CarroFantasmaException.class)
	public void umCarroNaoPodeSairAntesDeTerEntrado() throws EstacionamentoFechadoException, CarroFantasmaException, EstacionamentoLotadoException {
		Carro gol = gol();
		Calendar hora = Calendar.getInstance();
		hora.set(Calendar.HOUR_OF_DAY, 19);
		estacionamento.entrouUmCarro(gol, hora.getTime());

		hora.set(Calendar.HOUR_OF_DAY, 18);
		estacionamento.saiuUmCarro(gol, hora.getTime());

	}

	@Test
	public void umCarroPermaneceuDurante3HorasNoEstacionamento() throws EstacionamentoFechadoException, CarroFantasmaException,
			EstacionamentoLotadoException {
		Carro gol = gol();
		Calendar hora = Calendar.getInstance();
		hora.set(Calendar.HOUR_OF_DAY, 10);
		estacionamento.entrouUmCarro(gol, hora.getTime());

		hora.set(Calendar.HOUR_OF_DAY, 13);
		int numeroDeHorasQuePermaneceu = estacionamento.saiuUmCarro(gol, hora.getTime());
		assertEquals(3, numeroDeHorasQuePermaneceu);
	}

	@Test
	public void umCarroPermaneceuPor3HorasNoEstacionamentoEDevePagar4Reais() throws EstacionamentoFechadoException, EstacionamentoLotadoException,
			CarroFantasmaException {
		Carro gol = gol();
		estacionamento.entrouUmCarro(gol, as(13));
		int valorTotalAPagar = estacionamento.carroEstaSaindo(gol, as(16));
		assertEquals(4, valorTotalAPagar);

	}

	@Test
	public void umCarroPermaneceuPor4HorasNoEstacionamentoEDevePagar5Reais() throws EstacionamentoFechadoException, EstacionamentoLotadoException,
			CarroFantasmaException {
		Carro gol = gol();
		estacionamento.entrouUmCarro(gol, as(13));
		int valorTotalAPagar = estacionamento.carroEstaSaindo(gol, as(17));
		assertEquals(5, valorTotalAPagar);

	}

	@Test
	public void umCarroPermaneceuPor10HorasNoEstacionamentoEDevePagar20Reais() throws EstacionamentoFechadoException, EstacionamentoLotadoException,
			CarroFantasmaException {
		Carro gol = gol();
		estacionamento.entrouUmCarro(gol, as(11));
		int valorTotalAPagar = estacionamento.carroEstaSaindo(gol, as(21));
		assertEquals(20, valorTotalAPagar);

	}

	@Test
	public void umCarroPermaneceuPor1DiaNoEstacionamentoEDevePagar20Reais() throws EstacionamentoFechadoException, EstacionamentoLotadoException,
			CarroFantasmaException {
		Carro gol = gol();
		estacionamento.entrouUmCarro(gol, as(12));

		Calendar horaDeSaida = Calendar.getInstance();
		horaDeSaida.set(Calendar.HOUR_OF_DAY, 10);
		horaDeSaida.add(Calendar.DAY_OF_MONTH, 1);

		int valorTotalAPagar = estacionamento.carroEstaSaindo(gol, horaDeSaida.getTime());
		assertEquals(20, valorTotalAPagar);

	}

	private Carro palio() {
		return new Carro("FGK-9090");
	}

	private Carro gol() {
		return new Carro("MGQ-3267");
	}
}