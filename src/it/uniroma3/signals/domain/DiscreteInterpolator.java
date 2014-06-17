package it.uniroma3.signals.domain;


/**
 * Questa interfaccia permette di creare delle classi anonime che implementano il metodo calc.
 * In questo modo è possibile definire un'operazione nei segnali ed applicarla ad ogni valore
 * utilizzando un unico ciclo. In questo modo si ottimizzano le performance.
 */
public interface DiscreteInterpolator<T> {
	public T calc(T oldValue, int index);
}
