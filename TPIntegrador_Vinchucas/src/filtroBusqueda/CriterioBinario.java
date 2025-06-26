package filtroBusqueda;

public abstract class CriterioBinario implements Criterio{
	
	protected Criterio c1;
	protected Criterio c2;
	
	public Criterio getC1() {
		return c1;
	}

	public void setC1(Criterio c1) {
		this.c1 = c1;
	}

	public Criterio getC2() {
		return c2;
	}

	public void setC2(Criterio c2) {
		this.c2 = c2;
	}
}