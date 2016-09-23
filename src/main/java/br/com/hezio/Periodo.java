package br.com.hezio;

import java.util.Calendar;
import java.util.Date;

public class Periodo {
	private Date inicio;
	private Date fim;

	public Periodo(Date inicio, Date fim) {
		if (fim.before(inicio)) {
			throw new IllegalStateException();
		}
		this.inicio = inicio;
		this.fim = fim;
	}

	public int calculaDiferencaEmHoras() {
		Calendar calendarHoraDeEntrada = Calendar.getInstance();
		calendarHoraDeEntrada.setTime(inicio);
		Calendar calendarHoraDeSaida = Calendar.getInstance();
		calendarHoraDeSaida.setTime(fim);
		return calendarHoraDeSaida.get(Calendar.HOUR_OF_DAY) - calendarHoraDeEntrada.get(Calendar.HOUR_OF_DAY);
	}

	public boolean isHoraDentroDoPeriodo(Date hora) {
		Calendar horaSaida = Calendar.getInstance();
		horaSaida.setTime(hora);
		Calendar horaFim = Calendar.getInstance();
		horaFim.setTime(fim);
		horaSaida.set(Calendar.DAY_OF_MONTH, horaFim.get(Calendar.DAY_OF_MONTH));

		return horaSaida.getTime().after(inicio) && horaSaida.getTime().before(fim);
	}
}
